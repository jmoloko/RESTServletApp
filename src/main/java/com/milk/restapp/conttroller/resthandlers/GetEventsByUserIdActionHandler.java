package com.milk.restapp.conttroller.resthandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milk.restapp.dto.EventDto;
import com.milk.restapp.dto.Mapper;
import com.milk.restapp.model.Event;
import com.milk.restapp.service.EventService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@AllArgsConstructor
public class GetEventsByUserIdActionHandler implements Action{

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Mapper mapper = new Mapper();
    private EventService eventDB;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException, SQLException {
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        String[] stringsPath = pathInfo.split("/");

        int id = Integer.parseInt(stringsPath[2]);
        List<Event> userEvents = this.eventDB.findEventByUserId(id);
        List<EventDto> userEventsDto = userEvents.stream().map(mapper::EventToDto).collect(Collectors.toList());
        return objectMapper.writeValueAsString(userEventsDto);
    }
}
