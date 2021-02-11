package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.controller.MealController;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static java.time.LocalDateTime.parse;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final MealController controller = new MealController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        final String id = request.getParameter("id");

        if (action == null) {
            action = "";
        }
        switch (action) {
            case "update":
                Meal meal = controller.getById(parseInt(id));
                request.setAttribute("meal", meal);
            case "add":
                log.debug("redirect to editForm");
                request.getRequestDispatcher("editForm.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("deleting meal with id: " + id);
                controller.delete(parseInt(id));
            default:
                log.debug("redirect to meals");
                request.setAttribute("mealList", controller.getAllMeals());
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final String id = request.getParameter("id");
        final String text = request.getParameter("description");
        final String datetime = request.getParameter("datetime");
        final String calories = request.getParameter("calories");

        final Meal meal = new Meal(parse(datetime), text, parseInt(calories));
        if (!isEmpty(id)) {
            meal.setId(parseInt(id));
        }
        controller.save(meal);
        response.sendRedirect("meals");
    }

    private boolean isEmpty(String param) {
        return param == null || param.isEmpty();
    }
}
