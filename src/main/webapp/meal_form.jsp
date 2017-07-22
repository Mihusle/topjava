<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal form</title>
    <meta charset="UTF-8">
</head>
<body>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals" name="formAddMeal">
        ID: <input type="hidden" name="id" value="<c:out value="${meal.id}"/>"><br/>
        Description: <input type="text" name="description" value="<c:out value="${meal.description}"/>"><br/>
        Calories: <input type="text" name="calories" value="<c:out value="${meal.calories}"/>"><br/>
        Date: <input type="text" name="dateTime" value="${meal.dateTime.format(formatter)}"/> <br/>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
