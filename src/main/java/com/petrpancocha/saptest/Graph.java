package com.petrpancocha.saptest;

import java.util.ArrayList;
import java.util.List;

// A directed graph using neighbours list representation
public class Graph {

    // count of vertices in graph
    private int v;

    // list of neighbours
    private List<List<Integer>> neighbours;

    public Graph(int vertices) {

        // initialize vertex count
        this.v = vertices;

        // initialize list of neighbours
        initNeighbours();
    }

    private void initNeighbours() {
        neighbours = new ArrayList<>(v);

        for (int i = 0; i < v; i++) {
            neighbours.add(new ArrayList<>());
        }
    }

    // Adds edge from u to v
    public void addEdge(int u, int v) {
        checkVertexIndex(u);
        checkVertexIndex(v);

        // Add v to u's list.
        neighbours.get(u).add(v);

        // Add u to v's list because graph is bidirectional
        neighbours.get(v).add(u);
    }

    // Finds all paths from 's' to 'd'
    public List<List<Integer>> findAllPaths(int s, int d) {
        checkVertexIndex(s);
        checkVertexIndex(d);

        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        // add source to path[]
        pathList.add(s);

        // Call recursive utility
        List<List<Integer>> resultPaths = new ArrayList<>();
        findAllPathsRecursive(s, d, isVisited, pathList, resultPaths);

        return resultPaths;
    }

    private void checkVertexIndex(int vertex) {
        if (vertex < 0 || vertex >= v) {
            throw new IndexOutOfBoundsException(
                    "Vertex index " + vertex + " is outside graph range [0, " + v + ")");
        }
    }

    // A recursive function to find all paths from 'u' to 'd'.
    // isVisited[] keeps track of vertices in current path
    // localPathList<> stores actual vertices in the current path
    private void findAllPathsRecursive(Integer u, Integer d,
                                       boolean[] isVisited,
                                       List<Integer> localPathList,
                                       List<List<Integer>> resultPaths) {

        if (u.equals(d)) {
            List<Integer> resultPath = localPathList.stream().toList();
            resultPaths.add(resultPath);
            return;
        }

        // Mark the current node
        isVisited[u] = true;

        // Recur for all the vertices that are neighbours of current vertex
        for (Integer i : neighbours.get(u)) {
            if (!isVisited[i]) {
                // store current node in path[]
                localPathList.add(i);
                findAllPathsRecursive(i, d, isVisited, localPathList, resultPaths);

                // remove current node in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }
}
