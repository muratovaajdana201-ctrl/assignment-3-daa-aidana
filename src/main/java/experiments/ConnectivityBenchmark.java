
package experiments;

import connectivity.SCC;
import connectivity.TopologicalSort;
import graph.Edge;
import graph.Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectivityBenchmark {

    private static final int RUNS = 5;

    private static final int[][] SIZES = {
            {10, 30},
            {100, 500},
            {1000, 5000}
    };

    public static void main(String[] args) throws IOException {

        List<String> rows = new ArrayList<>();
        rows.add(
                "vertices,edges,scc_avg_ms,topological_avg_ms,scc_count"
        );

        for (int[] size : SIZES) {
            int vertices = size[0];
            int edges = size[1];

            Graph directedGraph =
                    generateDirectedGraph(vertices, edges, 42L);

            Graph dag =
                    generateDag(vertices, edges, 42L);

            double sccTime = averageSccTime(directedGraph);
            double topoTime = averageTopoTime(dag);

            int sccCount = SCC.findSCCs(directedGraph).size();

            rows.add(String.format(
                    java.util.Locale.US,
                    "%d,%d,%.6f,%.6f,%d",
                    vertices, edges, sccTime, topoTime, sccCount
            ));

            System.out.printf(
                    "V=%d E=%d SCC=%.6f ms Topological=%.6f ms SCC count=%d%n",
                    vertices, edges, sccTime, topoTime, sccCount
            );
        }

        // Test that a directed cycle is detected.
        Graph cyclic = new Graph(3);
        cyclic.addDirectedEdge(0, 1, 1);
        cyclic.addDirectedEdge(1, 2, 1);
        cyclic.addDirectedEdge(2, 0, 1);

        boolean cycleDetected = false;

        try {
            TopologicalSort.sort(cyclic);
        } catch (IllegalArgumentException expected) {
            cycleDetected = true;
        }

        if (!cycleDetected) {
            throw new IllegalStateException(
                    "Topological sort failed to detect a cycle"
            );
        }

        System.out.println("Cycle detection test: PASS");

        Path output = Path.of(
                "results", "connectivity_benchmark.csv"
        );

        Files.createDirectories(output.getParent());
        Files.write(output, rows);

        System.out.println(
                "Saved results to " + output.toAbsolutePath()
        );
    }

    private static double averageSccTime(Graph graph) {

        // Warm-up, outside the measured runs.
        SCC.findSCCs(graph);

        long total = 0;

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            SCC.findSCCs(graph);

            total += System.nanoTime() - start;
        }

        return total / (double) RUNS / 1_000_000.0;
    }

    private static double averageTopoTime(Graph graph) {

        // Warm-up, outside the measured runs.
        TopologicalSort.sort(graph);

        long total = 0;

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            TopologicalSort.sort(graph);

            total += System.nanoTime() - start;
        }

        return total / (double) RUNS / 1_000_000.0;
    }

    private static Graph generateDag(
            int vertices, int edges, long seed) {

        long maxEdges = (long) vertices * (vertices - 1) / 2;

        if (edges < vertices - 1 || edges > maxEdges) {
            throw new IllegalArgumentException(
                    "Invalid edge count for DAG"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed);

        // A chain guarantees that all vertices are reachable
        // from the preceding vertex and that the graph is connected.
        for (int v = 0; v < vertices - 1; v++) {
            graph.addDirectedEdge(v, v + 1, 1);
        }

        int added = vertices - 1;

        while (added < edges) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            // Every edge points from a smaller ID to a larger ID.
            if (from >= to || hasEdge(graph, from, to)) {
                continue;
            }

            graph.addDirectedEdge(from, to, 1);
            added++;
        }

        return graph;
    }

    private static Graph generateDirectedGraph(
            int vertices, int edges, long seed) {

        long maxEdges = (long) vertices * (vertices - 1);

        if (edges < 0 || edges > maxEdges) {
            throw new IllegalArgumentException(
                    "Invalid directed edge count"
            );
        }

        Graph graph = new Graph(vertices);
        Random random = new Random(seed);

        int added = 0;

        while (added < edges) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from == to || hasEdge(graph, from, to)) {
                continue;
            }

            graph.addDirectedEdge(
                    from, to, 1 + random.nextInt(20)
            );

            added++;
        }

        return graph;
    }

    private static boolean hasEdge(
            Graph graph, int from, int to) {

        for (Edge edge : graph.getNeighbors(from)) {
            if (edge.getDestination() == to) {
                return true;
            }
        }

        return false;
    }
}
