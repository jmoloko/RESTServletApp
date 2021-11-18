package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.FileDto;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.File;
import com.milk.restapp.service.FileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class GetFileByIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private FileService fileDB;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");

        int id = Integer.parseInt(stringsPath[2]);
        File file = this.fileDB.findById(id);
        FileDto fileDto = mapper.FileToDto(file);
        return objectMapper.writeValueAsString(fileDto);
    }
}
