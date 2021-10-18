package com.milk.restapp.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.milk.restapp.util.LocalDateTimeDeserializer;
import com.milk.restapp.util.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Jack Milk
 */
@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file")
    private File file;

    @Enumerated(EnumType.STRING)
    private Occasion occasion;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    public Event(File file, Occasion occasion, LocalDateTime date) {
        this.file = file;
        this.occasion = occasion;
        this.date = date;
    }

    public Event(File file, Occasion occasion) {
        this.file = file;
        this.occasion = occasion;
    }

    @Override
    public String toString() {
        return "Event:" + id + ", occasion: " + occasion + ", date:" + date;
    }
}
