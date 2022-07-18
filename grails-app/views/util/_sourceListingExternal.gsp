<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="app_version" value="${g.meta(name: "info.app.version")}"/>
   <asset:stylesheet src="bootstrap.css" />
    <asset:stylesheet src="layout.css"/>
    <asset:stylesheet src="content.css"/>
    <asset:stylesheet src="font-awesome.css"/>

</head>
<body>
<div class="sourceListing" style="margin-left:50px; font-size:14px;">
    ${renderContent}
</div>
</body>
</html>