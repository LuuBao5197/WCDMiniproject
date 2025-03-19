<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<c:forEach var="firstItem" items="${fList}" begin="0" end="0">
    <h1>List Feedback Of <span class="text-info">${firstItem.bookId.title} - id: ${firstItem.bookId.bookId} </span></h1>
    <div>
        <img src="images/${item.getPhoto()}" width="100"/>
    </div>
</c:forEach>
<table class="table table-stripped">
    <thead>
        <tr>
            <th>User Feedback</th>
            <th>Content Feedback</th>
            <th>
                Rating
            </th>
            <th>
                Date time
            </th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${fList}">
            <tr>
                <td> ${item.getUserId().getUsername()}</td>
                <td> ${item.getReviewContent()}</td>
                <td> ${item.getRating()}</td>
                <td>
                    ${item.getReviewDate()}
                </td>
            </tr>

        </c:forEach>
</table>
</tbody>
