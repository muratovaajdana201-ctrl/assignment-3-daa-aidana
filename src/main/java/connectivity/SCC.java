
package connectivity;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SCC {

    public static List<List<Integer>> findSCCs(Graph graph) {
        int vertices = graph.getVertices();

        boolean[] visited = new boolean[vertices];
        List<Integer> finishOrder = new ArrayList<>();

        // First DFS: record vertices in finishing order.
        for (int v = 0; v < vertices; v++) {
            if (!visited[v]) {
                dfsOriginal(graph, v, visited, finishOrder);
            }
        }

        // Reverse all directed edges.
        Graph reversed = new Graph(vertices);

        for (int u = 0; u < vertices; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                reversed.addDirectedEdge(
                        edge.getDestination(),
                        u,
                        edge.getWeight()
                );
            }
        }

        // Second DFS: explore the reversed graph in reverse finish order.
        java.util.Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();

        Collections.reverse(finishOrder);

        for (int v : finishOrder) {
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfsReversed(reversed, v, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private static void dfsOriginal(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> finishOrder
    ) {
        visited[vertex] = true;

        for (Edge edge : graph.getNeighbors(vertex)) {
            int next = edge.getDestination();

            if (!visited[next]) {
                dfsOriginal(graph, next, visited, finishOrder);
            }
        }

        finishOrder.add(vertex);
    }

    private static void dfsReversed(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> component
    ) {
        visited[vertex] = true;
        component.add(vertex);

        for (Edge edge : graph.getNeighbors(vertex)) {
            int next = edge.getDestination();

            if (!visited[next]) {
                dfsReversed(graph, next, visited, component);
            }
        }
    }
}
