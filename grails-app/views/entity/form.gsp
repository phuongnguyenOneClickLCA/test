<%@ page import="com.bionova.optimi.core.Constants" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <asset:javascript src="stupidtable.min.js" />
</head>
<body>
<script type="text/javascript">
    if(!!window.performance && window.performance.navigation.type === window.performance.navigation.TYPE_BACK_FORWARD) {
        // This is to detect user pressing back button on browser and reloading && clearing session properly with reload
        window.stop();
        window.location.reload();
    }
</script>
<div id="mainContent">
    <div class="container">
        <div class="screenheader">

            <g:render template="/entity/basicinfo" />
        </div>
    </div>

    <div class="container section">
        <g:uploadForm controller="entity" action="save">
            <input type="hidden" name="${_csrf?.parameterName}" value="${_csrf?.token}"/>
            <g:hiddenField name="checkMandatory" value="true"/>
            <g:hiddenField name="entityId" value="${entity?.id}"/>
            <g:hiddenField name="entityClass" value="${entityClassResource?.resourceId}" />
            <g:hiddenField name="automaticTrial" value="${automaticTrial}" />
            <g:hiddenField name="autocaseDesignId" value="${autocaseDesignId}" />
            <g:hiddenField name="autocaseProjectId" value="${autocaseProjectId}" />

            <div class="clearfix"></div>

            <div class="querysection" style="border: 0 !important;">
                <div class="control-group" >
                    <label><g:message code="entity.name" /> (<g:message code="mandatory" />)</label>
                    <div>
                        <div class="input-append">
                            <opt:textField modifiable="${modifiable}" entity="${entity}" name="name" value="${entity?.name ?: projectName ?: ""}" class="input-xlarge span8 mandatory ${!entity?.name&&!projectName ? 'redBorder' : ''}"  onkeyup="validateName(this, '${message(code:"invalid_character")}')"/>
                            <div class="hidden" data-content="${message(code:'invalid_character')}"><p class="warningRed"></p></div>
                        </div>
                    </div>
                </div>
            </div>

            <g:if test="${basicQuery}">

                <g:render template="basicquery" model="[entity: entity, query: basicQuery]"/>
            </g:if>
            <g:else>
                NO BASIC QUERY FOUND
            </g:else>
            <g:if test="${(licenseId && licenseName) || !licenses?.isEmpty()}">
                <div class="querysection control-group">
                    <g:if test="${licenseId && licenseName}">
                        <div class="questionHeadText">
                            <h3 style="display:inline;"><g:message code="licensing"/></h3>
                        </div>
                        <span class="bold">${licenseName}</span> <i class="icon-question-sign licenseHelp"></i>
                        <input type="hidden" value="${licenseId}" name="licenseId">
                    </g:if>
                    <g:elseif test="${licenses||entityValidLicenses}">
                        <div id="availableLicenses">
                            <div class="questionHeadText">
                                <h3 style="display:inline;"><g:message code="licensing"/></h3>
                            </div>
                            <g:if test="${licenses}">
                                <div>
                                    <label><strong><g:message code="entity.licenses" /></strong> <i class="icon-question-sign licenseHelp"></i></label>
                                    <div>
                                        <div class="input-append">
                                            <select name="licenseId" id="licenseId" ${modifiable ? "" : "disabled=\"disabled\""} multiple>
                                                <g:each in="${licenses}" var="license">
                                                    <option data-freeForAllEntities="${license.freeForAllEntities}" data-planetary="${license.planetary}" data-condiditionalCountries="${license?.conditionalCountries}" data-conditionalBuildingTypes="${license?.conditionalBuildingTypes}" value="${license?.id?.toString()}">${license?.name}</option>
                                                </g:each>
                                            </select>
                                            <%--<opt:select entity="${entity}" name="licenseId" from="${licenses}" optionKey="id" optionValue="${{it.name}}" noSelection="['':'']" modifiable="${modifiable}"/>--%>
                                        </div>
                                    </div>
                                </div>
                            </g:if>
                            <ul>
                                <g:each in="${entityValidLicenses}" var="license">
                                    <li>${license.name}<sec:ifAnyGranted roles="ROLE_SUPER_USER"> ${link(controller: 'license', action: 'removeEntity', id: license.id, params: [entityId: entity.id, permanentEntityDeletion: false, redirectToProject: true], class: 'btn btn-danger', onclick: 'return modalConfirm(this);', 'data-questionstr': message(code: 'admin.license.delete_building.question', args: [entity.name]), 'data-truestr': message(code: 'delete'), 'data-falsestr': message(code: 'cancel'), 'data-titlestr': message(code: 'admin.license.delete_building.header')) { message(code: 'delete') }}</sec:ifAnyGranted></li>
                                </g:each>
                            </ul>
                        </div>

                    </g:elseif>

                </div>
            </g:if>

            <div class="clearfix"></div>
            <opt:submit entity="${entity}" name="save" value="${message(code:'save')}" class="btn btn-primary" modifiable="${modifiable}" />
            <g:if test="${entity?.id}">
                <opt:link action="show" params="[entityId: entity?.id]" class="btn"><i class="icon-chevron-left"></i> <g:message code="back"/></opt:link>
                <g:if test="${entity?.manageable}">
                    <opt:link action="remove" class="btn btn-danger" id="${entity?.id}" onclick="return modalConfirm(this);"
                              data-questionstr="${message(code:'entity.delete_question', args:[entity?.name])}"
                              data-questionstr2="${message(code:'entity.delete.step2Question')}"
                              data-truestr="${message(code:'delete')}"
                              data-falsestr="${message(code:'cancel')}"
                              data-titlestr="${message(code:'entity.delete_confirm.header')}"><g:message code="delete" /></opt:link>
                </g:if>
            </g:if>
            <g:else>
                <button type="button" class="btn" onclick="goBack();"><i class="icon-chevron-left"></i> <g:message code="back" /></button>
            </g:else>
        </g:uploadForm>
    </div>
