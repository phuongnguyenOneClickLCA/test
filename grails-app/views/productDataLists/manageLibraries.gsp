<%-- Copyright (c) 2020 by Bionova Oy --%>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<%@ page expressionCodec="html" %>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h4>
            <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> > <opt:link controller="account" action="form" params="[id: account?.id]" ><g:message code="account.title_page"/> </opt:link> > <g:message code="pdl.manage_data_breadcrumb"/> ${account?.companyName ?: "${message(code: 'pdl.unnamed_company')}"} <br/>
        </h4>
      <div class="pull-left">
          <h1>
          <g:message code="pdl.manage_data_breadcrumb"/> ${account?.companyName ?: "${message(code: 'pdl.unnamed_company')}"}
        </h1></div>
    </div>
</div>
<div class="container section">
    <div class="sectionbody">
        <ul class="nav nav-tabs">
            <li class="navInfo active" name="companyLists" onclick="showHideChildDiv('productListsMainDiv', this);"><a href="#companyLists"><g:message code="pdl.company_lists"/> (${companyListCounts ? companyListCounts.size() : 0})</a></li>
            <li class="navInfo" name="referenceLists" onclick="showHideChildDiv('productListsMainDiv', this);"><a href="#referenceLists"><g:message code="pdl.reference_lists"/> (<span id="refCount">${referenceListCounts ? referenceListCounts.size() : 0}</span>)</a></li>
            <li class="navInfo pull-right" name="totalResources"><a href="#" class="boldText" style="font-size: medium" rel="popover" data-placement="top" data-trigger="click" data-content="${message(code:"pdl.resource_cap_explanation")}" data-original-title="" title=""> <span id="resourceCount">${totalResources}</span> / ${maxResources} ${message(code:"import.import_resource").capitalize()}<i class="fa fa-question-circle" style="padding-left: 5px" ></i></a></li>
        </ul>
        <div id="productListsMainDiv">

            <div id="companyLists" style="display: none;">
                <div class="wrapperForNewProductLists container">
                    <a href="javascript:" class="btn btn-primary" id="toggleNewGroup"><i id="plusMinus" class="icon icon-white icon-plus"></i>  <g:message code="pdl.add_new_product_list"/></a>
                    <div id="addNewProductListGroup" class="hidden">
                        <g:uploadForm controller="productDataLists" action="addNewCompanyProductDataList" style="margin: 5px 0 5px;" name="addNewCompanyProductDataList">
                            <table><tbody>
                            <tr>
                                <td><g:message code="entity.name"/></td><td>${classificationParameterQuestion.localizedQuestion}</td>
                            </tr>
                            <tr>
                                <td><opt:textField style="max-width:250px;" name="name" class="input redBorder"/></td>
                                <td>
                                    <g:hiddenField name="classificationQuestionId" value="${classificationParameterQuestion.questionId}" />
                                    <g:hiddenField name="privateProductDataListAccountId" value="${account?.id}" />
                                    <g:hiddenField name="privateProductDataListCompanyName" value="${account?.companyName}" />
                                    <select style="max-width: 150px;" name="classificationParamId">
                                        <g:each in="${classificationParams}" var="classificationParam">
                                            <option value="${classificationParam.answerId}">${classificationParam.getLocalizedAnswer()}</option>
                                        </g:each>
                                    </select>

                                </td>
                                <td><opt:submit style="margin-bottom: 9px" class="btn btn-primary" name="submit" value="${message(code: 'create')}"/></td>
                            </tr>
                            </tbody></table>
                        </g:uploadForm>
                    </div>
                </div>

                <div class="wrapperForExistingProductLists container">
                    <table class="table productListsTable">
                        <thead><tr><th><g:message code="pdl.product_list"/></th><th>${classificationParameterQuestion.localizedQuestion}</th><th>${message(code:"import.import_resource").capitalize()}</th><th>&nbsp;</th></tr></thead>
                        <tbody>
                        <g:if test="${companyListCounts}">
                            <g:each in="${companyListCounts}">
                                <tr>
                                    <td>
                                        <g:if test="${editable}">
