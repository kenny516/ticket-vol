<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Formulaire</title>
</head>
<body>
<% if (request.getAttribute("error") != null) { %>
<span style="color: red;">Misy error: <%= request.getAttribute("error").toString() %></span>
<% } %>
<form action="login" method="post">
    <label>Username</label>
    <input type="text" name="user.username"
           value="<%= request.getParameter("user.username") != null ? request.getParameter("user.username") : "" %>">
    <% if (request.getAttribute("error_username") != null) { %>
    <span style="color: red;"><%= request.getAttribute("error_username") %></span>
    <% } %>

    <label>Password</label>

    <input type="password" name="user.password">
    <% if (request.getAttribute("error_password") != null) { %>
    <span style="color: red;"><%= request.getAttribute("error_password") %></span>
    <% } %>

    <label>Age</label>
    <input type="number" name="user.age"
           value="<%= request.getParameter("user.age") != null ? request.getParameter("user.age") : "" %>">
    <% if (request.getAttribute("error_age") != null) { %>
    <span style="color: red;"><%= request.getAttribute("error_age") %></span>
    <% } %>

    <input type="hidden" name="user.role" value="user">

    <input type="submit" value="Submit">
</form>
</body>
</html>
