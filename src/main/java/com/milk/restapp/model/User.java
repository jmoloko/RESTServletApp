package com.milk.restapp.model;

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
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<File> files;

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User: " + id + ", name: " + name + ", files: " + files;
    }
}
