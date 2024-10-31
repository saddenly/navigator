package org.example.dao;

import org.example.model.ShortestPath;

import java.util.List;

public interface ShortestPathDao extends BaseDao<ShortestPath> {
    @Override
    ShortestPath findById(int id);

    @Override
    List<ShortestPath> findAll();

    @Override
    void save(ShortestPath shortestPath);

    @Override
    void delete(ShortestPath shortestPath);

    @Override
    void update(ShortestPath shortestPath, int id);
}
