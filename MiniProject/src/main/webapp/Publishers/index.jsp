<% 
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("/login.jsp");  // Nếu chưa đăng nhập, chuyển hướng về trang login
        return;
    }
%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Publisher Page</title>
</head>
<body>
    <h1>Welcome, Publisher!</h1>
</body>
</html>