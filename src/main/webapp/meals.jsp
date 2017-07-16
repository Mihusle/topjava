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
        <tr ${meal.exceed ? 'style="color: red"' : 'style="color: forestgreen"'}>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.dateTime.format(formatter)}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
