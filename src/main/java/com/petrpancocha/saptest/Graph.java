package com.petrpancocha.saptest;

import java.util.ArrayList;
import java.util.List;

// A directed graph using adjacency list representation
public class Graph {

    // Count of vertices in graph
    private int v;

    // adjacency list
    private ArrayList<Integer>[] adjList;

    public Graph(int vertices) {

        // initialise vertex count
        this.v = vertices;

        // initialise adjacency list
        initAdjList();
    }

    private void initAdjList() {
        adjList = new ArrayList[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    // Adds edge from u to v
    public void addEdge(int u, int v) {
        // Add v to u's list.
        adjList[u].add(v);
    }

    // Finds all paths from 's' to 'd'
    public List<List<Integer>> findAllPaths(int s, int d) {
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        // add source to path[]
        pathList.add(s);

        // Call recursive utility
        List<List<Integer>> resultPaths = new ArrayList<>();
        findAllPathsRecursive(s, d, isVisited, pathList, resultPaths);

        return resultPaths;
    }

    // A recursive function to find all paths from 'u' to 'd'.
    // isVisited[] keeps track of vertices in current path.
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

        // Recur for all the vertices
        // adjacent to current vertex
        for (Integer i : adjList[u]) {
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
