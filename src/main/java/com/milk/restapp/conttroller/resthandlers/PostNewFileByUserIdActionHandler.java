package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.Occasion;
import com.milk.restapp.service.EventService;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;
import com.milk.restapp.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;


/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class PostNewFileByUserIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;
    private FileService fileDB;
    private EventService eventDB;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo();
        String[] stringsPath = pathInfo.split("/");

        int id = Integer.parseInt(stringsPath[2]);
        String userName = userDB.findById(id).getName().replace(" ", "");
        String fileName = "default";

        String uploadPath = Constants.DEFAULT_PATH + java.io.File.separator + userName;
        java.io.File uploadDir = new java.io.File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        for (Part part : request.getParts()) {
            fileName = part.getSubmittedFileName();
            part.write(uploadPath + java.io.File.separator + fileName);
        }
        File newFile = fileDB.save(new File(fileName, uploadPath));
        Event newEvent = eventDB.save(new Event(userDB.findById(id), newFile, Occasion.UPLOAD));
        return objectMapper.writeValueAsString(mapper.FileToDto(newFile));
    }
}
