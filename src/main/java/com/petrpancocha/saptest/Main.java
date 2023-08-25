package com.petrpancocha.saptest;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int NUM_ZONES = 4;
    private static final int[][] ROADS = {{1, 2, 5}, {2, 3, 10}, {3, 4, 20}, {1, 4, 1}};
    private static final int[][] COMPLAINTS = {{2, 65}, {3, 15}};

    // zones/roads/complaints as model objects
    private List<Zone> zones;
    private List<Road> roads;
    private List<Complaint> complaints;

    //-----------------------------------------------------------------------------------
    // Methods for preparation of model objects from input "array data"
    private void createZones() {
        this.zones = new ArrayList<>();

        for (int i = 1; i <= NUM_ZONES; i++) {
            zones.add(new Zone(i));
        }
    }

    private Zone getZoneById(int zoneId) {
        return zones.stream()
                .filter(z -> z.getId() == zoneId)
                .findFirst()
                .orElse(null);
    }

    private Zone getZoneByGraphMappingId(int graphMappingId) {
        return zones.stream()
                .filter(z -> z.getGraphMappingId() == graphMappingId)
                .findFirst()
                .orElse(null);
    }

    private Road getRoadByZones(int fromZoneId, int toZoneId) {
        return roads.stream()
                .filter(r -> (r.getZoneA().getId() == fromZoneId && r.getZoneB().getId() == toZoneId)
                        || (r.getZoneA().getId() == toZoneId && r.getZoneB().getId() == fromZoneId))
                .findFirst()
                .orElse(null);
    }

    private void createRoads() {
        this.roads = new ArrayList<>();

        for (int[] road : ROADS) {
            roads.add(new Road(
                    getZoneById(road[0]),
                    getZoneById(road[1]),
                    road[2]));
        }
    }

    private void createComplaints() {
        this.complaints = new ArrayList<>();

        for (int[] complaint : COMPLAINTS) {
            complaints.add(new Complaint(
                    getZoneById(complaint[0]),
                    complaint[1]));
        }
    }

    private Graph createGraph() {
        Graph graph = new Graph(NUM_ZONES);

        for (Road road : roads) {
            graph.addEdge(road.getZoneA().getGraphMappingId(), road.getZoneB().getGraphMappingId());
        }

        return graph;
    }

    private List<Path> convert2Paths(List<List<Integer>> graphPaths) {
        List<Path> paths = new ArrayList<>();

        for (List<Integer> graphPath : graphPaths) {
            Zone from = getZoneByGraphMappingId(graphPath.get(0));
            Zone to = getZoneByGraphMappingId(graphPath.get(graphPath.size() - 1));

            Path path = new Path(from, to);

            for (int i = 0; i < graphPath.size() - 1; i++) {
                int idZoneA = getZoneByGraphMappingId(graphPath.get(i)).getId();
                int idZoneB = getZoneByGraphMappingId(graphPath.get(i + 1)).getId();

                Road road = getRoadByZones(idZoneA, idZoneB);
                path.addRoad(road);
            }

            paths.add(path);
        }

        return paths;
    }

    //-----------------------------------------------------------------------------------
    // Methods for business logic

    private void process(Graph graph) {
        Zone zoneHQ = getZoneById(1);
        for (Complaint complaint : complaints) {
            System.out.println("All paths from " + zoneHQ + " to " + complaint.getZone() + " to fix an issue");
            List<List<Integer>> graphPaths = graph.findAllPaths(zoneHQ.getGraphMappingId(), complaint.getZone().getGraphMappingId());
            List<Path> paths = convert2Paths(graphPaths);

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
        main.createZones();
        main.createRoads();
        main.createComplaints();

        // create graph representation of roads
        Graph graph = main.createGraph();

        // execute business logic
        main.process(graph);
    }
}