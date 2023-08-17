package com.petrpancocha.saptest;

import java.util.Objects;

public class Zone {
    private int id;

    private int graphMappingId;

    public Zone(int id) {
        this.id = id;
        this.graphMappingId = id - 1;
    }

    public int getId() {
        return id;
    }

    public int getGraphMappingId() {
        return graphMappingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return id == zone.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                '}';
    }
}
