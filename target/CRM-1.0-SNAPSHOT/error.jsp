<%--
  Created by IntelliJ IDEA.
  User: cthwmh
  Date: 2020/12/8
  Time: 14:57
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
    <title></title>
    <base href="<%=basePath%>">

</head>
<body>
服务器正忙……
</body>
</html>
