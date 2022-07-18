<%-- Copyright (c) 2012 by Bionova Oy --%>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<%@ page expressionCodec="html" %>
<html>
<head>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
    <g:set var="accountService" bean="accountService"/>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <asset:stylesheet src="filterCombobox.css"/>
    <style>
    .select2-results, .select2-results__options {
    max-height: 150px !important;
    overflow: auto !important;
    }
    </style>

    <script>
        <g:if test="${account?.id}">
            $(document).ready(function(){
                $('#productImageTable, #manufacturingDiagramTable').dataTable({
                    "iDisplayLength": 3,
                    "bPaginate": true,
                    "sPaginationType": "full_numbers"
                });
                $('#brandingImageTable').dataTable({
                    "iDisplayLength": 3,
                    "bPaginate": true,
                    "sPaginationType": "full_numbers"
                });
                var productImageTableCount = $('#productImageTable').dataTable({'bRetrieve': true});
                var manufacturingDiagramTableCount = $('#manufacturingDiagramTable').dataTable({'bRetrieve': true});
                var brandingImageTableCount = $('#brandingImageTable').dataTable({'bRetrieve': true});
                $("#productImageCount").html(productImageTableCount.fnGetData().length);
                $("#manufacturingDiagramCount").html(manufacturingDiagramTableCount.fnGetData().length);
                $("#brandingImageCount").html(brandingImageTableCount.fnGetData().length);

                $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
                    localStorage.setItem('activeTab', $(e.target).attr('href'));
                });
                var activeTab = localStorage.getItem('activeTab');
                if(activeTab){
                    $('#accountTabs a[href="' + activeTab + '"]').tab('show');
                    console.log(activeTab)
                    $(''+activeTab+'').show();
                } else {
                    $('#generalInfo').show();
                }
            });
        </g:if>
        <g:else>
            $(document).ready(function(){
                $('#generalInfo').show();
                var activeTab = localStorage.getItem('activeTab');
                if(activeTab){
                    localStorage.removeItem('activeTab');
                }
            });
        </g:else>
    </script>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <opt:breadcrumbsNavBar specifiedHeading="${message(code: 'account.title_page')}"/>
        <g:render template="/entity/basicinfoView"/>
    </div>
</div>


<g:if test="${showInfoMessage && !hideEcommerce}">
    <div class="container">
        <div class="alert alert-success" style="float: left; width: 1170px;">
            <button data-dismiss="alert" class="close" type="button">×</button>
            <div style="float: left; width: 650px; margin-right: 150px; font-size:12px;">
                <strong><g:message code="account.saved"/></strong><br/>
                <g:message code="account.infoMessage1"/><br/><br/>
                <g:message code="account.infoMessage2"/> <a href="${createLink(controller: 'wooCommerce', action: 'index')}">Global One Click LCA catalogue</a> <g:message code="account.infoMessage3"/>
            </div>
            <div style="float: left; font-size:12px;">
                <strong><g:message code="account.did_you_know_title"/></strong><br/>
                1. <g:message code="account.did_you_know_1"/> <opt:link controller="entity" action="form" params="[new: true, entityClass: 'building']"><g:message code="account.building"/>  <i class="fa fa-building"></i></opt:link><br/>
                2. <g:message code="account.did_you_know_2"/> <a href="http://www.oneclicklca.com/support/" target="_blank"><g:message code="account.here"/> <i class="fas fa-external-link-alt"></i></a><br/>
                3. <g:message code="account.did_you_know_3"/> <i class="fa fa-user"></i><br/>
                4. <g:message code="account.did_you_know_4"/> <g:link controller="wooCommerce" action="index" class="primary"><g:message code="account.products"/> <i class="fa fa-shopping-cart wooCommerceCart"></i> </g:link>
            </div>
        </div>
    </div>
</g:if>

<g:uploadForm action="save" name="taskForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
%{--Account name + save buttons, etc--}%
<div class="container">
    <div style="display:inline-block;">
        <g:if test="${account?.id && accountService.getEcommerceValidates(account)}">
            <div class="pull-left"><h1>${account?.companyName.capitalize()} ${message(code:'account.title_page').toLowerCase()}</h1></div>
        </g:if><g:else>
        <div class="pull-left"><h1><g:message code="account.title_create_company"/></h1></div>
    </g:else>
    </div>
    <div class="btn-group pull-right">
        <g:if test="${!hideEcommerce && account?.id && accountService.getEcommerceValidates(account)}">
            <div class="wooCommerceShopHolder pull-left" >
                <g:link controller="wooCommerce" action="index" class="btn btn-primary"><i class="fa fa-shopping-cart wooCommerceCart"></i> &nbsp; <g:message code="main.navi.view_and_buy"/></g:link>
            </div>
        </g:if>
        <g:else>
            <div class="wooCommerceShopHolder pull-left">
                <g:link controller="wooCommerce" action="index" class="btn btn-primary" params="[publicShop:true]" style="text-decoration:none !important;"><i class="fa fa-shopping-cart wooCommerceCart"></i> &nbsp; <g:message code="account.shop_without_account"/></g:link>
            </div>
        </g:else>
        <g:if test="${!formDisabled}">
            <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary" style="margin-left:10px;" />
            <opt:link action="index" class="btn"><g:message code="cancel" /></opt:link>
        </g:if>
        <g:else>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:else>
    </div>
</div>