</div>
<script type="text/javascript">

    $('#${Constants.COUNTRY_SELECT}, #${Constants.BLD_TYPE_SELECT}').on('change', function () {
        var licenseIdSelect = $('#licenseId');
        $(licenseIdSelect).val('').trigger("change");
        select2Options($(licenseIdSelect));
    });


    function resultState(data, container) {
        var fails = elementFails(data.element);

        if (fails) {
            return null
        } else {
            return data.text;
        }
    }

    var planetarySelected = false;
    var nonPlanetarySelected = false;

    function elementFails (elem) {
        var country = $('#${Constants.COUNTRY_SELECT}').val();
        var type = $('#${Constants.BLD_TYPE_SELECT}').val();
        var fails = false;
        var freeForAllEntities = $(elem).attr("data-freeForAllEntities");
        var planetary = $(elem).attr("data-planetary");

        if (planetarySelected && !planetary) {
            fails = true;
        } else if (nonPlanetarySelected && planetary) {
            fails = true;
        }

        if (!fails && "true" === freeForAllEntities) {
            var condiditionalCountries = $(elem).attr("data-condiditionalCountries");
            var conditionalBuildingTypes = $(elem).attr("data-conditionalBuildingTypes");

            if (condiditionalCountries) {
                if (country) {
                    if (condiditionalCountries.indexOf(country) === -1) {
                        fails = true;
                    }
                } else {
                    fails = true;
                }
            }

            if (conditionalBuildingTypes) {
                if (type) {
                    if (conditionalBuildingTypes.indexOf(type) === -1) {
                        fails = true;
                    }
                } else {
                    fails = true;
                }
            }
        }
        return fails;
    }

    function select2Options($elementSelect2) {
        var atleastOneShowable = false;
        for (var i = 0; i < $elementSelect2.children().length; i++) {
            var fails = elementFails($elementSelect2.children()[i]);

            if (!fails) {
                atleastOneShowable = true;
                $('#availableLicenses').show();
                break;
            }
        }

        if (!atleastOneShowable) {
            $('#availableLicenses').hide();
        }
    }

    $(function () {
        var licenseIdSelect = $('#licenseId');

        $(licenseIdSelect).select2({
            templateResult: resultState
        }).on('select2:select', function (e) {
            $(licenseIdSelect).select2("close");
            var selected = $('#licenseId option:selected');
            if ($(selected).length) {
                $.each(selected, function ( index, elem ) {
                    if ($(elem).attr('data-planetary')) {
                        planetarySelected = true;
                    } else {
                        nonPlanetarySelected = true;
                    }
                });
            } else {
                planetarySelected = false;
                nonPlanetarySelected = false;
            }
        }).on('select2:unselect', function (e) {
            var selected = $('#licenseId option:selected');

            if ($(selected).length) {
                $.each(selected, function ( index, elem ) {
                    if ($(elem).attr('data-planetary')) {
                        planetarySelected = true;
                    } else {
                        nonPlanetarySelected = true;
                    }
                });
            } else {
                planetarySelected = false;
                nonPlanetarySelected = false;
            }
        });

        select2Options($(licenseIdSelect));

        $('.numeric').on('input propertychange', function () {
            var start = this.selectionStart;
            end = this.selectionEnd;
            var val = $(this).val();
            if (val) {
                $(this).popover('hide');
            }
            if (isNaN(val)) {
                val = val.replace(/[^0-9\.\,\-]/g, '');

                if (val.split('.').length > 2) {
                    val = val.replace(/\.+$/, '');
                }

                if (val.split(',').length > 2) {
                    val = val.replace(/\,+$/, '');
                }
                var dotsAndCommas = ((val.match(/\./g) || []).length + (val.match(/,/g) || []).length);

                if (dotsAndCommas > 1) {
                    if (val.indexOf('.') < val.indexOf(',')) {
                        val = val.replace('.', '');
                    } else {
                        val = val.replace(',', '');
                    }
                }
            }
            $(this).val(val);
            this.setSelectionRange(start, end);
        });
        $('.licenseHelp').popover({
            content:"${message(code:'entity.form.indicator_help')}",
            placement:'right',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content indicatorHelp"></div></div>',
            trigger:'hover',
            html:true
        });
    });

    $(document).ready(function () {
        initQueryAutocompletes();
        select2theSingleSelects('${message(code: 'query.choose_resource')}');
        var country = $('#${Constants.COUNTRY_SELECT}');
        if(country.length > 0 && country.val() !== ""){
            country.trigger("change")
        }

    });
    function showPlanetaryCompatibility(element) {
        var valueOfSelect = $(element).val();
        var statesWithPlanetary = ${statesWithPlanetary as grails.converters.JSON};
        if(statesWithPlanetary && statesWithPlanetary.length > 0){
            var lastSibling = $(element).parent().siblings()
            if(statesWithPlanetary.indexOf(valueOfSelect) != -1){
                $(lastSibling).find(".success").removeClass("hidden")
                $(lastSibling).find(".alert-warning").addClass("hidden")
            } else {
                $(lastSibling).find(".success").addClass("hidden")
                $(lastSibling).find(".alert-warning").removeClass("hidden")
            }
        }
    }

</script>
</body>
</html>

