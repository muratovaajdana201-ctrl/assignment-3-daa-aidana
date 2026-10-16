
package shortestpath;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmMetrics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    private Dijkstra() {
    }

    private static class Node {
        private final int vertex;
        private final double distance;

        private Node(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static ShortestPathResult findShortestPaths(Graph graph, int source) {
        return findShortestPaths(graph, source, null);
    }

    public static ShortestPathResult findShortestPaths(
            Graph graph, int source, AlgorithmMetrics metrics) {

        int vertices = graph.getVertices();

        if (source < 0 || source >= vertices) {
            throw new IllegalArgumentException("Invalid source vertex: " + source);
        }

        // Validate all edges before running the algorithm.
        for (int u = 0; u < vertices; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                if (edge.getWeight() < 0) {
                    throw new IllegalArgumentException(
                            "Dijkstra does not support negative edge weights");
                }
            }
        }

        double[] distances = new double[vertices];
        int[] previous = new int[vertices];
        boolean[] settled = new boolean[vertices];

        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        Arrays.fill(previous, -1);
        distances[source] = 0.0;

        PriorityQueue<Node> queue = new PriorityQueue<>(
                Comparator.comparingDouble(node -> node.distance));
        queue.add(new Node(source, 0.0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int u = current.vertex;

            // Ignore obsolete priority-queue entries.
            if (current.distance > distances[u] || settled[u]) {
                continue;
            }

            settled[u] = true;

            for (Edge edge : graph.getNeighbors(u)) {
                if (metrics != null) {
                    metrics.recordEdgeExamination();
                }

                int v = edge.getDestination();
                double candidate = distances[u] + edge.getWeight();

                if (candidate < distances[v]) {
                    if (metrics != null) {
                        metrics.recordComparison();
                    }

                    distances[v] = candidate;
                    previous[v] = u;
                    queue.add(new Node(v, candidate));
                }
            }
        }

        return new ShortestPathResult(source, distances, previous);
    }
}
