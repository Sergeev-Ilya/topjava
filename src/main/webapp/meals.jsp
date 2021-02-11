<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <a href="meals?action=add">Add Meal</a>
    <br><br>
    <table border="2" style="border-collapse:collapse" cellpadding="7">
        <tr>
            <th>Date</th><th>Description</th><th>Calories</th><th></th><th></th>
        </tr>
        <c:forEach var="meal" items="${mealList}" varStatus="counter">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td>${meal.date} ${meal.time}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>