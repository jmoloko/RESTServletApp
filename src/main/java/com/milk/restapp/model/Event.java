package com.milk.restapp.model;


import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file")
    private File file;

    @Enumerated(EnumType.STRING)
    private Occasion occasion;

    private LocalDateTime date;

    public Event(User user, File file, Occasion occasion) {
        this.user = user;
        this.file = file;
        this.occasion = occasion;
    }
}
