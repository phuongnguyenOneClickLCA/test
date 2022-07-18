<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
</head>
<body>
<div class="container">
    <h1>Your user is disabled because it was not activated before the deadline: ${deadline}</h1>
    <h1>Please activate your user by verifying your email with the link in your inbox. Didn't receive one? <opt:link controller="index" action="resendActivationInSoftware" params="[logout: true]">Resend</opt:link> </h1>
</div>

</body>

</html>