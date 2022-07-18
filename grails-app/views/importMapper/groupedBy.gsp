<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:form action="dropGroupedBy" class="dropGroupedByForm">
    <div class="container">
        <div class="screenheader">
            <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
                <sec:ifLoggedIn>
                    <g:if test="${parentEntity}">
                        > <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                        <g:abbr value="${parentEntity?.name}" maxLength="20" />
                    </opt:link>
                        <g:if test="${entity}">
                            > <span id="childEntityName">${entity?.operatingPeriodAndName}</span>
                        </g:if>
                    </g:if>
                </sec:ifLoggedIn>
                > <g:message code="import_data" /> <br/> </h4>
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${currentStep}"/>
            </g:if>
            <div class="alert alert-info">
                <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                <strong>${message(code: "importMapper.filter_data_rowCounts1", args: [temporaryImportData.importDataPerStep?.get(2)]) } <span id="datapointsSpan">${temporaryImportData.importDataPerStep?.get(4)}</span> ${message (code:"importMapper.filter_data_rowCounts2")}</strong>
            </div>
            <div class="pull-right hide-on-print">
                <g:link action="cancel" class="btn" onclick="return modalConfirm(this);"
                        data-questionstr="${message(code: 'importMapper.index.cancel')}"
                        data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                        data-titlestr="Cancel"><g:message code="cancel" /></g:link>
                <g:link controller="importMapper" action="generateExcelFromDatasets" params="[returnUserTo: 'groupedBy', entityId: entityId, childEntityId: childEntityId]" class="btn btn-primary"><g:message code="results.expand.download_excel" /></g:link>
                <g:submitButton name="continue" id="ifcContinue" value="${message(code: 'continue')}" class="btn btn-primary" />
            </div>
            <h1><g:message code="filter"/> </h1>
        </div>
    </div>

    <div class="container section">
        <g:if test="${amountOfGroupedByData}">
            <div class="sectionbody">
                <h2><g:message code="importMapper.group.title" /></h2>
                <p><g:message code="importMapper.group.info" /></p>
            </div>
            <div class="sectionbody">
                <g:hiddenField name="entityId" value="${entityId}" />
                <g:hiddenField name="childEntityId" value="${childEntityId}" />
                <div class="control-group" >
                   <g:each in="${amountOfGroupedByData}">
                       <g:set var="random" value="${UUID.randomUUID().toString()}" />
                       <g:if test="${it.value > 0}">
                           <input type="checkbox" value="${it.key}" checked="checked" name="groupedBy"/> ${it.key} (<span id="${it.key.toString().replaceAll("\\s","")}AmountContainer">${it.value}</span>)
                           <td class="last">
                               <g:set var="infoId" value="${random}info" />
                               <a href="javascript:;" class="infoBubble" rel="collapser_data" id="${infoId}">
                                   <i class="fa fa-question greenInfoBubble" aria-hidden="true" onclick="openGroupedByData('${infoId}', '${it.key}')"></i></a>
                           </td>
                           <br />
                       </g:if>
                   </g:each>
                </div>
            </div>
        </g:if>
    </div>
</g:form>

<script type="text/javascript">
    (function( $ ) {
        $.widget( "ui.combobox", {
            _create: function() {
                var required = this.element.attr("required");

                if (required)
                {
                    this.wrapper = $( "<div>" )
                            .insertBefore( this.element)
                            .attr('class', 'input-append redBorder')
                }
                else {
                    this.wrapper = $( "<div>" )
                            .insertBefore( this.element)
                            .attr('class', 'input-append');
                }
                this._createAutocomplete();
                this._createShowAllButton();
            },

            _createAutocomplete: function(args) {
                var selected = this.element.children( ":selected" ),
                        value = selected.val(),
                        label = selected.text(),
                        elementname = this.element.attr('name');

                this.element.attr('name', '');
                this.element.css('display', 'none');

                var placecholder = this.element.attr('data-autocomplete-placeholder');
                var onchange = this.element.attr("onchange");
                var id = this.element.attr("id");

                this.hiddenvalue = $("<input>")
                        .appendTo( this.wrapper )
                        .attr("type", "hidden")
                        .attr("name", elementname)
                        .val( value );

                this.input = $( "<input>" )
                        .appendTo( this.wrapper )
                        .val( label )
                        .attr( "title", "" )
                        .attr( "type", "text" )
                        .attr( "placeholder", placecholder )
                        .attr("onchange", onchange)
                        .attr("id", id)
                        .addClass( "input-xlarge" )
                        .autocomplete({
                            delay: 1,
                            minLength: 0,
                            source: $.proxy( this, "_source" )
                        })
                        .removeClass('ui-autocomplete-input')
                        .tooltip({
                            tooltipClass: "ui-state-highlight"
                        });

                if(!value) {
                    this.input.val("");
                }

                this._on( this.input, {
                    autocompleteselect: function( event, ui ) {
                        ui.item.option.selected = true;
                        this.hiddenvalue.val(ui.item._value);
                        this._trigger( "select", event, {
                            item: ui.item.option
                        });
                    },

                    autocompletechange: "_removeIfInvalid"
                });
            },

            _createShowAllButton: function() {
                var wasOpen = false;
                var input = this.input;

                var showall =  $( "<a>" )
                        .attr( "tabIndex", -1 )
                        .attr( "title", "Show All Items" )
                        .tooltip()
                        .appendTo( this.wrapper )
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        })
                        .removeClass( "ui-corner-all" )
                        .addClass( "add-on" )
                        .append('<i class="icon-chevron-down"></i>')
                        .mousedown(function() {
                            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                        })
                        .click(function() {
                            input.trigger('focus');

                            if ( wasOpen ) {
                                return;
                            }

                            input.autocomplete( "search", "" );
                        });
            },

            _source: function( request, response ) {
                var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                response( this.element.children( "option" ).map(function() {
                    var text = $( this ).text();
                    if ( this.value && ( !request.term || matcher.test(text) ) )
                        return {
                            label: jQuery.trim(text),
                            value: jQuery.trim(text),
                            _value: $(this).val(),
                            option: this
                        };
                }) );
            },

            _removeIfInvalid: function( event, ui ) {

                if ( ui.item ) {
                    return;
                }

                var value = this.input.val(),
                        valueLowerCase = value.toLowerCase(), valid = false;

                if(jQuery.trim(value) == '') {
                    this.hiddenvalue.val('');
                }

                this.element.children( "option" ).each(function() {
                    if ( $( this ).text().toLowerCase() === valueLowerCase ) {
                        this.selected = valid = true;
                        return false;
                    }
                });

                if ( valid ) {
                    return;
                }

                this.input
                        .val( "" )
                        .attr( "title", value + " didn't match any item" )
                        .tooltip( "open" );

                this.hiddenvalue.val('');

                this.element.val( "" );
                this._delay(function() {
                    this.input.tooltip( "close" ).attr( "title", "" );
                }, 2500 );
                this.input.data( "ui-autocomplete" ).term = "";
            },

            _destroy: function() {
                this.wrapper.remove();
                this.element.show();
            }
        });
    })( jQuery );

    $(function() {
        $( ".autocompletebox" ).combobox();
        var	steps = $(".step");

        $('#ifcContinue').on("click", function() {
            $.each( steps, function( i ) {
                if (!$(steps[i]).hasClass('current') && !$(steps[i]).hasClass('completed')) {
                    $(steps[i]).addClass('current');
                    $(steps[i - 1]).removeClass('current').addClass('completed');
                    return false;
                }
            })
        });
    });
</script>
</body>
</html>



