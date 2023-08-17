package com.petrpancocha.saptest;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private Zone from;
    private Zone to;

    private List<Road> roads;

    public Path(Zone from, Zone to) {
        this.from = from;
        this.to = to;
        this.roads = new ArrayList<>();
    }

    public void addRoad(Road road) {
        roads.add(road);
    }

    public List<Road> getRoads() {
        return roads;
    }

    public int getTravelTime() {
        return roads.stream()
                .mapToInt(Road::getTravelTime)
                .sum();
    }

    @Override
    public String toString() {
        return "Path{" +
                "from=" + from +
                ", to=" + to +
                ", roads=" + roads +
                '}';
    }
}
