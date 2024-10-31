package org.example.dao;

import org.example.model.Path;

import java.util.List;

public interface PathDao extends BaseDao<Path>{
    @Override
    Path findById(int id);

    @Override
    List<Path> findAll();

    @Override
    void save(Path path);

    @Override
    void delete(Path path);

    @Override
    void update(Path path, int id);

    List<Path> getPathsFromCity(int id);
}
