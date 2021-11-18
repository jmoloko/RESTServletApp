package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.File;
import com.milk.restapp.model.User;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;
import com.milk.restapp.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class PutUpdateUserByIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;
    private FileService fileDB;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");
        int id = Integer.parseInt(stringsPath[2]);
        String oldName = userDB.findById(id).getName().replace(" ", "");
        String currentPath = Constants.DEFAULT_PATH + java.io.File.separator + oldName;

        String bodyParams = request.getReader().lines().collect(Collectors.joining());
        User upUser = objectMapper.readValue(bodyParams, User.class);
        upUser.setId(id);
        User sUser = userDB.update(upUser);

        String newName = sUser.getName().replace(" ", "");
        String newPath = Constants.DEFAULT_PATH + java.io.File.separator + newName;
        List<File> filesByUserId = fileDB.findFilesByUserId(id);
        for (File f: filesByUserId){
            f.setPath(newPath);
            fileDB.update(f);
        }

        java.io.File currentDirName = new java.io.File(currentPath);
        java.io.File newDirName = new java.io.File(newPath);
        if (currentDirName.renameTo(newDirName)){
            System.out.println("Directory renamed successfully");
        } else {
            System.out.println("Failed to rename directory");
        }

        return objectMapper.writeValueAsString(mapper.UserToDto(sUser));
    }
}
