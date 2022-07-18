package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.util.DomainObjectUtil

/**
 * @author Pasi-Markus Mäkelä
 */
class ChannelFeatureController extends ExceptionHandlerController {

    def channelFeatureService
    def optimiResourceService
    def entityService
    def userService

    def list() {
        if (userService.getSuperUser(userService.getCurrentUser())) {
            [channelFeatures: channelFeatureService.getAllChannelFeatures()]
        } else {
            redirect controller: "main"
        }
    }

    def setDefault() {
        if (params.id) {
            ChannelFeature channelFeature = channelFeatureService.getChannelFeatureById(params.id)
            channelFeature = channelFeatureService.setChannelAsDefault(channelFeature)

            if (channelFeature?.defaultChannel) {
                flash.fadeSuccessAlert = message(code: "admin.channelFeature.set_default.ok", args: [channelFeature.name])
            } else {
                flash.fadeErrorAlert = message(code: "admin.channelFeature.defaultChannel.unqiue")
            }
        }
        redirect action: "list"
    }

    def removeLocaleAttribute() {
        String attribute = params.attribute
        String locale = params.locale
        String id = params.id

        if (id && attribute && locale) {
            ChannelFeature channelFeature = channelFeatureService.getChannelFeatureById(id)

            if(channelFeature) {
                def object = DomainObjectUtil.callGetterByAttributeName(attribute, channelFeature)

                try {
                    ((Map) object).remove(locale)
                    channelFeatureService.saveChannelFeature(channelFeature)
                } catch (Exception e) {

                }
            }
        }
        redirect(action: "form", id: id)
    }

    def save(ChannelFeature channelFeature) {
        if (params.addPortfolioId) {
            if (channelFeature.portfolioIds) {
                channelFeature.portfolioIds.add(params.addPortfolioId)
            } else {
                channelFeature.portfolioIds = [params.addPortfolioId]
            }
        } else if (params.removePortfolioId) {
            channelFeature.portfolioIds.remove(params.removePortfolioId)
        } else {
            List<String> languagesForChannel = params.list('languagesForChannel')
            channelFeature.languagesForChannel?.clear()
            if(languagesForChannel && !languagesForChannel.contains(Constants.DEFAULT_LOCALE.toUpperCase())){
                languagesForChannel += Constants.DEFAULT_LOCALE.toUpperCase()
            } else if(!languagesForChannel){
                languagesForChannel = [Constants.DEFAULT_LOCALE.toUpperCase()]
            }
            channelFeature.languagesForChannel = languagesForChannel
        }

        if (channelFeature?.validate()) {
            channelFeature = channelFeatureService.saveChannelFeature(channelFeature)
            flash.fadeSuccessAlert = message(code: "admin.channelFeature.save.ok")
            redirect action: "form", id: channelFeature.id
        } else {
            flash.errorAlert = renderErrors(bean: channelFeature)
            chain(action: "form", model: [channelFeature: channelFeature])
        }
    }

    def form() {
        ChannelFeature channelFeature
        List<Portfolio> portfolios
        def entityClassResources = optimiResourceService.getParentEntityClasses()

        if (params.id) {
            channelFeature = channelFeatureService.getChannelFeatureById(params.id)
        } else if (chainModel?.channelFeature) {
            channelFeature = chainModel.channelFeature
        }

        if (channelFeature) {
            List<Entity> entities = entityService.getAllEntities(["portfolio"], false).findAll{Entity e -> e.isPortfolio}

            portfolios = Portfolio.withCriteria{
                'in'('entityId', entities.collect{it.id.toString()})

                if(channelFeature.addableEntityClasses) {
                    or {
                        isNull('entityClass')
                        'in'('entityClass', channelFeature.addableEntityClasses)
                    }
                }

                isNotNull('entityName')

            }

            if (channelFeature.portfolioIds && portfolios && !portfolios.isEmpty()) {
                portfolios.removeAll({DomainObjectUtil.stringsToObjectIds(channelFeature.portfolioIds).contains(it.id)})
            }
        }

        List<Resource> systemLocales = optimiResourceService.getSystemLocales()
        [channelFeature: channelFeature, entityClassResources: entityClassResources, systemLocales: systemLocales, portfolios: portfolios]
    }

    def remove() {
        if (params.id) {
            channelFeatureService.removeChannelFeature(params.id)
            flash.fadeSuccessAlert = message(code: "admin.channelFeature.deleted")
        }
        redirect action: "list"
    }

    def applyChannel() {
        String channelToken = params.token
        boolean ok = channelFeatureService.applyChannelFeature(session, channelToken)

        if (ok) {
            flash.fadeSuccessAlert = message(code: "admin.channelFeature.apply.ok")
        } else {
            flash.fadeErrorAlert = message(code: "admin.channelFeature.apply.error")
        }
        redirect action: "list"
    }
}
