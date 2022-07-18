package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.data.ResourceCache
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.bson.conversions.Bson
import org.springframework.context.i18n.LocaleContextHolder

import java.text.DecimalFormat
import java.text.NumberFormat

@Transactional
class CalculationMessageService {

    UserService userService
    def messageSource
    ValueReferenceService valueReferenceService

    @CompileStatic
    void generateEntitySaveMessage(Entity entity, Double currentTotal, CalculationRule displayRule, Entity parentEntity,
                                   ResourceCache resourceCache, Indicator indicator) {
        String message = createEntitySaveMessage(entity, currentTotal, displayRule, parentEntity, resourceCache, indicator)
        persistMessageForIndicator(entity, indicator, message)
    }

    /**
     * Shows the difference of carbon impacts to the user compared to last calculation and current one
     * @param entity
     * @param currentTotal
     * @param displayRule
     * @param parentEntity
     * @param resourceCache
     * @param indicator
     * @return
     */
    String createEntitySaveMessage(Entity entity, Double currentTotal, CalculationRule displayRule, Entity parentEntity,
                                   ResourceCache resourceCache, Indicator indicator) {
        String message = ''
        Double previousTotal = getPreviousTotalForIndicator(entity, indicator)
        Locale locale = LocaleContextHolder.getLocale()

        if (previousTotal && currentTotal && displayRule && !Constants.EntityClass.MATERIAL_SPECIFIER.type.toString().equalsIgnoreCase(entity.entityClass)) {
            User user = userService.getCurrentUser()
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
            decimalFormat.applyPattern('#,###,###')

            if (previousTotal == currentTotal) {
                message = "<p class=\"entitySaveMessage\">${messageSource.getMessage("query.saved.impactsUnchanged", null, locale)}</p>"
            } else if (previousTotal > currentTotal) {
                Double percentageChange = (100 - (currentTotal / previousTotal * 100)).round(3)
                Object[] args = [displayRule.localizedName, decimalFormat.format(previousTotal - currentTotal), displayRule.unitAsValueReference ?
                        valueReferenceService.getValueForEntity(displayRule.unitAsValueReference, parentEntity, Boolean.FALSE, Boolean.TRUE, resourceCache) :
                        displayRule.localizedUnit]
                message = "<p class=\"entitySaveMessage\">${messageSource.getMessage("query.saved.impactsReduced", args, locale)}</p>&nbsp;<p style=\"font-weight: bold !important; display:inline; color: green\">-${percentageChange} %</p><i style=\"margin-left: 4px; font-size: 1.2em !important; color: green\" class=\"fa fa-caret-down\" aria-hidden=\"true\"></i>"
            } else if (previousTotal < currentTotal) {
                Double percentageChange = Math.abs((100 - (currentTotal / previousTotal * 100)).round(3))
                Object[] args = [displayRule.localizedName, decimalFormat.format(currentTotal - previousTotal), displayRule.unitAsValueReference ?
                        valueReferenceService.getValueForEntity(displayRule.unitAsValueReference, parentEntity, Boolean.FALSE, Boolean.TRUE, resourceCache) :
                        displayRule.localizedUnit]
                message = "<p class=\"entitySaveMessage\">${messageSource.getMessage("query.saved.impactsIncreased", args, locale)}</p>&nbsp;<p style=\"font-weight: bold !important; display:inline; color: red\">+${percentageChange} %</p><i style=\"margin-left: 4px; font-size: 1.2em !important; color: red\" class=\"fa fa-caret-up\" aria-hidden=\"true\"></i>"
            }
        } else {
            message = "<p class=\"entitySaveMessage\">${messageSource.getMessage("query.saved.impactsIncalculable", null, locale)}</p>"
        }

        return message
    }

    @CompileStatic
    private static void persistMessageForIndicator(Entity entity, Indicator indicator, String message) {
        if (entity && indicator?.indicatorId && message != null) {
            if (entity.saveMessageByIndicator == null) {
                entity.saveMessageByIndicator = new HashMap<String, String>()
            }

            entity.saveMessageByIndicator.put(indicator.indicatorId, message)
        }
    }

    @CompileStatic
    String getEntityMessageAndRemoveFromEntity(Entity entity, String indicatorId) {
        String msg = getCurrentMessageForIndicator(entity, indicatorId)

        if (msg) {
            removeMessageForIndicator(entity, indicatorId)
        }

        return msg
    }

    @CompileStatic
    String getCurrentMessageForIndicator(Entity entity, String indicatorId) {
        String msg = ''

        if (entity?.saveMessageByIndicator && indicatorId) {
            msg = entity.saveMessageByIndicator.get(indicatorId)
        }

        return msg
    }

    @CompileStatic
    private static void removeMessageForIndicator(Entity entity, String indicatorId) {
        if (entity?.saveMessageByIndicator && indicatorId) {
            entity.saveMessageByIndicator.remove(indicatorId)
            if (entity.id) {
                Bson query = Filters.eq("_id", entity.id)
                Bson update = Updates.unset("saveMessageByIndicator.${indicatorId}")
                Entity.collection.updateOne(query, update)
            }
        }
    }

    @CompileStatic
    void persistPreviousTotal(Entity entity, String indicatorId, Double previousTotal) {
        if (entity && indicatorId && previousTotal != null) {
            if (entity.previousTotalByIndicator == null) {
                entity.previousTotalByIndicator = new HashMap<String, Double>()
            }

            entity.previousTotalByIndicator.put(indicatorId, previousTotal)
        }
    }

    @CompileStatic
    private static Double getPreviousTotalForIndicator(Entity entity, Indicator indicator) {
        return entity?.previousTotalByIndicator?.get(indicator?.indicatorId)
    }
}
