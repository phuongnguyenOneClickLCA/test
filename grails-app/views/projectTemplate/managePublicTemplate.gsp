<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bionova.optimi.core.Constants" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>Manage Public Quick Start Templates</h1>
    </div>
</div>

<div class="container section">
    <div id="quickStartTemplate" class="sectionbody">
        <div id="admin-publicQuickStartTemplateTable">
            <g:render template="/projectTemplate/projectTemplateTable"
                      model="[licenses              : licenses, buildingTypeResourceGroup: buildingTypeResourceGroup,
                              countriesResourceGroup: countriesResourceGroup, entityClassResources: entityClassResources,
                              account               : null, templates: publicTemplates, availableIndicators: availableIndicators,
                              isPublicTable         : true, isMainUser: userService.getSuperUser(user)]"/>
        </div>
        <div style="margin-top: 20px">
            <div style="display: flex; align-items: end">
                <opt:greenSwitch id="updateLiveLicenseToTemplate" onclick="fetchTemplatesToLicenseTable(null, this)"/>
                <div class="fiveMarginLeft">
                    <b>Live update</b>
                    <span class="triggerPopoverManagePublicTemplate"
                          data-content="Enable this to update the table below on every template create/edit/delete, which will increase the load to server. Alternatively, click fetch manually.">
                        <i class="icon-question-sign"></i>
                    </span>
                </div>
            </div>
            <a href="javascript:" class="btn btn-primary tenMarginVertical" onclick="fetchTemplatesToLicenseTable(this)">Fetch "${message(code: 'link.to.license')}" table</a>
        </div>
        <div id="${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}"></div>
    </div>
</div>
<script>
    $(function() {
        keepPopoverOnHover('.triggerPopoverManagePublicTemplate', 150, 450)
        $.ajaxSetup({
            beforeSend: function () {
                if (this.url.includes('projectTemplate') && $('#updateLiveLicenseToTemplate').is(':checked')) {
                    this.data += '&fetchLicenseTable=true'
                }
            }
        });
    })

    function fetchTemplatesToLicenseTable(btnElem, clickFromLiveUpdate) {
        let invoke = true
        if (clickFromLiveUpdate && !$(clickFromLiveUpdate).is(':checked')) {
            invoke = false
        }
        const successCB = (data) => {
            if (data.output === 'error') {
                errorSwal('error', 'Oops...', 'Something went wrong!')
                $('#${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}').empty()
            } else {
                $('#${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}').empty().append(data.output)
            }
            enableSubmit(btnElem)
        }

        const errorCB = (error) => {
            console.log('error fetch admin table', error)
            $('#${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}').empty()
            errorSwal('error', 'Oops...', 'Something went wrong!')
        }
        if (invoke) {
            disableSubmit(btnElem)
            appendLoader('${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}')
            ajaxForQueryString('/app/sec/projectTemplate/publicTemplateToLicenseTable', '', successCB, errorCB, true)
        }
    }
    $('#admin-publicQuickStartTemplateTable').on('change', function() {
        $('#${Constants.PROJECT_TEMPLATE_TO_LICENSE_TABLE_ID}').empty()
    })
</script>
</body>
</html>