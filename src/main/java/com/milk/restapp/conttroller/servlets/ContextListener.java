package com.milk.restapp.conttroller.servlets;

import com.milk.restapp.dao.EventDAO;
import com.milk.restapp.dao.FileDAO;
import com.milk.restapp.dao.UserDAO;
import com.milk.restapp.dao.implementation.EventDAOImpl;
import com.milk.restapp.dao.implementation.FileDAOImpl;
import com.milk.restapp.dao.implementation.UserDAOImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Jack Milk
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private UserDAO userDAO;
    private FileDAO fileDAO;
    private EventDAO eventDAO;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        userDAO = new UserDAOImpl();
        fileDAO = new FileDAOImpl();
        eventDAO = new EventDAOImpl();

        servletContext.setAttribute("userDB", userDAO);
        servletContext.setAttribute("fileDB", fileDAO);
        servletContext.setAttribute("eventDB", eventDAO);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
