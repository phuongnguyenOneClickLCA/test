<%-- Copyright (c) 2012 by Bionova Oy --%>
<!DOCTYPE html>

<html lang="en">
	<head>
    	<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="format-detection" content="telephone=no"/>
		<title>
			<g:message code="application.title" />
		</title>
        <asset:javascript src="jquery-3.6.0.min.js"/>
        <asset:javascript src="bootstrap.js"/>
        <asset:javascript src="layout.js"/>
        <asset:javascript src="jquery.dataTables.min.js"/>
        <asset:javascript src="jquery.dataTables.bootstrap-mod.js"/>
        <asset:javascript src="bootstrap-dropdown.js"/>
        <asset:javascript src="bootstrap-collapse.js"/>
		<link href='http://fonts.googleapis.com/css?family=Viga' rel='stylesheet' type='text/css'>
		<asset:stylesheet src="bootstrap.css"/>
        <asset:stylesheet src="layout.css"/>
        <asset:stylesheet src="jquery.dataTables-custom.css"/>
		<!--[if lte IE 8]>
        <asset:stylesheet src="ie8-and-down.css"/>
		<![endif]-->

		<script src="http://ajax.googleapis.com/ajax/libs/dojo/1.7.2/dojo/dojo.js"
        		data-dojo-config="async: true, isDebug: true, parseOnLoad: true">
    	</script>

	    <script type="text/javascript">
			$(document).ready(function() {
		    	$('.dropdown-toggle').dropdown();
		  	});
	    </script>
  	</head>

  	<body>
   	 	<div class="container contentscreen">
      		<g:layoutBody/>
     	</div>
	</body>
</html>