<%-- 
    Document   : index
    Created on : Mar 5, 2025, 4:06:45 PM
    Author     : Luu Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mt-4">
    <h1>Book List</h1>
    <table class="table table-stripped">
        <thead>
            <tr>
                <th>Selected</th>
                <th>Id</th>
                <th>Title</th>
                <th>Author</th>
                <th>
                    Edition
                </th>
                <th>Genre</th>
                <th>Photo</th>
                <th>Publisher</th>
            </tr>
        </thead>
        <form action="PublisherServlet" method="post">
            <tbody>

                <c:forEach var="item" items="${list}">
                    <tr>
                        <td>
                            <c:if test="${empty item.reviewsList}">
                                <input type="checkbox" value="${item.getBookId()}" name="del"/>
                            </c:if>
                        </td>

                        <td> ${item.getBookId()}</td>
                        <td> ${item.getTitle()}</td>
                        <td> ${item.getAuthor()}</td>
                        <td> ${item.getEdition()}</td>
                        <td> ${item.getGenre()}</td>
                        <td>
                            <img src="images/${item.getPhoto()}" width="100"/>
                        </td>
                        <td> ${item.getPublisherId().getName()}</td>
                    </tr>

                </c:forEach>
                <tr>
                    <td colspan="8" align="left">
                        <input type="submit" value="Delete" name="action" onclick="return confirm('Are you sure to delete')"/>
                    </td>
                </tr>
        </form>


    </table>
</tbody>
</div>



