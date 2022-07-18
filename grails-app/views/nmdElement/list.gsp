<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 1.4.2021
  Time: 10.59
--%>

<%@ page import="com.bionova.optimi.core.service.NmdApiService" contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
    <g:if test="${!userService.getDataManager(userService.getCurrentUser())}">
        <body>
        <div class="container">
            <div class="screenheader">
                <h1>
                    Active NMD Elements
                </h1>
            </div>

        </div>

        <div class="container">
            <g:uploadForm controller="nmdElement" action="uploadNmdElementExcel" useToken="true" method="post" novalidate="novalidate">
                <div id="left" style="width: 100%;">
                    <div class ="container-fluid center-block" style="margin-left:50px !important; margin-right: 50px !important;">
                        <div class="pull-left">
                            <div class="control-group" style="margin-bottom: 30px;">
                                <label class="control-label">
                                    <strong><g:message code="importMapper.index.choose_file"/></strong>
                                </label>
                                <input type="file" name="xlsFile" class="input-xlarge" value=""/>
                            </div>

                            <div>
                                <button class="btn btn-primary groupDisable"
                                        value="submit">Upload</button>
                            </div>
                        </div>
                    </div>
                </div>
            </g:uploadForm>
            <div>
                <button class="btn btn-primary groupDisable"
                        onclick="updateElementManually(this)">
                    Update NMD element with API manually from ${NmdApiService.NMD_FALLBACK_DATE}
                    <i style="margin-left:2px; " class="hidden fas fa-circle-notch fa-spin white-font trigger"></i>
                </button>
            </div>
            <g:render template="/import/checkNmdElement"/>
        </div>

        <div class="container">
            <table class="table table-condensed table-striped">
                <tr>
                    <th>elementId (Element_ID)</th>
                    <th>elementType</th>
                    <th>code (Code)</th>
                    <th>name (Elementnaam)</th>
                    <th>description (FunctioneleBeschrijving)</th>
                    <th>unitId (FunctioneleEenheidID)</th>
                    <th>unit (OCLID Unit)</th>
                    <th>isNlSfb (IsNLsfB)</th>
                    <th>isRaw (IsRAW)</th>
                    <th>additionalData (Toelichting)</th>
                    <th>isPart (IsOnderdeel)</th>
                    <th>cuasId (CUAS_ID)</th>
                    <th>active (IsGedeactiveerd)</th>
                    <th>isChapter (IsHoofdstuk)</th>
                    <th>mandatory (Verplicht)</th>
                    <th>isException (IsUitzondering)</th>
                    <th>isProcess (IsProces)</th>
                    <th>functions (Functie)</th>
                    <th>parentElementIds</th>
                    <th>activationDate (DatumActief)</th>
                    <th>deactivationDate (DatumInActief)</th>
                    <th>importSource</th>
                    <th>importDate</th>
                    <th>updatedDate</th>
                    <th>nmdUpdateDate</th>
                </tr>
                <g:if test="${nmdElements}">
                    <g:each in="${nmdElements.sort { a, b -> a.elementId <=> b.elementId }}" var="nmdElement">
                        <tr>
                            <td>${nmdElement.elementId}</td>
                            <td>${nmdElement.elementType}</td>
                            <td>${nmdElement.code}</td>
                            <td>${nmdElement.name}</td>
                            <td>${nmdElement.description}</td>
                            <td>${nmdElement.unitId}</td>
                            <td>${nmdElement.unit}</td>
                            <td>${nmdElement.isNlSfb}</td>
                            <td>${nmdElement.isRaw}</td>
                            <td>${nmdElement.additionalData}</td>
                            <td>${nmdElement.isPart}</td>
                            <td>${nmdElement.cuasId}</td>
                            <td>${nmdElement.active}</td>
                            <td>${nmdElement.isChapter}</td>
                            <td>${nmdElement.mandatory}</td>
                            <td>${nmdElement.isException}</td>
                            <td>${nmdElement.isProcess}</td>
                            <td>${nmdElement.functions}</td>
                            <td>${nmdElement.parentElementIds}</td>
                            <td>${nmdElement.activationDate}</td>
                            <td>${nmdElement.deactivationDate}</td>
                            <td>${nmdElement.importSource}</td>
                            <td>${nmdElement.importDate}</td>
                            <td>${nmdElement.updatedDate}</td>
                            <td>${nmdElement.nmdUpdateDate ?: ''}</td>
                        </tr>
                    </g:each>
                </g:if>
            </table>

        </div>
        <script type="text/javascript">
            function updateElementManually(btn) {
                $(btn).find('.trigger').removeClass('hidden')
                disableButton('.groupDisable')
                $.ajax({
                    type: 'POST',
                    url: '/app/sec/admin/nmdElement/getApiUpdateForNmdElementManual',
                    data: "test query",
                    success: function (data) {
                        console.log(data)
                        window.location.reload()
                    },
                    error: function (error) {
                        console.log(error)
                        enableButton('.groupDisable')
                    }
                })
            }
        </script>
        </body>
    </g:if>
</sec:ifAnyGranted>
</html>