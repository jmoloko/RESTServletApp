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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class GetFilesByUserIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private FileService fileDB;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");

        int id = Integer.parseInt(stringsPath[2]);
        List<File> userFiles = this.fileDB.findFilesByUserId(id);
        List<FileDto> userFilesDto = userFiles.stream().map(mapper::FileToDto).collect(Collectors.toList());
        return objectMapper.writeValueAsString(userFilesDto);
    }
}
