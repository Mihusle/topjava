package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by MHSL on 15.07.2017.
 */
public class MealServlet extends HttpServlet {
    
    private static final Logger LOG = getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals");
        List<MealWithExceed> mealWithExceed = convertWithExceed(MEALS, CALORIES_PER_DAY);
        request.setAttribute("meals", mealWithExceed);
        request.setAttribute("formatter", DATE_TIME_FORMATTER);
        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);
    }
    
    private static List<MealWithExceed> convertWithExceed(List<Meal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .map(meal -> createWithExceed(meal, caloriesByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
