<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    def indicatorService = grailsApplication.mainContext.getBean("indicatorService")
    def optimiResourceService = grailsApplication.mainContext.getBean("optimiResourceService")
    def portfolioService = grailsApplication.mainContext.getBean("portfolioService")
%>
    <div class="container">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main"/></opt:link> >
                <opt:link controller="entity" action="show" id="${entityId}">${portfolio?.entityName}</opt:link>
            > <g:message code="entity.portfolio.modify"/> <br/> </h4>
        <div class="screenheader">
            <h1><g:message code="entity.portfolio.modify"/></h1>
            <p style="line-height: 0px"><g:message code="portfolio.modify.help" /></p>
        </div>
    </div>

    <div class="container section">
        <div class="sectionbody">
            <g:form action="save" useToken="true">
                <g:hiddenField name="entityId" value="${entityId}"/>
                <g:hiddenField name="id" value="${portfolio?.id}"/>
                <div class="column_left">
                    <h3><g:message code="portfolio.form.basic_features" /></h3>

                    <fieldset>
                       <div class="control-group">
                            <label for="type" class="control-label">
                                <strong><g:message code="portfolio.type"/></strong><br />
                                <g:message code="portfolio.type.info" />
                            </label>

                            <div class="controls">
                                <g:select name="type" value="${portfolio?.type}" from="${portfolioTypes}"
                                          optionValue="${{ g.message(code: it) }}" noSelection="${['': '']}"/>
                            </div>
                        </div>

                        <div class="control-group">
                            <label for="type" class="control-label">
                                <strong><g:message code="portfolio.entityClasses"/></strong>
                            </label>

                            <div class="controls">
                                <g:select name="entityClass" value="${portfolio?.entityClass}" from="${entityClasses}"
                                    optionValue="${{ optimiResourceService.getLocalizedName(it) }}" optionKey="resourceId" noSelection="${['': '']}"/>
                            </div>
                        </div>

                        <g:if test="${choosablePeriods}">
                            <div class="control-group">
                                <label for="type" class="control-label">
                                    <strong><g:message code="portfolio.chosenPeriods"/></strong>
                                </label>

                                <div class="controls">
                                    <table class="indicators">
                                        <g:each in="${choosablePeriods}" var="period">
                                            <tr>
                                                <td>
                                                    <opt:checkBox entity="${entity}" name="chosenPeriods" value="${period}" checked="${portfolio.chosenPeriods?.contains(period)}" />
                                                    <strong>${period}</strong>
                                                </td>
                                            </tr>
                                        </g:each>
                                    </table>
                                </div>
                            </div>
                       </g:if>
                        <%--
                        <g:if test="${portfolio?.id}">
                            <div class="control-group">
                                <label class="control-label">
                                    <strong><g:message code="portfolio.additionalInfo"/></strong>
                                </label>

                                <div class="controls">
                                    <ul>
                                        <li><g:message code="portfolio.user"/>: ${portfolio.user?.username}</li>
                                        <li><g:message code="portfolio.dateCreated"/>: <g:formatDate
                                                date="${portfolio.dateCreated}" format="dd.MM.yyyy"/></li>
                                        <li><g:message code="portfolio.lastUpdater"/>: ${portfolio.lastUpdater.username}</li>
                                    </ul>
                                </div>
                            </div>
                        </g:if>
                        --%>

                        <g:if test="${portfolioService.getManageable(portfolio?.entityId) || userService.getSuperUser(userService.getCurrentUser())}">
                            <div class="control-group">
                                <label class="control-label"></label>

                                <div class="controls">
                                    <label class="checkbox">
                                        <g:checkBox id="enabled" value="${portfolio?.anonymous}" name="anonymous"/>
                                        <g:message code="portfolio.anonymous"/>
                                    </label>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label"></label>

                                <div class="controls">
                                    <label class="checkbox">
                                        <g:checkBox value="${portfolio?.showOnlySpecificallyAllowed}" name="showOnlySpecificallyAllowed" id="showOnlySpecificallyAllowed"/>
                                        <g:message code="portfolio.showOnlySpecificallyAllowed"/> <i class="icon-question-sign" id="showOnlySpecificallyAllowedHelp"></i>
                                    </label>
                                </div>

                                <div class="controls">
                                    <label class="checkbox">
                                        <g:checkBox value="${portfolio?.showOnlyLatestStatus}" name="showOnlyLatestStatus" id="showOnlyLatestStatus"/>
                                        <g:message code="portfolio.showOnlyLatestStatus"/>
                                    </label>
                                </div>
                            </div>
                        </g:if>


                        <div class="control-group">
                            <label class="control-label"></label>

                            <div class="controls">
                                <label class="checkbox">
                                    <g:checkBox id="enabled" value="${portfolio?.showAverageInsteadOfTotal}" name="showAverageInsteadOfTotal"/>
                                    <g:message code="portfolio.showAverageInsteadOfTotal"/>
                                </label>
                            </div>

                            <div class="controls">
                                <label class="checkbox">
                                    <g:checkBox id="enabled" value="${portfolio?.sortByDesc}" name="sortByDesc"/>
                                    <g:message code="portfolio.order_descend"/>
                                </label>
                            </div>
                        </div>


                    </fieldset>

                    <opt:link controller="entity" action="show" class="btn"><i
                            class="icon-chevron-left"></i> <g:message code="back"/></opt:link>
                    <opt:submit name="save" value="${message(code: 'save')}" class="btn btn-primary" />
                </div>
            </g:form>

            <div class="column_right" style="margin-left: 80px;">
                <h3><g:message code="portfolio.form.content" /></h3>

                <g:if test="${portfolio?.id}">
                    <g:if test="${!portfolio?.licenseId}">
                        <g:form action="addEntity">
                            <g:hiddenField name="id" value="${portfolio?.id}"/>
                            <g:hiddenField name="entityId" value="${entityId}"/>

                            <fieldset>
                                <div class="control-group">
                                    <label for="entities" class="control-label">
                                        <strong><g:message code="portfolio.entities"/></strong>
                                    </label>

                                    <div class="controls">
                                        <g:select class="autocompletebox select2" name="addableEntityId" from="${entities}"
                                                  optionKey="_id" optionValue="${{ it.name }}"
                                                  noSelection="['': '']"/>
                                        <opt:submit name="addEntity" value="${message(code: 'add')}" class="btn btn-primary"/>
                                    </div>

                                    <p>&nbsp;</p>
                                    <table class="table table-condensed">
                                        <tbody>
                                        <g:each in="${portfolioService.getEntitiesWithoutPortfolioEntities(portfolio)}" var="entity">
                                            <tr>
                                                <td>${entity.shortName}</td><td>
                                                <g:if test="${!portfolio.connectedIndicatorId}">
                                                <opt:link
                                                    action="removeEntity" id="${portfolio?.id}"
                                                    params="[removableEntityId: entity.id]" class="btn btn-danger"
                                                    onclick="return modalConfirm(this);"
                                                    data-questionstr="${message(code: 'portfolio.remove_entity.question', args: [entity.name])}"
                                                    data-truestr="${message(code: 'delete')}"
                                                    data-falsestr="${message(code: 'cancel')}"
                                                    data-titlestr="${message(code: 'portfolio.remove_entity.header')}"><g:message
                                                        code="delete"/></opt:link></td>
                                                </g:if>
                                            </tr>
                                        </g:each>
                                        </tbody>
                                    </table>
                                </div>
                            </fieldset>
                        </g:form>
                    </g:if>

                    <g:form action="addIndicator">
                        <g:hiddenField name="id" value="${portfolio?.id}"/>
                        <g:hiddenField name="entityId" value="${entityId}"/>

                        <fieldset>
                            <div class="control-group">
                                <label for="entities" class="control-label">
                                    <strong><g:message code="entity.show.designs_tools"/></strong>
                                </label>

                                <g:if test="${indicators}">
                                    <div class="controls">
                                        <g:select name="indicatorId" from="${indicators.sort({indicatorService.getLocalizedName(it)})}" optionKey="indicatorId"
                                                  optionValue="${{ indicatorService.getLocalizedName(it) }}" noSelection="['': '']"/>
                                        <opt:submit name="addIndicator" value="${message(code: 'add')}" class="btn btn-primary"/>
                                    </div>
                                </g:if>

                                <p>&nbsp;</p>
                                <table class="table table-condensed">
                                    <tbody>
                                    <g:each in="${portfolioService.getIndicators(portfolio?.indicatorIds)}" var="indicator">
                                        <tr>
                                            <td>${indicatorService.getLocalizedName(indicator)}</td><td><opt:link action="removeIndicator"
                                                                                             id="${portfolio?.id}"
                                                                                             params="[indicatorId: indicator.indicatorId]"
                                                                                             class="btn btn-danger"
                                                                                             onclick="return modalConfirm(this);"
                                                                                             data-questionstr="${message(code: 'portfolio.remove_indicator.question', args: [indicatorService.getLocalizedName(indicator)])}"
                                                                                             data-truestr="${message(code: 'delete')}"
                                                                                             data-falsestr="${message(code: 'cancel')}"
                                                                                             data-titlestr="${message(code: 'portfolio.remove_indicator.header')}"><g:message
                                                    code="delete"/></opt:link></td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                    </g:form>

                </g:if>
            </div>

            <sec:ifAnyGranted roles="ROLE_SUPER_USER">
            <div class="column_right" style="margin-left: 80px;">
                <h3>Admin features</h3>

                <g:form action="save" useToken="true">
                    <g:hiddenField name="entityId" value="${entityId}"/>
                    <g:hiddenField name="id" value="${portfolio?.id}"/>
                    <g:hiddenField name="type" value="${portfolio?.type}" />
                    <g:if test="${licenses}">
                        <div class="control-group">
                            <label for="type" class="control-label">
                                <strong><g:message code="portfolio.licenseId"/></strong>
                            </label>

                            <div class="controls">
                                <g:select class="select2" name="licenseId" value="${portfolio?.licenseId}" from="${licenses}"
                                          optionValue="${{ it.name }}" optionKey="${{it.id.toString()}}" noSelection="${['': '']}"/>
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${allIndicators}">
                        <div class="control-group">
                            <label for="type" class="control-label">
                                <strong><g:message code="portfolio.connectedIndicatorId"/></strong>
                            </label>

                            <div class="controls">
                                <g:select name="connectedIndicatorId" value="${portfolio?.connectedIndicatorId}" from="${allIndicators}"
                                          optionValue="${{ indicatorService.getLocalizedName(it) }}" optionKey="indicatorId" noSelection="${['': '']}"/>
                            </div>
                        </div>
                    </g:if>

                    <div class="control-group">
                        <label for="type" class="control-label">
                            <strong><g:message code="portfolio.licenseTypes"/></strong>
                        </label>

                        <div class="controls">
                            <g:select name="licenseType" value="${portfolio?.licenseType}" from="${licenseTypes}"
                                      optionValue="${{ it.value }}" optionKey="key" noSelection="${defaultLicenseType}"/>
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label"></label>

                        <div class="controls">
                            <label class="checkbox">
                                <g:checkBox value="${portfolio?.publishAsBenchmark}" name="publishAsBenchmark" id="publishAsBenchmark"/>
                                <g:message code="portfolio.publishAsBenchmark"/>
                            </label>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label"></label>

                        <div class="controls">
                            <label class="checkbox">
                                <g:checkBox value="${portfolio?.showOnlyCarbonHeroes}" name="showOnlyCarbonHeroes" id="showOnlyCarbonHeroes"/>
                                Select only carbon heroes
                            </label>
                        </div>
                    </div>

                    <div class="control-group">
                        <label for="portfolioId" class="control-label"><g:message code="portfolio.portfolioId" /></label>
                        <div class="controls"><opt:textField name="portfolioId" value="${portfolio?.portfolioId}"  /></div>
                    </div>

                    <opt:submit name="save" value="${message(code: 'save')}" class="btn btn-primary" />

                </g:form>
            </div>
            </sec:ifAnyGranted>

        </div>
    </div>
