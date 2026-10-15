
package mst;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Prim {

    public static MSTResult findMST(Graph graph) {
        int vertices = graph.getVertices();

        if (vertices == 0) {
            throw new IllegalArgumentException(
                    "Graph must contain at least one vertex"
            );
        }

        boolean[] visited = new boolean[vertices];

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
                (a, b) -> Double.compare(
                        a.getWeight(),
                        b.getWeight()
                )
        );

        List<Edge> mstEdges = new ArrayList<>();
        double totalWeight = 0.0;

        visited[0] = true;
        priorityQueue.addAll(graph.getNeighbors(0));

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
