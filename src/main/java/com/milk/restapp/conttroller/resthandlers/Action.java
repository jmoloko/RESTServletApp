package com.milk.restapp.conttroller.resthandlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Jack Milk
 */
public interface Action {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException;
}
