package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by MHSL on 16.07.2017.
 */
public class MemoryMealDAO implements MealDAO {
    
    private static final ConcurrentMap<Integer, Meal> MEALS_BY_ID = new ConcurrentHashMap<>();
    private static int count = 0;
    
    static {
        MEALS_BY_ID.put(++count, new Meal(count, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        MEALS_BY_ID.put(++count, new Meal(count, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        MEALS_BY_ID.put(++count, new Meal(count, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        MEALS_BY_ID.put(++count, new Meal(count, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        MEALS_BY_ID.put(++count, new Meal(count, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        MEALS_BY_ID.put(++count, new Meal(count, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }
    
    @Override
    public synchronized void add(Meal meal) {
        MEALS_BY_ID.put(++count, meal);
    }
    
    @Override
    public Meal get(int id) {
        return MEALS_BY_ID.get(id);
    }
    
    @Override
    public synchronized void update(int id, Meal newMeal) {
        Meal oldMeal = get(id);
        updateMealFields(oldMeal, newMeal);
    }
    
    @Override
    public synchronized void delete(int id) {
        MEALS_BY_ID.remove(id);
    }
    
    private void updateMealFields(Meal oldMeal, Meal newMeal) {
        oldMeal.setDateTime(newMeal.getDateTime());
        oldMeal.setDescription(newMeal.getDescription());
        oldMeal.setCalories(newMeal.getCalories());
    }
    
    @Override
    public List<Meal> getAll() {
        List<Meal> allMeals = new ArrayList<>();
        MEALS_BY_ID.forEach((key, value) -> allMeals.add(value));
        return allMeals;
    }
    
    @Override
    public boolean isExist(int id) {
        return MEALS_BY_ID.containsKey(id);
    }
}
