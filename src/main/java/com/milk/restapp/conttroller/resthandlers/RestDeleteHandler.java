package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.service.EventService;
import com.milk.restapp.service.FileService;
import com.milk.restapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class RestDeleteHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserService userDB;
    private FileService fileDB;
    private EventService eventDB;
}
