/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import org.springframework.beans.factory.annotation.Autowired

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Cristina Pasca / Software developer
 * @author Pasi-Markus Mäkelä
 *
 */
class TaskController extends AbstractDomainObjectController {

    def taskService
    def entityService
    def queryService
    def localeResolverUtil

    @Autowired
    EmailConfiguration emailConfiguration

    /**
     * Retrieves the list of all existing tasks for current user.
     */
    def list() {
        User user = (User) userService.getCurrentUser()
        def tasks = taskService.getTasksByUser(user)
        [tasks: tasks]
    }

    /**
     * Returns the entity with given id.
     */
    def show() {
        def task = taskService.getTaskById(params.id)
        if (!task) {
            redirect(action: "list")
            return
        }
        [task: task]
    }

    def form() {
        Task task
        Entity entity
        Entity childEntity
        def entityId
        def childEntityId
        def model = [:]

        if (params.id) {
            task = taskService.getTaskById(params.id)
            entity = task?.targetEntity

            if (entity && entity.parentEntityId) {
                childEntity = entity
                entity = entity.getParentById()
            }
            model.put("task", task)
        } else if (params.userId) {
            task = new Task()
            task.email = userService.getUserById(params.userId)?.username
            model.put("task", task)
        }

        if (!params.id) {
            entityId = params.entityId
            childEntityId = params.childEntityId

            if (entityId) {
                entity = entityService.getEntityById(entityId, session)
            }

            if (entity && childEntityId) {
                childEntity = entityService.getChildEntity(entity, childEntityId)
            }
        }
        def queryId = params.queryId
        Query query

        if (queryId) {
            query = queryService.getQueryByQueryId(queryId, true)
        }
        def indicatorId = params.indicatorId
        model.put("entity", entity)
        model.put("childEntity", childEntity)
        model.put("query", query)
        model.put("indicatorId", indicatorId)
        def frequencies = [1: message(code: 'task.frequency.daily'), 2: message(code: 'task.frequency.weekly')]
        model.put("reminderFrequencies", frequencies)
        return model
    }

    def saveInternal() {
        Task task
        def entityId = params.entityId
        def childEntityId = params.childEntityId
        def queryId = params.queryId
        def indicatorId = params.indicatorId
        Boolean isTaskUpdate = Boolean.FALSE
        if (params.id) {
            task = taskService.getTaskById(params.id)
            task.properties = params
        } else {
            User user = userService.getCurrentUser(Boolean.TRUE) as User
            task = new Task(params)
            task.deleguer = user
            task.targetQueryId = queryId
            task.targetIndicatorId = indicatorId
            task.targetEntityId = entityId
        }

        if (task.validate()) {
            if(task.deadline){
                Date currentDate = new Date()
                int deadlineDuration = task.deadline - currentDate
                if(deadlineDuration >= 0 && deadlineDuration < 61){
                    if (task.id) {
                        isTaskUpdate = Boolean.TRUE
                        taskService.updateTask(task)
                        flash.fadeSuccessAlert = g.message(code: "task.updated")
                    } else {
                        if (childEntityId) {
                            task = taskService.createTask(childEntityId, task)
                        } else {
                            task = taskService.createTask(entityId, task)
                        }
                    }
                    UserRegistration userRegistration = taskService.createTempUserForTask(task)
                    def taskLink = generateTaskLink(task, userRegistration)
                    task.taskLink = taskLink
                    task = taskService.updateTask(task)

                    try {
                        sendTaskEmail(task, userRegistration, isTaskUpdate)
                        flash.fadeSuccessAlert = isTaskUpdate ? g.message(code: "task.edited") : g.message(code: "task.created")
                    } catch (Exception e) {
                        loggerUtil.error(log, "Error in sending email: ${e}")
                        flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
                    }

                    if (childEntityId && queryId && indicatorId) {
                        redirect controller: "query", action: "form", params: [entityId: entityId, childEntityId: childEntityId, queryId: queryId, indicatorId: indicatorId]
                    } else {
                        redirect controller: "entity", action: "show", id: entityId, params: [activeTab: "tasks"]
                    }
                } else {
                    flash.errorAlert = g.message(code: "task.deadline_not_valid")
                    chain action: "form", model: [task: task], params: [entityId: entityId, childEntityId: childEntityId, queryId: queryId, indicatorId: indicatorId]
                }
            }

        } else {
            flash.errorAlert = "<h4 class=\"alert-error\">${g.message(code: "results.incomplete_queries")}</h4>" + renderErrors(bean: task)
            chain action: "form", model: [task: task], params: [entityId: entityId, childEntityId: childEntityId, queryId: queryId, indicatorId: indicatorId]
        }
    }

