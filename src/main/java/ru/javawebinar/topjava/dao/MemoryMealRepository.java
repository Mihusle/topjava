package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by MHSL on 16.07.2017.
 */
public class MemoryMealRepository implements MealRepository {
    
    private ConcurrentMap<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);
    
    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }
    
    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(count.incrementAndGet());
        }
        return repository.put(meal.getId(), meal);
    }
    
    @Override
    public Meal get(int id) {
        return repository.get(id);
    }
    
    @Override
    public void delete(int id) {
        repository.remove(id);
    }
    
    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}
