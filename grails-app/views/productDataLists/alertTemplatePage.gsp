<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <meta name="format-detection" content="telephone=no"/>
        <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    </head>
    <body>
    <div class="content container">
        <div class="container" id="messageContent">
            <h3>Additional Examples:</h3>
            <div class="alert alert-success">
                <i class="fas fa-tools pull-left" style="font-size: large; margin-right: 8px;"></i>
                <button type="button" class="close" data-dismiss="alert">×</button>
                <strong> Tool icon used in licence tool alert in entity page </strong>
            </div>
            <div class="alert alert-gray">
                <i class="fas fa-globe-europe" style="font-size: large; margin-right: 8px;"></i>
                <strong>Content box (e.g) used in grouping resources tool</strong>
                <i data-dismiss="alert" class="fas fa-circle-notch fa-spin close"></i>
                <br>
                <br>
                <input type="text">

            </div>
            <div class="alert alert-info">
                <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                <strong> Information about product </strong>
                <br>
                <br>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus in pellentesque nisl, eu sagittis eros.
                Nam lacinia, elit ut tincidunt viverra, tortor lacus convallis justo, vel mollis quam velit id odio.
                Sed vel egestas augue, at euismod elit. Suspendisse arcu metus, laoreet a consectetur eu, vulputate quis lacus.
                Maecenas auctor libero et risus feugiat, ac tempus dui venenatis. Proin vel leo et arcu accumsan tincidunt.
                Cras sit amet cursus lacus, at pharetra augue. Vestibulum posuere diam sed felis vulputate, sit amet placerat purus dapibus.
                Integer at tempor odio. Aliquam condimentum mauris et ipsum vulputate finibus. Ut id nunc nulla.
                Ut id tortor id mauris lobortis faucibus id sed quam.</p>
            </div>

            <h4>renderError(bean: ???) example:</h4>
            <div class="alert alert-error hide-on-print">
                <i class="fas fa-times pull-left" style="font-size: large; margin-right: 8px;"></i>
                <button data-dismiss="alert" class="close" style="top: 0px;" type="button">×</button>
                <strong><h4 class="alert-error">Mandatory data missing</h4><ul><li>Invalid email</li><li>Deadline cannot be empty</li></ul></strong>
            </div>

            <div class="alert alert-info">
                <i class="fas fa-info pull-left" style="font-size: large; margin-right: 8px;"></i>
                <strong> Information about Github integration can be found below </strong>
                <br>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus in pellentesque nisl, eu sagittis eros.
                Nam lacinia, elit ut tincidunt viverra, tortor lacus convallis justo, vel mollis quam velit id odio.
                Sed vel egestas augue, at euismod elit.</p>
            </div>
        </div>
    </div>
    </body>
</html>