    private void sendTaskEmail(Task task, UserRegistration userRegistration, Boolean isTaskUpdate) {
        if (task?.email && userRegistration) {
            def emailTo = userRegistration.username
            def emailSubject = isTaskUpdate ? message(code: "task.editted_email_subject") : message(code: "task.email_subject")
            def emailFrom = message(code: "email.support")
            def replyToAddress = message(code: "email.support")
            String codeForEmailBody = isTaskUpdate ? "task_edited.email_body" : "task.email_body"
            def emailBody
            def link = task.taskLink ? task.taskLink : generateTaskLink(task, userRegistration)
            DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy")
            Entity targetEntity = task.targetEntity

            if (targetEntity) {
                Entity parent

                if (targetEntity.parentEntityId) {
                    parent = targetEntity.getParentById()
                }
                emailBody = message(code: codeForEmailBody, args: [
                        task.deleguer.name,
                        targetEntity.operatingPeriodAndName + (parent ? ', ' + parent.name : ''), task.notes ? task.notes : '',
                        task.deadline ? dateFormatter.format(task.deadline) : '', link
                ])
            } else {
                emailBody = message(code: "task.email_body_simple", args: [
                        task.deleguer.name,
                        task.notes ? task.notes : '',
                        task.deadline ? dateFormatter.format(task.deadline) : '',
                        link
                ])
            }
            optimiMailService.sendMail({
                to emailTo
                from emailFrom
                replyTo replyToAddress
                subject emailSubject
                html emailBody
            }, emailTo)
        }
    }

    private String generateTaskLink(Task task, UserRegistration userRegistration) {
        def link
        Locale locale = localeResolverUtil.resolveLocale(session, request, response)
        def lang = locale?.language

        if (task.targetEntity && task.targetIndicatorId && task.targetQueryId) {
            link = createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                    controller: "queryTask", action: "index",
                    params: [taskId: task.id, entityId: task.targetEntity?.id, indicatorId: task.targetIndicatorId, queryId: task.targetQueryId, token: userRegistration?.token, lang: lang])
        } else {
            link = createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                    controller: "queryTask", action: "index", params: [taskId: task.id, token: userRegistration?.token, lang: lang])
        }
        return link
    }

    def remove() {
        def entityId = params.entityId
        def childEntityId = params.childEntityId
        def queryId = params.queryId
        def indicatorId = params.indicatorId
        def task = taskService.getTaskById(params.id)
        taskService.deleteTask(task)
        flash.fadeSuccessAlert = g.message(code: "task.removed")

        if (childEntityId) {
            redirect controller: "query", action: "form", params: [entityId: entityId, childEntityId: childEntityId, queryId: queryId, indicatorId: indicatorId]
        } else if (entityId) {
            redirect controller: "entity", action: "show", id: entityId, params: [activeTab: "tasks"]
        } else {
            redirect action: "list"
        }
    }

    def taskLink() {
        String taskLink

        if (params.taskId) {
            Task task = taskService.getTaskById(params.taskId)
            taskLink = task?.taskLink
        }
        [taskLink: taskLink]
    }

    def cancel() {
        def entityId = params.entityId
        def queryId = params.queryId
        def indicatorId = params.indicatorId
        def fromQuery = params.fromQuery

        if (fromQuery) {
            redirect controller: "query", action: "form", params: [entityId: entityId, queryId: queryId, indicatorId: indicatorId, fromQuery: fromQuery]
        } else {
            redirect controller: "entity", action: "show", id: entityId, params: [activeTab: "tasks"]
        }
    }
}
