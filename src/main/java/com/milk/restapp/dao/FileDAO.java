package com.milk.restapp.dao;

import com.milk.restapp.model.File;
import com.milk.restapp.model.User;

import java.util.List;

/**
 * @author Jack Milk
 */
public interface FileDAO extends GenericDAO<File, Integer>{
    List<File> getAll();
    File getById(Integer id);
    File save(File file);
    File update(File file);
    void deleteById(Integer id);
}
