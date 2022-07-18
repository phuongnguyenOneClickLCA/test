<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 26.4.2021
  Time: 12.33
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
    <body>
    <div class="container">
        <div class="screenheader">
            <h1>
                Send KPI report manually
            </h1>
        </div>
        <div>
            <a href="#" class="btn btn-primary" onclick="triggerKpiReportManually()">Trigger KPI report manually <i style="margin-left:2px; " class="hidden fas fa-circle-notch fa-spin white-font trigger"></i></a>
        </div>
    </div>
    <script type="text/javascript">
        $("#date").datepicker({
            dateFormat: 'yy-mm-dd',
        });
        function triggerKpiReportManually(){
            $(".trigger").removeClass("hidden")

            $.ajax({
                type: 'POST',
                url: '/app/sec/admin/dataSummary/triggerManualKpiUpdate',
                success: function (data, textStatus) {
                    console.log(data.output)
                    $(".trigger").addClass("hidden")
                    alert("KPI job done! Check your email or software log under Admin > logs ")

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(textStatus)
                    console.log(XMLHttpRequest)
                    $(".trigger").addClass("hidden")

                }
            });
        }
    </script>

    </body>
</sec:ifAnyGranted>
</html>