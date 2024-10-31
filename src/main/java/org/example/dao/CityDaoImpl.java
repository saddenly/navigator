package org.example.dao;

import org.example.ConnectionFactory;
import org.example.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDaoImpl implements CityDao {
    private final Connection connection;
    private static final String SELECT_CITY_BY_ID = "SELECT * FROM navigator.cities WHERE id = ?";
    private static final String SELECT_CITY_BY_NAME = "SELECT * FROM navigator.cities WHERE name = ?";
    private static final String SELECT_ALL_CITIES = "SELECT * FROM navigator.cities";
    private static final String INSERT_CITY = "INSERT INTO navigator.cities (name, x_coordinate, y_coordinate) VALUES (?, ?, ?)";
    private static final String UPDATE_CITY = "UPDATE navigator.cities SET name = ?, x_coordinate = ?, y_coordinate = ? WHERE id = ?";
    private static final String DELETE_CITY = "DELETE FROM navigator.cities WHERE id = ?";

    public CityDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public City findById(int id) {
        City city = new City();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CITY_BY_ID);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                city.setId(resultSet.getInt("id"));
                city.setXCoordinate(resultSet.getDouble("x_coordinate"));
                city.setYCoordinate(resultSet.getDouble("y_coordinate"));
                city.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return city;
    }

    public City findByName(String name) {
        City city = new City();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CITY_BY_NAME);) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("name"));
                city.setXCoordinate(resultSet.getDouble("x_coordinate"));
                city.setYCoordinate(resultSet.getDouble("y_coordinate"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return city;
    }

    @Override
    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CITIES);
            while (resultSet.next()) {
                City city = new City();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("name"));
                city.setXCoordinate(resultSet.getDouble("x_coordinate"));
                city.setYCoordinate(resultSet.getDouble("y_coordinate"));
                cities.add(city);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cities;
    }

    @Override
    public void save(City city) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CITY);) {
            statement.setString(1, city.getName());
            statement.setDouble(2, city.getXCoordinate());
            statement.setDouble(3, city.getYCoordinate());
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(City city) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CITY);) {
            statement.setInt(1, city.getId());
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(City city, int id) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CITY);) {
            statement.setString(1, city.getName());
            statement.setDouble(2, city.getXCoordinate());
            statement.setDouble(3, city.getYCoordinate());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
