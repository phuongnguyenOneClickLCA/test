/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.taglib

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.Constants.ConfigName
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.ComboField
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.EntitySummaryData
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.bionova.optimi.core.domain.mongo.TrialStatus
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.ActiveCampaignService
import com.bionova.optimi.core.service.ConfigurationService
import com.bionova.optimi.util.DateUtil
import grails.util.Environment
import org.apache.commons.codec.binary.Base64
import org.grails.encoder.CodecLookup
import org.grails.encoder.Encoder
import org.grails.plugins.web.taglib.FormatTagLib
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpSession
import java.math.MathContext
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class UiTagLib {
    static namespace = "opt"
    static encodeAsForTags = [
            entityDisplayName: 'html',
    ]

    def entityService
    def loggerUtil
    def configurationService
    def queryService
    def questionService
    def optimiResourceService
    def userService
    def channelFeatureService
    def messageSource
    def indicatorService
    def optimiUserAgentIdentService
    def activeCampaignService
    CodecLookup codecLookup
    def localeResolverUtil
    def datasetService
    def stringUtilsService
    def verificationPointService
    def trialStatusService

    @Autowired
    EmailConfiguration emailConfiguration

    static private String APP_BUILD = null

    def backgroundImage = { attrs ->
        Indicator indicator = attrs.indicator

        if (indicator) {
            def backgroundImage

            if (indicator.report) {
                backgroundImage = indicator.report.backgroundImage
            }

            if (backgroundImage) {
                out << "<img src=\"${backgroundImage}\" alt=\"\" style=\"position: absolute; z-index: -1; left: -3px;\" />"
            }
        }
    }

    def entitySummaryData = { attrs ->
        Entity entity = attrs.entity
        Query basicQuery = queryService.getBasicQuery()

        if (entity?.entityClass && basicQuery) {
            EntitySummaryData foundSummaryData
            EntitySummaryData summaryData

            basicQuery.summaryData?.each {
                summaryData = (EntitySummaryData) it

                if (summaryData.entityClass.equals(entity.entityClass)) {
                    foundSummaryData = summaryData
                }
            }

            if (foundSummaryData) {
                def display

                foundSummaryData.comboFields?.each { ComboField comboField ->
                    def comboFieldContent
                    def value

                    comboField.content?.each { String attributeName ->
                        value = entity.getSummaryDataValue(attributeName)

                        if (value) {
                            if (value instanceof Boolean) {
                                value = formatBoolean(boolean: value, true: message(code: 'yes'), false: message(code: 'no'))
                            }
                            comboFieldContent = comboFieldContent ? comboFieldContent + ", " + value : value

                        }
                    }

                    if (comboFieldContent) {
                        def modLink = opt.link(controller: "entity", action: "form", params: [entityId: entity?.id], class: "black") {
                            "<i class=\"fas fa-pencil-alt grayLink\"></i>"
                        }
                        def modLink2 = opt.link(controller: "entity", action: "form", params: [entityId: entity?.id], class: "black") {
                            comboFieldContent.encodeAsHTML()
                        }
                        display = display ? display + "<tr><th>${comboField.localizedComboFieldName}</th><td class=\"editable\">${!entity.readonly ? modLink2 : value.encodeAsHTML()} <span class=\"pull-right\">${!entity.readonly ? modLink : ''}</span></td></tr>" :
                                "<tr><th>${comboField.localizedComboFieldName}</th><td style=\"word-break: break-word !important;\" class=\"editable\">${!entity.readonly ? modLink2 : value.encodeAsHTML()}<span class=\"pull-right\">${!entity.readonly ? modLink : ''}</span></td></tr>"
                    }
                }

                foundSummaryData.displayName?.each { String attributeName ->
                    def value = entity.getSummaryDataValue(attributeName)
                    value = value ? value : ""
                    def label = questionService.getQuestion(basicQuery, attributeName)?.localizedQuestion

                    if (!label) {
                        label = message(code: "entity.${attributeName}")
                    }
                    def modLink = opt.link(controller: "entity", action: "form", params: [entityId: entity?.id], class: "black") {
                        "<i class=\"fas fa-pencil-alt grayLink\"></i>"
                    }

                    if (value instanceof Boolean) {
                        value = formatBoolean(boolean: value, true: message(code: 'yes'), false: message(code: 'no'))
                    }
                    def modLink2 = opt.link(controller: "entity", action: "form", params: [entityId: entity?.id], class: "black") {
                        value.encodeAsHTML()
                    }

                    display = display ? display + "<tr><th>${label}</th><td style=\"word-break: break-word !important;\" class=\"editable\">${!entity?.readonly ? modLink2 : value.encodeAsHTML()} <span class=\"pull-right\">${!entity?.readonly ? modLink : ''}</span></td></tr>" : "<tr><th>${label.encodeAsHTML()}</th><td class=\"editable\">${!entity.readonly ? modLink2 : value.encodeAsHTML()} <span class=\"pull-right\">${!entity.readonly ? modLink : ''}</span></td></tr>"
                }

                if (display) {
                    out << display
                } else {
                    out << "<tr><th colspan=\"2\">${message(code: 'entity.summaryData.displayValue.missing')}</th></tr>"
                }
            } else {
                loggerUtil.error(log, "No summaryData found for entityClass ${entity?.entityClass} from basicQuery (QueryId: ${basicQuery?.queryId}, importFile: ${basicQuery?.importFile}, importTime: ${basicQuery?.importTime}. BasicQuery's summaryData entityClasses are ${basicQuery?.summaryData?.collect({ it.entityClass })}")
            }
        }
    }

    def resourceIdFromComponentIndicator = { attrs ->
        String indicatorId = attrs.indicatorId
        String entityId = attrs.entityId
        String resourceId

        if (indicatorId && entityId) {
            Entity entity = entityService.readEntity(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, false)

            if (entity && indicator) {
                Query query = indicator.getQueries(entity)?.find({ Query q -> q.componentQuery })

                if (query) {
                    Dataset dataset = entity.datasets?.find({ Dataset d -> query.queryId.equals(d.queryId) && "resourceId".equals(d.questionId) })
                    resourceId = dataset?.answerIds?.get(0)
                }
            }
        }
        out << resourceId
    }

    def entityDisplayName = { attrs ->
        Query basicQuery = attrs.basicQuery
        Entity entity = attrs.entity
        def displayName = ""

        if (basicQuery && entity) {
            EntitySummaryData summaryData = basicQuery.summaryData?.find({ EntitySummaryData sd -> sd.entityClass.equals(entity.entityClass) })

            if (summaryData) {
                List<ComboField> comboFields = summaryData.comboFields

                if (comboFields) {
                    def value

                    comboFields.each { ComboField comboField ->
                        comboField.content?.each { String property ->
                            value = entity.getSummaryDataValue(property)

                            if (value) {
                                if (displayName) {
                                    displayName = displayName + ", " + value
                                } else {
                                    displayName = displayName + value
                                }
                            }
                        }
                    }
                }

                List<String> display = summaryData.displayName

                if (display) {
                    def value

                    display.each { String property ->
                        value = entity.getSummaryDataValue(property)

                        if (value) {
                            if (displayName) {
                                displayName = displayName + ", " + value
                            } else {
                                displayName = displayName + value
                            }
                        }
                    }
                }
            }
        }
        out << displayName
    }

    def parentEntityClasses = { attrs ->
        long now = System.currentTimeMillis()
        def var = attrs.var
        def parentEntityClasses = channelFeatureService.getEntityClassesForChannel(session)

        if (var) {
            pageScope.(var.toString()) = parentEntityClasses
        } else {
            pageScope.parentEntityClasses = parentEntityClasses
        }
    }

    def help = { attrs ->
        def messageCode = attrs.messageCode
        def message = g.message(code: messageCode)
        Locale locale = FormatTagLib.resolveLocale(attrs.locale)
        def helpFileUrlConfig = ConfigName.HELP_FILE_URL.toString() + locale.language
        def helpFileUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, helpFileUrlConfig)

        if (!helpFileUrl) {
            helpFileUrlConfig = ConfigName.HELP_FILE_URL.toString() + Constants.DEFAULT_LOCALE
            helpFileUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, helpFileUrlConfig)
        }
        out << "<a href=\"" + helpFileUrl + "\" target=\"_blank\">" + message + "</a>"
    }

    def showThumbImage = { attrs ->
        def entityId = attrs.entityId
        def thumbnails = entityService.getEntityImages(entityId, true)

        if (thumbnails) {
            EntityFile file = thumbnails[0]

            try {
                Base64 encoder = new Base64()
                def data = encoder.encodeBase64String(file.data)
                out << "<img src=\"data:" + file.contentType + ";base64," + data + "\" />"
            } catch (Exception e) {
                loggerUtil.error(log, "Error in showing thumb image: " + e)
            }
        }
    }

    def showImage = { attrs ->
        Entity entity = attrs.entity
        def images = entityService.getEntityImages(entity?.id, null)

        if (images) {
            EntityFile file = images[0]

            try {
                Base64 encoder = new Base64()
                def data = encoder.encodeBase64String(file.data)
                def imageClass = entity?.smallImage ? "entityImage-small" : "entityImage"
                out << "<img src=\"data:" + file.contentType + ";base64," + data + "\" class=\"${imageClass}\" />"
            } catch (Exception e) {
                loggerUtil.error(log, "Error in showing image: " + e)
            }
        }
    }

    def base64BackgroundImage = { attrs ->
        byte[] image = attrs.imageSource

        if (image) {
            try {
                Base64 encoder = new Base64()
                def data = encoder.encodeBase64String(image)
                out << "background:url(\"data:image/jpg;base64, ${data}\") no-repeat center center fixed;" +
                        ""
            }  catch (Exception e) {
                loggerUtil.error(log, "Error in showing image: " + e)
            }
        }
    }

    def displayDomainClassImage = { attrs ->
        byte[] image = attrs.imageSource
        String contentType = 'image/jpeg'
        String imageElementId = attrs.element
        String width = attrs.width
        String height= attrs.height

        if (image) {

            try {
                Base64 encoder = new Base64()
                def data = encoder.encodeBase64String(image)
                out << "<img src=\"data:" + contentType + ";base64," + data + "\" style=\"max-width:${width}; max-height:${height};\" id=\"${imageElementId ? imageElementId :''}\"/>"
            }  catch (Exception e) {
                loggerUtil.error(log, "Error in showing image: " + e)
            }
        }
    }

    def submit = { attrs ->
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity
        def formId = attrs.formId
        def modifiable = attrs.modifiable
        Boolean disabled = attrs.disabled
        Boolean keepPositionOnReload = attrs.keepPositionOnReload
        String confirmOnFormChange = attrs.confirmOnFormChange

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")

            }
        }

        if (disabled) {
            attrs.put("disabled", "disabled")

        }

        if (formId) {
            if(keepPositionOnReload){
                attrs.put("onclick", "doubleSubmitPrevention('" + formId + "', true, this);")
            }else if (confirmOnFormChange) {
                attrs.put("onclick", "return doubleSubmitPrevention('" + formId + "', false, null, '"+confirmOnFormChange+"');")
            } else {
                attrs.put("onclick", "doubleSubmitPrevention('" + formId + "');")
            }
        }
        out << g.submitButton(attrs)
    }

    def actionSubmit = { attrs ->
        def modifiable = attrs.modifiable
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")
            }
        }
        out << g.actionSubmit(attrs)
    }

    def textField = { attrs ->
        def modifiable = attrs.modifiable
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")
            }
        }
        out << g.textField(attrs)
    }

    def textArea = { attrs ->
        def modifiable = attrs.modifiable
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")
            }
        }
        out << g.textArea(attrs)
    }

    def select = { attrs ->
        def modifiable = attrs.modifiable

        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")
            }
        }
        out << g.select(attrs)
    }

    def link = { attrs, body ->
        def parameters = attrs.params
        if (!attrs?.removeEntityId) {
            def entityId = params?.entityId

            if (entityId) {
                if (parameters) {
                    parameters.put("entityId", entityId)
                } else {
                    parameters = [entityId: entityId]
                }
            }
        } else {
            if (parameters) {
                parameters.remove("entityId")
            }
        }
        attrs.params = parameters
        out << g.link(attrs, body)
    }

    def deleteButton = { attrs ->
        def id = attrs.id
        def action = attrs.action

        if (id) {
            if (!action) {
                attrs.put("action", "remove")
            }
            attrs.put("class", "btn btn-danger")
            attrs.put("onclick", "return modalConfirm(this);")
            attrs.put("data-truestr", message(code: 'delete'))
            attrs.put("data-falsestr", message(code: 'cancel'))
            def deleteTitle = attrs.deleteTitle

            if (deleteTitle) {
                attrs.put("data-titlestr", deleteTitle)
            }
            def deleteQuestion = attrs.deleteQuestion

            if (deleteQuestion) {
                attrs.put("data-questionstr", deleteQuestion)
            }
            out << g.link(attrs, message(code: "delete"))
        }
    }

    def createLink = { attrs, body ->
        def parameters = attrs.params

        if (!attrs?.removeEntityId) {
            def entityId = params?.entityId

            if (entityId) {
                if (parameters) {
                    parameters.put("entityId", entityId)
                } else {
                    parameters = [entityId: entityId]
                }
            }
        } else {
            if (parameters) {
                parameters.remove("entityId")
            }
        }
        attrs.params = parameters
        out << g.createLink(attrs, body)
    }

    def radioButton = { attrs ->
        def modifiable = attrs.modifiable
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")
            }
        }
        out << g.radio(attrs)
    }

    def checkBox = { attrs ->
        def modifiable = attrs.modifiable
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity

        if (!parentEntity) {
            if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                attrs.put("disabled", "disabled")
            }
        } else {
            if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                attrs.put("disabled", "disabled")
            }
        }
        out << g.checkBox(attrs)
    }

    def typeAhead = { attrs ->
        def value = attrs.value
        def name = attrs.name
        def id = attrs.id
        def resources = attrs.resources
        StringBuilder sb = new StringBuilder()
        def data = ""

        resources?.each { Resource r ->
            data = data + "{\"label\":\"" + optimiResourceService.getLocalizedName(r) + (r.unitForData ? " (" + r.unitForData + ")" : "") + "\", \"value\":\"" + r.resourceId + " " + r.unitForData + "\"},\n"
        }

        sb << """
        <input id="${id}Search" type="text" placeholder="Valitse resurssi" value="${value}" />
        <input type="hidden" name="${name}" id="${id}" value="${value}" />

        <script>
            var data = [
                   ${data}
                   ];
             var dataMap = {};
             var dataLabels = [];

             \$('#${id}Search').typeahead({
                minLength: 1,
                source: function (query, process) {
                    dataLabels = [];
                    dataMap = {};

                    \$.each(data, function (i, obj) {
                        dataMap[obj.label] = obj;
                        dataLabels.push(obj.label);
                     });
                     process(dataLabels);
                   },
                 updater: function (item) {
                     \$('#${id}').val(dataMap[item].value);
                     return item;
                  }
              });

              \$('#${id}Search').change(function(){
                  if(\$(this).val().trim() == "") {
                      \$('#${id}').val("");
                  }
              });
        </script>
        """
        out << sb
    }

    def entityIcon = { attrs ->
        Entity entity = attrs.entity
        def iconType = ""

        if (entity) {
            def entityTypeResourceId = entity.getTypeResourceId()
            Resource buildindTypeResource = optimiResourceService.getResourceWithParams(entityTypeResourceId, null, null)

            if (buildindTypeResource) {
                buildindTypeResource?.resourceGroup?.each {
                    if (it.contains("picture")) {
                        def iconNo = it.replaceAll("picture", "")?.trim()

                        if ("1" == iconNo) {
                            iconType = "entitytype-smallhouse"
                        } else if ("2" == iconNo) {
                            iconType = "entitytype-apartmenthouse"
                        } else if ("3" == iconNo) {
                            iconType = "entitytype-office"
                        } else if ("4" == iconNo) {
                            iconType = "entitytype-shop"
                        } else if ("5" == iconNo) {
                            iconType = "entitytype-hotel"
                        } else if ("6" == iconNo) {
                            iconType = "entitytype-hospital"
                        } else if ("7" == iconNo) {
                            iconType = "entitytype-sportcenter"
                        } else if ("8" == iconNo) {
                            iconType = "entitytype-school"
                        } else if ("9" == iconNo) {
                            iconType = "entitytype-factory"
                        } else if ("10" == iconNo) {
                            iconType = "entitytype-portfolio"
                        }
                    }
                }

                if (!iconType) {
                    iconType = "entitytype-${buildindTypeResource.resourceId.toLowerCase()}"
                }
            }

            if (!iconType) {
                iconType = "entitytype-${entity.entityClass?.toLowerCase()}"
            }
        }
        out << iconType
    }

    def emissionCloudForQuintile = { attrs ->
        Integer quintile = attrs.quintile
        String emissionCloud = ""

        if (quintile && quintile > 0 && quintile <= 5) {
            emissionCloud = quintile == 5 ? "deepGreenEmissions" : quintile == 4 ? "greenEmissions" : quintile == 3 ? "yellowEmissions" : quintile == 2 ? "orangeEmissions" : quintile == 1 ? "redEmissions" : null
        }

        if (emissionCloud) {
            emissionCloud = "<div class='co2CloudContainer'><i class='fa fa-cloud ${emissionCloud}'></i><p class='co2Text'>co<sub>2</sub></p></div>"
        }
        out << emissionCloud
    }

    def channelMessage = { attrs ->
        Locale locale = FormatTagLib.resolveLocale(attrs.locale)
        String text = ""

        if (attrs.code) {
            String code = attrs.code?.toString()
            List args = []

            if (attrs.args) {
                args = attrs.encodeAs ? attrs.args as List : encodeArgsIfRequired(attrs.args)
            }
            String defaultMessage

            if (attrs.containsKey('default')) {
                defaultMessage = attrs['default']?.toString()
            }
            def message = messageSource.getMessage(code, args == null ? null : args.toArray(),
                    defaultMessage, locale)

            if (message != null) {
                text = message
            }
        }

        if (text) {
            Encoder encoder = codecLookup.lookupEncoder(attrs.encodeAs?.toString() ?: 'raw')
            text = encoder ? encoder.encode(text) : text
        }
        out << text
    }

    def breadcrumbs = { attrs ->
        TemporaryImportData temporaryImportData = (TemporaryImportData) session?.getAttribute("temporaryImportData")
        Map<Integer, String> steps = [1:"breadcrumbs.data", 2:"breadcrumbs.settings" ,3:"breadcrumbs.converting", 4:"filter", 5:"breadcrumbs.combining", 6:"breadcrumbs.data_review", 7:"breadcrumbs.mapping", 9:"breadcrumbs.updating"]
        Map<Integer, String> stepHelps = [1:"simulationTool.chooseFile_help", 2:"breadcrumbs.settings_help" ,3:"breadcrumbs.converting_help", 4:"breadcrumbs.filtering_help", 5:"breadcrumbs.combining_help", 6:"breadcrumbs.data_review_help", 7:"breadcrumbs.mapping_help",9:"breadcrumbs.updating_help"]
        Integer current = attrs.current
        List<Integer> defaultManualStep = [5,7]
        String nav = "<div class=\"container\"><div class=\"wrapper\" style=\"margin-bottom: 20px;\"><div class=\"arrow-steps clearfix\">"
        String checkboxRow = "<div class=\"checkBoxContainer ${current == 2 ?'':'hidden'}\">"
        steps.each { Integer step, String heading ->
            Boolean last = Boolean.FALSE
            if (step == 7 && current == step) {
                last = Boolean.TRUE
            }
            String localizedHeading = message(code: heading)
            String localizedHelp = message(code: stepHelps.get(step))
            String status = step == current ? "current" : current > step ? "completed" : ""
            Integer dataAmountForThisStep = temporaryImportData?.importDataPerStep?.get(step)
            String descriptiveDatasetsCount = ""
            if (last) {
                Integer identifiedDescriptiveCount = temporaryImportData?.importDataPerStep?.get(71) ?: 0
                Integer unidentifiedDescriptiveCount = temporaryImportData?.importDataPerStep?.get(81) ?: 0
                if (identifiedDescriptiveCount || unidentifiedDescriptiveCount) {
                    descriptiveDatasetsCount = " / <span id=\"identifiedDescriptiveDatapoints\">${identifiedDescriptiveCount}</span> + <span id=\"unidentifiedDescriptiveDatapoints\">${unidentifiedDescriptiveCount}</span>"
                }
            }
            nav = "${nav}<div class=\"step ${status}\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${localizedHelp}\"><span><strong>${status.equals("completed") ? "✓ " : ""}${localizedHeading.toUpperCase()}</strong>${dataAmountForThisStep  != null ? "</br>${message(code: 'breadcrumbs.datapoints')}: ${last ? "<span id=\"identifiedDatapoints\">${dataAmountForThisStep}</span> + <span id=\"unidentifiedDatapoints\">${temporaryImportData?.importDataPerStep?.get(8) ?: 0}</span>${descriptiveDatasetsCount}" : "${dataAmountForThisStep}"}" : ""}</span></div>"
            if (current == 2) {
                checkboxRow = "${checkboxRow}<div class=\"arrowCheckbox\">"
                if(step > 2) {
                    String helpPopover = ""
                    if(step == 9){
                        helpPopover = "<a href=\"#\" class=\"just_black importMapperHelpPopover\" rel=\"popover\"" +
                                "     data-trigger=\"hover\" data-content=\"${message(code: 'importMapper.index.updateQuant_checkbox_help')}\">" +
                                "     <i class=\"fas fa-question-circle\"></i>" +
                                "     </a>"
                    }
                    checkboxRow = "${checkboxRow}<label><input type=\"checkbox\" id=\"stepId${step}\" value=\"${step}\" ${step == 9 ? 'disabled="disabled"' : ''} ${defaultManualStep.contains(step) ? "checked=\"checked\"" : ""} name=\"steps\"> ${message(code: 'breadcrumbs.review_manually')} ${helpPopover} </label></div>"
                }else{
                    checkboxRow = "${checkboxRow}</div>"
                }
            }
        }
        checkboxRow="${checkboxRow}</div>"
        nav = "${nav}</div>${checkboxRow}</div></div>"
        out << nav
    }

    def breadcrumbsNavBar = {attrs ->
        Indicator indicator = attrs.indicator
        Entity parent = attrs.parentEntity
        Entity child = attrs.childEntity
        Query query = attrs.query
        String childName = attrs.childName
        String indicatorLicenseName = attrs.indicatorLicenseName
        Integer step = attrs.step
        def entityClassResource = attrs.entityClassResource
        User user = userService.getCurrentUser()
        String specifiedHeading = attrs.specifiedHeading

        String nav = "<h4 ${step == 1 ? 'id="breadcrumbForQuery"' : ''}>"
        if(user){
            nav = "${nav}<a href='${createLink(controller: "main",removeEntityId: true)}'>${message(code:"main")}</a>  "
            if(!parent && !child && !specifiedHeading){
                nav = "${nav} > ${message(code:"entity.new_entity_title")}"
            }
            if(parent){
                if(step == 0){
                    nav = "${nav}  >  <a href='#' rel='popover' class='just_black' data-trigger='hover' data-content=\"${parent.name}\">${parent.name}</a>"

                } else {
                    nav = "${nav}  > <span id='mainProjectLink'><a href='${createLink(controller: "entity",action: "show", params: [id: parent.id])}'>${parent.name}</a></span>"
                }
            }
            if(child) {
                if(step == 1){
                    nav = "${nav} > <strong><a href='#' rel='popover' class='just_black' data-trigger='hover' data-content='${child.name ?: child.operatingPeriod}'> ${abbr(value: child.name ?: child.operatingPeriod, maxLength: 38)}</a></strong>"
                } else {
                    nav = "${nav} > <strong>${child.name ?: child.operatingPeriod}</strong>"

                }
            }
            if(childName){
                nav = "${nav} > <strong>${childName}</strong>"
            }
            if(entityClassResource){
                nav="${nav} (${optimiResourceService.getLocalizedName(entityClassResource)})"
            }
            if(indicator) {
                nav = "${nav} > <strong> ${indicatorService.getLocalizedName(indicator) ?: ''}${indicatorLicenseName ?: ''} </strong>"
            }

        }
        if(specifiedHeading){
            nav = "${nav} > ${specifiedHeading}"
        }
        if(query){
            nav = "${nav} > ${message(code:"data_missing")} : ${query.localizedName}"
        }
        nav = "${nav}<br></h4>"

        out << nav
    }

    private List encodeArgsIfRequired(arguments) {
        arguments.collect { value ->
            if (value == null || value instanceof Number || value instanceof Date) {
                value
            } else {
                Encoder encoder = codecLookup.lookupEncoder('HTML')
                encoder ? encoder.encode(value) : value
            }
        }
    }

    def renderDatasetInformation = { attrs ->
        String datasetManualId = attrs.datasetManualId
        String childEntityId = attrs.childEntityId
        String constructionId = attrs.constructionId
        String queryId = attrs.queryId
        String sectionId = attrs.sectionId
        String questionId = attrs.questionId

        if (datasetManualId) {
            Map<String, String> params = [:]

            if (childEntityId && queryId && sectionId && questionId) {
                params = [datasetManualId:datasetManualId, childEntityId: childEntityId, queryId:queryId, sectionId:sectionId, questionId:questionId]
            }
            if (constructionId) {
                params = [datasetManualId:datasetManualId, constructionId:constructionId]
            }

            if (params) {
                out << "<a href=\"javascript:;\" class=\"fiveMarginLeft\" onclick=\"window.open('${createLink(controller: "util", action: "renderDatasetInformation", params:params)}', '_blank', 'width=1024, height=768, scrollbars=1');\"><i class=\"fa fa-archive\"></i></a>"

            }
        }

    }

    def splitOrChangeDataset = { attrs ->
        String datasetManualId = attrs.datasetManualId
        String childEntityId = attrs.childEntityId
        String parentEntityId = attrs.parentEntityId
        String indicatorId = attrs.indicatorId
        String queryId = attrs.queryId
        String sectionId = attrs.sectionId
        String questionId = attrs.questionId
        String mainSectionId = attrs.mainSectionId
        Boolean showGWP = attrs.showGWP
        Boolean preventChanges = attrs.preventChanges
        Boolean constructionResource = attrs.constructionResource
        Boolean locked = attrs.locked
        String originalResourceTableId = attrs.originalResourceTableId


        if (datasetManualId && childEntityId && queryId && sectionId && questionId && parentEntityId && indicatorId) {
            String queryString = "originalResourceTableId=${originalResourceTableId}&mainSectionId=${mainSectionId}&showGWP=${showGWP}&preventChanges=${preventChanges}&datasetManualId=${datasetManualId}&childEntityId=${childEntityId}&queryId=${queryId}&sectionId=${sectionId}&questionId=${questionId}&parentEntityId=${parentEntityId}&indicatorId=${indicatorId}"
            if (constructionResource) {
                out << "<li ${locked ? "style=\"opacity: 0.5; pointer-events: none;\"" : ""} class=\"splitChangeLi disableWhenCalcIsRunning\"><a href=\"javascript:\" onclick=\"renderSplitOrChangeView('${queryString + '&changeView=true'}', true)\"><i class=\"fa fa-retweet\" arial-hidden=\"true\"></i> ${message(code: 'change_material')}</a></li>"
            } else {
                out << "<li ${locked ? "style=\"opacity: 0.5; pointer-events: none;\"" : ""} class=\"splitChangeLi disableWhenCalcIsRunning\"><a href=\"javascript:\" onclick=\"renderSplitOrChangeView('${queryString + '&splitView=true'}', true)\"> <i class=\"fas fa-code-branch\" style =\"padding-left: 2px; padding-right: 2px;\"arial-hidden=\"true\"></i>  ${message(code: 'split_material')}</a></li>" +
                        "<li ${locked ? "style=\"opacity: 0.5; pointer-events: none;\"" : ""} class=\"splitChangeLi disableWhenCalcIsRunning\"><a href=\"javascript:\" onclick=\"renderSplitOrChangeView('${queryString + '&changeView=true'}', true)\"><i class=\"fa fa-retweet\" arial-hidden=\"true\"></i> ${message(code: 'change_material')}</a></li>"
            }
        }
    }

    def userLicenseInformationTag = { attrs ->
        User user = attrs.user
        if (user) {
            Integer amountOfLicenses = userService.getLicenses(user)?.size()
            if (amountOfLicenses) {
                out << "<a href=\"javascript:;\" class=\"fiveMarginLeft\" onclick=\"getUserLicenseInformation(this, '${user.id}');\">${amountOfLicenses}</a>"

            } else {
                out << 0
            }

            }
        }

    def newToolsAvailableMessage = { attrs ->
        String indicatorUse = attrs.indicatorUse
        String render = ""

        if (attrs.entityId && indicatorUse) {
            String link = "${opt.link(controller: "entity", action: "addIndicators", params: [entityId: attrs.entityId, indicatorUse: indicatorUse]) {"${message(code: "entity.show.indicators")}"}}"
            render = "<div class=\"alert alert-success\"><i class=\"fas fa-tools pull-left\" style=\"font-size: large; margin-right: 8px;\"></i><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button> <strong> ${message(code: "entity.newToolsAvailable")} ${link} </strong></div>"
        }
        out << render
    }

    def licenseExpiryMessage = { attrs ->
        License license = attrs.license
        Entity entity = attrs.entity
        String renderer = ""
        if (license && entity) {
            LocalDateTime currentDate = LocalDateTime.now()
            Date expiryDate = license.validUntil

            if (license.trialLength) {
                expiryDate = license.getChangeHistory(entity)?.dateAdded?.plus(license?.trialLength)
            }

            if (expiryDate) {
                long daysLeft = Duration.between(currentDate, DateUtil.dateToLocalDatetime(expiryDate)).toDays()

                if (daysLeft <= 30) {
                    String link = "<a href=\"mailto:${emailConfiguration.supportEmail}\">${emailConfiguration.supportEmail}</a>"
                    renderer = "${renderer} <div class=\"alert alert-warning bold\">${message(code: 'license')}:" +
                            " ${license.name}, ${message(code: 'license.expires_on', args: [expiryDate?.format("dd.MM.yyyy"), daysLeft])} " +
                            " ${daysLeft < 30 && !["trial", "education"].contains(license.type?.toLowerCase()) ? "${message(code: 'license.expire_contact')} ${link} ${message(code: 'license.expire_to_renew')}" : '.'} " +
                            " </div>"
                }
            }
        }
        out << renderer
    }

    def internetExplorerWarning = { attrs ->
        Boolean ie = optimiUserAgentIdentService.isMsie()
        Boolean index = attrs.index
        String internetExplorer = ""


        if (ie) {
            internetExplorer = "<div class=\"${index ? "" : "alert "}alert-error\">\n" +
                    "                <strong>${message(code: "internetExplorerWarning")}</strong>" +
                    "           </div>"
        }
        out << internetExplorer
    }

    def renderPopUpTrial = { attrs ->
        String stage = attrs.stage.toString()
        Entity entity = attrs.entity
        List<Entity> entities = attrs.entities
        Entity currentDesign = attrs.currentDesign
        License license = attrs.license
        List<Entity> designs = attrs.designs
        User user = userService.getCurrentUser()
        Boolean projectLevel = attrs.projectLevel
        List<Query> parameterQueries = attrs.parameterQueries
        Query query = attrs.query
        List<Indicator> selectedDesignIndicators = attrs.selectedDesignIndicators
        List<Entity> operatingPeriods = attrs.operatingPeriods
        ChannelFeature channelFeature = attrs.channelFeature
        String renderer = ""
        def helpUrl
        String tagStatus = ""
        String trialProjectLink
        if(user && !user.trialStatus){
            user.trialStatus = new TrialStatus()
        }
        if(user && !user?.trialStatus?.completedTrial){
            if(channelFeature){
                helpUrl = channelFeature.helpUrl ? channelFeature.helpUrl : channelFeature?.helpDocumentUrls?.get(session?.urlLang) ? channelFeature.helpDocumentUrls.get(session?.urlLang) : channelFeature?.helpDocumentUrls?.get("EN")
            }
            if(!helpUrl){
                helpUrl = com.bionova.optimi.core.Constants.HELP_CENTER_URL
            }

            switch (stage) {
                case "trialsAvailable":
                    if(!entities){
                        renderer = "<div class=\"homePageFixedWidth\"><div class=\"help_finger_up\">${message(code:'help_float_tip0c')}</div></div>"
                    }
                    break
                case "noProjectAddHomePage":
                    if(!entities){
                        renderer = "<div class=\"homePageFixedWidth\"><div class=\"help_finger_up\">${message(code:'help_float_tip0d')}</div></div>"
                    }
                    break
                case "noProjectDemoHomePage":
                    if(!entities){
                        renderer = "<div><div class=\"help_finger\">${message(code:'help_float_tip0a')}</div></div>"
                    }
                    break
                case "newProject":
                    if(!entity){
                        renderer = "<div class=\"help_finger\">${message(code:'help_float_tip1')}</div>"
                    }
                    break
                case "trialActivationProjectPage":
                    renderer="<div class=\"help_finger\">${message(code:'help_float_tip2')}</div>"
                    if(entity && !user.trialStatus.firstProject && user.trialStatus.activeTrial){
                        user.trialStatus.firstProject = new Date()
                        user.trialStatus.entityName = entity.name
                        user.trialStatus.entityId = entity.id.toString()
                        tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_PROJECT_TAG_ID
                        activeCampaignService.updateFieldsForUser(user, null, [(ActiveCampaignService.TRIAL_ENTITY_FIELD_ID.toString()): generateEntityLink(entity, session)])
                        String newTagIdforAC = activeCampaignService.updateTrialStatusTagForUser(user, tagStatus, session)
                        if(newTagIdforAC){
                            user.trialStatus.tagId = newTagIdforAC
                            user.trialStatus.tagStatus = tagStatus
                        }
                    }
                    userService.updateUser(user)
                    break
                case "projectMainPageDesign":
                    if(!designs){
                        renderer = "<div class=\"container\">" +
                                "            <div class=\"help_finger pull-right\">${message(code:'help_float_tip5')}</div>" +
                                "        </div>"
                    }
                    if(designs && parameterQueries && entity?.getIsAllParameterQueriesReady(parameterQueries.collect({ it.queryId })) && !selectedDesignIndicators?.find({ Indicator ind -> designs.find({it.queryReadyPerIndicator?.get(ind.indicatorId)?.find({!Constants.PARAMETER_QUERYIDS.contains(it.key) && it.value})})  })){
                        renderer = "  <div class=\"container\">" +
                                "                    <div class=\"help_finger large\">${message(code:'trialWelcomeContent')}" +
                                "                            <a href=\"${helpUrl}\" target=\"_blank\">${message(code:'main.navi.user_instructions')}</a>" +

                                "     </div></div>"
                    } else if(designs && parameterQueries && entity?.getIsAllParameterQueriesReady(parameterQueries.collect({ it.queryId })) && selectedDesignIndicators?.find({ Indicator ind -> designs.find({it.queryReadyPerIndicator?.get(ind.indicatorId)?.find({!Constants.PARAMETER_QUERYIDS.contains(it.key) && it.value})})  })){
                        if(!user.trialStatus.firstQueryInput && user.trialStatus.activeTrial){
                            user.trialStatus.firstQueryInput = new Date()
                            tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_QUERY_INPUT_TAG_ID
                            String newTagIdforAC = activeCampaignService.updateTrialStatusTagForUser(user, tagStatus, session)
                            if(newTagIdforAC){
                                user.trialStatus.tagId = newTagIdforAC
                                user.trialStatus.tagStatus = tagStatus
                            }
                        }
                    }
                    if(!user.trialStatus.activeTrial && license && license.openTrial){
                        user.trialStatus.accountCreated = user.registrationTime
                        user.trialStatus.activeTrial = new Date()
                        user.trialStatus.licenseId = license?.id?.toString()
                        user.trialStatus.licenseName = license?.name?.toString()
                        activeCampaignService.updateFieldsForUser(user, null, [(ActiveCampaignService.TRIAL_LICENSE_NAME_FIELD_ID.toString()): license?.name?.toString()])
                        trialProjectLink = generateEntityLink(entity, session)
                        tagStatus = ActiveCampaignService.TRIAL_ACTIVATED_TAG_ID
                    } else if (user.trialStatus.activeTrial){
                        if(parameterQueries && entity?.getIsAllParameterQueriesReady(parameterQueries.collect({ it.queryId })) && !user.trialStatus.projectParameter){
                            user.trialStatus.projectParameter = new Date()
                            tagStatus = ActiveCampaignService.TRIAL_STUCK_PARAMETER_QUERY_TAG_ID
                        }
                        if(!user.trialStatus.firstProject && entity){
                            user.trialStatus.firstProject = new Date()
                            user.trialStatus.entityName = entity?.name
                            user.trialStatus.entityId = entity?.id?.toString()
                            activeCampaignService.updateFieldsForUser(user, null, [(ActiveCampaignService.TRIAL_ENTITY_FIELD_ID.toString()): generateEntityLink(entity, session)])
                            tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_PROJECT_TAG_ID
                        }
                        if(!user.trialStatus.indicatorName && entity && entity.indicatorIds){
                            user.trialStatus.indicatorName = entity.indicatorIds.first()
                        }

                        if(designs && designs.size() == 1 && !user.trialStatus.firstDesign){
                            user.trialStatus.firstDesign = new Date()
                            tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_DESIGN_TAG_ID
                        }
                        if (designs && designs.size() == 2 && user.trialStatus.firstDesign && !user.trialStatus.secondDesign){
                            user.trialStatus.secondDesign = new Date()
                            tagStatus = ActiveCampaignService.TRIAL_ACTIVE_SECOND_DESIGN_TAG_ID
                        }
                    }
                    String newTagIdforAC = activeCampaignService.updateTrialStatusTagForUser(user, tagStatus, session, trialProjectLink)
                    if(newTagIdforAC){
                        user.trialStatus.tagId = newTagIdforAC
                        user.trialStatus.tagStatus = tagStatus
                    }
                    userService.updateUser(user)
                    break
                case "getStartedPeriod":
                    if(!operatingPeriods && !selectedDesignIndicators){
                        renderer = "<div class=\"container\">" +
                                "            <div class=\"help_finger pull-right\">${message(code:'help_float_tip5p')}</div>" +
                                "        </div>"
                    }
                    break
                case "projectMainPagePeriod":
                    if(operatingPeriods && !operatingPeriods.findAll({it?.queryReady?.findAll({it.value == true})}) && !selectedDesignIndicators && channelFeature){
                        renderer = "  <div class=\"container\">" +
                                "                    <div class=\"help_finger large\">${message(code:'trialWelcomeContentPeriod')}" +
                                "                            <a href=\"${helpUrl}\" target=\"_blank\">${message(code:'main.navi.user_instructions')}</a>" +

                                "     </div></div>"

                    }

                    if(!user.trialStatus.activeTrial && license && license.openTrial){
                        user.trialStatus.accountCreated = user.registrationTime
                        user.trialStatus.activeTrial = new Date()
                        user.trialStatus.licenseId = license?.id?.toString()
                        user.trialStatus.licenseName = license?.name?.toString()
                        activeCampaignService.updateFieldsForUser(user, null, [(ActiveCampaignService.TRIAL_LICENSE_NAME_FIELD_ID.toString()): license?.name?.toString()])
                        trialProjectLink = generateEntityLink(entity, session)
                        tagStatus = ActiveCampaignService.TRIAL_ACTIVATED_TAG_ID
                    } else if(user.trialStatus.activeTrial){
                        if(!user.trialStatus.firstProject && entity){
                            user.trialStatus.firstProject = new Date()
                            user.trialStatus.entityName = entity?.name
                            user.trialStatus.entityId = entity?.id?.toString()
                            activeCampaignService.updateFieldsForUser(user, null, [(ActiveCampaignService.TRIAL_ENTITY_FIELD_ID.toString()): generateEntityLink(entity, session)])
                            tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_PROJECT_TAG_ID
                        }
                        if(operatingPeriods){
                            if(operatingPeriods.size() == 1 && !user.trialStatus.firstDesign){
                                user.trialStatus.firstDesign = new Date()
                                tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_DESIGN_TAG_ID

                            }else if (operatingPeriods.size() == 2  && user.trialStatus.firstDesign && !user.trialStatus.secondDesign){
                                user.trialStatus.secondDesign = new Date()
                                tagStatus = ActiveCampaignService.TRIAL_ACTIVE_SECOND_DESIGN_TAG_ID

                            }

                        }
                    }
                    String newTagIdforAC = activeCampaignService.updateTrialStatusTagForUser(user, tagStatus, session, trialProjectLink)
                    if(newTagIdforAC){
                        user.trialStatus.tagId = newTagIdforAC
                        user.trialStatus.tagStatus = tagStatus
                    }
                    userService.updateUser(user)
                    break
                case "queryPage":
                    if(projectLevel && Constants.PARAMETER_QUERYIDS.contains(query?.queryId) && !entity?.getIsAllParameterQueriesReady([query.queryId])){
                        String saveText = message(code:'save')
                        renderer = "<div class=\"help_finger large\">${message(code:'help_float_tip3a')} ${submit(entity: currentDesign, parentEntity: entity, formId: 'queryForm', name: 'save', value:saveText, class:'fakeLink noButton')}. ${message(code:'help_float_tip3b')} </a></div>"
                    }

                    if(!projectLevel && query && currentDesign){
                        if(!currentDesign?.queryReady?.findAll({!Constants.PARAMETER_QUERYIDS.contains(it.key) && it.value}) && user.trialStatus.activeTrial && !user.trialStatus.firstQueryInput){
                            renderer = "<div class=\"help_finger large\" id=\"firstQueryInputHelp\">${message(code:'help_float_tip8')} <span id=\"hiddenHelp\" class=\"hidden\">${message(code:'help_float_tip9')}</span></div>"
                        }else{
                            if(!user.trialStatus.firstQueryInput && user.trialStatus.activeTrial){
                                user.trialStatus.firstQueryInput = new Date()
                                tagStatus = ActiveCampaignService.TRIAL_ACTIVE_FIRST_QUERY_INPUT_TAG_ID
                            }
                        }
                    }
                    if(user.trialStatus.firstQueryInput && !user.trialStatus.viewResult){
                        renderer = "<div class=\"container\">" +
                                "            <div class=\"help_finger_up pull-right\">${message(code:'help_float_tip10')}</div>" +
                                "        </div>"
                        user.trialStatus.viewResult = new Date()
                    }

                    String newTagIdforAC = activeCampaignService.updateTrialStatusTagForUser(user, tagStatus, session)
                    if(newTagIdforAC){
                        user.trialStatus.tagId = newTagIdforAC
                        user.trialStatus.tagStatus = tagStatus
                    }
                    userService.updateUser(user)
                    break
                case "noParameterWarningProjectPage":
                    if(selectedDesignIndicators && designs && parameterQueries && !entity?.getIsAllParameterQueriesReady(parameterQueries.collect({ it.queryId }))){
                        renderer = "<div class=\"container\">" +
                                "            <div class=\"help_finger pull-right\">${message(code:'help_float_tip4')}</div>" +
                                "        </div>"
                    }
                    break

                case "compareDesignProjectPage":
                    if(designs && designs.size() == 1 && selectedDesignIndicators?.find({ Indicator ind -> designs.find({it.queryReadyPerIndicator?.get(ind.indicatorId)?.find({!Constants.PARAMETER_QUERYIDS.contains(it.key) && it.value})})  })){
                        renderer = "<div class=\"container\">" +
                                "            <div class=\"help_finger_up pull-right\">${message(code:'help_float_tip6')}</div>" +
                                "        </div>"

                    } else if (user.trialStatus.viewResult && user.trialStatus.secondDesign && !user.trialStatus.completedTrial){
                        user.trialStatus.completedTrial = Boolean.TRUE
                        tagStatus = ActiveCampaignService.TRIAL_COMPLETED_TAG_ID
                        String newTagIdforAC = activeCampaignService.updateTrialStatusTagForUser(user, tagStatus, session)
                        if(newTagIdforAC){
                            user.trialStatus.tagId = newTagIdforAC
                            user.trialStatus.tagStatus = tagStatus
                        }
                        userService.updateUser(user)
                    }
                    break
            }
        }
        if(user && license && license.planetary && !user.trialStatus.isPlanetaryUser){
            user.trialStatus.isPlanetaryUser = true
            userService.updateUser(user)
            tagStatus = ActiveCampaignService.USER_PLANETARY_TAG_ID
            activeCampaignService.updateFieldsForUser(user, null, [(ActiveCampaignService.TRIAL_LICENSE_NAME_FIELD_ID.toString()): license?.name?.toString()])
            String ok = activeCampaignService.updateTrialStatusTagForUser(user,tagStatus)
        }
        out << renderer
    }

    def String generateEntityLink(Entity entity, HttpSession session) {
        def channelToken = channelFeatureService.getChannelFeature(session)?.token
        Locale locale = localeResolverUtil.resolveLocale(session, request, response)
        return createLink(base: "$request.scheme://$request.serverName$request.contextPath",
                controller: "entity", action: "show", id: entity?.id, params: [channelToken: channelToken, lang: locale?.language])
    }

    def renderLicenseMenu = { attrs ->
        long now = System.currentTimeMillis()
        Account account = attrs.account
        ChannelFeature channelFeature = attrs.channelFeature
        User userForFloatingLicense = attrs.userForFloatingLicense
        User loggedInUserObject = attrs.loggedInUserObject
        User user = attrs.user
        def reloadOnFloatingStatusChange = attrs.reloadOnFloatingStatusChange
        String toRender = ""
        Date date = new Date()

        List<License> floatingLicenses = userService.getUsableValidFloatingLicenses(userForFloatingLicense)
        //0010224
        List<License> licenses = userService.getUsableAndLicensedLicenses(loggedInUserObject)

        if (floatingLicenses) {
            if (licenses) {
                licenses.addAll(floatingLicenses)
            } else {
                licenses = floatingLicenses
            }
        }

        if (account) {
            toRender = "${toRender}<li style=\"margin-top: 10px !important\">${g.link(controller: 'account', action: 'form', params: [id: account.id]){"<span style=\"font-weight: bold !important;\">${message(code:"main.navi.account", args: [account.companyName])}</span>"}}</li>"
        }
        toRender = "${toRender}<li class=\"parent_menu\"><a href=\"#\">${message(code:"main.navi.account.catalogues")}</a></li>"

        if (licenses) {
            licenses.unique({it.id})?.sort({it.name.toLowerCase()})?.each { License license ->
                if (userForFloatingLicense && license.isFloatingLicense) {
                    toRender = "${toRender}<li class=\"sub_menu\"><a href=\"javascript:\" class=\"floatingLicenseLink\" onclick=\"openFloatingLicenseModal('${license.id.toString()}', null, null, ${reloadOnFloatingStatusChange ? true : false})\">${license.name}&nbsp;"
                    toRender = "${toRender}<span class=\"floatingLicenseMain\">"
                    if (license.floatingLicenseCheckedInUsers?.find({it.userId == user?.id?.toString()})) {
                        toRender = "${toRender}${message(code:"license.floating.checkOut")}&nbsp;&nbsp;"
                    } else {
                        toRender = "${toRender}${message(code:"license.floating.checkIn")}&nbsp;&nbsp;"
                    }
                    toRender = "${toRender}</span></a></li>"
                } else {
                    Integer usedEntities = license.licensedEntityIds && license.licensedEntityIds.size() ? license.licensedEntityIds.size() : 0
                    String indicatorNames = license.licensedIndicators?.findAll({!"benchmark".equalsIgnoreCase(it.visibilityStatus)})?.collect({indicatorService.getLocalizedShortName(it)})?.join(", ")
                    String usernames = license.getLicenseUsernames()?.join(", ")
                    String templateNames = license.licenseTemplates?.collect({it.name})?.join(", ")

                    if (indicatorNames && indicatorNames.length() > 200) {
                        indicatorNames = abbr(value: indicatorNames, maxLength: 200, smartCut: true)
                    }

                    if (usernames && usernames.length() > 200) {
                        usernames = abbr(value: usernames, maxLength: 200, smartCut: true)
                    }

                    if (templateNames && templateNames.length() > 200) {
                        templateNames = abbr(value: templateNames, maxLength: 200, smartCut: true)
                    }

                    String autoRenewalInMessage

                    if ("autoRenewal".equals(license.renewalStatus)) {
                        Date nextRenewalDate = license.nextRenewalDate
                        if (nextRenewalDate) {
                            Integer daysToRenewal = nextRenewalDate - date
                            if (daysToRenewal >= 0 && daysToRenewal <= 30) {
                                autoRenewalInMessage = "<br/><span>${asset.image(src:"img/icon-warning.png", style:"width:13px")} Renewal in ${daysToRenewal} days.</span>"
                            }
                        }
                    }

                    String subMenuName = "${license.name} <br/> " +
                            "<i class=\"wrapListItem\" style=\"font-size: 11px; width: 250px;\">${license.maxEntities ? message(code:"licenses.entitiesUsed") + ': ' + usedEntities.toString() + ' / ' + license.maxEntities.toString() + '.': license.userBased ? 'User-based license.' : ''} " +
                            "${license.expired ? "<span style=\"color: red;\">${message(code:"expired.upperCase")} - ${formatDate(date:license.validUntil, format:"dd.MM.yyyy")}</span>" : "${message(code:"licenseHandling.renew")} ${license?.validUntil ? formatDate(date: license?.validUntil, format: 'dd.MM.yyyy') : "N/A"}"}" +
                            "${autoRenewalInMessage ?: ""}" +
                            "${indicatorNames ? "<br/>Tools: ${indicatorNames}" : ""}" +
                            "${usernames ? "<br/>Users: ${usernames}" : ""}" +
                            "${templateNames ? "<br/>Templates: ${templateNames}" : ""}</i>"
                    //0010224
                    //change the below to list .contains due to 12536 change
                    if (userService.getSuperUser(loggedInUserObject) || license.managerIds?.contains(user.id.toString())) {
                        toRender = "${toRender}<li class=\"sub_menu\">${opt.link(controller:"license", action:"form", params: [id: license.id]) {subMenuName}}</li>"
                    } else {
                        toRender = "${toRender}<li class=\"sub_menu\"><a href=\"#\">${subMenuName}</a></li>"
                    }
                }
            }
        } else if (account) {
            toRender = "${toRender}<li class=\"sub_menu\"><a href=\"#\">${message(code: "main.navi.account.no_catalogue")}</a></li>"
        }

        if (!channelFeatureService.getHideEcommerceStatus(channelFeature)) {
            if (account) {
                toRender = "${toRender}<li class=\"parent_menu\" style=\"margin-top: 0px !important;\"><a href=\"javascript:\"> ${message(code: "ecommerce.shopping_org")} ${account.companyName}</a></li>"
                toRender = "${toRender}<li class=\"sub_menu\">${link(controller: "wooCommerce", action: "index", class: "wooCommerceMain", target: "_blank") {"${message(code: "main.navi.view_and_buy")} <i class=\"fa fa-shopping-cart buyButtonMain\"></i>"}}</li>"
                toRender = "${toRender}<li class=\"sub_menu\"><a href=\"${channelFeature?.quoteUrl}\" target=\"_blank\"> ${message(code: "ecommerce.contact_sales")} </a></li>"
            } else {
                toRender = "${toRender}<li class=\"parent_menu\"><a href=\"javascript:\"> ${message(code: "ecommerce.public_mode")}</a></li>"
                toRender = "${toRender}<li class=\"sub_menu\"> ${link(controller: "account", action: "form", params: [join: true]){message(code: "main.navi.create_account")}} </li>"
                toRender = "${toRender}<li class=\"sub_menu\"> ${link(controller: "wooCommerce", action: "index", class: "wooCommerceMain", target: "_blank", params: [publicShop: true]){"${message(code: "main.navi.view_and_buy")} <i class=\"fa fa-shopping-cart buyButtonMain\"></i>"}} </li>"
                toRender = "${toRender}<li class=\"sub_menu\"><a href=\"${channelFeature?.quoteUrl}\" target=\"_blank\"> ${message(code: "ecommerce.contact_sales")} </a></li>"
            }
        }
        log.info("Licenses menu rendering took: ${System.currentTimeMillis() - now} ms")
        out << toRender
    }
    def renderTrialStatus = { attrs ->
        List<User> userList = attrs.userList
        boolean sortData = attrs.sortData
        String toRender = ""
        if(userList){
            Iterator<User> iterator = userList.iterator()
            toRender = "${toRender}<table class=\"table table-striped table-condensed ${sortData ? 'table-data' : ''}\" id=\"trialUserList\"><thead><tr><th>Username</th><th>User type</th><th>Account created</th><th>Trial count</th><th>First project</th><th>First project name</th><th>Trial activated</th><th>Trial license name</th><th>Trial tool</th><th>First design</th><th>Parameter query</th><th>First query input</th><th>Second design</th><th>Result page introduced</th><th>Completed trial</th></tr></thead><tbody>"
            while(iterator.hasNext()){
                User user = iterator.next()
                TrialStatus trialStatus = user.trialStatus
                if(trialStatus && user){
                    toRender = "${toRender}<tr><td>${user.username.encodeAsHTML()}</td>"
                    toRender = "${toRender}<td>${user?.type?.encodeAsHTML() ?: ''}</td>"
                    toRender = "${toRender}<td>${formatDate(format:'dd.MM.yyyy HH:MM:ss',date:user.registrationTime)}</td>"
                    toRender = "${toRender}<td>${user?.trialCount}</td>"
                    toRender = "${toRender}<td>${formatDate(format:'dd.MM.yyyy HH:MM:ss',date:trialStatus.firstProject)}</td>"
                    toRender = "${toRender}<td>${trialStatus.entityName?.encodeAsHTML()}</td>"
                    toRender = "${toRender}<td>${trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.activeTrial, trialStatus.firstProject)} </td>"
                    toRender = "${toRender}<td>${trialStatus.licenseName?.encodeAsHTML()}</td>"
                    toRender = "${toRender}<td>${trialStatus.indicatorName?.encodeAsHTML()}</td>"
                    toRender = "${toRender}<td>${trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.firstDesign, trialStatus.firstProject)} </td>"
                    toRender = "${toRender}<td>${trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.projectParameter, trialStatus.firstProject)} </td>"
                    toRender = "${toRender}<td>${trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.firstQueryInput, trialStatus.firstProject)} </td>"
                    toRender = "${toRender}<td>${trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.secondDesign, trialStatus.firstProject)} </td>"
                    toRender = "${toRender}<td>${trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.viewResult, trialStatus.firstProject)} </td>"
                    toRender = "${toRender}<td>${trialStatus.completedTrial}</td></tr>"
                }

            }
            toRender = "${toRender}</tbody></table>"
        }
        out << toRender
    }

    def formatNumber = { attrs ->
        User user = userService.getCurrentUser()
        Double number = attrs.number
        String formattedNumber = ""

        if (number != null) {
            BigDecimal bd = new BigDecimal(number)
            bd = bd.round(new MathContext(2))
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
            decimalFormat.applyPattern('###,###.#########')
            formattedNumber = decimalFormat.format(bd.doubleValue())
        }
        out << formattedNumber
    }
    def renderCompareDataBtn = {attrs ->
        Entity parentEntity = attrs.parentEntity
        Entity materialCompareEntity = attrs.materialCompareEntity ?: entityService.getMaterialSpecifierEntity(parentEntity)
        Integer size
        def maxRowLimit = attrs.maxRowLimit
        Boolean querySave = attrs.querySave
        if(materialCompareEntity){
            Set<Dataset> datasets = datasetService.getDatasetsByEntityAndQueryId(materialCompareEntity, "materialSpecifier")
            size = datasets?.size() ?: 0
            attrs.put('entityId',materialCompareEntity?.id)
         }
        String btnContent = "<i class=\"fas fa-balance-scale icon-white\"></i> ${message(code:'compare_resources')} <span class=\"compareBtnCountTotal\">${size ? '('+size+')' : ''}</span> "
        String btnOut
        if(querySave){
            btnOut = "<a class='btn btn-primary preventDoubleSubmit' href='#' onclick=\"submitQuery(null, 'queryForm', null, 'compareMat', null, null, null, ${maxRowLimit})\">${btnContent}</a>"
        } else {
            btnOut = opt.link(attrs,btnContent)
        }
        out << btnOut
    }

    def renderDataCardBtn = { attrs ->
        if (!attrs.resourceId) {
            log.info("renderDataCardBtn in UiTaglib MUST HAVE resourceId")
            out << """
                        <a class='infoBubble' rel='popover' data-trigger='hover' 
                         data-content='${message(code: 'resource.sourcelisting_no_information_available')}'>
                            <i class='fa fa-question greenInfoBubble' aria-hidden='true'></i>
                        </a>
            """
        } else {
            try {
                // DO NOT REARRANGE THE ORDER.
                // A basic data card must have at least resourceId, and (if possible) profileId. The rest are optional
                String elementId
                String infoId = attrs.infoId ?: UUID.randomUUID().toString()
                if(attrs.targetBubbleId) {
                    elementId = attrs.targetBubbleId
                } else {
                    elementId = 'qMark' + infoId
                }
                String parameters = "\"${attrs.resourceId}\", \"${infoId}\""
                parameters += attrs.indicatorId ? ",\"${attrs.indicatorId}\"" : ',null'
                parameters += attrs.questionId ? ",\"${attrs.questionId}\"" : ',null'
                parameters += attrs.profileId ? ",\"${attrs.profileId}\"" : ',null'
                parameters += attrs.childEntityId ? ",\"${attrs.childEntityId}\"" : ',null'
                parameters += attrs.showGWP ? ",\"${attrs.showGWP}\"" : ',null'
                parameters += attrs.queryId ? ",\"${attrs.queryId}\"" : ',null'
                parameters += attrs.sectionId && attrs.datasetId ? ",\"${attrs.sectionId}\",\"${attrs.datasetId}\"" : ',null,null'
                parameters += attrs.queryPage ? ",\"${attrs.queryPage}\"" : ',null'
                parameters += ",\"${attrs.disabled}\""
                parameters += attrs.manualId ? ",\"${attrs.manualId}\"" : ',null'
                parameters += attrs.customName ? ",\"${attrs.customName.encodeAsHTML()}\"" : ',null'
                parameters += attrs.productDataListPage ? ",${attrs.productDataListPage}" : ',null'
                parameters += attrs.targetElement ? ",\"${attrs.targetElement}\"" : ',null' // use this selector (e.g. #someElement) if the data card need to append to an element. add to the 'body' by default
                parameters += attrs.cardPosition ? ",\"${attrs.cardPosition}\"" : ',null'
                parameters += attrs.topCoordinate ? ",\"${attrs.topCoordinate}\"" : ',null' // use this to set the location of data card
                parameters += attrs.leftCoordinate ? ",\"${attrs.leftCoordinate}\"" : ',null' // use this to set the location of data card
                parameters += attrs.width ? ",\"${attrs.width}\"" : ',null' // use this to set the width of data card, min 450 or equivalent
                parameters += attrs.height ? ",\"${attrs.height}\"" : ',null' // use this to set the width of data card, min 0.3 (i.e. 30% screenHeight) or equivalent (in pixel)
                parameters += attrs.zIndex ? ",\"${attrs.zIndex}\"" : ',null' // use this to set the zIndex of data card, default 100
                parameters += attrs.hideImage ? ",${attrs.hideImage}" : ',null'
                parameters += attrs.constructionPage ? ",${attrs.constructionPage}" : ',null'

                out << """
                        <a href='javascript:' id='${elementId}' class='infoBubble' 
                         onclick='renderSourceListingToAnyElement(${parameters})' 
                         data-profileId='${attrs.profileId}', data-resourceId='${attrs.resourceId}'>
                            <i class='fa fa-question greenInfoBubble' aria-hidden='true'></i>
                        </a>
                """
            } catch (e) {
                log.info("Error while rendering datacard from renderDataCardBtn UiTaglib: ${e}")
                out << """
                       <a class='infoBubble' rel='popover' data-trigger='hover' 
                         data-content='Oops.. Error occurred. Please contact support to inform developer. Thank you!'>
                            <i class='fa fa-question greenInfoBubble' aria-hidden='true'></i>
                       </a>
                """
            }
        }
    }

    def verificationPointIcon = {attrs ->
        if (!attrs.question && !attrs.section) {
            out << ''
        } else {
            Question question = attrs.question
            QuerySection section = attrs.section
            String vpId = verificationPointService.getVpLocationId(attrs.question as Question, attrs.section as QuerySection)
            String vpPoints = verificationPointService.getVPointsUI(question, section)
            String renderString = ''
            renderString += "<div id=\"${vpId}\" class=\"VPointContainer hide\">"
            renderString += '<i class="fas fa-angle-left fa-sm"></i>'
            renderString += '<span class="VPointText">' + vpPoints + '</span>'
            renderString += '<i class="fas fa-angle-right fa-sm"></i>'
            renderString += '</div>'
            out << renderString
        }
    }

    def greenSwitch = { attrs ->
        String style = attrs.style ?: ''
        String checked = Boolean.valueOf(attrs.checked as String) ? "checked" : ''
        String disabled = Boolean.valueOf(attrs.disabled as String) ? "disabled class=\"removeClicks\"" : ''

        attrs.remove('style')
        attrs.remove('checked')
        attrs.remove('disabled')
        String attributes = stringUtilsService.outputAttributes(attrs)
        if (disabled) {
            style = 'pointer-events: none; ' + style
        }

        out << '<label class="switch">' +
                    "<input data-switch=\"true\" ${attributes} ${disabled} ${style ? "style=\"${style}\"" : ''} ${checked} type=\"checkbox\">" +
                    "<span ${disabled ? 'style="pointer-events: none;"' : ''} class=\"slider round ${disabled ? 'removeClicks' : ''}\"></span>" +
                '</label>'

    }

    def showEnvironmentBuild = { attrs ->
        if (APP_BUILD == null) {
            String serverName = request?.serverName

            if (serverName && !com.bionova.optimi.core.Constants.PROD_DOMAINS.contains(serverName)) {
                // show text Production build in red if we deploy Prod build on a development server.
                if (Environment.current == Environment.PRODUCTION) {
                    APP_BUILD = '<span class="redScheme">Production build</span>'
                } else {
                    APP_BUILD = 'Development build'
                }
            } else {
                // show nothing on Prod
                APP_BUILD = ''
            }
        }

        out << APP_BUILD
    }

    def spinner = { attrs ->

        String spinnerId = attrs.spinnerId ?: "loadingSpinner"
        Boolean display = Boolean.parseBoolean(attrs.display?.toString())

        out << render(template: "/util/spinner", model: [spinnerId: spinnerId, display: display])
    }
}
