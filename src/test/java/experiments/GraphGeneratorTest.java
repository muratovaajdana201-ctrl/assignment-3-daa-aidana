
package experiments;

import graph.Edge;
import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class GraphGeneratorTest {

    @Test
    void generatesGraphWithExpectedNumberOfVerticesAndEdges() {
        Graph graph =
                GraphGenerator.generateConnectedUndirectedGraph(
                        10, 30, 42L
                );

        assertEquals(10, graph.getVertices());

        // Every undirected edge is stored in both directions.
        assertEquals(60, graph.getAllEdges().size());
    }

    @Test
    void generatedGraphIsConnected() {
        Graph graph =
                GraphGenerator.generateConnectedUndirectedGraph(
                        10, 30, 42L
                );

        boolean[] visited = new boolean[graph.getVertices()];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[0] = true;
        queue.add(0);

        while (!queue.isEmpty()) {
            int vertex = queue.remove();

            for (Edge edge : graph.getNeighbors(vertex)) {
                int next = edge.getDestination();

                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }

        for (boolean wasVisited : visited) {
            assertTrue(wasVisited);
        }
    }

    @Test
    void sameSeedProducesSameGraph() {
        Graph first =
                GraphGenerator.generateConnectedUndirectedGraph(
                        10, 30, 42L
                );

        Graph second =
                GraphGenerator.generateConnectedUndirectedGraph(
                        10, 30, 42L
                );

        assertEquals(first.getAllEdges().size(),
                second.getAllEdges().size());

        for (int vertex = 0; vertex < first.getVertices(); vertex++) {
            assertEquals(
                    first.getNeighbors(vertex).size(),
                    second.getNeighbors(vertex).size()
            );

            for (int i = 0; i < first.getNeighbors(vertex).size(); i++) {
                Edge a = first.getNeighbors(vertex).get(i);
                Edge b = second.getNeighbors(vertex).get(i);

                assertEquals(a.getSource(), b.getSource());
                assertEquals(a.getDestination(), b.getDestination());
                assertEquals(a.getWeight(), b.getWeight());
            }
        }
    }
}
