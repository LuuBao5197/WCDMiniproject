<%-- 
    Document   : login
    Created on : Mar 14, 2025, 1:10:48 PM
    Author     : kyanh
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="styles.css"> 
    </head>
    <body>
        <div class="container">
            <h2>Login</h2>

            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>

            <form action="/login" method="POST">
                <input type="text" name="j_username" id="username" placeholder="Username" required>
                <input type="password" name="j_password" id="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>

            <a href="#" class="forgot-password">Forgot password?</a>
        </div>
    </body>
</html>



