package org.example.dao;

import java.util.List;

public interface BaseDao<T> {
    T findById(int id);

    List<T> findAll();

    void save(T t);

    void delete(T t);

    void update(T t, int id);
}
