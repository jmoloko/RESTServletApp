package com.milk.restapp.service;

import com.milk.restapp.dao.implementation.EventDAOImpl;
import com.milk.restapp.model.Event;


import java.util.List;

/**
 * @author Jack Milk
 */
public class EventService {

    private EventDAOImpl eventDAO;

    public EventService(EventDAOImpl eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<Event> findAll() {
        return eventDAO.getAll();
    }

    public List<Event> findEventByUserId(Integer id) {
        return eventDAO.getEventsByUserId(id);
    }

    public Event save(Integer id) {
        return eventDAO.getById(id);
    }

    public Event save(Event event) {
        return eventDAO.save(event);
    }

    public Event update(Event event) {
        return eventDAO.update(event);
    }

    public void deleteById(Integer id) {
        eventDAO.deleteById(id);
    }

}
