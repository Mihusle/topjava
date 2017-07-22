package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.dao.MemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceed;

/**
 * Created by MHSL on 15.07.2017.
 */
public class MealServlet extends HttpServlet {
    
    private static final Logger LOG = getLogger(MealServlet.class);
    
    private static final String MEALS_PAGE = "/meals.jsp";
    private static final String FORM_PAGE = "/meal_form.jsp";
    
    private MealRepository repository;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MemoryMealRepository();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            showMealsList(request, response);
        } else {
            switch (action) {
                case "delete": {
                    deleteMeal(request, response);
                    break;
                }
                case "update": {
                    updateMeal(request, response);
                    break;
                }
                default: forwardToFormPage(request, response);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = getMealFromRequest(request);
        repository.save(meal);
        showMealsList(request, response);
    }
    
    private void showMealsList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        LOG.debug("forwarding to meals list page");
        List<MealWithExceed> mealsWithExceed = getFilteredWithExceed(repository.getAll(), CALORIES_PER_DAY);
        request.setAttribute("meals", mealsWithExceed);
        request.setAttribute("formatter", TimeUtil.DATE_TIME_FORMATTER);
        request.getRequestDispatcher(MEALS_PAGE).forward(request, response);
    }
    
    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getId(request);
        LOG.debug("deleting the meal with id={}", id);
        repository.delete(id);
        response.sendRedirect("meals");
    }
    
    private void updateMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        LOG.debug("updating the meal with id={}", id);
        Meal meal = repository.get(id);
        request.setAttribute("meal", meal);
        request.setAttribute("formatter", TimeUtil.DATE_TIME_FORMATTER);
        forwardToFormPage(request, response);
    }
    
    private void forwardToFormPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        LOG.debug("forwarding to meals_form page");
        request.getRequestDispatcher(FORM_PAGE).forward(request, response);
    }
    
    private Meal getMealFromRequest(HttpServletRequest request) {
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), TimeUtil.DATE_TIME_FORMATTER);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        int id = getId(request);
        return new Meal(id, dateTime, description, calories);
    }
    
    private int getId(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(id);
    }
}
