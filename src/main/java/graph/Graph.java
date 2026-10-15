package graph;

import java.util.*;

public class Graph {

    private final int vertices;
    private final List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices cannot be negative");
        }

        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public int getVertices() {
        return vertices;
    }

    public void addDirectedEdge(int source, int destination, double weight) {
        validateVertex(source);
        validateVertex(destination);

        adjacencyList.get(source)
                .add(new Edge(source, destination, weight));
    }

    public void addUndirectedEdge(int source, int destination, double weight) {
        validateVertex(source);
        validateVertex(destination);

        adjacencyList.get(source)
                .add(new Edge(source, destination, weight));

        adjacencyList.get(destination)
                .add(new Edge(destination, source, weight));
    }

    public List<Edge> getNeighbors(int vertex) {
        validateVertex(vertex);

        return Collections.unmodifiableList(adjacencyList.get(vertex));
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();

        for (List<Edge> neighbors : adjacencyList) {
            edges.addAll(neighbors);
        }

        return edges;
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException(
                    "Invalid vertex: " + vertex
            );
        }
    }
}