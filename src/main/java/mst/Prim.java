
package mst;

import graph.Edge;
import graph.Graph;
import metrics.AlgorithmMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    public static MSTResult findMST(Graph graph) {
        return findMST(graph, null);
    }

    public static MSTResult findMST(
            Graph graph,
            AlgorithmMetrics metrics
    ) {
        int vertices = graph.getVertices();

        if (vertices == 0) {
            throw new IllegalArgumentException(
                    "Graph must contain at least one vertex"
            );
        }

        boolean[] visited = new boolean[vertices];

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
                (a, b) -> {
                    if (metrics != null) {
                        metrics.recordComparison();
                    }

                    return Double.compare(
                            a.getWeight(),
                            b.getWeight()
                    );
                }
        );

        List<Edge> mstEdges = new ArrayList<>();
        double totalWeight = 0.0;

        visited[0] = true;

        for (Edge edge : graph.getNeighbors(0)) {
            if (metrics != null) {
                metrics.recordEdgeExamination();
            }

            priorityQueue.add(edge);
        }

        while (!priorityQueue.isEmpty()
                && mstEdges.size() < vertices - 1) {

            Edge edge = priorityQueue.poll();
            int destination = edge.getDestination();

            if (visited[destination]) {
                continue;
            }

            visited[destination] = true;
            mstEdges.add(edge);
            totalWeight += edge.getWeight();

            for (Edge neighbor : graph.getNeighbors(destination)) {
                if (metrics != null) {
                    metrics.recordEdgeExamination();
                }

                if (!visited[neighbor.getDestination()]) {
                    priorityQueue.add(neighbor);
                }
            }
        }

        if (mstEdges.size() != vertices - 1) {
            throw new IllegalArgumentException(
                    "Graph must be connected to have an MST"
            );
        }

        return new MSTResult(mstEdges, totalWeight);
    }
}
