
package shortestpath;

import graph.Edge;
import graph.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {

    private static class NodeDistance {
        private final int vertex;
        private final double distance;

        private NodeDistance(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static double[] shortestPaths(Graph graph, int source) {
        int vertices = graph.getVertices();

        if (source < 0 || source >= vertices) {
            throw new IllegalArgumentException(
                    "Invalid source vertex: " + source
            );
        }

        // Dijkstra requires non-negative edge weights.
        for (int u = 0; u < vertices; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                if (!Double.isFinite(edge.getWeight())
                        || edge.getWeight() < 0) {
                    throw new IllegalArgumentException(
                            "Dijkstra requires finite, non-negative edge weights"
                    );
                }
            }
        }

        double[] distances = new double[vertices];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[source] = 0.0;

        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(
                (a, b) -> Double.compare(a.distance, b.distance)
        );

        queue.add(new NodeDistance(source, 0.0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            int u = current.vertex;

            // Ignore outdated queue entries.
            if (current.distance > distances[u]) {
                continue;
            }

            for (Edge edge : graph.getNeighbors(u)) {
                int v = edge.getDestination();
                double newDistance =
                        distances[u] + edge.getWeight();

                if (newDistance < distances[v]) {
                    distances[v] = newDistance;
                    queue.add(new NodeDistance(v, newDistance));
                }
            }
        }

        return distances;
    }
}
