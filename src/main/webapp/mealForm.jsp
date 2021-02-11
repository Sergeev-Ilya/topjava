<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == "update" ? 'Edit meal' : 'Create meal'}</h2>
    <br>
    <form method="post" action="meals">
        <input type="text" name="id" value="${meal.id}" hidden>
        <label>Description<input type="text" name="description" value="${meal.description}" required></label><br>
        <label>Date<input type="datetime-local" name="datetime" value="${meal.dateTime}" required></label><br>
        <label>Calories<input type="number" name="calories" value="${meal.calories}" required></label><br>
        <button type="submit">Save</button><br>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</body>
</html>