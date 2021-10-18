package com.milk.restapp.dao;

import com.milk.restapp.model.User;

import java.util.List;

/**
 * @author Jack Milk
 */
public interface UserDAO extends GenericDAO<User, Integer>{
    List<User> getAll();
    User getById(Integer id);
    User save(User user);
    User update(User user);
    void deleteById(Integer id);
}
