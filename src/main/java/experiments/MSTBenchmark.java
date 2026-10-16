
package experiments;

import graph.Graph;
import metrics.AlgorithmMetrics;
import mst.Kruskal;
import mst.MSTResult;
import mst.Prim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MSTBenchmark {

    private static final int REPEATS = 5;

    private MSTBenchmark() {
    }

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);

        int[][] sizes = {
                {10, 30},
                {100, 500},
                {1000, 5000}
        };

        List<String> csvRows = new ArrayList<>();

        csvRows.add(
                "vertices,edges,kruskal_avg_ms,prim_avg_ms,"
                        + "kruskal_edge_examinations,kruskal_comparisons,"
                        + "dsu_find_calls,dsu_union_calls,"
                        + "prim_edge_examinations,prim_comparisons"
        );

        System.out.println("MST Algorithm Benchmark");
        System.out.println("Average runtime over " + REPEATS + " runs");
        System.out.println();

        System.out.printf(
                "%-8s %-8s %-18s %-18s %-18s %-18s%n",
                "V", "E", "Kruskal (ms)", "Prim (ms)",
                "Kruskal ops", "Prim ops"
        );

        for (int[] size : sizes) {
            int vertices = size[0];
            int edges = size[1];

            Graph graph =
                    GraphGenerator.generateConnectedUndirectedGraph(
                            vertices, edges, 42L
                    );

            double kruskalTime = averageKruskalTime(graph);
            double primTime = averagePrimTime(graph);

            AlgorithmMetrics kruskalMetrics =
                    new AlgorithmMetrics();
            Kruskal.findMST(graph, kruskalMetrics);

            AlgorithmMetrics primMetrics =
                    new AlgorithmMetrics();
            Prim.findMST(graph, primMetrics);

            long kruskalOps =
                    kruskalMetrics.getEdgeExaminations()
                            + kruskalMetrics.getComparisons()
                            + kruskalMetrics.getDsuFindCalls()
                            + kruskalMetrics.getDsuUnionCalls();

            long primOps =
                    primMetrics.getEdgeExaminations()
                            + primMetrics.getComparisons();

            System.out.printf(
                    "%-8d %-8d %-18.4f %-18.4f %-18d %-18d%n",
                    vertices, edges, kruskalTime, primTime,
                    kruskalOps, primOps
            );

            String csvRow = String.format(
                    Locale.US,
                    "%d,%d,%.6f,%.6f,%d,%d,%d,%d,%d,%d",
                    vertices,
                    edges,
                    kruskalTime,
                    primTime,
                    kruskalMetrics.getEdgeExaminations(),
                    kruskalMetrics.getComparisons(),
                    kruskalMetrics.getDsuFindCalls(),
                    kruskalMetrics.getDsuUnionCalls(),
                    primMetrics.getEdgeExaminations(),
                    primMetrics.getComparisons()
            );

            csvRows.add(csvRow);
        }

        Path output = Path.of("results", "mst_benchmark.csv");
        Files.createDirectories(output.getParent());
        Files.write(output, csvRows);

        System.out.println();
        System.out.println(
                "CSV results saved to: " + output.toAbsolutePath()
        );
    }

    private static double averageKruskalTime(Graph graph) {
        long totalNanos = 0;

        for (int i = 0; i < REPEATS; i++) {
            long start = System.nanoTime();
            MSTResult result = Kruskal.findMST(graph);
            long end = System.nanoTime();

            totalNanos += end - start;

            if (result.getEdges().size()
                    != graph.getVertices() - 1) {
                throw new IllegalStateException(
                        "Kruskal returned an invalid MST"
                );
            }
        }

        return totalNanos / (double) REPEATS / 1_000_000.0;
    }

    private static double averagePrimTime(Graph graph) {
        long totalNanos = 0;

        for (int i = 0; i < REPEATS; i++) {
            long start = System.nanoTime();
            MSTResult result = Prim.findMST(graph);
            long end = System.nanoTime();

            totalNanos += end - start;

            if (result.getEdges().size()
                    != graph.getVertices() - 1) {
                throw new IllegalStateException(
                        "Prim returned an invalid MST"
                );
            }
        }

        return totalNanos / (double) REPEATS / 1_000_000.0;
    }
}
