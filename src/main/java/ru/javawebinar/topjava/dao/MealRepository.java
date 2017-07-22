package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by MHSL on 16.07.2017.
 */
public interface MealRepository {
    
    Meal save(Meal meal);
    Meal get(int id);
    Collection<Meal> getAll();
    void delete(int id);
}
