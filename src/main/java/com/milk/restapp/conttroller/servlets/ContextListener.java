package com.milk.restapp.conttroller.servlets;

import com.milk.restapp.conttroller.resthandlers.*;
import com.milk.restapp.dao.implementation.EventDAOImpl;
import com.milk.restapp.dao.implementation.FileDAOImpl;
import com.milk.restapp.dao.implementation.UserDAOImpl;
import com.milk.restapp.service.EventService;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Jack Milk
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private UserService userService;
    private FileService fileService;
    private EventService eventService;

    private GetAllUsersActionHandler getAllUsersActionHandler;
    private GetAllFilesActionHandler getAllFilesActionHandler;
    private GetEventsByUserIdActionHandler getEventsByUserIdActionHandler;
    private GetFileByIdActionHandler getFileByIdActionHandler;
    private GetFileByUserIdDownloadActionHandler getFileByUserIdDownloadActionHandler;
    private GetFilesByUserIdActionHandler getFilesByUserIdActionHandler;
    private GetUserByIdActionHandler getUserByIdActionHandler;

    private PostNewFileByUserIdActionHandler postNewFileByUserIdActionHandler;
    private PostNewUserActionHandler postNewUserActionHandler;

    private PutUpdateUserByIdActionHandler putUpdateUserByIdActionHandler;
    private PutUpdateFileByIdActionHandler putUpdateFileByIdActionHandler;

    private DeleteFileByIdActionHandler deleteFileByIdActionHandler;
    private DeleteUserByIdActionHandler deleteUserByIdActionHandler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        userService = new UserService(new UserDAOImpl());
        fileService = new FileService(new FileDAOImpl());
        eventService = new EventService(new EventDAOImpl());

        getAllUsersActionHandler = new GetAllUsersActionHandler(userService);
        getAllFilesActionHandler = new GetAllFilesActionHandler(fileService);
        getEventsByUserIdActionHandler = new GetEventsByUserIdActionHandler(eventService);
        getFileByIdActionHandler = new GetFileByIdActionHandler(fileService);
        getFileByUserIdDownloadActionHandler = new GetFileByUserIdDownloadActionHandler(userService, fileService, eventService);
        getFilesByUserIdActionHandler = new GetFilesByUserIdActionHandler(fileService);
        getUserByIdActionHandler = new GetUserByIdActionHandler(userService);
        postNewFileByUserIdActionHandler = new PostNewFileByUserIdActionHandler(userService, fileService, eventService);
        postNewUserActionHandler = new PostNewUserActionHandler(userService);
        putUpdateUserByIdActionHandler = new PutUpdateUserByIdActionHandler(userService, fileService);
        putUpdateFileByIdActionHandler = new PutUpdateFileByIdActionHandler(userService, fileService, eventService);
        deleteFileByIdActionHandler = new DeleteFileByIdActionHandler(userService, fileService, eventService);
        deleteUserByIdActionHandler = new DeleteUserByIdActionHandler(userService, fileService, eventService);

        servletContext.setAttribute("getAllUsersActionHandler", getAllUsersActionHandler);
        servletContext.setAttribute("getAllFilesActionHandler", getAllFilesActionHandler);
        servletContext.setAttribute("getEventsByUserIdActionHandler", getEventsByUserIdActionHandler);
        servletContext.setAttribute("getFileByIdActionHandler", getFileByIdActionHandler);
        servletContext.setAttribute("getFileByUserIdDownloadActionHandler", getFileByUserIdDownloadActionHandler);
        servletContext.setAttribute("getFilesByUserIdActionHandler", getFilesByUserIdActionHandler);
        servletContext.setAttribute("getUserByIdActionHandler", getUserByIdActionHandler);
        servletContext.setAttribute("postNewFileByUserIdActionHandler", postNewFileByUserIdActionHandler);
        servletContext.setAttribute("postNewUserActionHandler", postNewUserActionHandler);
        servletContext.setAttribute("putUpdateUserByIdActionHandler", putUpdateUserByIdActionHandler);
        servletContext.setAttribute("putUpdateFileByIdActionHandler", putUpdateFileByIdActionHandler);
        servletContext.setAttribute("deleteFileByIdActionHandler", deleteFileByIdActionHandler);
        servletContext.setAttribute("deleteUserByIdActionHandler", deleteUserByIdActionHandler);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
