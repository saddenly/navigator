package org.example;

import org.example.dao.CityDao;
import org.example.dao.PathDao;
import org.example.model.City;
import org.example.model.Path;

import java.sql.SQLException;
import java.util.List;

public class MapRenderer {
    private CityDao cityDao;
    private PathDao pathDao;

    private static final int MAP_WIDTH = 160;  // Width of the terminal map
    private static final int MAP_HEIGHT = 80; // Height of the terminal map

    public MapRenderer(CityDao cityDao, PathDao pathDao) {
        this.cityDao = cityDao;
        this.pathDao = pathDao;
    }

    public void drawMap() throws SQLException {
        // Fetch cities and paths from the database
        List<City> cities = cityDao.findAll();
        List<Path> paths = pathDao.findAll();

        // Initialize an empty map with spaces
        char[][] map = new char[MAP_HEIGHT][MAP_WIDTH];
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                map[i][j] = ' ';
            }
        }

        // Scale cities to fit on the map
        for (City city : cities) {
            int x = (int) (city.getXCoordinate() / 10 * (MAP_WIDTH - 1));
            int y = (int) (city.getYCoordinate() / 10 * (MAP_HEIGHT - 1));

            // Place the city symbol on the map
            map[y][x] = city.getName().charAt(0); // Use the first letter of the city as its symbol
        }

        // Draw paths between cities
        for (Path path : paths) {
            City cityStart = cityDao.findById(path.getStartCity());
            City cityEnd = cityDao.findById(path.getEndCity());

            int x1 = (int) (cityStart.getXCoordinate() / 10 * (MAP_WIDTH - 1));
            int y1 = (int) (cityStart.getYCoordinate() / 10 * (MAP_HEIGHT - 1));
            int x2 = (int) (cityEnd.getXCoordinate() / 10 * (MAP_WIDTH - 1));
            int y2 = (int) (cityEnd.getYCoordinate() / 10 * (MAP_HEIGHT - 1));

            // Draw line between the two cities
            drawLineOnMap(map, x1, y1, x2, y2);
        }

        // Print the map
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    // Bresenham's line algorithm for drawing paths
    private void drawLineOnMap(char[][] map, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            if (x1 >= 0 && x1 < MAP_WIDTH && y1 >= 0 && y1 < MAP_HEIGHT) {
                if (map[y1][x1] == ' ') {
                    map[y1][x1] = '.'; // Mark the path with a dot
                }
            }

            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }
}
