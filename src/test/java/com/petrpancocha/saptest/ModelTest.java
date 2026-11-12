package com.petrpancocha.saptest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelTest {

    @Test
    void zoneExposesIdsAndImplementsEquality() {
        Zone zone = new Zone(3);
        Zone sameZone = new Zone(3);

        assertEquals(3, zone.getId());
        assertEquals(2, zone.getGraphMappingId());
        assertEquals(zone, zone);
        assertEquals(zone, sameZone);
        assertEquals(zone.hashCode(), sameZone.hashCode());
        assertNotEquals(zone, new Zone(4));
        assertNotEquals(zone, null);
        assertNotEquals(zone, "zone");
        assertEquals("Zone{id=3}", zone.toString());
    }

    @Test
    void roadExposesValuesAndImplementsEquality() {
        Zone first = new Zone(1);
        Zone second = new Zone(2);
        Road road = new Road(first, second, 7);
        Road sameRoad = new Road(new Zone(1), new Zone(2), 99);

        assertEquals(first, road.getZoneA());
        assertEquals(second, road.getZoneB());
        assertEquals(7, road.getTravelTime());
        assertEquals(road, road);
        assertEquals(road, sameRoad);
        assertEquals(road.hashCode(), sameRoad.hashCode());
        assertNotEquals(road, new Road(second, first, 7));
        assertNotEquals(road, null);
        assertNotEquals(road, "road");
        assertEquals("Line{zoneA=Zone{id=1}, zoneB=Zone{id=2}, travelTime=7}", road.toString());
    }

    @Test
    void complaintExposesValuesAndImplementsEquality() {
        Zone zone = new Zone(2);
        Complaint complaint = new Complaint(zone, 15);
        Complaint sameZoneComplaint = new Complaint(new Zone(2), 60);

        assertEquals(zone, complaint.getZone());
        assertEquals(15, complaint.getTime());
        assertEquals(complaint, complaint);
        assertEquals(complaint, sameZoneComplaint);
        assertEquals(complaint.hashCode(), sameZoneComplaint.hashCode());
        assertNotEquals(complaint, new Complaint(new Zone(3), 15));
        assertNotEquals(complaint, null);
        assertNotEquals(complaint, "complaint");
        assertEquals("Complaint{zone=Zone{id=2}, time=15}", complaint.toString());
    }

    @Test
    void pathAccumulatesTravelTimeAndFormatsIt() {
        Zone first = new Zone(1);
        Zone second = new Zone(2);
        Road road = new Road(first, second, 5);
        Path path = new Path(first, second);

        assertNotNull(path.getRoads());
        assertTrue(path.getRoads().isEmpty());
        assertEquals(0, path.getTravelTime());

        path.addRoad(road);
        path.addRoad(new Road(second, new Zone(3), 10));

        assertEquals(2, path.getRoads().size());
        assertEquals(15, path.getTravelTime());
        assertTrue(path.toString().contains("from=Zone{id=1}"));
        assertTrue(path.toString().contains("to=Zone{id=2}"));
        assertFalse(path.toString().isBlank());
    }
}