%{-- Navigation / tab bar, if you wish to add another tab, copy one and change the li name and the href and create a new div before with a matching Id--}%
<div class="container">
    <ul class="nav nav-tabs" id="accountTabs">
        <li class="navInfo nav-item" name="generalInfo" onclick="showHideChildDiv('accountNaviDiv', this);"><a href="#generalInfo" class="nav-link active" data-toggle="tab"><g:message code="general_information.heading"/></a></li>
        <g:if test="${account?.id}">
            <li class="navInfo nav-item" name="licensesAndUsers" onclick="showHideChildDiv('accountNaviDiv', this);" ><a href="#licensesAndUsers" class="nav-link" data-toggle="tab"><g:message code="account.licenses_user"/></a></li>
            <li class="navInfo nav-item" name="dataManagement" onclick="showHideChildDiv('accountNaviDiv', this);"><a href="#dataManagement" class="nav-link" data-toggle="tab"><g:message code="account.data_management"/></a></li>
            <li class="navInfo nav-item" name="epdCreation" onclick="showHideChildDiv('accountNaviDiv', this);"><a href="#epdCreation" class="nav-link" data-toggle="tab"><g:message code="account.epd_creation"/></a></li>
            <g:if test="${isQuickStartTemplateLicensed}">
                <li class="navInfo nav-item" name="quickStartTemplate" onclick="showHideChildDiv('accountNaviDiv', this);"><a href="#quickStartTemplate" class="nav-link" data-toggle="tab"><g:message code="account.quickStartTemplate"/></a></li>
            </g:if>
            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                <li class="navInfo nav-item pull-right"  name="admin" onclick="showHideChildDiv('accountNaviDiv', this);"><a href="#admin" class="nav-link" data-toggle="tab">Sales and Administration</a></li>
            </sec:ifAnyGranted>
        </g:if>
    </ul>

    <div id="accountNaviDiv" class="tab-content">
        %{-- General Information, logo, editable user info--}%
        <div id="generalInfo" class="tab-pane show active" >
            <div class="sectionheader">
                <h2 style="margin-left: 15px;"><g:message code="general_information.heading"/></h2>
            </div>
            <g:hiddenField name="id" value="${account?.id}"/>
            <div class="spanAccount">
                <h2><g:message code="account.invoicing"/></h2>

                <div class="control-group">
                    <label for="companyName" class="control-label"><g:message code="account.company_name" /> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.companyName_help')}"></i></label>
                    <div class="controls">
                        <opt:textField name="companyName" value="${companyName}" class="input-xlarge ${companyName ?: 'redBorder'}"
                                       disabled="${formDisabled}" />
                    </div>
                </div>

                <div class="control-group">
                    <label for="addressLine1" class="control-label"><g:message code="account.address" /> 1</label>
                    <div class="controls">
                        <opt:textField name="addressLine1" value="${account?.addressLine1}" class="input-xlarge ${!account?.addressLine1 ? 'redBorder' : '' }"
                                       disabled="${formDisabled}"/>
                    </div>
                </div>

                <div class="control-group">
                    <label for="addressLine2" class="control-label"><g:message code="account.address" /> 2</label>
                    <div class="controls">
                        <opt:textField name="addressLine2" value="${account?.addressLine2}" class="input-xlarge"
                                       disabled="${formDisabled}" />
                    </div>
                </div>

                <div class="control-group hidden">
                    <label for="addressLine3" class="control-label"><g:message code="account.address" /> 3</label>
                    <div class="controls">
                        <opt:textField name="addressLine3" value="${account?.addressLine3}" class="input-xlarge"
                                       disabled="${formDisabled}" />
                    </div>
                </div>

                <div class="control-group">
                    <label for="postcode" class="control-label"><g:message code="account.postcode" /></label>
                    <div class="controls">
                        <opt:textField name="postcode" value="${account?.postcode}" class="input-xlarge"
                                       disabled="${formDisabled}" />
                    </div>
                </div>

                <div class="control-group">
                    <label for="town" class="control-label"><g:message code="account.town" /></label>
                    <div class="controls">
                        <opt:textField name="town" value="${account?.town}" class="input-xlarge ${!account?.town ? 'redBorder' : '' }"
                                       disabled="${formDisabled}" />
                    </div>
                </div>
            </div>
            <div class="spanAccount">
                <h2><g:message code="account.tax_info" /></h2>
                <div class="control-group">
                    <label for="country" class="control-label"><g:message code="account.country" /></label>
                    <div class="controls" >
                        <g:if test="${countries}">
                            <g:select disabled="${formDisabled}" name="country" id="countrySelect" from="${countries}" optionKey="${{it.isoCountryCode ? it.isoCountryCode : ''}}" optionValue="${{optimiResourceService.getLocalizedName(it)}}" value="${account?.country}" noSelection="['' : '' ]" />
                        </g:if>
                    </div>
                </div>

                <div class="control-group"${!'CA'.equals(account?.country) && !'US'.equals(account?.country) ? ' style=\"display: none;\"' : ''} id="stateDiv">
                    <label for="state" class="control-label"><g:message code="account.state" /></label>
                    <div class="controls">
                        <g:select name="state" disabled="${formDisabled}" from="${states}" optionKey="${{optimiResourceService.getLocalizedName(it)}}"
                                  optionValue="${{optimiResourceService.getLocalizedName(it)}}" value="${account?.state}" noSelection="['' : '']" />
                    </div>
                </div>

                <div class="control-group">
                    <label for="vatNumber" class="control-label"><g:message code="account.vat" /> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-html="true" data-content="${message(code: 'account.vat_help')}"></i></label>
                    <div class="controls">
                        <input type="text" placeholder="country code + vat e.q: FI12345678" name="vatNumber" id="vatNumber" value="${account?.vatNumber}"${formDisabled ? ' disabled=\"disabled\"' : ''}
                               class="input-xlarge"${!vatEditable ? ' readonly' : ''} />
                        <span id="validitySpan"><i id="validityIcon" class="fa fa-2x ${account?.vatValid ? 'fa-check oneClickColorScheme' : 'fa-times redScheme'}" aria-hidden="true"></i></span>
                        <input type="hidden" name="vatValid" id="vatValidated" value="${account?.vatValid}"/>
                    </div>
                </div>
                <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                    <div class="control-group">
                        <label for="vatNote" class="control-label"><g:message code="account.vat_note" /></label>
                        <div class="controls">
                            <opt:textField name="vatNote" id="vatNote" value="${account?.vatNote}" class="input-xlarge"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="dateOfVatValidation" class="control-label"><g:message code="account.vat_date" /></label>
                        <div class="controls">
                            <label><g:textField name="dateOfVatValidation" id="dateOfVatValidation" value="${account?.dateOfVatValidation ? formatDate(date: account.dateOfVatValidation, format: 'dd.MM.yyyy') : ''}" class="input-xlarge datepicker"
                                                style="cursor: pointer;"/><span class="add-on"><i class="icon-calendar"></i></span></label>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="emailForm" class="control-label"><g:message code="account.email_address_form" /></label>
                        <div class="controls">
                            <input type="text" name="emailForm" value="${emailForm}" class="input-xlarge"/>
                            <label class="checkbox${userService.getSuperUser(user) ? "" : " removeClicks"}"><g:checkBox name="emailFormValidationOverride" checked="${account?.emailFormValidationOverride ? true : false}" /> Ignore domain restrictions</label>
                        </div>
                    </div>
                </sec:ifAnyGranted>
                <sec:ifNotGranted roles="ROLE_SALES_VIEW">
                    <input type="hidden" name="emailForm" value="${emailForm}" />
                </sec:ifNotGranted>
            </div>
            <div class="spanAccount">
                <h2><g:message code="account.epd_template_values" /></h2>
                <div class="control-group">
                    <label for="companyWebsite" class="control-label"><g:message code="account_website" /></label>
                    <div class="controls">
                        <input type="text" ${formDisabled ? ' disabled=\"disabled\"' : ''} name="companyWebsite" value="${account?.companyWebsite}" class="input-xlarge"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="organizationPhoneNr" class="control-label"><g:message code="account.company_phone" /> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.company_epd_help')}"></i></label>
                    <div class="controls" >
                        <g:textField class="input-xlarge" type="tel" value="${account?.organizationPhoneNr}" name="organizationPhoneNr"
                                pattern="^\\+(?:[0-9]●?){4,25}[0-9]\$" disabled="${formDisabled}"
                                title="${message(code: 'user.phone.size.error')}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="organizationEmail" class="control-label"><g:message code="account.company_email" /> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.company_epd_help')}"></i></label>
                    <div class="controls">
                        <g:textField class="input-xlarge" type="email" value="${account?.organizationEmail}" name="organizationEmail"
                                     disabled="${formDisabled}" title="${message(code: 'user.username.email.invalid')}"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="organizationContactPerson" class="control-label"><g:message code="account.company_contact" /> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.company_epd_help')}"></i></label>
                    <div class="controls">
                        <g:textField class="input-xlarge" type="email" value="${account?.organizationContactPerson}" name="organizationContactPerson" disabled="${formDisabled}"/>
                    </div>
                </div>

                <h2><g:message code="account_set_carbon_price"/></h2>
                <table>
                    <thead>
                    <th><g:message code="social_cost_carbon"/> <g:message code="per_ton"/></th>
                    <th><g:message code="entity.show.unit"/></th>
                    </thead>
                    <tbody>
                    <td><input ${formDisabled ? "disabled" : ""} name="carbonCost" id="carbon_price"  type="number" step="0.01" value="${account?.carbonCost ?: '50'}" class="input-medium"/></td>
                    <g:if test="${currencySymList}">
                        <td style="vertical-align: baseline;"><g:select name="unitCarbonCost" id="unit_carbon_price"  class="input-mini" from="${currencySymList}"  optionKey="${{it}}"
                                                                        optionValue="${{it}}" value="${account?.unitCarbonCost}" noSelection="['' : '']" disabled="${formDisabled}"></g:select>
                        </td></g:if>
                    </tbody>
                </table>
            </div>
        </div>

        %{-- License Info, Users, etc--}%
        <div id="licensesAndUsers" class="tab-pane" >
            <div class="sectionheader">
                <h2 style="margin-left: 15px;"><g:message code="account.licenses_user"/></h2>
            </div>
            <g:if test="${account?.id}">
                <g:if test="${account.requestToJoinUserIds}">
                    <div class="spanAccount">
                        <label class="control-label"><g:message code="account.pending_requests"/></label>
                        <br>
                        <table class="table table-condensed" id="requestToJoinTable">
                            <tbody>
                            <g:each in="${accountService.getRequestToJoinUsers(account.requestToJoinUserIds)}">
                                <tr>
                                    <td>${it.name + " (" + it.username + ")"}</td>
                                    <g:if test="${!formDisabled}">
                                        <td>
                                            <opt:link action="acceptRequest" id="${account.id}" params="[userId: it.id]" class="btn btn-primary"><g:message code="accept" /></opt:link>
                                        </td>
                                        <td>
                                            <opt:link action="declineRequest" id="${account.id}" params="[userId: it.id]" class="btn btn-primary"><g:message code="decline" /></opt:link>
                                        </td>
                                    </g:if>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                        <hr>
                    </div>
                </g:if>
                <div class="spanAccount">
                    <div class="controls">
                        <h2><g:message code="entity.show.licenses"/></h2>
                        <g:if test="${userService.getSalesView(user)}">
                            <g:select disabled="${formDisabled}" name="ajaxLicenseIds" id="ajaxLicenseIds" onchange="addLicense('${account.id}');" from="${licenses}" optionKey="${{it.id.toString()}}" optionValue="${{it.name}}" noSelection="['' : '']" />
                        </g:if><g:else>
                            <g:select disabled="disabled" name="ajaxLicenseIds" id="ajaxLicenseIds" from=""/>
                        </g:else>
                    </div>
                    <br>
                    <table class="table table-condensed" id="licensesTable">
                        <tbody>
                        <g:each in="${accountLicenses}">
                            <tr>
                                <td>${it.name }</td>
                                <td>
                                    <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                                        <opt:link action="removeLicense" id="${account.id}" params="[licenseId: it.id]" class="btn btn-danger"><g:message code="delete"/></opt:link>
                                    </sec:ifAnyGranted>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="spanAccount">
                    <div class="controls">
                        <h2><g:message code="entity.users" /></h2>
                        <g:if test="${users}">
                            <g:select disabled="${formDisabled}" name="ajaxUserIds" id="ajaxUserIds" from="${users}" onchange="addUser('#ajaxUserIds', '${account.id}')" optionKey="${{it.id.toString()}}" optionValue="${{it.name + " (" + it.username + ")"}}" noSelection="['' : '']"  />
                        </g:if>
                        <g:else>
                            <span class="alert-warning"><g:message code="account.no_users_with_same_domain" /></span>
                        </g:else>
                    </div>
                    <br>
                    <table class="table table-condensed" id="usersTable">
                        <tbody>
                        <g:each in="${accountService.getUsers(account.userIds)}">
                            <tr>
                                <td>${it.name + " (" + it.username + ")"}</td>
                                <td>
                                    <g:if test="${!formDisabled}">
                                        <opt:link action="removeUser" id="${account.id}" params="[userId: it.id]" class="btn btn-danger"><g:message code="delete" /></opt:link>
                                    </g:if>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
                <div class="spanAccount">
                    <div class="controls">
                        <h2><g:message code="account.main_users" /></h2>
                        <g:if test="${users}">
                            <g:select disabled="${formDisabled}" name="ajaxMainUserIds" id="ajaxMainUserIds" onchange="addMainUser('#ajaxMainUserIds', '${account.id}');" from="${users}" optionKey="${{it.id.toString()}}" optionValue="${{it.name + " (" + it.username + ")"}}" noSelection="['' : '']"  />
                        </g:if>
                        <g:else>
                            <span class="alert-warning"><g:message code="account.no_users_with_same_domain" /></span>
                        </g:else>
                    </div>
                    <br>
                    <table class="table table-condensed" id="mainUsersTable">
                        <tbody>
                        <g:each in="${accountService.getMainUsers(account.mainUserIds)}">
                            <tr>
                                <td>${it.name + " (" + it.username + ")"}</td>
                                <td>
                                    <g:if test="${!formDisabled && account.mainUserIds.size() > 1}">
                                        <opt:link action="removeUser" id="${account.id}" params="[mainUserId: it.id]" class="btn btn-danger"><g:message code="delete" /></opt:link>
                                    </g:if>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </g:if>
        </div>

        %{-- Data and features management --}%
        <div id="dataManagement" class="tab-pane" >
            <div class="sectionheader">
                <h2 style="margin-left: 15px;"><g:message code="account.data_management"/></h2>
            </div>
            <div class="container">
                <div class="sectionbody">
                    <g:if test="${account?.id && accountService.getEcommerceValidates(account)}">
                        <div style="display: flex;">
                        <table class="table table-striped table-data" style="margin-left: 30px; margin-right: 30px;">
                            <thead>
                            <tr><th>%{--Name of license feature--}%</th><th>Count</th><th>%{--Edit/view button--}%</th><th>%{--Help button--}%</th>
                            </thead>
                            <tbody>
                            <tr>
                                <g:set var="privateEPDS" value="${privateEpdcs && classificationParamsInies && classificationParameterQuestionInies}"/>
                                <td><g:message code="account.upload_xml"/></td>
                                <td class="fitwidth"></td>
                                <td class="fitwidth">
                                    <div class="btn-group">
                                        <a href="javascript:" class="${!account?.id || !editable || !privateEPDS? "removeClicks" : ""} btn btn-primary dropdown-toggle" style="margin: 1px;"><g:message code="edit"/> <span class="caret-middle"></span></a>
                                        <ul class="dropdown-menu">
                                            <g:each in="${classificationParamsInies}" var="classificationParameter">
                                                <li><g:link action="uploadEPDC" params="[accountId: account?.id, classificationParameterId:classificationParameter.answerId, classificationQuestionId:classificationParameterQuestionInies.questionId]">${classificationParameter.localizedAnswer}</g:link></li>
                                            </g:each>
                                        </ul>
                                    </div>
                                    <div class="clearfix"></div>
                                </td>
                                <td class="fitwidth"><span id="helpFormDatsets" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:if test="${!privateEPDS}"><strong>${message(code: 'enterprise_feature_warning', args: [message(code: 'Special' + " Upload INIES XML EPDs. ")])}</strong><br><br></g:if><g:message code="help.organization_inies_epds"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            <tr>
                                <g:set var="privateConstructions" value="${createConstructions}"/>
                                <td><g:message code="account.private_constructions_header"/></td>
                                <td class="fitwidth">${accountPrivateConstructionsAmount}</td>
                                <td class="fitwidth"><g:link controller="construction" action="index" class="btn btn-primary ${!account?.id || !editable || !privateConstructions ? "removeClicks" : ""}" params="[accountId: account?.id]" style="margin: 1px;"><g:message code="edit"/></g:link></td>
                                <td class="fitwidth"><span id="helpFormConstructions" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:if test="${!privateConstructions}"><strong>${message(code: 'enterprise_feature_warning', args: [message(code: 'Expert') + "  Private constructions. "])}</strong><br><br></g:if><g:message code="help.organization_form"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            <tr>
                                <g:set var="privateDatasets" value="${privateDatasets}"/>
                                <td><g:message code="account.private_datasets_header"/></td>
                                <td class="fitwidth">${raw(accountPrivateDatasetsAmount)}</td>
                                <td class="fitwidth"><g:link action="privateDatasets" class="btn btn-primary ${!account?.id || !editable || !privateDatasets ? "removeClicks" : ""}" params="[accountId: account?.id]" style="margin: 1px;"><g:message code="edit"/></g:link></td>
                                <td class="fitwidth"><span id="helpFormCustomEPD" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:if test="${!privateDatasets}"><strong>${message(code:'enterprise_feature_warning', args:[message(code: 'enterprise') + "  Private datasets. "])}</strong><br><br></g:if><g:message code="help.organization_private_datasets"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            <tr>
                                <g:set var="privateClassifications" value="${privateClassifications}"/>
                                <td><g:message code="account.private_classifications"/></td>
                                <td class="fitwidth"></td>
                                <td class="fitwidth"><g:link controller="privateClassificationList" action="privateClassifications" class="btn btn-primary ${!account?.id || !editable || !privateClassifications ? "removeClicks" : ""}" params="[accountId: account?.id]" style="margin: 1px;"><g:message code="edit"/></g:link></td>
                                <td class="fitwidth"><span id="helpFormClassifications" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:if test="${!privateClassifications}"><strong>${message(code: 'enterprise_feature_warning', args: [message(code: 'Expert' + " Private classifications. ")])}</strong><br><br></g:if><g:message code="help.organization_private_classifications"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            <tr>
                                <g:set var="privateLibraries" value="${privateLibraries}"/>
                                <td><g:message code="pdl.manage_libraries"/></td>
                                <td class="fitwidth"></td>
                                <td class="fitwidth"><g:link controller="productDataLists" action="manageLibraries" style="margin: 1px;" params="[accountId: account?.id]" class="btn btn-primary ${!account?.id || !editable || !privateLibraries ? "removeClicks" : ""}"><g:message code="edit"/></g:link></td>
                                <td class="fitwidth"><span id="helpFormDatalists" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:if test="${ecoiventUncapped}"><strong>${message(code:'account.ecoinvent_uncapped_help')}</strong><br><br></g:if><g:elseif test="${!privateLibraries}"><strong>${message(code:'enterprise_feature_warning', args:[message(code: 'EPD') + " Ecoinvent 50/100/200. "])}</strong><br><br></g:elseif><g:message code="help.organization_data_lists"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            <tr>
                                <g:set var="hasFavorite" value="${hasFavoriteResource}"/>
                                <td>${g.message(code: "typeahead.group8")?.toLowerCase()?.capitalize()}</td>
                                <td class="fitwidth">${hasFavorite ? account?.favoriteMaterialIdAndUserId?.size() : 0}</td>
                                <td class="fitwidth"><g:link controller="account" action="manageFavorites" style="margin: 1px;" params="[accountId: account?.id]" class="btn btn-primary ${!account?.id || !editable || !hasFavorite ? "removeClicks" : ""}"><g:message code="edit"/></g:link></td>
                                <td class="fitwidth"><span id="helpFormFaveMats" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:if test="${!hasFavorite}"><strong>${message(code:'enterprise_feature_warning', args:[message(code: 'enterprise') + "  Private datasets. "])}</strong><br><br></g:if><g:message code="help.organization_fave_materials"/>"><i class="fas fa-question"></i></span></td>
                            </tr>
                            <tr>
                                <td><g:message code="account.view_organisation_scenarios"/></td>
                                <td class="fitwidth">
                                    <g:if test="${unapprovedScenarios}">
                                        ${approvedScenarios} + <span class="custom-badge-green">${unapprovedScenarios}</span>
                                    </g:if>
                                    <g:else>
                                        ${approvedScenarios}
                                    </g:else>
                                </td>
                                <td class="fitwidth">
                                    <g:link controller="carbonDesigner3D" action="viewScenarios"
                                            style="margin: 1px;" params="[accountId: account?.id]"
                                            class="btn btn-primary ${!account?.id || !editable || !hasOrgScenarios || !hasScenariosFeature ? "removeClicks" : ""}">
                                        <g:message code="edit"/>
                                    </g:link>
                                </td>
                                <td class="fitwidth">
                                    <span id="helpFormScenarios" class="btn btn-primary longcontent" rel="popover" data-trigger="click" data-html="true" style="margin: 1px;"
                                          data-content="
                                            <g:if test="${!hasOrgScenarios}">
                                              <strong>${message(code: 'enterprise_feature_warning', args: [message(code: 'Expert') + "  Enable Carbon Designer 3D Scenarios. "])}</strong><br><br>
                                            </g:if>
                                            <g:message code="help.organization_scenarios"/>
                                          ">
                                        <i class="fas fa-question"></i>
                                    </span>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </div>
                    </g:if>
                </div>
            </div>
        </div>

        %{-- EPD Creation tab --}%
        <div id="epdCreation" class="tab-pane" >
            <div class="sectionheader">
                <div class="pull-right">
                    <span id="brandHelpInfo" class="btn btn-primary longcontent" rel="popover" data-trigger="click"  data-html= "true" style="margin: 1px;" data-content="<g:message code="account.brand_help_info"/>"><i class="fas fa-question"></i></span>
                </div>
                <h2 style="margin-left: 15px; cursor: auto;"><g:message code="account.epd_creation"/></h2>
            </div>
            <g:if test="${account?.id}">
                <div id="uploadImageDiv" class="container">
                    <div style="margin-left: 30px">
                        <h2><g:message code="account.upload_image"/></h2>
                    </div>
                    <div id="imageUpload" style="display: flex; margin-left: 30px;">
                        <div style="margin: 9px">
                            <label class="control-label bold"><g:message code="account.image_brand_name"/> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.image_name_help')}"></i></label>
                            <div class="controls">
                                <opt:textField id="imageName" name="name" value="" class="input-xlarge" disabled="${formDisabled}"
                                               onkeyup="validateName(this, '${message(code: "invalid_character")}', 'uploadAccountImageButton', true)"/>
                                <div class="hidden"><p class="warningRed"></p></div>
                            </div>
                        </div>
                        <div style="margin: 9px">
                            <label class="control-label bold"><g:message code="account.choose_image"/></label>
                            <div class="controls">
                                <input ${formDisabled ? "disabled" : ""} type="file" name="accountImage" class="companyImages" id="accountImage">
                            </div>
                            <span class="help-block" style="max-width: 230px;"><g:message code="account.image_limit_text"/></span>
                        </div>
                        <div style="margin: 9px">
                            <label class="control-label bold"><g:message code="account.image_style"/> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.image_style_help')}"></i></label>
                            <div class="controls">
                                <div style="display: flex;">
                                    <input ${formDisabled ? "disabled" : ""} type="radio" id="boxed" name="imageStyle" value="boxed" checked="checked">
                                    <label for="boxed" style="margin-left: 5px"><g:message code="account.boxed_image"/></label>
                                </div>
                                <div style="display: flex;">
                                    <input ${formDisabled ? "disabled" : ""} type="radio" id="full" name="imageStyle" value="full">
                                    <label for="full" style="margin-left: 5px"><g:message code="account.fullpage_image"/></label>
                                </div>
                            </div>
                        </div>
                        <div style="margin: 9px">
                            <label class="control-label bold"><g:message code="account.image_type"/> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.image_type_help')}"></i></label>
                            <div class="controls">
                                <div style="display: flex;">
                                    <input ${formDisabled ? "disabled" : ""} type="radio" id="productImage" name="imageType" value="productImage" checked="checked">
                                    <label for="productImage" style="margin-left: 5px"><g:message code="account.product_image"/> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.product_size_help')}"></i></label>
                                </div>
                                <div style="display: flex;">
                                    <input ${formDisabled ? "disabled" : ""} type="radio" id="manufacturingDiagram" name="imageType" value="manufacturingDiagram">
                                    <label for="manufacturingDiagram" style="margin-left: 5px"><g:message code="account.manufacturing_diagram"/> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.manu_size_help')}"></i></label>
                                </div>
                                <div style="display: flex;">
                                    <input ${formDisabled ? "disabled" : ""} type="radio" id="brandingImage" name="imageType" value="brandingImage">
                                    <label for="brandingImage" style="margin-left: 5px"><g:message code="account.brand_image"/> <i class="icon-question-sign" rel="popover" data-trigger="hover" data-content="${message(code: 'account.brand_size_help')}"></i></label>
                                </div>
                            </div>
                        </div>
                        <div style="margin: 9px">
                            <div class="controls">
                                <button id="uploadAccountImageButton" ${formDisabled ? "disabled" : ""} class="btn btn-primary" type='button' onclick="uploadAccountImage('${account.id}');"><g:message code="upload"/></button>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="imageTypeContainer" class="container" style="width: auto; margin: 0 30px 0 30px;">
                    <div class="sectionheader">
                        <button type="button" class="pull-left sectionexpander" data-name="productImage" ><i id="collapseableProductImageExpanded" class="icon icon-chevron-down expander"></i></button>
                        <div class="sectioncontrols pull-right">
                            <h2><span id="productImageCount">0</span> / 20</h2>
                        </div>
                        <h2 class="h2expander" onclick="toggleSection('collapseableProductImageExpanded', 'productImageDiv')"
                            style="margin-left: 15px;"><g:message code="account.product_images"/></h2>

                    </div>
                    <div id="productImageDiv" style="margin: 0 30px 10px 30px;">
                        <table id="productImageTable" class="table table-striped">
                            <thead>
                                <tr><th><g:message code="default"/></th><th><g:message code="account.image_name"/></th><th><g:message code="account.image_style"/></th><th><g:message code="image"/></th><th style="width: auto !important;">&nbsp;</th></tr>
                            </thead>
                            <tbody>
                            <g:if test="${productImages}">
                                <g:each in="${productImages}" var="productImage" >
                                    <g:set var="isDefaultImage" value="${account.defaultProductImage.equals(productImage.id.toString())}"/>
                                    <tr>
                                        <td><g:if test="${isDefaultImage}"><i class="defaultImage far fa-check-circle"></i></g:if></td>
                                        <td>${productImage.name}</td>
                                        <td>${productImage.style}</td>
                                        <td><opt:displayDomainClassImage imageSource="${productImage?.image}" elementId="${productImage.name}ProductImage" /></td>
                                        <td><g:link action="setDefaultImage" params="[accountId: account.id, accountImageId: productImage.id.toString()]" class="${isDefaultImage? "removeClicks" : ""}"><input ${formDisabled ? "disabled" : ""} type="button" value="${message(code:'account.set_default')}" class="<g:if test='${account.defaultProductImage.equals(productImage.id.toString())}'>removeClicks</g:if> btn btn-primary" style="width: 100%; margin-bottom: 3px;"/></g:link>
                                            <g:link action="deleteAccountImage" params="[accountId: account.id, accountImageId: productImage.id.toString()]"><input ${formDisabled ? "disabled" : ""} type="button" value="${message(code:'delete')}" class="btn btn-danger" style="width: 100%; margin-bottom: 3px;"/></g:link></td>
                                    </tr>
                                </g:each>
                            </g:if>
                            </tbody>
                        </table>
                    </div>
                    <div class="sectionheader">
                        <button type="button" class="pull-left sectionexpander" data-name="manufacturingDiagram" ><i id="collapseableManufacturingExpanded" class="icon icon-chevron-down expander"></i></button>
                        <div class="sectioncontrols pull-right">
                            <h2><span id="manufacturingDiagramCount">0</span> / 20</h2>
                        </div>
                        <h2 class="h2expander" onclick="toggleSection('collapseableManufacturingExpanded', 'manufacturingDiagramDiv')"
                            style="margin-left: 15px;"><g:message code="account.manufacturing_diagrams"/></h2>
                    </div>
                    <div id="manufacturingDiagramDiv" style="margin: 0 30px 10px 30px;">
                        <table id="manufacturingDiagramTable" class="table table-striped">
                            <thead>
                                <tr><th><g:message code="default"/></th><th><g:message code="account.image_name"/></th><th><g:message code="account.image_style"/></th><th><g:message code="image"/></th><th style="width: auto !important;">&nbsp;</th></tr>
                            </thead>
                            <tbody>
                            <g:if test="${manufacturingDiagrams}">
                                <g:each in="${manufacturingDiagrams}" var="manufacturingDiagram" >
                                    <g:set var="isDefaultImage" value="${account.defaultManufacturingDiagram.equals(manufacturingDiagram.id.toString())}"/>
                                    <tr>
                                        <td><g:if test="${isDefaultImage}"><i class="defaultImage far fa-check-circle"></i></g:if></td>
                                        <td>${manufacturingDiagram.name}</td>
                                        <td>${manufacturingDiagram.style}</td>
                                        <td><opt:displayDomainClassImage imageSource="${manufacturingDiagram?.image}" elementId="${manufacturingDiagram.name}ManufacturingDiagram" /></td>
                                        <td><g:link action="setDefaultImage" params="[accountId: account.id, accountImageId: manufacturingDiagram.id.toString()]" class="${isDefaultImage? "removeClicks" : ""}"><input ${formDisabled ? "disabled" : ""} type="button" value="${message(code:'account.set_default')}" class="btn btn-primary" style="width: 100%; margin-bottom: 3px;"/></g:link>
                                            <g:link action="deleteAccountImage" params="[accountId: account.id, accountImageId: manufacturingDiagram.id.toString()]"><input ${formDisabled ? "disabled" : ""} type="button" value="${message(code:'delete')}" class="btn btn-danger" style="width: 100%; margin-bottom: 3px;"/></g:link></td>
                                    </tr>
                                </g:each>
                            </g:if>
                            </tbody>
                        </table>
                    </div>
                    <div class="sectionheader">
                        <button type="button" class="pull-left sectionexpander" data-name="brandingImage" ><i id="collapseableBrandExpanded" class="icon icon-chevron-down expander"></i></button>
                        <div class="sectioncontrols pull-right">
                            <h2><span id="brandingImageCount">0</span> / 20</h2>
                        </div>
                        <h2 class="h2expander" onclick="toggleSection('collapseableBrandExpanded', 'brandingImageDiv')"
                            style="margin-left: 15px;"><g:message code="account.brand_images"/></h2>
                    </div>
                    <div id="brandingImageDiv" style="margin: 0 30px 10px 30px;">
                        <table id="brandingImageTable" class="table table-striped">
                            <thead>
                                <tr><th><g:message code="default"/></th><th><g:message code="account.brand_name"/></th></th><th><g:message code="image"/></th><th style="width: auto !important;">&nbsp;</th></tr>
                            </thead>
                            <tbody>
                            <g:if test="${brandingImages}">
                                <g:each in="${brandingImages}" var="brandImage" >
                                    <g:set var="isDefaultImage" value="${account.defaultBrandingImage.equals(brandImage.id.toString())}"/>
                                    <tr>
                                        <td><g:if test="${isDefaultImage}"><i class="defaultImage far fa-check-circle"></i></g:if></td>
                                        <td>${brandImage.name}</td>
                                        <td><opt:displayDomainClassImage imageSource="${brandImage?.image}" elementId="${brandImage.name}BrandImage" /></td>
                                        <td><g:link action="setDefaultImage" params="[accountId: account.id, accountImageId: brandImage.id.toString()]" class="${isDefaultImage? "removeClicks" : ""}"><input ${formDisabled ? "disabled" : ""} type="button" value="${message(code:'account.set_default')}" class="<g:if test='${account.defaultBrandingImage.equals(brandImage.id.toString())}'>removeClicks</g:if> btn btn-primary" style="width: 100%; margin-bottom: 3px;"/></g:link>
                                            <g:link action="deleteAccountImage" params="[accountId: account.id, accountImageId: brandImage.id.toString()]"><input ${formDisabled ? "disabled" : ""} type="button" value="${message(code:'delete')}" class="btn btn-danger" style="width: 100%; margin-bottom: 3px;"/></g:link></td>
                                    </tr>
                                </g:each>
                            </g:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </g:if>
        </div>

        %{-- Quick Start Template tab --}%
        <g:if test="${isQuickStartTemplateLicensed}">
            <div id="quickStartTemplate" class="tab-pane" >
                <g:set var="isMainUser" value="${account?.mainUserIds?.contains(user?.id?.toString()) || userService.getSuperUser(user)}"/>
                <g:render template="/projectTemplate/projectTemplateTable" model="[licenses: accountLicenses, buildingTypeResourceGroup: buildingTypeResourceGroup, account: account, templates: accountProjectTemplates, availableIndicators: availableIndicators, isPublicTable: false, isMainUser: isMainUser]"/>
                <g:render template="/projectTemplate/projectTemplateToLicenseTable" model="[licenses: accountLicenses, accountId: account?.id?.toString(), templates: accountProjectTemplates, isPublicTable: false, isMainUser: isMainUser, user: user]"/>
            </div>
        </g:if>

        %{-- Tab for sales / administrative related duties, only visual to sales-team and above --}%
        <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
            <div id="admin" class="tab-pane" >
                <div class="sectionheader">
                    <h2 style="margin-left: 15px;">Sales and Administration</h2>
                </div>
                <g:if test="${account?.id}">
                    <div class="span4" style="margin-left: 20px">
                        <div class="sectionbody">
                            <h2><g:message code="account_catalogues" /></h2>
                            <div class="controls">
                                <label class="control-label"><g:message code="account_catalogues" /></label>
                                <g:select disabled="${formDisabled}" name="ajaxSlugs" id="ajaxSlugs" onchange="addCatalogue('#ajaxSlugs', '${account.id}');" from="${catalogues}" optionKey="${{it.slug}}" optionValue="${{it.name }}" noSelection="['' : '']"  />
                            </div>
                            <br>
                            <table class="table table-condensed" id="cataloguesTable">
                                <tbody>
                                <g:each in="${accountService.getCatalogues(account?.catalogueSlugs)}">
                                    <tr>
                                        <td>${it.slug}</td>
                                        <td>${it.name}</td>
                                        <td>
                                            <g:if test="${!formDisabled}">
                                                <opt:link action="removeCatalogueFromAccount" id="${account.id}" params="[slug: it.slug, id: it.id]" class="btn btn-danger"><g:message code="delete" /></opt:link>
                                            </g:if>
                                        </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                        <div class="span4" style="margin-left: 20px">
                            <h2>Account login page settings</h2>
                            <div class="control-group">
                                <label class="control-label bold">Branding logo url</label>
                                <p>optimal size: width="200px" height="50px", higher will be scaled down</p>
                                <div class="controls">
                                    <g:if test="${account?.branding}">
                                        <div id="brandImage" class="companyImages">
                                            <div class="img-holder" style="margin-top:30px;">
                                                <opt:displayDomainClassImage imageSource="${account?.branding}" elementId="brandingImage" />
                                                <g:if test="${!formDisabled}">
                                                    <a href="javascript:" class="link btn btn-danger btn-small" onclick="changeImage('brandImage', 'branding', 'branding','true')">Change image</a>
                                                </g:if>
                                            </div>
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <input type="file"  name="branding" class="companyImages" id="branding">
                                    </g:else>
                                    <label class="checkbox"><g:checkBox disabled="${formDisabled}" name="showLogoInSoftware" id="showLogoInSoftware" checked="${account?.showLogoInSoftware}" />Show logo in software</label>
                                </div>
                            </div>

                            <div class="control-group">
                                <label for="loginKey" class="control-label bold">Account login key</label>
                                <div class="controls">
                                    <g:hiddenField name="loginKey" value="${companyName?.replaceAll("\\s","")?.toLowerCase()}"/>
                                    <g:if test="${account?.visibleLoginUrl}">
                                        <opt:textField name="visibleLoginUrl" id="visibleLoginKey" readOnly="readOnly" value="${account?.visibleLoginUrl}" class="input-xlarge"/>
                                    </g:if><g:else>
                                        <opt:textField name="visibleLoginUrl" id="visibleLoginKey" readOnly="readOnly" value="?accountKey=${companyName?.replaceAll("\\s","")?.toLowerCase()}" class="input-xlarge"/>
                                    </g:else>
                                    <i class="far fa-clipboard copyToClipBoard" onclick="copyText('visibleLoginKey');"></i>
                                    <div class="btn-group">
                                        <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown">Choose a channel to add to key</a><ul class="dropdown-menu">
                                        <g:each in="${channelFeatures}" var="channelFeature">
                                            <li><a href="javascript:" onclick="addChannelTokenToLoginKey('${channelFeature?.token}')" id="channelFeatureToLoginKey">${channelFeature.token}</a></li>
                                        </g:each>
                                    </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label bold">Branding background image for login</label>
                                <div class="controls">
                                    <p> optimals size: width="1920px" height="1080px", higher will be scaled down </p>
                                    <g:if test="${account?.backgroundImage}">
                                        <div id="backgroundImageContainer" class="companyImages">
                                            <div class="img-holder" style="margin-top:30px;">
                                                <opt:displayDomainClassImage imageSource="${account?.backgroundImage}"  elementId="backgroundImage"/>
                                                <g:if test="${!formDisabled}">
                                                    <a href="javascript:" class="link btn btn-danger btn-small" onclick="changeImage('backgroundImageContainer', 'backgroundImage','backgroundImage')">Change image</a>
                                                </g:if>
                                            </div>
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <input type="file"  name="backgroundImage" class="companyImages" id="backgroundImage">
                                    </g:else>
                                </div>
                            </div>
                            <div class="control-group">
                                <label for="loginBoxColor" class="control-label">Loginbox backgroundcolor</label>
                                <div class="controls">
                                    <opt:textField  name="loginBoxColor" value="${account?.loginBoxColor}" class="input-xlarge"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label for="submitButtonColor" class="control-label">Loginbox submit color</label>
                                <div class="controls">
                                    <opt:textField  name="submitButtonColor" value="${account?.submitButtonColor}" class="input-xlarge"/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label for="fontColor" class="control-label">Login page links  color</label>
                                <div class="controls">
                                    <opt:textField  name="fontColor" value="${account?.fontColor}" class="input-xlarge"/>
                                </div>
                            </div>
                        </div>
                    </sec:ifAnyGranted>
                    <g:if test="${brandingImages}">
                        <div class="span4" style="margin-left: 20px">
                            <div class="sectionbody">
                                <h2>Brand Ids list</h2>
                                <table class="table table-condensed" id="brandingImageIds">
                                    <thead>
                                        <tr>
                                            <th>Brand</th>
                                            <th>Id</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <g:each in="${brandingImages}" var="brandImage">
                                        <tr>
                                            <td>${brandImage.name}</td>
                                            <td>${brandImage.id.toString()}</td>
                                        </tr>
                                    </g:each>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </g:if>
                </g:if>
            </div>
        </sec:ifAnyGranted>
    </div>
