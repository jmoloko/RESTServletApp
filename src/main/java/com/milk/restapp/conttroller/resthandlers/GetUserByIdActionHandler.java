package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.dto.UserDto;
import com.milk.restapp.model.User;
import com.milk.restapp.service.UserService;
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
public class GetUserByIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");

        int id = Integer.parseInt(stringsPath[2]);
        User user = userDB.findById(id);
        UserDto userDto = mapper.UserToDto(user);
        return objectMapper.writeValueAsString(userDto);
    }
}
