package com.milk.restapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jack Milk
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private int id;
    private String name;
    private String path;
}
