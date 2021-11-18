package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.Event;
import com.milk.restapp.model.Occasion;
import com.milk.restapp.service.EventService;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class GetFileByUserIdDownloadActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;
    private FileService fileDB;
    private EventService eventDB;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");

        int userId = Integer.parseInt(stringsPath[2]);
        int fileId = Integer.parseInt(stringsPath[4]);

        String fName = fileDB.findById(fileId).getName();
        String fPath = fileDB.findById(fileId).getPath();

        File file = new File(fPath + File.separator + fName);
        ServletOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fName + "\"");
            response.setContentLength((int) file.length());
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

        Event newEvent = eventDB.save(new Event(userDB.findById(userId), fileDB.findById(fileId), Occasion.DOWNLOAD));

        return objectMapper.writeValueAsString(mapper.EventToDto(newEvent));
    }
}
