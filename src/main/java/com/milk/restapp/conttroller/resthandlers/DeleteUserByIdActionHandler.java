package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.File;
import com.milk.restapp.model.User;
import com.milk.restapp.service.EventService;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;
import com.milk.restapp.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class DeleteUserByIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;
    private FileService fileDB;
    private EventService eventDB;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {

        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");

        int userId = Integer.parseInt(stringsPath[2]);
        User deletedUser = userDB.findById(userId);
        List<File> filesByUserId = fileDB.findFilesByUserId(userId);

        userDB.deleteById(userId);
        for (File f: filesByUserId){
            fileDB.deleteById(f.getId());
        }

        String pathToDeleted = Constants.DEFAULT_PATH + java.io.File.separator + deletedUser.getName().replace(" ", "");
        java.io.File delDir = new java.io.File(pathToDeleted);
        FileUtils.deleteDirectory(delDir);

        return "{\"status\": \"Deleted\"}";
    }
}
