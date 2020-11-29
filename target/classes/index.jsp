<%--
  Created by IntelliJ IDEA.
  User: cthwmh
  Date: 2020/11/26
  Time: 8:13
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() +
            "://" +
            request.getServerName() +
            ":" +
            request.getServerPort() +
            request.getContextPath() +
            "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>index</title>
    <base href="<%=basePath%>">
</head>
<body>
<img src="image/home.png"/>
</body>
</html>
