
package experiments;

import graph.Graph;
import metrics.AlgorithmMetrics;
import shortestpath.Dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DijkstraBenchmark {
    private static final int RUNS = 5;

    private static final int[][] SIZES = {
            {10, 30},
            {100, 500},
            {1000, 5000}
    };

    public static void main(String[] args) throws IOException {
        List<String> rows = new ArrayList<>();
        rows.add("vertices,edges,avg_time_ms,edge_examinations,comparisons");

        System.out.printf("%-10s %-10s %-15s%n",
                "Vertices", "Edges", "Dijkstra ms");

        for (int[] size : SIZES) {
            int vertices = size[0];
            int edges = size[1];

            Graph graph = generateDirectedGraph(vertices, edges, 42L);
            long totalNanos = 0;

            // Warm-up to reduce some JVM startup effects.
            Dijkstra.findShortestPaths(graph, 0);

            for (int run = 0; run < RUNS; run++) {
                long start = System.nanoTime();
                Dijkstra.findShortestPaths(graph, 0);
                totalNanos += System.nanoTime() - start;
            }

            AlgorithmMetrics metrics = new AlgorithmMetrics();
            Dijkstra.findShortestPaths(graph, 0, metrics);

            double averageMs = totalNanos / (double) RUNS / 1_000_000.0;

            rows.add(String.format(java.util.Locale.US,
                    "%d,%d,%.6f,%d,%d",
                    vertices, edges, averageMs,
                    metrics.getEdgeExaminations(),
                    metrics.getComparisons()));

            System.out.printf("%-10d %-10d %-15.6f%n",
                    vertices, edges, averageMs);
        }

        Path output = Path.of("results", "dijkstra_benchmark.csv");
        Files.createDirectories(output.getParent());
        Files.write(output, rows);

        System.out.println("Saved results to " + output.toAbsolutePath());
    }

    private static Graph generateDirectedGraph(
            int vertices, int edges, long seed) {

        long maxEdges = (long) vertices * (vertices - 1);
        if (edges < vertices - 1 || edges > maxEdges) {
            throw new IllegalArgumentException("Invalid edge count");
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed);

        // A directed chain makes every vertex reachable from vertex 0.
        for (int v = 1; v < vertices; v++) {
            graph.addDirectedEdge(v - 1, v, 1 + random.nextInt(20));
        }

        int added = vertices - 1;
        while (added < edges) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from == to || hasDirectedEdge(graph, from, to)) {
                continue;
            }

            graph.addDirectedEdge(from, to, 1 + random.nextInt(20));
            added++;
        }

        return graph;
    }

    private static boolean hasDirectedEdge(Graph graph, int from, int to) {
        for (var edge : graph.getNeighbors(from)) {
            if (edge.getDestination() == to) {
                return true;
            }
        }
        return false;
    }
}
