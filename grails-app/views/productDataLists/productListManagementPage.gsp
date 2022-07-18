<!DOCTYPE html>
<html>
<%@ page expressionCodec="html" %>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Data list management page</h1>
    </div>
</div>
<div class="container">
    <div class="sectionbody">
        <ul class="nav nav-tabs">
            <li class="navInfo active" name="premadeLists" onclick="showHideChildDiv('productListsMainDiv', this);"><a href="#premadeLists">Premade Lists (${premadeListCounts ? premadeListCounts.size() : 0})</a></li>
            <li class="navInfo" name="companyLists" onclick="showHideChildDiv('productListsMainDiv', this);"><a href="#companyLists">Organisation Lists (${companyDataLists ? companyDataLists.size() : 0})</a></li>
            <li class="navInfo" name="companyListsOld" onclick="showHideChildDiv('productListsMainDiv', this);"><a href="#companyListsOld">All data lists (${companyListCounts ? companyListCounts.size() : 0})</a></li>

        </ul>
        <div id="productListsMainDiv">
            <div id="premadeLists" style="display: none;">
                <div class="wrapperForNewProductLists container">
                    <a href="javascript:" class="btn btn-primary" id="toggleNewGroup"><i id="plusMinus" class="icon icon-white icon-plus"></i>  Add a new data list</a>
                    <div id="addNewProductListGroup" class="hidden">
                        <g:uploadForm controller="productDataLists" action="addNewProductList" style="margin: 5px 0 5px;" name="addNewProductList">
                            <table><tbody>
                            <tr>
                                <td>Name</td><td>${classificationParameterQuestion.localizedQuestion}</td>
                            </tr>
                            <tr>
                                <td><opt:textField style="max-width:150px;" name="name" class="input redBorder"/></td>
                                <td>
                                    <g:hiddenField name="classificationQuestionId" value="${ classificationParameterQuestion.questionId}" />
                                    <select style="max-width: 150px;" name="classificationParamId">
                                        <g:each in="${classificationParams}" var="classificationParam">
                                            <option value="${classificationParam.answerId}">${classificationParam.getLocalizedAnswer()}</option>
                                        </g:each>
                                    </select>
                                </td>
                                <td><opt:submit style="margin-bottom: 9px" class="btn btn-primary" name="submit" value="create"/></td>
                            </tr>
                            </tbody></table>
                        </g:uploadForm>
                    </div>
                </div>

                <div class="wrapperForExistingProductLists container">
                    <table class="table productListsTable">
                        <thead><tr><th>Data Lists</th><th>Data list type</th><th>Company</th><th>Resources</th><th>&nbsp;</th></tr></thead>
                        <tbody>
                        <g:if test="${premadeListCounts}">
                            <g:each in="${premadeListCounts}">
                                <tr>
                                    <td>
                                        <span id="productList${it.key.id}">${it.key.name}</span> <a href="javascript:" onclick="changeProductListName(this, '${it.key.id}')"><i class="fas fa-pencil-alt"></i></a><span class="hidden inliner"><opt:textField style="max-width:150px;" name="name" value="${it.key?.name}" class="input inliner"/> <a href="javascript:" style="vertical-align: top;" class="btn btn-primary">Save</a></span>
                                    </td>
                                    <td>
                                        <g:set var="classificationParamId" value="${it.key.classificationParamId}" />
                                        ${classificationParams?.find({it.answerId.equals(classificationParamId)})?.localizedAnswer}
                                    </td>
                                    <td>
                                        Preset Created
                                    </td>
                                    <td><productDataListRender:inactiveDataWarning productDataList="${it.key}"/> <productDataListRender:missmatchDataWarning productDataList="${it.key}"/> active ${it.value?.get("actives")} / ${it.value?.get("all")}</td>
                                    <td>
                                        <opt:link action="form" params="[id:it.key.id, classificationQuestionId: it.key.classificationQuestionId, classificationParamId: it.key.classificationParamId, accountId: it.key.privateProductDataListAccountId, name: it.key.name, admin: true]" class="btn btn-primary"><g:message code="edit"/></opt:link>
                                        <a href="javascript:" class="btn btn-danger" onclick="deleteProjectDataList('${it.key?.id}','${message(code: 'warning')}', 'Are you sure you want to delete product list: ${it.key.name} ?', 'Successfully deleted product list: ${it.key.name}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message code="delete"/></a>
                                    </td>
                                </tr>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="companyListsOld" style="display: none;">
                <div class="wrapperForExistingProductLists container">
                    <table class="table productListsTable">
                        <thead><tr><th>Data Lists</th><th>Company</th><th>Data list type</th><th>Resources</th><th>&nbsp;</th></tr></thead>
                        <tbody>
                        <g:if test="${companyListCounts}">
                            <g:each in="${companyListCounts}">
                                <tr>
                                    <td>
                                        ${it.key.name}
                                    </td>
                                    <td>
                                        ${it.key.privateProductDataListCompanyName}
                                    </td>
                                    <td>
                                        <g:set var="classificationParamId" value="${it.key.classificationParamId}" />
                                        ${classificationParams?.find({it.answerId.equals(classificationParamId)})?.localizedAnswer}
                                    </td>
                                    <td><productDataListRender:inactiveDataWarning productDataList="${it.key}"/> <productDataListRender:missmatchDataWarning productDataList="${it.key}"/> active ${it.value?.get("actives")} / ${it.value?.get("all")}</td>
                                    <td>
                                        <opt:link action="form" params="[id:it.key.id, classificationQuestionId: it.key.classificationQuestionId, classificationParamId: it.key.classificationParamId, accountId: it.key.privateProductDataListAccountId, name: it.key.name, admin: true]" class="btn btn-primary"><g:message code="edit"/></opt:link>
                                        <a href="javascript:" class="btn btn-danger" onclick="deleteProjectDataList('${it.key?.id}','${message(code: 'warning')}', 'Are you sure you want to delete product list: ${it.key.name} ?', 'Successfully deleted product list: ${it.key.name}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message code="delete"/></a>
                                    </td>
                                </tr>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="companyLists" style="display: none;">
                <div class="wrapperForExistingProductLists container">
                    <table class="table productListsTable">
                        <thead><tr><th>Organisation</th><th>Total lists</th><th>Data list types</th><th>Total resources per type <a href="#" rel="popover" data-trigger="hover" data-content="Total includes reference lists"><i class="icon-question-sign"></i></a></th><th> </th></tr></thead>
                        <tbody>
                        <g:if test="${companyDataLists}">
                            <g:each in="${companyDataLists}">
                                <tr>
                                    <td>
                                        ${it.key}
                                    </td>
                                    <td>
                                        ${raw(it.value.get("totalLists").replace("\n", "<br>"))}
                                    </td>
                                    <td>
                                        ${raw(it.value.get("paramNames").replace("\n", "<br>"))}
                                    </td>
                                    <td>
                                        ${raw(it.value?.get("totalListResources").replace("\n", "<br>"))}
                                    </td>
                                    <td>
                                        <opt:link action="manageLibraries" params="[accountId: it.value.get('accountId')]" class="btn btn-primary"><g:message code="view"/></opt:link>
                                    </td>
                                </tr>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    $(function (){
        showActiveTab();
    });

    $('#toggleNewGroup').bind('click', function () {
         var newProductListGroup = $('#addNewProductListGroup');
         var plusMinus = $('#plusMinus');
         if (!newProductListGroup.is(":visible")) {
             $(newProductListGroup).slideDown().removeClass('hidden');
             $(plusMinus).toggleClass('icon-plus icon-minus');
         } else {
             $(newProductListGroup).slideUp().addClass('hidden');
             $(plusMinus).toggleClass('icon-minus icon-plus');
         }
    });

     function changeProductListName(element, id) {
         $(element).hide();
         $('#productList'+id).hide()
         var editField = $(element).next();
         $(editField).fadeIn().removeClass('hidden');
         var submit = $('a', editField);
         $(submit).on('click', function () {
             var name = $(this).prev().val();
             $('<i id="validatinSpinner" style="padding-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(submit)
             $(submit).attr('disabled', 'disabled').off('click')
             $.ajax({
                 url: '/app/sec/productDataLists/editProductDataList',
                 data: 'id=' + id + '&name=' + name,
                 type: 'POST',
                 success: function () {
                     location.reload();
                 }
             });
         });
     }

     function deleteProjectDataList(id, warningTitle, warningText, successText, yes, back) {
         Swal.fire({
             title: warningTitle,
             text: warningText,
             icon: "warning",
             confirmButtonText: yes,
             cancelButtonText: back,
             confirmButtonColor: "red",
             showCancelButton: true,
             reverseButtons: true,
             showLoaderOnConfirm: true,
             preConfirm: function () {
                 return new Promise(function (resolve) {
                     $.ajax({
                         type: 'POST',
                         url: '/app/sec/productDataLists/deleteProductDataList',
                         data: 'id=' + id,
                         success: function () {
                             resolve()
                         }
                     });
                 })
             },
             allowOutsideClick: false
         }).then((result) => {
             if (result.value) {
                 Swal.fire({
                     icon: 'success',
                     title: successText,
                 }).then(function () {
                     location.reload();
                 });
             }
         });
     }

</script>
</body>
</html>