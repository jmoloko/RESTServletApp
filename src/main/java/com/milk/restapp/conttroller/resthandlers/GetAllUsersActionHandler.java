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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class GetAllUsersActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        List<User> users = this.userDB.findAll();
        List<UserDto> usersDto = users.stream().map(mapper::UserToDto).collect(Collectors.toList());
        return objectMapper.writeValueAsString(usersDto);
    }
}
