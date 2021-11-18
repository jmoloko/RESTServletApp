package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.Occasion;
import com.milk.restapp.service.EventService;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class PutUpdateFileByIdActionHandler implements Action{

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
        int fileId = Integer.parseInt(stringsPath[4]);
        String path = fileDB.findById(fileId).getPath();
        String fileName = fileDB.findById(fileId).getName();

        String bodyParams = request.getReader().lines().collect(Collectors.joining());
        File upFile = objectMapper.readValue(bodyParams, File.class);
        upFile.setId(fileId);
        upFile.setPath(path);

        File sFile = fileDB.update(upFile);

        java.io.File currentFileName = new java.io.File(path + java.io.File.separator + fileName);
        java.io.File newFileName = new java.io.File(path + java.io.File.separator + sFile.getName());
        if (currentFileName.renameTo(newFileName)){
            System.out.println("Directory renamed successfully");
        } else {
            System.out.println("Failed to rename directory");
        }

        Event newEvent = eventDB.save(new Event(userDB.findById(userId), sFile, Occasion.RENAME));

        return objectMapper.writeValueAsString(mapper.FileToDto(sFile));
    }
}
