<%-- 
    Document   : index
    Created on : Mar 10, 2025, 2:39:40 PM
    Author     : Admin
--%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <body>
        <h1>Books list!</h1>
        <form action="DeleteBookServlet">
            <table border="1">
                <tr>
                    <th>Selected</th>
                    <th>#</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Edition</th>
                    <th>Genre</th>
                    <th>Photo</th>
                    
                </tr>
                <c:forEach items="${list}" var="it">
                    <tr>
                        <td>
                            <input type="checkbox" value="${it.getBookId()}" name="del"/>
                        </td>
                        <td><c:out value="${it.getBookId()}"/></td>
                        <td><c:out value="${it.getTitle()}"/></td>
                        <td><c:out value="${it.getAuthor()}"/></td>
                        <td><c:out value="${it.getEdition()}"/></td>
                        <td><c:out value="${it.getGenre()}"/></td>
                        <td><c:out value="${it.getPhoto()}"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="7" align="left">
                        <input type="submit" value="Delete" name="action" onclick="return comfirm('Are you sure to delete')"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
