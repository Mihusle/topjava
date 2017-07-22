package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by MHSL on 16.07.2017.
 */
public interface MealDAO {
    
    void add(Meal meal);
    Meal get(int id);
    List<Meal> getAll();
    void update(int id, Meal newMeal);
    void delete(int id);
    boolean isExist(int id);
}