%{--                                            <a href="javascript:" onclick="changeProductListName(this, '${it.key.id}')">${it.key.name}</a><span class="hidden inliner"><opt:textField style="max-width:150px;" name="name" value="${it.key?.name}" class="input inliner"/> <a href="javascript:" style="vertical-align: top;" class="btn btn-primary">Save</a></span>--}%
                                            <span id="productList${it.key.id}">${it.key.name}</span> <a href="javascript:" onclick="changeProductListName(this, '${it.key.id}')"><i class="fas fa-pencil-alt"></i></a><span class="hidden inliner"><opt:textField style="max-width:150px;" name="name" value="${it.key?.name}" class="input inliner"/> <a href="javascript:" style="vertical-align: top;" class="btn btn-primary">Save</a></span>
                                        </g:if>
                                        <g:else>
                                            ${it.key.name}
                                        </g:else>
                                    </td>
                                    <td>
                                        <g:set var="classificationParamId" value="${it.key.classificationParamId}" />
                                        ${classificationParams?.find({it.answerId.equals(classificationParamId)})?.localizedAnswer}
                                    </td>
                                    <td><productDataListRender:inactiveDataWarning productDataList="${it.key}"/> <productDataListRender:missmatchDataWarning productDataList="${it.key}"/> <g:message code="active"/> ${it.value?.get("actives")} / ${it.value?.get("all")}</td>
                                    <td>
                                        <opt:link action="form" params="[id:it.key.id, classificationQuestionId: it.key.classificationQuestionId, classificationParamId: it.key.classificationParamId, accountId:account?.id, name: it.key.name]" class="btn btn-primary"><g:message code="edit"/></opt:link>
                                        <g:if test="${editable && userService.getSuperUser(user)}">
                                            <a href="javascript:" class="btn btn-danger" onclick="deleteProductDataList('${it.key?.id}','${message(code: 'warning')}', 'Are you sure you want to delete product list: ${it.key.name} ?', 'Successfully deleted product list: ${it.key.name}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message code="delete"/> (SU)</a>
                                        </g:if>
                                    </td>
                                </tr>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="referenceLists" style="display: none;">
                <div class="wrapperForNewProductLists container">
                    <g:if test="${hasEditListFeature}">
                        <a href="javascript:" class="btn btn-primary" id="toggleReferenceListGroup"><i id="plusMinus" class="icon icon-white icon-plus"></i>  <g:message code="pdl.add_premade_list"/></a>
                        <div id="addReferenceListGroup" class="hidden">
                            <table>
                                <tbody>
                                <tr>
                                    <td>
                                        <g:hiddenField name="accountId" value="${account?.id}" />
                                        <select style="max-width: 350px; width: fit-content" class="referenceDataListOptionId" name="referenceDataListId">
                                            <g:each in="${referenceDataLists}" var="referenceDataList">
                                                <option value="${referenceDataList.id}">${referenceDataList.name} (${referenceDataList.datasets.size()})</option>
                                            </g:each>

                                        </select>
                                    </td>
                                    <td><a style="margin-bottom: 9px" class="btn btn-primary" href="javascript:" onclick="renderReferenceListTable('${accountId}')">Add</a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </g:if>
                    <g:else>
                        <span rel="popover" data-content="${message(code:"pdl.contact_support_feature")}" data-trigger="hover" data-toggle="popover" data-placement="right"><a href="javascript:" class="btn btn-primary removeClicksLowOpacity" id="disabledButton"><i id="plusMinus" class="icon icon-white icon-plus"></i>  <g:message code="pdl.add_premade_list"/></a></span>
                    </g:else>
                </div>
                <div class="sectionbody" id="referenceListTableWrapper">
                    <g:render template="/productDataLists/referenceListTable" model="[referenceListCounts: referenceListCounts, classificationParams: classificationParams, classificationParameterQuestion: classificationParameterQuestion, editable: editable, account: account, user: user]"/>
                </div>

            </div>
        </div>

    </div>
