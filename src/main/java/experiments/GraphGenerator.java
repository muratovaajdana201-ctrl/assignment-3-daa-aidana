
package experiments;

import graph.Edge;
import graph.Graph;

import java.util.Random;

public class GraphGenerator {

    private GraphGenerator() {
        // Utility class
    }

    public static Graph generateConnectedUndirectedGraph(
            int vertices,
            int edges,
            long seed
    ) {
        if (vertices < 1) {
            throw new IllegalArgumentException(
                    "The graph must contain at least one vertex"
            );
        }

        long maxEdges = (long) vertices * (vertices - 1) / 2;

        if (edges < vertices - 1 || edges > maxEdges) {
            throw new IllegalArgumentException(
                    "Invalid edge count for a connected simple graph"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed);

        // First, connect all vertices in a chain.
        for (int vertex = 1; vertex < vertices; vertex++) {
            double weight = 1 + random.nextInt(100);
            graph.addUndirectedEdge(vertex - 1, vertex, weight);
        }

        // Then add random edges without duplicates.
        int addedEdges = vertices - 1;

        while (addedEdges < edges) {
            int source = random.nextInt(vertices);
            int destination = random.nextInt(vertices);

            if (source == destination
                    || hasEdge(graph, source, destination)) {
                continue;
            }

            double weight = 1 + random.nextInt(100);
            graph.addUndirectedEdge(source, destination, weight);
            addedEdges++;
        }

        return graph;
    }

    private static boolean hasEdge(
            Graph graph,
            int source,
            int destination
    ) {
        for (Edge edge : graph.getNeighbors(source)) {
            if (edge.getDestination() == destination) {
                return true;
            }
        }

        return false;
    }
}
