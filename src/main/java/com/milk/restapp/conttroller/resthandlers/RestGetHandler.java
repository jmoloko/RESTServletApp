package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.EventDto;
import com.milk.restapp.dto.FileDto;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.dto.UserDto;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
public class RestGetHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;
    private FileService fileDB;
    private EventService eventDB;

    public String handleRestRequest(String reqPath, HttpServletResponse resp) throws IOException, SQLException {
        String[] stringsPath = reqPath.split("/");

        if (stringsPath[1].equalsIgnoreCase("users")) {

            if (reqPath.matches("^/users/\\d+$")) {

                int id = Integer.parseInt(stringsPath[2]);
                User user = userDB.findById(id);
                UserDto userDto = mapper.UserToDto(user);
                return objectMapper.writeValueAsString(userDto);

            } else if (reqPath.matches("^/users/\\d+/files$")) {

                int id = Integer.parseInt(stringsPath[2]);
                List<File> userFiles = this.fileDB.findFilesByUserId(id);
                List<FileDto> userFilesDto = userFiles.stream().map(mapper::FileToDto).collect(Collectors.toList());
                return objectMapper.writeValueAsString(userFilesDto);

            } else if (reqPath.matches("^/users/\\d+/files/\\d+/upload$")) {

                int userId = Integer.parseInt(stringsPath[2]);
                int fileId = Integer.parseInt(stringsPath[4]);

                String fName = fileDB.findById(fileId).getName();

                java.io.File file = new java.io.File("/home/milk/Code/JAVA/Suleymanov/Schooling/RESTServletApp/Files/" + fName);
                ServletOutputStream outputStream = null;
                BufferedInputStream inputStream = null;
                try {
                    outputStream = resp.getOutputStream();
                    resp.setContentType("text/plain");
                    resp.setHeader("Content-Disposition", "attachment; filename=\"" + fName + "\"");
                    resp.setContentLength((int) file.length());
                    FileInputStream fileInputStream = new FileInputStream(file);
                    inputStream = new BufferedInputStream(fileInputStream);
                    int readBytes = 0;
                    while ((readBytes = inputStream.read()) != -1)
                        outputStream.write(readBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                }

                Event newEvent = eventDB.save(new Event(userDB.findById(userId), fileDB.findById(fileId), Occasion.UPLOAD));

                return objectMapper.writeValueAsString(mapper.EventToDto(newEvent));

            } else if (reqPath.matches("^/users/\\d+/events$")){

                int id = Integer.parseInt(stringsPath[2]);
                List<Event> userEvents = this.eventDB.findEventByUserId(id);
                List<EventDto> userEventsDto = userEvents.stream().map(mapper::EventToDto).collect(Collectors.toList());
                return objectMapper.writeValueAsString(userEventsDto);

            } else {

                List<User> users = this.userDB.findAll();
                List<UserDto> usersDto = users.stream().map(mapper::UserToDto).collect(Collectors.toList());
                return objectMapper.writeValueAsString(usersDto);

            }
        } else if (stringsPath[1].equalsIgnoreCase("files")) {

            if (reqPath.matches("^/files/\\d+$")) {

                int id = Integer.parseInt(stringsPath[2]);
                File file = this.fileDB.findById(id);
                FileDto fileDto = mapper.FileToDto(file);
                return objectMapper.writeValueAsString(fileDto);

            } else {

                List<File> files = this.fileDB.findAll();
                List<FileDto> filesDto = files.stream().map(mapper::FileToDto).collect(Collectors.toList());
                return objectMapper.writeValueAsString(filesDto);

            }

        }
        return "{ \"status\": \"Not Found\" }";
    }

}
