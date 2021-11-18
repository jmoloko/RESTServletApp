package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.User;
import com.milk.restapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class PostNewUserActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private UserService userDB;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bodyParams = request.getReader().lines().collect(Collectors.joining());
        User newUser = objectMapper.readValue(bodyParams, User.class);
        User sUser = userDB.save(newUser);
        return objectMapper.writeValueAsString(mapper.UserToDto(sUser));
    }
}
