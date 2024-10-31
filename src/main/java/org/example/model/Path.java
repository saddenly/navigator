package org.example.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Path {
    private int id;
    private int startCity;
    private int endCity;
    private double distance;

    public Path(int startCity, int endCity, double distance) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.distance = distance;
    }
}
