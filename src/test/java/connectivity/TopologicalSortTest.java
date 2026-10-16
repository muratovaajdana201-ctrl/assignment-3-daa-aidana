
package connectivity;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {

    @Test
    void returnsValidOrderForDag() {
        Graph graph = new Graph(4);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(0, 2, 1);
        graph.addDirectedEdge(1, 3, 1);
        graph.addDirectedEdge(2, 3, 1);

        List<Integer> order = TopologicalSort.sort(graph);

        assertEquals(4, order.size());
        assertEquals(4, order.stream().distinct().count());

        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertTrue(order.indexOf(1) < order.indexOf(3));
        assertTrue(order.indexOf(2) < order.indexOf(3));
    }

    @Test
    void detectsCycle() {
        Graph graph = new Graph(3);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 2, 1);
        graph.addDirectedEdge(2, 0, 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> TopologicalSort.sort(graph)
        );
    }

    @Test
    void handlesSingleVertex() {
        Graph graph = new Graph(1);

        assertEquals(List.of(0), TopologicalSort.sort(graph));
    }
}
