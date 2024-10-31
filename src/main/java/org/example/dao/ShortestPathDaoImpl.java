package org.example.dao;

import org.example.model.ShortestPath;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShortestPathDaoImpl implements ShortestPathDao {
    private final Connection connection;
    private static final String SELECT_ALL_SHORTEST_PATHS = "SELECT * FROM navigator.shortestpaths";

    public ShortestPathDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ShortestPath findById(int id) {
        return null;
    }

    @Override
    public List<ShortestPath> findAll() {
        List<ShortestPath> shortestPaths = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SHORTEST_PATHS);
            while (resultSet.next()) {
                ShortestPath shortestPath = new ShortestPath();
                shortestPath.setId(resultSet.getInt(1));
                shortestPath.setOriginCity(resultSet.getInt(2));
                shortestPath.setDestinationCity(resultSet.getInt(3));
                shortestPath.setDistance(resultSet.getDouble(4));
                shortestPath.setPathSequence(null);//TODO: how to map JSON field from SQL to list
                shortestPaths.add(shortestPath);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return shortestPaths;
    }

    @Override
    public void save(ShortestPath shortestPath) {

    }

    @Override
    public void delete(ShortestPath shortestPath) {

    }

    @Override
    public void update(ShortestPath shortestPath, int id) {

    }
}
