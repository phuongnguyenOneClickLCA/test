<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    def updatePublishService = grailsApplication.mainContext.getBean("updatePublishService")
%>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
    <g:if test="${!userService.getDataManager(userService.getCurrentUser())}">
        <body>
        <div class="container">
            <div class="screenheader">
                <h1>
                    Publishing updates management
                </h1>
            </div>
        </div>

        <div class="container section">
            <div class="sectionbody">
                <ul class="nav nav-tabs">
                    <li class="navInfo active" name="addUpdate" onclick="showHideChildDiv('updateMainDiv',this)"><a href="#addUpdate">Add update</a></li>
                    <li class="navInfo" name="updateList" onclick="showHideChildDiv('updateMainDiv', this);intializeTable()"><a href="#updateList">Updates published</a></li>
                </ul>
                <div id="updateMainDiv">
                    <div id="addUpdate" style="display: block">
                        <div class="formContainer">
                            <g:form action="save">
                                <label>Type <br/></label>
                                <g:select class="select2Object" name="type" from="${typeForUpdate}" optionKey="${{it.key}}"  optionValue="${{it.value}}"></g:select>
                                <br/>
                                <br/>
                                <label>Title  <input type="text" class="redBorder" name="title"/></label>
                                <br/>
                                <label>Link  <input type="text" name="link"/></label>
                                <br/>
                                <label>Additional info <br/>
                                    <textarea name="additionalInfo" rows="4" cols="10" style="width: 25%"></textarea>
                                    <p id="characterWarns" class="hidden warningHighlight"><span class="characterCounts"> </span> characters. Not safe to insert HTML tag yet </p>
                                    <p id="characterOK" class="hidden success"><span class="characterCounts"> </span> characters. Now safe to insert HTML tag</p>
                                </label>
                                <label>Expired date <br/>
                                    <g:textField name="dateExpired" class="input-xlarge datePicker" /><span class="add-on"><i class="icon-calendar"></i></span>
                                </label><br/>
                                <label>Channel features (Select at least 1)<br/></label>
                                <g:select class="redBorder" multiple="multiple" name="channelFeatureIds" from="${channelFeatures}" optionKey="${{it.id}}" optionValue="${{it.name}}"></g:select>
                                <label>License type (Select none if apply for all)<br/></label>
                                <g:select class="select2Object" multiple="multiple" name="licensesType" from="${licenseTypes}" optionKey="${{it}}"  optionValue="${{it}}"></g:select>
                                <label>License level (Select none if apply for all)<br/></label>
                                <g:select class="select2Object" multiple="multiple" name="licensesLevel" from="${licenceFeatureClass}" optionKey="${{it}}"  optionValue="${{it}}"></g:select>
                                <label>Indicator (Select none if apply for all)<br/></label>
                                <g:select class="select2Object" multiple="multiple" name="indicatorId" from="${indicatorIds}" optionKey="${{it}}" optionValue="${{it}}"></g:select>
                                <br/>
                                <label>Country (Select none if apply for all)<br/></label>
                                <g:select class="select2Object" multiple="multiple" name="countries" from="${countries}" optionKey="${{it.resourceId}}" optionValue="${{it.nameEN}}"></g:select>
                                <br/>

                                <label><input type="checkbox" name="showAsPopUp"/>  Show as popup (this will automatically remove previous popup on the same channel)</label><br/>
                                <label><input type="checkbox" name="stickyNote"/>  Sticky note (Will prioritize these for top slots of update list for users)</label><br/>
                                <label><input type="checkbox" name="disabling"/> Disable</label><br/><br/>


                                <opt:submit name="save" class="btn btn-primary pull-right">Save</opt:submit>
                            </g:form>
                        </div>

                    </div>

                    <div id="updateList" style="display: none">
                        <g:set var="updateList" value="${updatesPublished?.sort({a,b -> b.lastUpdated <=> a.lastUpdated})}"/>
                        <table class="table table-striped table-condensed table-data" id="updatePublishTable">
                            <thead>
                            <tr>
                                <th>Type</th>
                                <th>Title</th>
                                <th>Link</th>
                                <th>Additional Info</th>
                                <th>License type</th>
                                <th>License level</th>
                                <th>Indicators</th>
                                <th>Channel Features</th>
                                <th>Countries</th>
                                <th>Expiring on</th>
                                <th>Show as popup?</th>
                                <th>Sticky note?</th>
                                <th>Disabled ? </th>
                                <th>Saved / Added by</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:if test="${updateList && updateList.size() > 0}">
                            <g:each in="${updateList}" var="update">
                                <g:form action="update" >
                                    <input type="hidden" value="${update?.id}" name="id">
                                    <tr>
                                        <td class="display${update?.id}">${update?.type}</td>
                                        <td class="display${update?.id}">${update?.title}</td>
                                        <td class="display${update?.id}">${update?.link ?: "Non specified"}</td>
                                        <td class="display${update?.id}">${update?.additionalInfo ?: "N/A"}</td>
                                        <td class="display${update?.id}">${update?.licensesType ?: "Non specified"}</td>
                                        <td class="display${update?.id}">${update?.licensesLevel ?: "Non specified"}</td>
                                        <td class="display${update?.id}">${update?.indicatorId ?: "Non specified"}</td>
                                        <td class="display${update?.id}">${updatePublishService.getChannelFeatureNames(update?.channelFeatureIds) ?: "Non specified"}</td>
                                        <td class="display${update?.id}">${update?.countries ?: "Non specified"}</td>
                                        <td class="display${update?.id}"><g:formatDate date="${update?.dateExpired}" format="dd.MM.yyyy" /></td>
                                        <td class="display${update?.id}">${update?.showAsPopUp ? "TRUE" : ""}</td>
                                        <td class="display${update?.id}">${update?.stickyNote ? "TRUE" : ""}</td>
                                        <td class="display${update?.id}">${update?.disabling ? "Disabled" : ""}</td>
                                        <td>
                                            ${update?.userName}
                                        </td>
                                        <td>
                                            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                                                <opt:link action="remove" class="btn btn-danger" onclick="return modalConfirm(this);" id="${update?.id}"
                                                                                                data-questionstr="Are you sure deleting this update with title: ${update?.title}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="Delete warning"><g:message code="delete" /></opt:link>
                                            </sec:ifAnyGranted>

                                            <a href="javascript:;" class="btn btn-primary display${update?.id}" onclick="editUpdatePublish('${update?.id}', '#editUpdate')">Edit</a>
                                        </td>
                                    </tr>
                                </g:form>
                            </g:each>
                            </g:if>
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
            <div class="modal fade bigModal" id="editUpdate"></div>
        </div>
        <script>
            $(function () {
                $("[name='additionalInfo']").on("keydown change", function () {
                    var value = $(this).val().length > 120
                    $(".characterCounts").text($(this).val().length)
                    if(value){
                        $("#characterWarns").addClass("hidden")
                        $("#characterOK").removeClass("hidden")
                    }else {
                        $("#characterWarns").removeClass("hidden")
                        $("#characterOK").addClass("hidden")
                    }
                })
                $(".select2Object").select2();
                $(".datePicker").datepicker({
                    dateFormat: 'dd.mm.yy',
                    minDate: 0
                });

            })
            function intializeTable() {
                $('#updatePublishTable').dataTable({
                    "aaSorting": [],
                    "bPaginate": false,
                    "sPaginationType": "full_numbers",
                    'bDestroy': true})
            }
        </script>
        </body>
    </g:if>
</sec:ifAnyGranted>
</html>