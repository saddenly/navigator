package org.example.service;

import org.example.dao.CityDao;
import org.example.dao.PathDao;
import org.example.model.City;
import org.example.model.Path;

import java.util.*;

public class PathService {
    private final PathDao pathDao;
    private final CityDao cityDao;

    public PathService(PathDao pathDao, CityDao cityDao) {
        this.pathDao = pathDao;
        this.cityDao = cityDao;
    }

    public void generateRandomPaths(int minPaths) {
        Random random = new Random();
        List<City> cities = cityDao.findAll();
        Set<String> existingPaths = new HashSet<>();

        int i = 0;
        while (i < minPaths) {
            City randomCity = cities.get(random.nextInt(cities.size()));
            City randomCity2 = cities.get(random.nextInt(cities.size()));
            if (randomCity.getId() != randomCity2.getId()) {
                double distance = Math.sqrt(
                        Math.pow(randomCity2.getXCoordinate() - randomCity.getXCoordinate(), 2) +
                                Math.pow(randomCity2.getYCoordinate() - randomCity.getYCoordinate(), 2));

                if (addPath(randomCity.getId(), randomCity2.getId(), distance, existingPaths)) {
                    i++;
                }
            }
        }
    }

    public void generateRandomPaths2(int minPaths) {
        Set<City> connectedCities = new HashSet<>();
        List<City> cities = cityDao.findAll();
        connectedCities.add(cities.getFirst());

        while (connectedCities.size() < cities.size()) {
            City cityA = getRandomElement(connectedCities);
            City cityB = getRandomElementNotInSet(connectedCities);

            if (cityB != null) {
                pathDao.save(new Path(cityA.getId(), cityB.getId(), calculateDistance(cityA, cityB)));
                connectedCities.add(cityB);
            }
        }

        //while ()
    }

    private City getRandomElement(Set<City> cities) {
        Random random = new Random();
        int index = random.nextInt(cities.size());
        return new ArrayList<>(cities).get(index);
    }

    private City getRandomElementNotInSet(Set<City> cities) {
        Random random = new Random();
        List<City> remaining = new ArrayList<>();
        for (City city : cityDao.findAll()) {
            if (!cities.contains(city)) {
                remaining.add(city);
            }
        }
        if (remaining.isEmpty()) return null;
        return remaining.get(random.nextInt(remaining.size()));
    }

    private boolean addPath(int cityStart, int cityEnd, double distance, Set<String> existingPaths) {
        String connectionKey = cityStart + "-" + cityEnd;

        if (!existingPaths.contains(connectionKey)) {
            pathDao.save(new Path(cityStart, cityEnd, distance));
            pathDao.save(new Path(cityEnd, cityStart, distance));
            existingPaths.add(connectionKey);
            return true;
        }
        return false;
    }

    private double calculateDistance(City cityA, City cityB) {
        return Math.sqrt(Math.pow(cityB.getXCoordinate() - cityA.getXCoordinate(), 2) -
                Math.pow(cityB.getYCoordinate() - cityA.getYCoordinate(), 2));
    }
}
