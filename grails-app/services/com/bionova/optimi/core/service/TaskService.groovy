/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Task
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserRegistration
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.DateUtil
import grails.plugin.springsecurity.annotation.Secured
import org.grails.datastore.mapping.core.OptimisticLockingException
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.context.i18n.LocaleContextHolder
import java.text.SimpleDateFormat
import java.time.Duration

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 *
 */
class TaskService {
    static final Date currentDate = new Date()

    def userService
    def entityService
    def optimiSecurityService
    def loggerUtil
    def messageSource
    def optimiMailService

    @Secured(["ROLE_AUTHENTICATED"])
    def createTempUserForTask(Task task) {
        def email = task?.email
        UserRegistration userRegistration

        if (email) {
            email = email.trim().toLowerCase()
            userRegistration = UserRegistration.findByUsername(email)

            if (userRegistration) {
                userRegistration.delete(flush: true)
            }
            userRegistration = new UserRegistration(username: email)
            userRegistration = userRegistration.save(failOnError: true, flush: true)
        }
        return userRegistration
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def createTask(entityId, Task task) {
        try {
            if (entityId) {
                task.targetEntityId = entityId
            }
            task.lastUpdater = userService.getCurrentUser(Boolean.TRUE)
            Date date = new Date()
            task.dateCreated = date
            task.lastUpdated = date

            if (!task.hasErrors()) {
                task = task.save(failOnError: true, flush: true)
            }

            if (entityId) {
                Entity entity = entityService.getEntityById(entityId)

                if (entity) {
                    if (entity.targetedTaskIds) {
                        entity.targetedTaskIds.add(task.id)
                    } else {
                        entity.targetedTaskIds = [task.id]
                    }
                    entity.merge(flush: true)
                }
            }
        } catch (OptimisticLockingException e) {
            loggerUtil.error(log, "Error in creating task " + task.id + ": " + e)
        }
        return task
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def updateTask(Task task) {
        try {
            task.lastUpdater = userService.getCurrentUser(Boolean.TRUE)

            if (!task.hasErrors()) {
                task = task.merge(flush: true)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in updating task " + task.id + ": " + e)
        }
        return task
    }

    def updateTask(Task task, String token) {
        if (optimiSecurityService.tokenOk(token)) {
            try {
                if (!task.hasErrors()) {
                    task = task.merge(flush: true)
                }
            } catch (Exception e) {
                loggerUtil.error(log, "Error in updating task " + task.id + ": " + e)
            }
            return task
        } else {
            throw new SecurityException("Trying to access without invalid token: " + token)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getTaskById(taskId) {
        if (taskId) {
            return Task.get(taskId)
        }
    }

    def getTaskByIdAndToken(String taskId, String token) {
        if (optimiSecurityService.tokenOk(token)) {
            if (taskId) {
                return Task.get(taskId)
            }
        } else {
            throw new SecurityException("Trying to access without invalid token: " + token)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getTaskByEntityAndQueryId(Entity entity, queryId) {
        Task task

        if (entity && queryId) {
            def dbObject = Task.collection.findOne([targetEntityId: entity.id.toString(), targetQueryId: queryId])

            if (!dbObject) {
                dbObject = Task.collection.findOne([targetEntityId: entity.id, targetQueryId: queryId])
            }

            if (dbObject) {
                task = dbObject as Task
            }
        }
        return task
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getTaskByEntityIdAndQueryId(entityId, queryId) {
        def entity = Entity.get(entityId)
        getTaskByEntityAndQueryId(entity, queryId)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getTasksByDeleguer(User deleguer) {
        Task.findAllByDeleguer(deleguer)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def deleteTask(Task task) {
        if (task) {
            if (task.targetEntityId) {
                Entity entity = entityService.getEntityById(task.targetEntityId)
                entity.targetedTaskIds?.remove(task.id?.toString())
                entity.merge(flush: true)
            }
            task.delete(flush: true)
        }
    }

    def getTasksByEntities(List<Entity> entities) {
        //loggerUtil.info(log, "Start of getting tasks")
        def tasks = []
        entities = entities?.findAll({ Entity e -> e.targetedTaskIds })
        def taskIds = []

        entities?.each { Entity e ->
            taskIds.addAll(e.targetedTaskIds)
        }

        if (taskIds) {
            taskIds = taskIds.unique()
            tasks = Task.getAll(DomainObjectUtil.stringsToObjectIds(taskIds))
        }
        //loggerUtil.info(log, "End of getting tasks")
        return tasks?.sort({ it.deadline })
    }

    def getTasksByUser(User user) {
        def tasks = []
        tasks.addAll(getTasksByDeleguer(user))
        return tasks
    }

    void taskReminderJob() {
        def tasks = getTasksToRemind()

        if (tasks && !tasks.isEmpty()) {
            sendReminderMails(tasks)
        }
    }

    def getTasksToRemind() {
        def dateAsStr = new Date().format("dd.MM.yyyy")
        def date = new SimpleDateFormat("dd.MM.yyyy").parse(dateAsStr)
        BuildableCriteria criteria = Task.createCriteria()
        List<Task> tasks = criteria.list {
            le('deadline', date)
            and {
                eq('autoReminder', Boolean.TRUE)
            }
            and {
                ne('completed', Boolean.TRUE)
            }
        }

        if (tasks && !tasks.isEmpty()) {
            tasks = tasks.findAll({ Task t -> !t.remindersSent || (t.remindersSent && t.remindersSent < t.reminderAmount) })

            if (tasks) {
                Date currentDate = new Date()
                tasks = tasks.findAll({
                    !it.lastReminder || (it.lastReminder && it.reminderFrequency && Duration.between(DateUtil.dateToLocalDatetime(it.lastReminder), DateUtil.dateToLocalDatetime(currentDate)).toDays() >= it.reminderFrequency.intValue())
                })
            }
        }
        return tasks
    }

    private sendReminderMails(List<Task> tasks) {
        if (tasks) {
            def emailFrom = messageSource.getMessage("email.support", null, LocaleContextHolder.getLocale())
            def replyToAddress = messageSource.getMessage("email.support", null, LocaleContextHolder.getLocale())
            def emailSubject = optimiMailService.addHostnameToSubject(messageSource.getMessage("task.reminder_mail.subject", null, LocaleContextHolder.getLocale()))
            def emailBody

            tasks?.each { Task task ->
                emailBody = createReminderMailBody(task)

                if (emailBody) {
                    String ccAddress = !task.remindersSent && task.ccToDeleguer ? task.deleguer?.username : ''

                    try {
                        if (ccAddress) {
                            optimiMailService.sendMail({
                                to task.email
                                from emailFrom
                                cc ccAddress
                                replyTo replyToAddress
                                subject emailSubject
                                html emailBody
                            }, task.email)
                        } else {
                            optimiMailService.sendMail({
                                to task.email
                                from emailFrom
                                replyTo replyToAddress
                                subject emailSubject
                                html emailBody
                            }, task.email)
                        }
                        task.remindersSent = task.remindersSent ? task.remindersSent + 1 : 1
                        task.lastReminder = new Date()
                        task.merge(flush: true)
                    } catch (Exception e) {
                        log.error("Error in sending task reminder mail: ${e}")
                    }
                } else {
                    log.error("Could not get taskLink for task " + task + " when trying to send reminder")
                }
            }
        }
    }

    private getLocaleMessage(String key, def args = null,  Locale locale) {
        String value = key
        try {
            value = messageSource.getMessage(value, args, locale)
        } catch (Exception e) {
            log.error("${e}")
        }
        return value
    }

    private String createReminderMailBody(Task task) {
        def link = task?.taskLink
        def mailBody

        if (link) {
            if (task?.targetEntity && task?.targetIndicatorId && task?.targetQueryId) {
                mailBody = getLocaleMessage("task.reminder_mail.body", [task.deleguer.name, task.targetEntity?.name, link].toArray(),  LocaleContextHolder.getLocale())
            } else {
                mailBody = getLocaleMessage("task.reminder_mail.body.simple", [task.deleguer.name, link].toArray(), LocaleContextHolder.getLocale())
            }
        }
        return mailBody
    }
}
