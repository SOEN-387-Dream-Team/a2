<%@ page import="soen387.Course" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: mauran
  Date: 2022-11-11
  Time: 4:31 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course was created</title>
</head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<body>
<div class="alert alert-success">
    <strong>Course was created successfully</strong>
</div>
<% response.setHeader("Refresh", "3;url=AdminPage.jsp"); %>
</body>
</html>