</div>
<script type="text/javascript">

    $(function () {
        showActiveTab();
    });

    function renderReferenceListTable(accountId, event) {
        var referenceDataListId = $(".referenceDataListOptionId").children("option:selected").val();
        var referenceDataListName = $(".referenceDataListOptionId").children("option:selected").html();

        if(accountId && referenceDataListId) {

            Swal.fire({
                title: "${message(code: 'pdl.ref_list_add_warning_title')}",
                text: "${message(code: 'pdl.ref_list_add_warning_text')}" + referenceDataListName,
                icon: "warning",
                confirmButtonText: "${message(code: 'yes')}",
                cancelButtonText: "${message(code: 'back')}",
                confirmButtonColor: "red",
                showCancelButton: true,
                reverseButtons: true,
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {

                        var url = '/app/sec/productDataLists/addReferenceListToAccount'
                        var loadingSpinner = $("#loadingSpinner_overview")
                        var referenceTable = $(".wrapperForExistingReferenceLists");
                        var messageContainer = $("#messageContent");
                        var refCount = $("#refCount");
                        var totalResources = $("#resourceCount");
                        //$(loadingSpinner).show()
                        //$(referenceTable).hide().empty()
                        $.ajax({
                            type: "POST",
                            data: 'accountId=' + accountId + '&referenceDataListId=' + referenceDataListId + '&totalResources=' + totalResources.html() + '&maxResources=' + ${maxResources},
                            url: url,
                            success: function(data){
                                //$(loadingSpinner).hide()
                                if (data) {
                                    if (data.addedAlready) {
                                        $(messageContainer).append(
                                            "<div class=\"alert alert-error fadetoggle hide-on-print\" data-fadetoggle-delaytime=\"7000\">\n" +
                                            "   <i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>\n" +
                                            "   <i data-dismiss=\"alert\" class=\"fas fa-circle-notch fa-spin close\" style=\"top: 0px;\"></i>\n" +
                                            "   <strong>" + data.errorMessage + "</strong>\n" +
                                            "</div>")
                                        fadetoggle()
                                    } else if (data.capExceeded) {
                                        $(messageContainer).append(
                                            "<div class=\"alert alert-error hide-on-print\">\n" +
                                            "   <i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>\n" +
                                            "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>\n" +
                                            "   <strong>" + data.errorMessage + "</strong>\n" +
                                            "</div>")
                                        fadetoggle()
                                    } else {
                                        $(referenceTable).empty().append(data.htmlContent)
                                        if (refCount && totalResources) {
                                            refCount.html($('.referenceListsTable > tbody > tr').length);
                                            totalResources.html(parseInt($("#resourceCount").html()) + data.resourceCount);
                                        }
                                        if (data.admin && data.errorMessage.length > 0) {
                                            $(messageContainer).append(
                                                "<div class=\"alert alert-error hide-on-print\">\n" +
                                                "   <i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>\n" +
                                                "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>\n" +
                                                "   <strong>" + data.errorMessage + "</strong>\n" +
                                                "</div>")
                                            fadetoggle()
                                        }
                                        $(messageContainer).append(
                                            "<div class=\"alert alert-success fadetoggle hide-on-print\" data-fadetoggle-delaytime=\"7000\">\n" +
                                            "   <i class=\"far fa-thumbs-up pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>\n" +
                                            "   <i data-dismiss=\"alert\" class=\"fas fa-circle-notch fa-spin close\" style=\"top: 0px;\"></i>\n" +
                                            "   <strong>Successfully added reference list: " + referenceDataListName + "</strong>\n" +
                                            "</div>")
                                        fadetoggle()
                                    }
                                    resolve()
                                }
                            },
                            error: function(error){
                                console.log(error)
                            }

                        })
                        stopBubblePropagation(event)
                    })
                },
                allowOutsideClick: false
            }).then((result) => {
                if (result.dismiss === swal.DismissReason.close) {
                    console.log("pressed back button")
                }
            });



        }
    }



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

    $('#toggleReferenceListGroup').bind('click', function () {
        var referenceListGroup = $('#addReferenceListGroup');
        var plusMinus = $('#plusMinus');
        if (!referenceListGroup.is(":visible")) {
            $(referenceListGroup).slideDown().removeClass('hidden');
            $(plusMinus).toggleClass('icon-plus icon-minus');
        } else {
            $(referenceListGroup).slideUp().addClass('hidden');
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

    function deleteProductDataList(id, warningTitle, warningText, successText, yes, back) {
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

    function removeRefListFromAccount(id, accountId, warningTitle, warningText, successText, yes, back) {
        var referenceTable = $(".wrapperForExistingReferenceLists");
        var referenceDataListName = $(".referenceDataListOptionId").children("option:selected").html();
        var messageContainer = $("#messageContent");
        var refCount = $("#refCount");
        var totalResources = $("#resourceCount");
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
                        url: '/app/sec/productDataLists/removeReferenceList',
                        data: 'id=' + id + '&accountId=' + accountId,
                        success: function (data) {
                            $(referenceTable).empty().append(data.htmlContent)
                            if (refCount && totalResources) {
                                refCount.html($('.referenceListsTable > tbody > tr').length);
                                totalResources.html(parseInt($("#resourceCount").html()) - data.resourceCount);
                            }
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
                    $(messageContainer).append(
                        "<div class=\"alert alert-success fadetoggle hide-on-print\" data-fadetoggle-delaytime=\"7000\">\n" +
                        "   <i class=\"far fa-thumbs-up pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>\n" +
                        "   <i data-dismiss=\"alert\" class=\"fas fa-circle-notch fa-spin close\" style=\"top: 0px;\"></i>\n" +
                        "   <strong>Successfully removed reference list: " + referenceDataListName + "</strong>\n" +
                        "</div>")
                    fadetoggle()
                });
            }
        });
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
</script>
</body>
</html>