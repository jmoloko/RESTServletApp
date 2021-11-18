package com.milk.restapp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.milk.restapp.model.Occasion;
import com.milk.restapp.util.LocalDateTimeDeserializer;
import com.milk.restapp.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private int id;
    private FileDto file;
    private Occasion occasion;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;
}
