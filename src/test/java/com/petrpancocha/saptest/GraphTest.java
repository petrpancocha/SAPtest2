package com.petrpancocha.saptest;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GraphTest {

    @Test
    void findsAllSimplePathsInBidirectionalGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 3);
        graph.addEdge(0, 2);
        graph.addEdge(2, 3);

        assertEquals(List.of(List.of(0, 1, 3), List.of(0, 2, 3)), graph.findAllPaths(0, 3));
    }

    @Test
    void returnsEmptyWhenDestinationIsUnreachable() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1);

        assertEquals(List.of(), graph.findAllPaths(0, 2));
    }

    @Test
    void returnsSingleVertexPathWhenSourceIsDestination() {
        Graph graph = new Graph(1);

        assertEquals(List.of(List.of(0)), graph.findAllPaths(0, 0));
    }

    @Test
    void rejectsOutOfRangeVertexIndicesWhenSearching() {
        Graph graph = new Graph(2);

        assertThrows(IndexOutOfBoundsException.class, () -> graph.findAllPaths(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> graph.findAllPaths(0, 2));
    }

    @Test
    void validatesBothEdgeEndpointsBeforeChangingGraph() {
        Graph graph = new Graph(2);

        assertThrows(IndexOutOfBoundsException.class, () -> graph.addEdge(0, 2));
        assertEquals(List.of(), graph.findAllPaths(0, 1));
    }
}
