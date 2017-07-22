package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MemoryMealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.convertWithExceed;

/**
 * Created by MHSL on 15.07.2017.
 */
public class MealServlet extends HttpServlet {
    
    private static final Logger LOG = getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final MealDAO DAO = new MemoryMealDAO();
    
    private static final String MEALS_PAGE = "/meals.jsp";
    private static final String FORM_PAGE = "/meal_form.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            showMealsList(request);
            request.getRequestDispatcher(MEALS_PAGE).forward(request, response);
        } else {
            switch (action) {
                case "delete": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    LOG.debug("delete the meal with id=" + id);
                    DAO.delete(id);
                    response.sendRedirect("meals");
                    break;
                }
                case "update": {
                    LOG.debug("forward to meals_form page");
                    int id = Integer.parseInt(request.getParameter("id"));
                    LOG.debug("update the meal with id=" + id);
                    Meal meal = DAO.get(id);
                    request.setAttribute("meal", meal);
                    request.setAttribute("formatter", DATE_TIME_FORMATTER);
                    request.getRequestDispatcher(FORM_PAGE).forward(request, response);
                    break;
                }
                default:
                    LOG.debug("forward to meals_form page");
                    request.getRequestDispatcher(FORM_PAGE).forward(request, response);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = getMealFromRequest(req);
        int id = req.getParameter("id").equals("") ? 0 : Integer.parseInt(req.getParameter("id"));
        addOrUpdateMeal(id, meal);
        showMealsList(req);
        req.getRequestDispatcher(MEALS_PAGE).forward(req, resp);
    }
    
    private Meal getMealFromRequest(HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DATE_TIME_FORMATTER);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id = request.getParameter("id").equals("") ? 0 : Integer.parseInt(request.getParameter("id"));
        return new Meal(id, dateTime, description, calories);
    }
    
    private void addOrUpdateMeal(int id, Meal meal) {
        if (DAO.isExist(id)) {
            DAO.update(id, meal);
        } else {
            DAO.add(meal);
        }
    }
    
    private void showMealsList(HttpServletRequest request) {
        LOG.debug("forward to meals list page");
        List<MealWithExceed> mealsWithExceed = convertWithExceed(DAO.getAll(), CALORIES_PER_DAY);
        request.setAttribute("meals", mealsWithExceed);
        request.setAttribute("formatter", DATE_TIME_FORMATTER);
    }
}
