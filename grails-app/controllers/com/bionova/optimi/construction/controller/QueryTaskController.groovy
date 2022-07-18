/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryApplyFilterOnCriteria


/**
 * @author Pasi-Markus Mäkelä
 */
class QueryTaskController extends ExceptionHandlerController {

    def indicatorService
    def entityService
    def queryService
    def taskService
    def datasetService
    def optimiSecurityService
    def errorMessageUtil

    def index() {
        String token = params.token
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String taskId = params.taskId
        Boolean tokenOk = optimiSecurityService.tokenOk(token)

        if (!tokenOk) {
            session?.removeAttribute(SessionAttribute.TOKEN_OK.toString())
            redirect controller: "index"
        } else {
            def task = taskService.getTaskByIdAndToken(taskId, token)

            if (!task) {
                flash.fadeErrorAlert = message(code: "task.invalid_task")
                chain(controller: "index", action: "message")
            } else {
                if(task.deadline) {
                    Date currentDate = new Date()
                    int deadlineDuration = task.deadline - currentDate
                    if(deadlineDuration >= 0){
                        session?.setAttribute(SessionAttribute.TOKEN_OK.toString(), "true")

                        if (entityId && indicatorId && queryId) {
                            redirect action: "form", params: [entityId: entityId, indicatorId: indicatorId, queryId: queryId, token: token, deadline: task.deadline.toString()]
                        } else if (taskId) {
                            redirect action: "taskform", params: [taskId: taskId, token: token]
                        } else {
                            redirect controller: "index"
                        }
                    } else {
                        flash.fadeErrorAlert = message(code: "task.invalid_task")
                        chain(controller: "index", action: "message")
                    }
                } else {
                    flash.fadeErrorAlert = message(code: "task.invalid_task")
                    chain(controller: "index", action: "message")
                }


            }
        }
    }

    def form() {
        def token = params.token
        def deadline = params.deadline ? params.deadline :""
        Indicator indicator = indicatorService.getIndicatorByIndicatorIdAndToken(params.indicatorId, token, true)
        Entity entity = entityService.getEntityByIdAndToken(params.entityId, token)
        def queries = queryService.getQueriesByIndicatorAndEntity(indicator, entity)
        def todoQueries = []
        def readyQueries = []

        queries?.each { query ->
            if (entity?.queryReadyPerIndicator?.get(indicator?.indicatorId)?.get(query.queryId) || entity.queryReady?.get(query.queryId)) {
                readyQueries.add(query)
            } else {
                todoQueries.add(query)
            }
        }
        Query query = queryService.getQueryByIndicatorAndQueryIdAndToken(indicator, params.queryId, token, entity) as Query
        QueryApplyFilterOnCriteria filterOnCriteria = indicator.indicatorQueries?.find({query.queryId.equals(it.queryId) && it.applyFilterOnCriteria})?.applyFilterOnCriteria
        String sessionFilterOnCriteria = session?.getAttribute("filterOnCriteria")

        if (filterOnCriteria) {
            if (sessionFilterOnCriteria && !filterOnCriteria.choices?.contains(sessionFilterOnCriteria)) {
                session?.removeAttribute("filterOnCriteria")
            }

            if (filterOnCriteria.mandatoryFilter && !(params.filterOnCriteria || sessionFilterOnCriteria)) {
                flash.fadeErrorAlert = message(code: "query.mandatory_filter")
            }

            if (params.filterOnCriteria) {
                session?.setAttribute("filterOnCriteria", params.filterOnCriteria)
            }
        }  else {
            session?.removeAttribute("filterOnCriteria")
        }
        def entityName = entity?.operatingPeriodAndName ? entity?.operatingPeriodAndName : entity?.name
        Entity parentEntity = entity?.getParentById()
        def showMandatory = !query?.disableHighlight
        def modifiable = true
        def queryTask = true
        def allowMonthlyData = entity.monthlyDataLicensed && query?.allowMonthlyData && Constants.EntityClass.OPERATING_PERIOD.toString().equals(entity.entityClass)
        def allowQuarterlyData = entity.quarterlyDataLicensed && query?.allowMonthlyData && Constants.EntityClass.OPERATING_PERIOD.toString().equals(entity.entityClass)
        def monthlyInputEnabled
        def quarterlyInputEnabled

        if (allowMonthlyData || allowQuarterlyData) {
            if (allowQuarterlyData) {
                quarterlyInputEnabled = entity.quarterlyInputEnabledByQuery?.get(query.queryId)
            }

            if (allowMonthlyData && !quarterlyInputEnabled) {
                monthlyInputEnabled = entity.monthlyInputEnabledByQuery?.get(query.queryId)
            }
        }
        if(deadline){
            flash.fadeSuccessAlert = g.message(code: "task.deadline_display", args: [deadline])
        }
        [indicator: indicator, parentEntity: parentEntity, entity: entity, modifiable: modifiable, query: query, readyQueries: readyQueries,
         todoQueries: todoQueries, entityName: entityName, token: token, queryForm: true, showMandatory: showMandatory,
         queryTask: queryTask, filterOnCriteria: filterOnCriteria, quarterlyInputEnabled: quarterlyInputEnabled,
         monthlyInputEnabled: monthlyInputEnabled, allowQuarterlyData: allowQuarterlyData, allowMonthlyData: allowMonthlyData]

        /*
        [indicator       : indicator, parentEntity: parentEntity, entity: entity, query: query, readyQueries: readyQueries, todoQueries: todoQueries, task: task,
                             entityName      : entityName, printable: printable, showMandatory: showMandatory, queryForm: true,
                             showHistory     : showHistory, showOtherDesigns: showOtherDesigns, otherDesigns: otherDesigns,
                             allowMonthlyData: allowMonthlyData, allowQuarterlyData: allowQuarterlyData, monthlyInputEnabled: monthlyInputEnabled, quarterlyInputEnabled: quarterlyInputEnabled,
                             modifiable      : modifiable, toCopyEntities: toCopyEntities, filterOnCriteria: filterOnCriteria, unitSystem: unitSystem]
         */
    }

