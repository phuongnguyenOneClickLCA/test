<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="app_version" value="${g.meta(name: "info.app.version")}"/>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="content.css"/>
    <asset:stylesheet src="font-awesome.css"/>


</head>

<body>

<div class="container">
    <div class="screenheader">
        <h1>Dataset information </h1>
            <a href="javascript:" id="nulLValueHandler" onclick="hideNullValues();" >Hide null values</a>
    </div>
</div>
    <div class="container section">
        ${renderContent}
    </div>
<asset:javascript src="jquery-3.6.0.min.js"/>

<script type="text/javascript">

    function hideNullValues() {
        $('[data-nullValue]').hide();
    }
</script>
</body>
</html>