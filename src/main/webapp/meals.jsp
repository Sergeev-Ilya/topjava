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
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form method="get" action="meals">
                    <input type="text" name="update" value="${meal.id}" hidden>
                    <input type="text" name="action" value="edit" hidden>
                    <input type="submit" value="Update">
                </form>
            </td>
            <td>
                <form method="post" action="meals">
                    <input type="text" name="delete" value="${meal.id}" hidden>
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>