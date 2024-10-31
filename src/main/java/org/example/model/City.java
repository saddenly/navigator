package org.example.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class City {
    private int id;
    private double xCoordinate;
    private double yCoordinate;
    private String name;

    public City(double xCoordinate, double yCoordinate, String name) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.name = name;
    }
}
