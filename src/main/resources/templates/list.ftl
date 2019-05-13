<html>
<head>
    <title>Welcome!</title>
</head>
<body>
<#-- Greet the user with his/her name -->
<h1>Welcome ${user}!</h1>
<p>We have these animals:
<ul>
<#list users as user>
<li>${user.phone} for ${user.price} Euros
</#list>
</ul>
</body>
</html>