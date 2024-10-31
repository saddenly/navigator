package org.example.dao;

import org.example.model.City;

import java.util.List;

public interface CityDao extends BaseDao<City> {
    @Override
    City findById(int id);

    @Override
    List<City> findAll();

    @Override
    void save(City city);

    @Override
    void delete(City city);

    @Override
    void update(City city, int id);

    City findByName(String startCityName);
}
