<!doctype html>
<%-- MAIN SCREEN --%>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="tinymce/tinymce.min.js"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <g:set var="queryService" bean="queryService"/>
</head>
<body>
<g:uploadForm class="form-horizontal" controller="queryTask" action="save" name="queryForm" novalidate="novalidate">

    <div class="container">
        <div class="screenheader">
            <div class="pull-right hide-on-print">
                <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary" />
                <g:actionSubmit value="${message(code:'leave')}" action="leave" class="btn" />
                <g:if test="${filterOnCriteria}">
                    <div class="btn-group" style="display: inline-block;"><a href="#" rel="popover" data-trigger="hover" data-content="${filterOnCriteria.localizedFilterText}" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><g:message code="filter" /><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <g:each in="${filterOnCriteria.choices}" var="filterChoice">
                                <li><g:link action="form" params="[entityId: entity?.id, queryId: query?.queryId, indicatorId: indicator?.indicatorId, token: token, filterOnCriteria: filterChoice]"><g:if test="${filterChoice.equals(session?.filterOnCriteria)}"><i class="icon-done"></i> </g:if> ${filterChoice}</g:link></li>
                            </g:each>
                        </ul>
                    </div>
                </g:if>
            </div>
            <g:render template="/entity/basicinfoView"/>
        </div>
    </div>

    <div class="container section">
        <div class="sectionheader">
            <h2>${query?.localizedName}</h2>
        </div>

        <div class="sectionbody">
            <g:if test="${query?.localizedPurpose}">
                <p><asset:image src="img/infosign_small.png" /> ${queryService.getLocalizedPurpose(query)}</p>
            </g:if>
            <div>
                <g:hiddenField name="entityId" value="${entity?.id}" />
                <g:hiddenField name="queryId" value="${query?.queryId}" />
                <g:hiddenField name="indicatorId" value="${indicator?.indicatorId}" />
                <g:hiddenField name="token" value="${token}" />
                <g:each in="${query?.sections}" var="section" status="index"><%--
                    --%><g:render template="/query/section" model="[entity:entity, query:query, section:section, display:true, isMain:true, sectionNumber: index + 1]" /><%--
                 --%></g:each>
            </div>

        </div>
    </div>
</g:uploadForm>
<script type="text/javascript">
    $(document).ready(function() {
        initQueryAutocompletes();

        if (!detectmob() && $('.sectionbody').height() > 1000) {
            $(function () {
                // Stick the #nav to the top of the window
                var nav = $('#buttons');
                var screenheader = $('#screenheader');
                var navHomeY = nav.offset().top;
                var isFixed = false;
                var $w = $(window);
                $w.on('scroll', function () {
                    var scrollTop = $w.scrollTop();
                    var shouldBeFixed = scrollTop > navHomeY;
                    if (shouldBeFixed && !isFixed) {
                        nav.css({
                            position: 'fixed',
                            top: '54px',
                            'z-index': '9999',
                            left: nav.offset().left,
                            width: nav.width()
                        });

                        screenheader.css({'margin-bottom': '0'});
                        isFixed = true;
                    }
                    else if (!shouldBeFixed && isFixed) {
                        nav.css({
                            position: 'static'
                        });
                        isFixed = false;
                        screenheader.css({'margin-bottom': '30px'});
                    }
                });
            });
        }
    });
</script>

<g:if test="${session?.validBrowser && !session?.wrongIE}">
    <script type="text/javascript">
        $(document).ready(function() {
            tinymce.init({
                mode: "specific_textareas",
                editor_selector: "htmlEditor",
                plugins: [
                    "advlist autolink lists link image charmap print preview hr anchor pagebreak",
                    "searchreplace wordcount visualblocks visualchars code fullscreen",
                    "insertdatetime media nonbreaking save table contextmenu directionality",
                    "emoticons template paste textcolor colorpicker textpattern"
                ],
                toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
                toolbar2: "print preview media | forecolor backcolor emoticons",
                image_advtab: true,
                resize: "both"
            });
        });
    </script>
</g:if>
</body>
</html>