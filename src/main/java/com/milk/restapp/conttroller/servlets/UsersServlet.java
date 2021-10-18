package com.milk.restapp.conttroller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dao.EventDAO;
import com.milk.restapp.dao.FileDAO;
import com.milk.restapp.dao.UserDAO;
import com.milk.restapp.dao.implementation.UserDAOImpl;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@WebServlet(urlPatterns = {"/*"})
public class UsersServlet extends HttpServlet {

   private UserDAO userDB;
   private FileDAO fileDB;
   private EventDAO eventDB;

    @Override
    public void init() throws ServletException {
        final Object userDB = getServletContext().getAttribute("userDB");
        final Object fileDB = getServletContext().getAttribute("fileDB");
        final Object eventDB = getServletContext().getAttribute("eventDB");
        this.userDB = (UserDAO) userDB;
        this.fileDB = (FileDAO) fileDB;
        this.eventDB = (EventDAO) eventDB;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        if (pathInfo.contains("users")){
            List<User> users = this.userDB.getAll();
            String allUserJson = objectMapper.writeValueAsString(users);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.write(allUserJson);
        } else if (pathInfo.contains("files")) {
            List<File> files = this.fileDB.getAll();
            String allFileJson = objectMapper.writeValueAsString(files);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.write(allFileJson);
        } else if (pathInfo.contains("events")) {
            List<Event> events = this.eventDB.getAll();
            String allEventJson = objectMapper.writeValueAsString(events);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.write(allEventJson);
        } else {
            resp.setStatus(201);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String bodyParams = req.getReader().lines().collect(Collectors.joining());
        User newUser = objectMapper.readValue(bodyParams, User.class);
        User sUser = userDB.save(newUser);

        resp.setContentType("application/json; charset=UTF-8");

        resp.getWriter().write(objectMapper.writeValueAsString(sUser));
    }
}
