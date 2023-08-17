package com.petrpancocha.saptest;

import java.util.Objects;

public class Road {
    private Zone zoneA;

    private Zone zoneB;

    private int travelTime;

    public Road(Zone zoneA, Zone zoneB, int travelTime) {
        this.zoneA = zoneA;
        this.zoneB = zoneB;
        this.travelTime = travelTime;
    }

    public Zone getZoneA() {
        return zoneA;
    }

    public Zone getZoneB() {
        return zoneB;
    }

    public int getTravelTime() {
        return travelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return Objects.equals(zoneA, road.zoneA) && Objects.equals(zoneB, road.zoneB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoneA, zoneB);
    }

    @Override
    public String toString() {
        return "Line{" +
                "zoneA=" + zoneA +
                ", zoneB=" + zoneB +
                ", travelTime=" + travelTime +
                '}';
    }
}
