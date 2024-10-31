package org.example.service;

import org.example.dao.CityDao;
import org.example.model.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CityService {
    private final CityDao cityDao;
    private final List<String> CITY_NAMES = List.of("Warsaw", "Milan", "Rome", "Venice", "San-Marino", "Krakow", "Gdansk", "Munich", "Berlin", "Palermo", "Oslo", "Paris", "Budapest", "London", "Lisbon", "Manchester", "Vienna", "Barcelona", "Madrid", "Bucharest");

    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public void generateRandomCities(int numCities) {
        if (numCities > CITY_NAMES.size()) {
            throw new IllegalArgumentException("Number of cities exceeds number of available city names");
        }

        List<String> randomizedCities = new ArrayList<>(CITY_NAMES);
        Collections.shuffle(randomizedCities);
        Random rand = new Random();

        for (int i = 0; i < numCities; i++) {
            City city = new City();
            city.setName(randomizedCities.get(i));
            city.setXCoordinate(rand.nextDouble() * 10);
            city.setYCoordinate(rand.nextDouble() * 10);
            cityDao.save(city);
        }
    }
}
