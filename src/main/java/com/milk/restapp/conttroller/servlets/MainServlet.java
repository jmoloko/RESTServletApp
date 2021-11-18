package com.milk.restapp.conttroller.servlets;

import com.milk.restapp.conttroller.resthandlers.Action;
import com.milk.restapp.conttroller.resthandlers.NotFoundActionHandler;
import com.milk.restapp.util.FlywayUtil;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Milk
 */
@WebServlet(urlPatterns = {"/api/v1/*"})
@MultipartConfig()
public class MainServlet extends HttpServlet {

    private final Map<String, Action> getActionHandlers = new HashMap<>();
    private final Map<String, Action> postActionHandlers = new HashMap<>();
    private final Map<String, Action> putActionHandlers = new HashMap<>();
    private final Map<String, Action> deleteActionHandlers = new HashMap<>();
    private NotFoundActionHandler notFoundActionHandler;

    @Override
    public void init() throws ServletException {

        /*
          @Init Database
         */
        FlywayUtil.flywayMigrations();

        getActionHandlers.put("^/users$", (Action) getServletContext().getAttribute("getAllUsersActionHandler"));
        getActionHandlers.put("^/files$", (Action) getServletContext().getAttribute("getAllFilesActionHandler"));
        getActionHandlers.put("^/users/\\d+/events$", (Action) getServletContext().getAttribute("getEventsByUserIdActionHandler"));
        getActionHandlers.put("^/files/\\d+$", (Action) getServletContext().getAttribute("getFileByIdActionHandler"));
        getActionHandlers.put("^/users/\\d+/files/\\d+/download$", (Action) getServletContext().getAttribute("getFileByUserIdDownloadActionHandler"));
        getActionHandlers.put("^/users/\\d+/files$", (Action) getServletContext().getAttribute("getFilesByUserIdActionHandler"));
        getActionHandlers.put("^/users/\\d+$", (Action) getServletContext().getAttribute("getUserByIdActionHandler"));
        postActionHandlers.put("^/users/\\d+/files$", (Action) getServletContext().getAttribute("postNewFileByUserIdActionHandler"));
        postActionHandlers.put("^/users$", (Action) getServletContext().getAttribute("postNewUserActionHandler"));
        putActionHandlers.put("^/users/\\d+$", (Action) getServletContext().getAttribute("putUpdateUserByIdActionHandler"));
        putActionHandlers.put("^/users/\\d+/files/\\d+$", (Action) getServletContext().getAttribute("putUpdateFileByIdActionHandler"));
        deleteActionHandlers.put("^/users/\\d+/files/\\d+$", (Action) getServletContext().getAttribute("deleteFileByIdActionHandler"));
        deleteActionHandlers.put("^/users/\\d+$", (Action) getServletContext().getAttribute("deleteUserByIdActionHandler"));

        notFoundActionHandler = new NotFoundActionHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/HTML; charset=UTF-8");

        try {
            Action action = null;
            for (Map.Entry<String, Action> handler : getActionHandlers.entrySet()) {
                if (pathInfo.matches(handler.getKey())) {
                    action = handler.getValue();
                }
            }
            if (action == null) {
                action = notFoundActionHandler;
            }
            String json = action.execute(req, resp);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.write(json);
        }catch (NullPointerException | SQLException | NoResultException | IllegalArgumentException e) {
            PrintWriter out = resp.getWriter();
            if (pathInfo.contains("users")) {
                out.write("Не найден user с таким ID");
            } else if (pathInfo.contains("files")) {
                out.write("Не найден file с таким ID");
            }
            resp.setStatus(404);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");

        try {

            Action action = null;
            for (Map.Entry<String, Action> handler : postActionHandlers.entrySet()) {
                if (pathInfo.matches(handler.getKey())) {
                    action = handler.getValue();
                }
            }
            if (action == null) {
                action = notFoundActionHandler;
            }
            String json = action.execute(req, resp);
            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.write(json);
            resp.setStatus(200);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");

        try {

            Action action = null;
            for (Map.Entry<String, Action> handler : putActionHandlers.entrySet()) {
                if (pathInfo.matches(handler.getKey())) {
                    action = handler.getValue();
                }
            }
            if (action == null) {
                action = notFoundActionHandler;
            }
            String json = action.execute(req, resp);
            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.write(json);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        req.setCharacterEncoding("UTF-8");

        try {

            Action action = null;
            for (Map.Entry<String, Action> handler : deleteActionHandlers.entrySet()) {
                if (pathInfo.matches(handler.getKey())) {
                    action = handler.getValue();
                }
            }
            if (action == null) {
                action = notFoundActionHandler;
            }
            String json = action.execute(req, resp);
            resp.setContentType("application/json; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.write(json);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
