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
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class RestPostHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;
    private FileService fileDB;
    private EventService eventDB;

    public String handlerRestRequest(String reqPath, HttpServletRequest req) throws IOException, SQLException, ServletException {
        String filesPath = "/home/milk/Code/JAVA/Suleymanov/Schooling/RESTServletApp/Files";
        String[] stringsPath = reqPath.split("/");

        if (stringsPath[1].equalsIgnoreCase("users")) {

            if (reqPath.matches("^/users$")) {

                String bodyParams = req.getReader().lines().collect(Collectors.joining());
                User newUser = objectMapper.readValue(bodyParams, User.class);
                User sUser = userDB.save(newUser);
                return objectMapper.writeValueAsString(mapper.UserToDto(sUser));

            } else if (reqPath.matches("^/users/\\d+/files$")){

                int id = Integer.parseInt(stringsPath[2]);
                String userName = userDB.findById(id).getName().replace(" ", "");
                String fileName = "";
                for (Part part : req.getParts()) {
                    fileName = part.getSubmittedFileName();
                    part.write(userName + "_" + fileName);
                }
                String fName = userName + "_" + fileName;
                File newFile = fileDB.save(new File(fName, filesPath));
                Event newEvent = eventDB.save(new Event(userDB.findById(id), newFile, Occasion.DOWNLOAD));
                return objectMapper.writeValueAsString(mapper.FileToDto(newFile));

            }
        }

        return "{ \"status\": \"Error\" }";
    }
}
