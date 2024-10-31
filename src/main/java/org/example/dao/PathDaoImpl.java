package org.example.dao;

import org.example.ConnectionFactory;
import org.example.model.Path;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PathDaoImpl implements PathDao {
    private final Connection connection;
    private static final String SELECT_PATH_BY_ID = "SELECT * FROM navigator.paths WHERE id = ?";
    private static final String SELECT_ALL_PATHS = "SELECT * FROM navigator.paths";
    private static final String INSERT_PATH = "INSERT INTO navigator.paths (start_city, end_city, distance) VALUES (?, ?, ?)";
    private static final String UPDATE_PATH = "UPDATE navigator.paths SET start_city = ?, end_city = ?, distance = ? WHERE id = ?";
    private static final String DELETE_PATH = "DELETE FROM navigator.paths WHERE id = ?";
    private static final String SELECT_PATHS_FROM_CITY = "SELECT * FROM navigator.paths WHERE start_city = ?";

    public PathDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Path findById(int id) {
        Path path = new Path();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PATH_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                setPath(resultSet, path);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return path;
    }

    @Override
    public List<Path> findAll() {
        List<Path> paths = new ArrayList<>();
        try (Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_PATHS);
            while (resultSet.next()) {
                Path path = new Path();
                setPath(resultSet, path);
                paths.add(path);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return paths;
    }

    @Override
    public void save(Path path) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PATH);) {
            statement.setInt(1, path.getStartCity());
            statement.setInt(2, path.getEndCity());
            statement.setDouble(3, path.getDistance());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Path path) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PATH);) {
            statement.setInt(1, path.getId());
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Path path, int id) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PATH);) {
            statement.setInt(1, path.getStartCity());
            statement.setInt(2, path.getEndCity());
            statement.setDouble(3, path.getDistance());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Path> getPathsFromCity(int id) {
        List<Path> paths = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PATHS_FROM_CITY)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Path path = new Path();
                    setPath(resultSet, path);
                    paths.add(path);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return paths;
    }

    private void setPath(ResultSet resultSet, Path path) throws SQLException {
        path.setId(resultSet.getInt("id"));
        path.setStartCity(resultSet.getInt("start_city"));
        path.setEndCity(resultSet.getInt("end_city"));
        path.setDistance(resultSet.getDouble("distance"));
    }
}
