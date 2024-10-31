package org.example;

import org.example.dao.CityDao;
import org.example.dao.CityDaoImpl;
import org.example.dao.PathDao;
import org.example.dao.PathDaoImpl;
import org.example.model.City;
import org.example.service.CityService;
import org.example.service.PathService;
import org.example.service.ShortestPathService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = ConnectionFactory.createConnection();
        CityDao cityDao = new CityDaoImpl(conn);
        PathDao pathDao = new PathDaoImpl(conn);
        CityService cityService = new CityService(cityDao);
        PathService pathService = new PathService(pathDao, cityDao);
        ShortestPathService shortestPathService = new ShortestPathService(cityDao, pathDao);
        MapRenderer mapRenderer = new MapRenderer(cityDao, pathDao);

        /*cityService.generateRandomCities(20);
        pathService.generateRandomPaths(20);*/

        List<City> cities = cityDao.findAll();
        cities.forEach(System.out::println);

        String startCityName = "San-Marino";
        String endCityName = "Warsaw";
        List<String> path = shortestPathService.findShortestPath(cityDao.findByName(startCityName).getId(), cityDao.findByName(endCityName).getId());

        if (!path.isEmpty()) {
            System.out.println("Shortest path from city " + startCityName + " to city " + endCityName + ": " + path);
        } else {
            System.out.println("No shortest path from city " + startCityName + " to city " + endCityName);
        }

        TreeRenderer treeRenderer = new TreeRenderer(cityDao, pathDao);
        treeRenderer.displayTree();
        mapRenderer.drawMap();
    }
}