    def taskform() {
        def taskId = params.taskId
        def token = params.token
        def task = taskService.getTaskByIdAndToken(taskId, token)
        [task: task, token: token]
    }

    def savetask() {
        def taskId = params.id
        def token = params.token
        def task = taskService.getTaskByIdAndToken(taskId, token)

        if (task) {
            task.properties = params

            if (task.validate()) {
                taskService.updateTask(task, token)
                flash.fadeSuccessAlert = g.message(code: "task.updated")
            } else {
                flash.fadeErrorAlert = renderErrors(bean: task)
            }
        }
        redirect action: "taskform", params: [taskId: taskId, token: token]
    }

    def save() {
        session?.removeAttribute("filterOnCriteria")
        def indicatorId = params.indicatorId
        def entityId = params.entityId
        def queryId = params.queryId
        def token = params.token
        Entity entity

        try {
            entity = datasetService.saveDatasetsFromParameters(params, request, entityId, null, null)
            entityService.calculateEntity(entity, indicatorId, queryId, false, false)
            entity.merge(flush: true, failOnError: true)
        } catch (Exception e) {
            log.error("Error in saving datasets: ${e}")
            flash.fadeErrorAlert = message(code: "entity.wrong_image_type")
        }

        if (entity?.queryReadyPerIndicator?.get(indicatorId)?.get(queryId) || entity?.queryReady?.get(queryId)) {
            flash.fadeSuccessAlert = g.message(code: 'query.ready_in_task')
        } else {
            flash.warningAlert = g.message(code: 'query.not_ready')
        }
        chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, queryId: queryId, token: token])
    }

    def leave() {
        flash.fadeSuccessAlert = message(code: "querytask.leave_message")
        session?.removeAttribute(SessionAttribute.TOKEN_OK.toString())
        redirect controller: "index", action: "message"
    }
}
