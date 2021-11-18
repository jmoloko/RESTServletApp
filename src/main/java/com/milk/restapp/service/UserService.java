package com.milk.restapp.service;

import com.milk.restapp.dao.implementation.UserDAOImpl;
import com.milk.restapp.model.User;

import java.util.List;

/**
 * @author Jack Milk
 */
public class UserService {

    private UserDAOImpl userDAO;

    public UserService(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> findAll() {
        return userDAO.getAll();
    }

    public User findById(Integer id) {
        return userDAO.getById(id);
    }

    public User save(User user) {
        return userDAO.save(user);
    }

    public User update(User user) {
        return userDAO.update(user);
    }

    public void deleteById(Integer id) {
        userDAO.deleteById(id);
    }

}
