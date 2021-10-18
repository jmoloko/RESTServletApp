package com.milk.restapp.dao;

import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;

import java.util.List;

/**
 * @author Jack Milk
 */
public interface EventDAO extends GenericDAO<Event, Integer>{
    List<Event> getAll();
    Event getById(Integer id);
    Event save(Event event);
    Event update(Event event);
    void deleteById(Integer id);
}
