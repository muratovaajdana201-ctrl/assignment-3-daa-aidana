
package shortestpath;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    @Test
    void findsShortestDistancesAndPaths() {
        Graph graph = new Graph(5);
        graph.addDirectedEdge(0, 1, 4);
        graph.addDirectedEdge(0, 2, 1);
        graph.addDirectedEdge(2, 1, 2);
        graph.addDirectedEdge(1, 3, 1);
        graph.addDirectedEdge(2, 3, 5);

        ShortestPathResult result = Dijkstra.findShortestPaths(graph, 0);

        assertEquals(0.0, result.getDistanceTo(0));
        assertEquals(3.0, result.getDistanceTo(1));
        assertEquals(1.0, result.getDistanceTo(2));
        assertEquals(4.0, result.getDistanceTo(3));
        assertEquals(List.of(0, 2, 1, 3), result.getPathTo(3));
    }

    @Test
    void marksUnreachableVertices() {
        Graph graph = new Graph(3);
        graph.addDirectedEdge(0, 1, 7);

        ShortestPathResult result = Dijkstra.findShortestPaths(graph, 0);

        assertTrue(result.isReachable(1));
        assertFalse(result.isReachable(2));
        assertTrue(Double.isInfinite(result.getDistanceTo(2)));
        assertTrue(result.getPathTo(2).isEmpty());
    }

    @Test
    void rejectsNegativeWeights() {
        Graph graph = new Graph(2);
        graph.addDirectedEdge(0, 1, -1);

        assertThrows(IllegalArgumentException.class,
                () -> Dijkstra.findShortestPaths(graph, 0));
    }

    @Test
    void handlesSingleVertex() {
        Graph graph = new Graph(1);

        ShortestPathResult result = Dijkstra.findShortestPaths(graph, 0);

        assertEquals(0.0, result.getDistanceTo(0));
        assertEquals(List.of(0), result.getPathTo(0));
    }

    @Test
    void rejectsInvalidSource() {
        Graph graph = new Graph(2);

        assertThrows(IllegalArgumentException.class,
                () -> Dijkstra.findShortestPaths(graph, 2));
    }
}
