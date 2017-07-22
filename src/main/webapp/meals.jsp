<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <meta charset="UTF-8">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <td><b>Description</b></td>
        <td><b>Calories</b></td>
        <td><b>Date</b></td>
        <td colspan="2"></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr ${meal.exceed ? 'style="color: red"' : 'style="color: forestgreen"'}>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.dateTime.format(formatter)}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    <p><a href="meals?action=add">Add meal</a></p>
</table>
</body>
</html>
