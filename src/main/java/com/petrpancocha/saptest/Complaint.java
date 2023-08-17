package com.petrpancocha.saptest;

import java.util.Objects;

public class Complaint {
    private Zone zone;

    private int time;

    public Complaint(Zone zone, int time) {
        this.zone = zone;
        this.time = time;
    }

    public Zone getZone() {
        return zone;
    }

    public int getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return Objects.equals(zone, complaint.zone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "zone=" + zone +
                ", time=" + time +
                '}';
    }
}
