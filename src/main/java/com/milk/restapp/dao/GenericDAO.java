package com.milk.restapp.dao;

import java.util.List;

/**
 * @author Jack Milk
 */

public interface GenericDAO<T, ID> {
    List<T> getAll();
    void deleteById(ID id);
    T getById(ID id);
    T save(T t);
    T update(T t);
}

