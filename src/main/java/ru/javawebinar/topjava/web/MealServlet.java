package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.controller.MealController;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.time.LocalDateTime.of;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final MealController controller = new MealController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        final String action = request.getParameter("action");
        if (isNotEmpty(action)) {
            final String updateId = request.getParameter("update");
            if (isNotEmpty(updateId)) {
                Meal meal = controller.getById(parseInt(updateId));
                request.setAttribute("meal", meal);
                request.setAttribute("date", meal.getDate());
                request.setAttribute("time", meal.getTime());
            }
            log.debug("redirect to edit");
            request.getRequestDispatcher("editForm.jsp").forward(request, response);
            return;
        }

        final List<MealTo> mealList = controller.getAllMeals(LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealList", mealList);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        final String deleteId = request.getParameter("delete");
        if (isNotEmpty(deleteId)) {
            delete(deleteId);
        } else {
            createOrUpdate(request);
        }

        response.sendRedirect("meals");
    }

    private boolean isNotEmpty(String param) {
        return param != null && !param.isEmpty();
    }

    private void createOrUpdate(HttpServletRequest request) {
        final String description = request.getParameter("description");
        final String date = request.getParameter("date");
        final String time = request.getParameter("time");
        final String calories = request.getParameter("calories");

        final String updateId = request.getParameter("update");
        if (isNotEmpty(updateId)) {
            final Meal newMeal = new Meal(parseInt(updateId), of(LocalDate.parse(date), LocalTime.parse(time)),
                    description, parseInt(calories));
            log.debug("updating meal with id: " + updateId);
            controller.update(newMeal);
            return;
        }

        final Meal meal = new Meal(of(LocalDate.parse(date), LocalTime.parse(time)), description, parseInt(calories));
        log.debug("creating meal");
        controller.add(meal);
    }


    private void delete(String deleteId) {
        log.debug("deleting meal with id: " + deleteId);
        controller.delete(parseInt(deleteId));
    }
}
