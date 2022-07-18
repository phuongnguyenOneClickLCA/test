<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Construction management page</h1>
    </div>
</div>
<div class="container">
    <div class="sectionbody">
        <ul class="nav nav-tabs">
            <li class="navInfo active" name="public" onclick="showHideChildDiv('constructionsMainDiv', this);"><a href="#public">Public (${publicCount})</a></li>
            <li class="navInfo" name="private" onclick="showHideChildDiv('constructionsMainDiv', this);"><a href="#private">Private (${privateCount})</a></li>
        </ul>
        <div id="constructionsMainDiv">
            <div id="public" style="display: none;">
                <div class="wrapperForNewConstructions container">
                    <a href="javascript:" class="btn btn-primary" id="toggleNewGroup"><i id="plusMinus" class="icon icon-white icon-plus"></i>  Add a new group</a>

                    <div id="addNewConstructionGroup" class="hidden">
                    <g:uploadForm controller="construction" action="addNewConstructionGroup" name="addNewConstructionGroup">
                        <table><tbody>
                        <tr><td><opt:textField style="max-width:150px;" name="name" class="input redBorder"/></td>
                            <td><opt:submit value="add" name="submit" class="btn btn-primary "/></td></tr>
                        <tr><td><label for="earlyPhase">Early Phase <g:checkBox name="earlyPhase"></g:checkBox></label></td>
                            <td></td></tr>
                        </tbody></table>

                    </g:uploadForm>
                    </div>

                </div>
                <div class="wrapperForExistingConstructions container">

                    <h1>Constructions by groups (${constructionsWithGroup}) - ungrouped ones (<opt:link action="index" params="[unGrouped:true]">${constructionsWithoutGroup}</opt:link>) - all - (<opt:link action="index">${activeAmount}/${allConstructions}</opt:link>) -- active/total</h1>

                    <g:if test="${user.internalUseRoles}">
                        <h1><opt:link action="showUsedResources">Show resources used in constructions</opt:link></h1>
                    </g:if>

                    <table class="table constructionsTable">
                        <thead><tr><th>Group</th><th>Count</th><th>Early Phase</th><th>&nbsp;</th></tr></thead>
                        <tbody>
                        <g:if test="${constructionCountsByGroup}">
                            <g:each in="${constructionCountsByGroup}">
                                <g:if test="${!it.key.privateAccountId}">
                                    <tr>
                                        <td>
                                            <g:if test="${it.key.uneditable}">
                                                ${it.key.name}
                                            </g:if>
                                            <g:else>
                                                <span id="constructionGroup${it.key.id}">${it.key.name}</span> <a href="javascript:" onclick="changeConstructionGroupName(this, '${it.key.id}')"><i class="fas fa-pencil-alt"></i></a><span class="hidden inliner"><opt:textField style="max-width:150px;" name="name${it.key.id}" value="${it.key?.name}" class="input inliner" data-groupId="${it.key.id}"/> <a href="javascript:" style="vertical-align: top;" class="btn btn-primary">Save</a></span>
                                            </g:else>
                                        </td>
                                        <td><span class="groupWarnings" id="${it.key?.groupId}" data-groupId="${it.key?.groupId}"><i class="fas fa-circle-notch fa-spin oneClickColorScheme"></i></span> <opt:link action="index" params="[group:it.key.id]"> active ${it.value?.get("actives")} / ${it.value?.get("all")} </opt:link></td>
                                        <td>${it.key.earlyPhase ? "True" : ""}</td>
                                        <td>
                                            <a href="javascript:" class="btn btn-danger"
                                               onclick="deleteConstructionGroup('${it.key?.id}','${message(code: 'warning')}', 'Are you sure you want to delete group: ${it.key.name} ?', 'Successfully deleted group ${it.key.name}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message
                                                    code="delete"/></a>
                                        </td>
                                    </tr>
                                </g:if>
                            </g:each>
                        </g:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="private" style="display: none;">
                <div class="wrapperForExistingConstructions container">

                    <h1>Constructions by groups (${constructionsWithGroup}) - ungrouped ones (<opt:link action="index" params="[unGrouped:true]">${constructionsWithoutGroup}</opt:link>) - all - (<opt:link action="index">${activeAmount}/${allConstructions}</opt:link>) -- active/total</h1>

                    <g:if test="${user.internalUseRoles}">
                        <h1><opt:link action="showUsedResources">Show resources used in constructions</opt:link></h1>
                    </g:if>

                    <table class="table constructionsTable">
                        <thead><tr><th>Group</th><th>Count</th><th>Early Phase</th><th>&nbsp;</th></tr></thead>
                        <tbody>
                        <g:if test="${constructionCountsByGroup}">
                            <g:each in="${constructionCountsByGroup}">
                                <g:if test="${it.key.privateAccountId}">
                                    <tr>
                                        <td>(PRIVATE) ${it.key.name}</td>
                                        <td><span class="groupWarnings" id="${it.key?.groupId}" data-groupId="${it.key?.groupId}"><i class="fas fa-circle-notch fa-spin oneClickColorScheme"></i></span> <opt:link action="index" params="[group:it.key.id]"> active ${it.value?.get("actives")} / ${it.value?.get("all")} </opt:link></td>
                                        <td>${it.key.earlyPhase ? "True" : ""}</td>
                                        <td>
                                            <a href="javascript:" class="btn btn-danger"
                                               onclick="deleteConstructionGroup('${it.key?.id}','${message(code: 'warning')}', 'Are you sure you want to delete group: ${it.key.name} ?', 'Successfully deleted group ${it.key.name}','${message(code: 'yes')}', '${message(code: 'back')}')"><g:message
                                                    code="delete"/></a>
                                        </td>
                                    </tr>
                                </g:if>
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

    $(function () {
        showActiveTab();

        var query = ""
        var i = 0;

        $(".groupWarnings").each(function () {
            if (i === 0) {
                query = query + "groupIds=" + $(this).attr("data-groupId");
            } else {
                query = query + "&groupIds=" + $(this).attr("data-groupId");
            }
            i++
        })

        $.ajax({
            url: '/app/sec/construction/getWarningsForGroups',
            data: query,
            type: 'GET',
            success: function (data) {
                for (var key in data.groupIdsWithWarnings) {
                    if (data.groupIdsWithWarnings.hasOwnProperty(key)) {
                        $('#' + key).empty().append(data.groupIdsWithWarnings[key]);
                    }
                }

                $("a[rel=popover]").popover({
                    placement: 'top',
                    template: '<div class="popover importmapperPopover modalPopover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
                    trigger: 'hover',
                    html: true,
                    container: 'body'
                });
            }
        });
    });

    $('#toggleNewGroup').on('click', function () {
        var newConstructionGroup =    $('#addNewConstructionGroup');
        var plusMinus = $('#plusMinus');
        if (!newConstructionGroup.is(":visible")) {
         $(newConstructionGroup).slideDown().removeClass('hidden');
         $(plusMinus).toggleClass('icon-plus icon-minus');
        } else {
         $(newConstructionGroup).slideUp().addClass('hidden');
         $(plusMinus).toggleClass('icon-minus icon-plus');
        }
    });

    function changeConstructionGroupName(element, groupId) {
        $(element).hide();
        $('#constructionGroup' + groupId).hide();
        var editField = $(element).next();
        $(editField).fadeIn().removeClass('hidden');
        var submit = $('a', editField);
        $(submit).on('click', function () {
            var name = $(this).prev().val();
            $('<i id="validatinSpinner" style="padding-right: 4px" class="fas fa-circle-notch fa-spin"></i>').prependTo(submit)
            $(submit).attr('disabled', 'disabled').off('click')
            $.ajax({
                url: '/app/sec/construction/editConstructionGroup',
                data: 'id=' + groupId + '&name=' + name,
                type: 'POST',
                success: function () {
                 location.reload();
                }
            });
        });
    }

</script>
</body>
</html>