
package connectivity;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {

    @Test
    void producesValidTopologicalOrder() {
        Graph graph = new Graph(6);

        graph.addDirectedEdge(5, 2, 1);
        graph.addDirectedEdge(5, 0, 1);
        graph.addDirectedEdge(4, 0, 1);
        graph.addDirectedEdge(4, 1, 1);
        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 1, 1);

        List<Integer> order = TopologicalSort.sort(graph);

        assertEquals(6, order.size());
        assertEquals(6, order.stream().distinct().count());

        // Every directed edge must go from an earlier to a later vertex.
        int[] position = new int[6];

        for (int i = 0; i < order.size(); i++) {
            position[order.get(i)] = i;
        }

        for (int u = 0; u < graph.getVertices(); u++) {
            for (var edge : graph.getNeighbors(u)) {
                assertTrue(
                        position[u] < position[edge.getDestination()],
                        "Edge violates topological order: " + edge
                );
            }
        }
    }

    @Test
    void handlesGraphWithNoEdges() {
        Graph graph = new Graph(4);

        List<Integer> order = TopologicalSort.sort(graph);

        assertEquals(4, order.size());
        assertEquals(4, order.stream().distinct().count());
    }

    @Test
    void rejectsDirectedCycle() {
        Graph graph = new Graph(3);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 2, 1);
        graph.addDirectedEdge(2, 0, 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSort.sort(graph)
        );
    }
}
