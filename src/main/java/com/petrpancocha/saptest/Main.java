package com.petrpancocha.saptest;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int NUM_ZONES = 4;
    private static final int[][] ROADS = {{1, 2, 5}, {2, 3, 10}, {3, 4, 20}, {1, 4, 1}};
    private static final int[][] COMPLAINTS = {{2, 65}, {3, 15}};

    //-----------------------------------------------------------------------------------
    // Methods for preparation of model objects from input "array data"
    private List<Zone> createZones() {
        List<Zone> zones = new ArrayList<>();

        for (int i = 1; i <= NUM_ZONES; i++) {
            zones.add(new Zone(i));
        }

        return zones;
    }

    private Zone getZoneById(List<Zone> zones, int zoneId) {
        return zones.stream()
                .filter(z -> z.getId() == zoneId)
                .findFirst()
                .orElse(null);
    }

    private Zone getZoneByGraphMappingId(List<Zone> zones, int graphMappingId) {
        return zones.stream()
                .filter(z -> z.getGraphMappingId() == graphMappingId)
                .findFirst()
                .orElse(null);
    }

    private Road getRoadByZones(List<Road> roads, int fromZoneId, int toZoneId) {
        return roads.stream()
                .filter(r -> (r.getZoneA().getId() == fromZoneId && r.getZoneB().getId() == toZoneId)
                        || (r.getZoneA().getId() == toZoneId && r.getZoneB().getId() == fromZoneId))
                .findFirst()
                .orElse(null);
    }

    private List<Road> createRoads(List<Zone> zones) {
        List<Road> roads = new ArrayList<>();

        for (int[] road : ROADS) {
            roads.add(new Road(
                    getZoneById(zones, road[0]),
                    getZoneById(zones, road[1]),
                    road[2]));
        }

        return roads;
    }

    private List<Complaint> createComplaints(List<Zone> zones) {
        List<Complaint> complaints = new ArrayList<>();

        for (int[] complaint : COMPLAINTS) {
            complaints.add(new Complaint(
                    getZoneById(zones, complaint[0]),
                    complaint[1]));
        }

        return complaints;
    }

    private Graph createGraph(List<Road> roads) {
        Graph graph = new Graph(NUM_ZONES);

        for (Road road : roads) {
            graph.addEdge(road.getZoneA().getGraphMappingId(), road.getZoneB().getGraphMappingId());
            graph.addEdge(road.getZoneB().getGraphMappingId(), road.getZoneA().getGraphMappingId()); // opposite direction
        }

        return graph;
    }

    private List<Path> convert2Paths(List<List<Integer>> graphPaths, List<Zone> zones, List<Road> roads) {
        List<Path> paths = new ArrayList<>();

        for (List<Integer> graphPath : graphPaths) {
            Zone from = getZoneByGraphMappingId(zones, graphPath.get(0));
            Zone to = getZoneByGraphMappingId(zones, graphPath.get(graphPath.size() - 1));

            Path path = new Path(from, to);

            for (int i = 0; i < graphPath.size() - 1; i++) {
                int idZoneA = getZoneByGraphMappingId(zones, graphPath.get(i)).getId();
                int idZoneB = getZoneByGraphMappingId(zones, graphPath.get(i + 1)).getId();

                Road road = getRoadByZones(roads, idZoneA, idZoneB);
                path.addRoad(road);
            }

            paths.add(path);
        }

        return paths;
    }

    //-----------------------------------------------------------------------------------
    // Methods for business logic

    private void process(Graph graph, List<Zone> zones, List<Road> roads, List<Complaint> complaints) {
        Zone zoneHQ = getZoneById(zones, 1);
        for (Complaint complaint : complaints) {
            System.out.println("All paths from " + zoneHQ + " to " + complaint.getZone() + " to fix an issue");
            List<List<Integer>> graphPaths = graph.findAllPaths(zoneHQ.getGraphMappingId(), complaint.getZone().getGraphMappingId());
            List<Path> paths = convert2Paths(graphPaths, zones, roads);

            int minimumTravelTime = -1;
            Path pathWithMinimumTravelTime = null;

            for (Path path : paths) {
                System.out.println(path);
                if (pathWithMinimumTravelTime == null || path.getTravelTime() < minimumTravelTime) {
                    pathWithMinimumTravelTime = path;
                    minimumTravelTime = path.getTravelTime();
                }
            }

            int fullTravelTime = 2 * minimumTravelTime;
            System.out.println("Minimum travel time (to + back) is " + fullTravelTime);

            int complaintTime = complaint.getTime();
            System.out.println("Time to fix a complaint is " + complaintTime);

            System.out.println("Rest time is " +
                    (fullTravelTime > complaintTime ? 0 : complaintTime - fullTravelTime));

            System.out.println();
        }
    }

    //-----------------------------------------------------------------------------------
    // Execution of exercise
    public static void main(String[] args) {
        Main main = new Main();

        // prepare model objects
        List<Zone> zones = main.createZones();
        List<Road> roads = main.createRoads(zones);
        List<Complaint> complaints = main.createComplaints(zones);

        Graph graph = main.createGraph(roads); // create graph representation of roads

        // execute business logic
        main.process(graph, zones, roads, complaints);
    }
}