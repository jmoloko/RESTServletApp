package com.milk.restapp;

import com.milk.restapp.dao.implementation.UserDAOImpl;
import com.milk.restapp.model.User;

import java.util.List;

/**
 * @author Jack Milk
 */
public class Test {
    public static void main(String[] args) {
        List<User> users = new UserDAOImpl().getAll();
//        List<Event> events = new EventDAOImpl().getEventsByUserId(1);
//        List<File> files = new FileDAOImpl().getFilesByUserId(1);
        users.forEach(u -> System.out.println(u));
//        User user = new UserDAOImpl().getById(1);
//        System.out.println(user);
    }
}
