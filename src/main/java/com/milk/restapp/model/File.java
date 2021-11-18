package com.milk.restapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Jack Milk
 */
@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String path;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "file")
    private List<Event> event;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public File(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
