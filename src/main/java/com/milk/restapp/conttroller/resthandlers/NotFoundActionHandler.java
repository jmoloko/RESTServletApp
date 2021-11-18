package com.milk.restapp.conttroller.resthandlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Jack Milk
 */
public class NotFoundActionHandler implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        response.setStatus(404);
        return "{\"error\": \"Not Found\"}";
    }
}
