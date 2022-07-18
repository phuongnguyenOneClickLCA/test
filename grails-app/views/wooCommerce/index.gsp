<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <g:set var="accountService" bean="accountService"/>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <g:render template="/entity/basicinfoView"/>
    </div>
</div>

<div class="container section">
    <opt:spinner display="true"/>

    <div class="sectionbody">
            <form action="${wooCommerceApiUrl}" method="POST" id="modifiableForm">
            <g:hiddenField name="user_id" value="${user?.id}" />
            <g:hiddenField name="timestamp" value="${timestamp}" />
            <g:hiddenField  name="firstname" value="${firstname}" />
            <g:hiddenField name="lastname" value="${lastname}" />
            <g:hiddenField name="phone" value="${user?.phone}" />
            <g:hiddenField name="email" value="${user?.username}" />
            <g:hiddenField name="companyName" value="${account?.companyName}" />
            <g:hiddenField name="address1" value="${account?.addressLine1}" />
            <g:hiddenField name="address2" value="${account?.addressLine2}" />
            <g:hiddenField name="address3" value="${account?.addressLine3}" />
            <g:hiddenField name="postcode" value="${account?.postcode}" />
            <g:hiddenField name="town" value="${account?.town}" />
            <g:hiddenField name="country" value="${account?.country}" />
            <g:hiddenField name="state" value="${account?.state}" />
            <g:hiddenField name="vatNumber" value="${account?.vatNumber}" />
            <g:hiddenField name="private_catalogues" value="${accountService.getCatalogueSlugsForForm(account?.catalogueSlugs)}" />
            <g:hiddenField name="product_ids" value="${productId}" />
            <g:hiddenField name="straight_to_checkout" value="true" />
            <g:hiddenField name="return_url" value="${wooCommerceReturnUrl}" />
            <g:hiddenField name="access_key" value="${wooCommerceUserKey}" />
            <input type="hidden" id="hash" name="hash" value="" />

            <%--
            <div class="clearfix"></div>
             <a href="javascript:" onclick="recreateHash();" class="btn btn-primary">Create hash before submit</a>
             <input type="submit" name="Send" value="GO" class="btn btn-primary" />
             --%>
            </form>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        var datastring = $("#modifiableForm").serialize();

        $.ajax({
            type: "POST",
            async: false,
            url: "/app/sec/wooCommerce/recreateHash",
            data: datastring,
            success: function (data) {
                $("#hash").val(data.output);
            }
        });
        $('#modifiableForm').trigger('submit');
    });
</script>

</body>
</html>



