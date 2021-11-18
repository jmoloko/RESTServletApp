package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.Occasion;
import com.milk.restapp.model.User;
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

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class DeleteFileByIdActionHandler implements Action{

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

        int fileId = Integer.parseInt(stringsPath[4]);
        int userId = Integer.parseInt(stringsPath[2]);
        User user = userDB.findById(userId);
        File deletedFile = fileDB.findById(fileId);

        Event newEvent = eventDB.save(new Event(user, deletedFile, Occasion.DELETE));

        java.io.File delFile = new java.io.File(deletedFile.getPath() + java.io.File.separator + deletedFile.getName());
        if (delFile.delete()){
            System.out.println("File " + deletedFile.getName() + " deleted!");
        } else {
            System.out.println("Failed to deleted " + deletedFile.getName() + " file");
        }

        fileDB.deleteById(fileId);
        return objectMapper.writeValueAsString(mapper.EventToDto(newEvent));
    }
}
