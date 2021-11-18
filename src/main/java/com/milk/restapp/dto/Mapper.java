package com.milk.restapp.dto;

import com.milk.restapp.model.Event;
import com.milk.restapp.model.File;
import com.milk.restapp.model.User;
import org.modelmapper.ModelMapper;

/**
 * @author Jack Milk
 */
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper() {
        this.modelMapper = new ModelMapper();
    }

    public UserDto UserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public FileDto FileToDto(File file) {
        return modelMapper.map(file, FileDto.class);
    }

    public EventDto EventToDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }
}
