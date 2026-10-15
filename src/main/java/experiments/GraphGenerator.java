
package experiments;

import graph.Graph;

import java.util.Random;

public class GraphGenerator {

    private GraphGenerator() {
        // Utility class.
    }

    public static Graph generateConnectedUndirectedGraph(
            int vertices,
            int edges,
            long seed
    ) {
        if (vertices < 1) {
            throw new IllegalArgumentException(
                    "The graph must have at least one vertex"
            );
        }

        long maxEdges = (long) vertices * (vertices - 1) / 2;

        if (edges < vertices - 1 || edges > maxEdges) {
            throw new IllegalArgumentException(
                    "A connected simple undirected graph requires "
                            + "V - 1 <= E <= V * (V - 1) / 2"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed);

        // Step 1: create a spanning chain so the graph is connected.
        for (int v = 1; v < vertices; v++) {
            double weight = 1 + random.nextInt(100);
            graph.addUndirectedEdge(v - 1, v, weight);
        }

        // Step 2: add random edges without duplicates.
        while (graph.getAllEdges().size() / 2 < edges) {
            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);

            if (u == v || hasEdge(graph, u, v)) {
                continue;
            }

            double weight = 1 + random.nextInt(100);
            graph.addUndirectedEdge(u, v, weight);
        }

        return graph;
    }

    private static boolean hasEdge(Graph graph, int u, int v) {
        for (var edge : graph.getNeighbors(u)) {
            if (edge.getDestination() == v) {
                return true;
            }
        }

        return false;
    }
}
