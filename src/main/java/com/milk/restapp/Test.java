package com.milk.restapp;

import com.milk.restapp.dao.UserDAO;
import com.milk.restapp.dao.implementation.EventDAOImpl;
import com.milk.restapp.dao.implementation.FileDAOImpl;
import com.milk.restapp.dao.implementation.UserDAOImpl;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.User;

import java.util.List;

/**
 * @author Jack Milk
 */
public class Test {
    public static void main(String[] args) {
        List<User> users = new UserDAOImpl().getAll();
//        List<Event> events = new EventDAOImpl().getAll();
//        List<File> files = new FileDAOImpl().getAll();
        users.forEach(u -> System.out.println(u));
//        User user = new UserDAOImpl().getById(1);
//        System.out.println(user);
    }
}
