<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <br>
    <table>
        <tr>
            <th>Number<th><th>Description</th><th>Time</th><th>Calories</th>
        </tr>
        <c:forEach var="meal" items="${mealList}" varStatus="counter">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td>${counter.count}</td>
            <td>${meal.description}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.calories}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>