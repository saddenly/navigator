package org.example.service;

import org.example.dao.CityDao;
import org.example.dao.PathDao;
import org.example.model.City;
import org.example.model.Path;

import java.sql.SQLException;
import java.util.*;

public class ShortestPathService {
    private CityDao cityDao;
    private PathDao pathDao;

    public ShortestPathService(CityDao cityDao, PathDao pathDao) {
        this.cityDao = cityDao;
        this.pathDao = pathDao;
    }

    public List<String> findShortestPath(int startCityId, int endCityId) throws SQLException {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> previousCities = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (City city : cityDao.findAll()) {
            distances.put(city.getId(), Double.POSITIVE_INFINITY);
        }
        distances.put(startCityId, 0.0d);

        queue.add(new Node(startCityId, 0.0d));
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int currentCityId = currentNode.getCityId();

            if (currentCityId == endCityId) {
                return buildPath(previousCities, endCityId);
            }

            if (visited.contains(currentCityId)) continue;
            visited.add(currentCityId);

            List<Path> neighbors = pathDao.getPathsFromCity(currentCityId);

            for (Path path : neighbors) {
                int neighborCityId = path.getEndCity();
                double newDistance = distances.get(currentCityId) + path.getDistance();

                if (newDistance < distances.get(neighborCityId)) {
                    distances.put(neighborCityId, newDistance);
                    previousCities.put(neighborCityId, currentCityId);
                    queue.add(new Node(neighborCityId, newDistance));
                }
            }
        }
        return Collections.emptyList();
    }

    private List<String> buildPath(Map<Integer, Integer> previousCities, int endCityId) {
        LinkedList<String> path = new LinkedList<>();
        for (Integer at = endCityId; at != null; at = previousCities.get(at)) {
            path.addFirst(cityDao.findAll().get(at - 1).getName());
        }
        return path;
    }

    private static class Node {
        private int cityId;
        private double distance;

        public Node(int cityId, double distance) {
            this.cityId = cityId;
            this.distance = distance;
        }

        public int getCityId() {
            return cityId;
        }

        public double getDistance() {
            return distance;
        }
    }
}
