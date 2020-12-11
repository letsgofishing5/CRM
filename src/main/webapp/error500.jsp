<%--
  Created by IntelliJ IDEA.
  User: cthwmh
  Date: 2020/12/10
  Time: 21:12
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
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">

</head>
<body>
500,服务器正忙……
</body>
</html>
