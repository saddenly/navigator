package org.example.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShortestPath {
    private int id;
    private int originCity;
    private int destinationCity;
    private double distance;
    private List<Integer> pathSequence;

    public ShortestPath(int originCity, int destinationCity, double distance, List<Integer> pathSequence) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.distance = distance;
        this.pathSequence = pathSequence;
    }
}
