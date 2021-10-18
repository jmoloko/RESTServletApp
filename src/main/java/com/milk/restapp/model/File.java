package com.milk.restapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Jack Milk
 */
@Entity
@Table(name = "file")
@Getter
@Setter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    private String name;

    private String path;

    @OneToMany(fetch = FetchType.EAGER,  mappedBy = "file")
    private List<Event> events;

    public File(User user, String name, String path) {
        this.user = user;
        this.name = name;
        this.path = path;
    }

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return "File: " + "id: " + id + ", name: " + name + ", path: " + path + ", events: " + events;
    }
}