<script type="text/javascript">
    $(function () {
        var allSelects = $('.select2');
        if (allSelects) {
            $(allSelects).select2({
                width: '310px'
            }).maximizeSelect2Height();
        }
    });

    $('#showOnlySpecificallyAllowedHelp').popover({
        content:"${message(code:'portfolio.showOnlySpecificallyAllowed.help')}",
        placement:'right',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp"></div></div>',
        trigger:'hover',
        html:true
    });


    (function ($) {
        $.widget("ui.combobox", {
            _create: function () {
                this.wrapper = $("<div>")
                        .insertBefore(this.element)
                        .attr('class', 'input-append');

                this._createAutocomplete();
                this._createShowAllButton();
            },

            _createAutocomplete: function (args) {


                var selected = this.element.children(":selected"),
                        value = selected.val(),
                        label = selected.text(),
                        elementname = this.element.attr('name');

                this.element.attr('name', '');
                this.element.css('display', 'none');

                var placecholder = this.element.attr('data-autocomplete-placeholder');


                this.hiddenvalue = $("<input>")
                        .appendTo(this.wrapper)
                        .attr("type", "hidden")
                        .attr("name", elementname)
                        .val(value);

                this.input = $("<input>")
                        .appendTo(this.wrapper)
                        .val(label)
                        .attr("title", "")
                        .attr("type", "text")
                        .attr("placeholder", placecholder)
                        .addClass("input-xlarge")
                        .autocomplete({
                            delay: 0,
                            minLength: 0,
                            source: $.proxy(this, "_source")
                        })
                        .removeClass('ui-autocomplete-input')
                        .tooltip({
                            tooltipClass: "ui-state-highlight"
                        });

                if (!value) {
                    this.input.val("");
                }

                this._on(this.input, {
                    autocompleteselect: function (event, ui) {
                        ui.item.option.selected = true;
                        this.hiddenvalue.val(ui.item._value);
                        this._trigger("select", event, {
                            item: ui.item.option
                        });
                    },

                    autocompletechange: "_removeIfInvalid"
                });
            },

            _createShowAllButton: function () {
                var wasOpen = false;
                var input = this.input;

                var showall = $("<a>")
                        .attr("tabIndex", -1)
                        .attr("title", "Show All Items")
                        .tooltip()
                        .appendTo(this.wrapper)
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        })
                        .removeClass("ui-corner-all")
                        .addClass("add-on")
                        .append('<i class="icon-chevron-down"></i>')
                        .on('mousedown', function () {
                            wasOpen = input.autocomplete("widget").is(":visible");
                        })
                        .on('click', function () {
                            input.trigger('focus');

                            if (wasOpen) {
                                return;
                            }

                            input.autocomplete("search", "");
                        });
            },

            _source: function (request, response) {
                var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
                response(this.element.children("option").map(function () {
                    var text = $(this).text();
                    if (this.value && ( !request.term || matcher.test(text) ))
                        return {
                            label: jQuery.trim(text),
                            value: jQuery.trim(text),
                            _value: $(this).val(),
                            option: this
                        };
                }));
            },

            _removeIfInvalid: function (event, ui) {

                if (ui.item) {
                    return;
                }

                var value = this.input.val(),
                        valueLowerCase = value.toLowerCase(), valid = false;

                if (jQuery.trim(value) == '') {
                    this.hiddenvalue.val('');
                }

                this.element.children("option").each(function () {
                    if ($(this).text().toLowerCase() === valueLowerCase) {
                        this.selected = valid = true;
                        return false;
                    }
                });

                if (valid) {
                    return;
                }

                this.input
                        .val("")
                        .attr("title", value + " didn't match any item")
                        .tooltip("open");

                this.hiddenvalue.val('');

                this.element.val("");
                this._delay(function () {
                    this.input.tooltip("close").attr("title", "");
                }, 2500);
                this.input.data("ui-autocomplete").term = "";
            },

            _destroy: function () {
                this.wrapper.remove();
                this.element.show();
            }
        });
    })(jQuery);

</script>
</body>
</html>
