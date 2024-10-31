package org.example;

import org.example.dao.CityDao;
import org.example.dao.PathDao;
import org.example.model.City;
import org.example.model.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeRenderer {
    private CityDao cityDao;
    private PathDao pathDao;

    private Map<City, List<City>> cityConnections;

    public TreeRenderer(CityDao cityDao, PathDao pathDao) {
        this.cityConnections = new HashMap<>();
        this.cityDao = cityDao;
        this.pathDao = pathDao;

        buildConnections();
    }

    // Build a map of city connections for the tree representation
    private void buildConnections() {
        List<Path> paths = pathDao.findAll();
        for (Path path : paths) {
            cityConnections
                    .computeIfAbsent(cityDao.findById(path.getStartCity()), k -> new ArrayList<>())
                    .add(cityDao.findById(path.getEndCity()));
            cityConnections
                    .computeIfAbsent(cityDao.findById(path.getEndCity()), k -> new ArrayList<>())
                    .add(cityDao.findById(path.getStartCity())); // Since paths are bidirectional
        }
    }

    public void displayTree() {
        for (City city : cityDao.findAll()) {
            if (!cityConnections.containsKey(city)) {
                continue; // Skip cities without connections
            }
            System.out.println(city.getName() + ":");
            displayConnections(city, "", new ArrayList<>());
        }
    }

    // Recursive function to display connections in a tree structure
    private void displayConnections(City city, String prefix, List<City> visited) {
        visited.add(city);

        List<City> connectedCities = cityConnections.get(city);
        for (City connectedCity : connectedCities) {
            if (!visited.contains(connectedCity)) {
                System.out.println(prefix + " └── " + connectedCity.getName());
                displayConnections(connectedCity, prefix + "     ", visited);
            }
        }
    }
}