</div>
</g:uploadForm>


<script type="text/javascript">

    $(function () {
        var popOverSettings = {
            placement: 'top',
            container: 'body',
            html: true,
            template: '<div class="popover popover-fade" style=" display: block; max-width: 180px;"><div class="arrow"></div><div class="popover-content"></div></div>'
        };
        $(".longcontent[rel=popover]").popover(popOverSettings)
            .on('click', function(e) {
                $(".longcontent[rel=popover]").not(this).popover('hide');
            });
    });

    $('#vatNumber').on('input', function () {
        var countrycode = $(this).val().substring(0, 2);

        if (countrycode) {
            countrycode = countrycode.toUpperCase()
        }
        var vatNumber = $(this).val().substring(2);

        var EUCountryCodes =  ['AT', 'BE', 'BG', 'HR', 'CY', 'CZ', 'DK', 'EE', 'FI', 'FR', 'DE', 'EL', 'HU',
            'IE', 'IT', 'LV', 'LT', 'LU', 'MT', 'NL', 'PL', 'PT', 'RO', 'SK', 'SI', 'ES', 'SE', 'GB'];

        if ($(this).val().length >= 10 && countrycode && EUCountryCodes.contains(countrycode) && vatNumber) {

            $('#validityIcon').hide();
            $('#validitySpan').append('<i id="validatinSpinner"  class="fa fa-spinner fa-spin fa-2x fa-fw"></i>');

            $.ajax({
                url: '/app/sec/account/validateVat',
                data: {
                    countryCode: countrycode,
                    vatNumber: vatNumber
                },
                type: 'POST',
                dataType:'json',
                success: function (data) {
                    if (data) {
                        $('#vatValidated').val(data.validity);

                        $('#validatinSpinner').remove();
                        if (data.validity && data.validity === "true") {
                            $('#vatNote').val(data.companyName + " -verified by European comission");
                            $('#dateOfVatValidation').val(data.requestDate);
                            $('#validityIcon').show();
                            $('#validityIcon').removeClass('fa-times redScheme').addClass('fa-check oneClickColorScheme')
                        } else {
                            $('#vatNote').val('');
                            $('#dateOfVatValidation').val('');
                            $('#validityIcon').show();
                            $('#validityIcon').removeClass('fa-check oneClickColorScheme').addClass('fa-times redScheme')
                            console.log("vat is not valid")
                        }
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("error" + errorThrown)
                }
            });
        } else {
            $('#vatNote').val('');
            $('#dateOfVatValidation').val('');
            $('#validityIcon').removeClass('fa-check oneClickColorScheme').addClass('fa-times redScheme')
        }

    });

    $(function () {
        if ($('#countrySelect').val() === "") {
            $('#countrySelect').addClass('redBorder')
        }
        var allSelects = $('select').not('.maskWithSelect2, .maskWithSelect2AllowClear');
        if (allSelects) {
            $(allSelects).select2({
                placeholder:'<g:message code="select"/>',
                containerCssClass:":all:"
            }).maximizeSelect2Height();
        }

        var validFromElem= document.getElementById("dateOfVatValidation")
        var validFrom = $(validFromElem).val();
        if (validFrom && validFrom !== "undefined") {
            var day = validFrom.substring(0, 2);
            var month = validFrom.substring(3, 5);
            var year = validFrom.substring(6, 10);
            var startDate = new Date(year, month - 1, day);

            $("#dateOfVatValidation").datepicker({
                dateFormat: 'dd.mm.yy',
                changeMonth: true,
                changeYear: true,
                minDate: startDate
            });
        }

    });



    function uploadAccountImage(accountId) {
        if (accountId) {
            var messageContainer = $("#messageContent");
            var imageName = $('#imageName').val();
            var imageType = $("input[type='radio'][name='imageType']:checked").val();
            var imageStyle = $("input[type='radio'][name='imageStyle']:checked").val();
            var imageFile = $('#accountImage')[0].files[0];

            if (!imageFile || imageName.length === 0) {
                $(messageContainer).append(jsFlashMessages("error", "${g.message(code:"account.image_info_missing")}"))
                return false;
            } else {
                if ((imageFile.size / 1024 / 1024) <= 2) {
                    var productImageTable = $('#productImageTable').dataTable({'bRetrieve': true});
                    var manufacturingDiagramTable = $('#manufacturingDiagramTable').dataTable({'bRetrieve': true});
                    var brandingImageTable = $('#brandingImageTable').dataTable({'bRetrieve': true});
                    var imageTypeAllowed = false;
                    if (imageType === "productImage" && productImageTable.fnGetData().length < 20) {
                        imageTypeAllowed = true;
                    } else if (imageType === "manufacturingDiagram" && manufacturingDiagramTable.fnGetData().length < 20) {
                        imageTypeAllowed = true;
                    } else if (imageType === "brandingImage" && brandingImageTable.fnGetData().length < 20) {
                        imageTypeAllowed = true;
                    }
                    if (imageTypeAllowed) {
                        var formData = new FormData();
                        formData.append('accountId', accountId);
                        formData.append('imageName', imageName);
                        formData.append('imageType', imageType);
                        formData.append('imageStyle', imageStyle);
                        formData.append('accountImage', imageFile);

                        if (formData) {
                            $.ajax({
                                headers: getCsrfHeader(),
                                type: 'POST',
                                processData: false,
                                contentType: false,
                                data: formData,
                                url: '/app/sec/account/addAccountImage',
                                success: function (data) {
                                    if (data) {
                                        if (data.errorMessage) {
                                            $(messageContainer).append(jsFlashMessages("error", data.errorMessage))
                                        } else {
                                            $('#imageName').val("")
                                            $('#accountImage').val("")
                                            if (imageType === "productImage") {
                                                productImageTable.fnAddData([data.default, data.name, data.style, data.image, data.buttons]);
                                                $("#productImageCount").html(productImageTable.fnGetData().length);
                                                $(messageContainer).append(jsFlashMessages("success", "${g.message(code:"account.successfully_added_image")} " + data.name))
                                            } else if (imageType === "manufacturingDiagram") {
                                                manufacturingDiagramTable.fnAddData([data.default, data.name, data.style, data.image, data.buttons]);
                                                $("#manufacturingDiagramCount").html(manufacturingDiagramTable.fnGetData().length);
                                                $(messageContainer).append(jsFlashMessages("success", "${g.message(code:"account.successfully_added_image")} " + data.name))
                                            } else if (imageType === "brandingImage") {
                                                brandingImageTable.fnAddData([data.default, data.name, data.image, data.buttons]);
                                                $("#brandingImageCount").html(brandingImageTable.fnGetData().length);
                                                $(messageContainer).append(jsFlashMessages("success", "${g.message(code:"account.successfully_added_image")} " + data.name))
                                            }
                                            fadetoggle()

                                        }

                                    }
                                },
                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    if (XMLHttpRequest.responseText) {
                                        $(messageContainer).append(jsFlashMessages("error", XMLHttpRequest.responseText));
                                    }
                                }
                            });
                        }
                    }  else {
                        $(messageContainer).append(jsFlashMessages("error", "${g.message(code:'attachment.capacityForTypeExceeded')}"))
                        return false;
                    }

                } else {
                    $(messageContainer).append(jsFlashMessages("error", "${g.message(code:'attachment.fileToBig2')}"))
                    return false;
                }
            }
        }
    }

    function fadetoggle(){
        $('.fadetoggle').fadeIn(function (){
            var delaytime = $(this).attr('data-fadetoggle-delaytime');
            if(!delaytime) {
                delaytime = 3000
            }
            $(this).delay(delaytime).fadeOut(function (){
                $(this).alert('close')
            })

        });
    }

    /**
    * Return div content for a flash.?Alert
    * @param  {String} type - "error" / "success"
    * @param  {String} message - this is the message to display
    * @return {String} Div content to append, with type + message embedded into the
    */
    function jsFlashMessages(type, message) {
        if (type === "error") {
            return "<div class=\"alert alert-error hide-on-print\">" +
                "   <i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                "   <strong>" + message + "</strong>" +
                "</div>"
        } else if (type === "success") {
            return "<div class=\"alert alert-success fadetoggle hide-on-print data-fadetoggle-delaytime=\"7000\"\">" +
                "   <i class=\"far fa-thumbs-up pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                "   <i data-dismiss=\"alert\" class=\"fas fa-circle-notch fa-spin close\"></i>" +
                "   <strong>" + message + "</strong>" +
                "</div>"
        }
    }

    function addChannelTokenToLoginKey(channelToken) {
       var loginKeyBox = $('#visibleLoginKey');
       var currentValue = $(loginKeyBox).val();
       console.log("channelToken " +channelToken)
        console.log("currentVal " + currentValue)
        $(loginKeyBox).val(currentValue.split('&')[0] + '&channelToken=' + channelToken).trigger('change');
       console.log("nev val" + currentValue)
    }


    function addUser(selectId, accountId) {
        if (selectId && accountId) {
            var userId = $(selectId).val();

            if (userId) {
                $.ajax({
                    type: 'POST', data: 'id=' + accountId + '&userId=' + userId, url: '/app/sec/account/addUser',
                    success: function (data, textStatus) {
                        $('#usersTable').append(data.output);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        }
    }

    function addCatalogue(selectId, accountId) {
        if (selectId && accountId) {
            var slug = $(selectId).val();

            if (slug) {
                $.ajax({
                    type: 'POST', data: 'id=' + accountId + '&slug=' + slug, url: '/app/sec/account/addCatalogue',
                    success: function (data, textStatus) {
                        $('#cataloguesTable').append(data.output);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        }
    }

    function addMainUser(selectId, accountId) {
        if (selectId && accountId) {
            var userId = $(selectId).val();

            if (userId) {
                $.ajax({
                    type: 'POST', data: 'id=' + accountId + '&mainUserId=' + userId, url: '/app/sec/account/addUser',
                    success: function (data, textStatus) {
                        $('#mainUsersTable').append(data.output);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        }
    }

    function addLicense(accountId) {
        if (accountId) {
            var licenseId = $('#ajaxLicenseIds').val();

            if (licenseId) {
                $.ajax({
                    type: 'POST', data: 'id=' + accountId + '&licenseId=' + licenseId, url: '/app/sec/account/addLicense',
                    success: function (data, textStatus) {
                        $('#licensesTable').append(data.output);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
        }
    }

    $('#country').on('change', function() {
        var country = this.value;
        var element = document.getElementById('stateDiv');

        if ('USA' == country || 'canada' == country) {
            element.style.display = 'block';
        } else {
            element.style.display = 'none';
            $('#state').val('');
        }
    });
    var element = document.getElementById('');

    if (element) {
        if (element.style.display == 'none') { // if it is checked, make it
            // visible, if not, hide it
            element.style.display = 'block';
            element.style.float = 'left';
        } else {
            element.style.display = 'none';
        }
    }

</script>
</body>
</html>



