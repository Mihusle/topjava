<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <td><b>Description</b></td>
        <td><b>Calories</b></td>
        <td><b>Date</b></td>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${meal.exceed}">
                <tr style="color: red">
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>${meal.dateTime.format(formatter)}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color: forestgreen">
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>${meal.dateTime.format(formatter)}</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>
