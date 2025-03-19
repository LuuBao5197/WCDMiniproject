<%-- 
    Document   : ratingList
    Created on : Mar 18, 2025, 9:45:33 PM
    Author     : Luu Bao
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <h1>Rating Book List</h1>
    <table class="table table-stripped">
        <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Photo</th>
                <th>Author</th>
                <th>
                    Average Rating
                </th>
                <th>
                    
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${rList}">
                <tr>
                    <td> ${item.getBookId()}</td>
                    <td> ${item.getTitle()}</td>
                    <td>
                        <img src="images/${item.getPhoto()}" width="100"/>
                    </td>
                    <td> ${item.getAuthor()}</td>
                    <td> ${item.getAvgRating()}</td>
                    <td>
                        <a href="PublisherServlet?action=Feedback&bookId=${item.getBookId()}">View Feedback</a>
                    </td>
                </tr>

            </c:forEach>
    </table>
</tbody>
