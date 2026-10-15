
package shortestpath;

import graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    @Test
    void findsCorrectShortestDistances() {
        Graph graph = new Graph(5);

        graph.addDirectedEdge(0, 1, 10);
        graph.addDirectedEdge(0, 2, 3);
        graph.addDirectedEdge(2, 1, 1);
        graph.addDirectedEdge(1, 3, 2);
        graph.addDirectedEdge(2, 3, 8);
        graph.addDirectedEdge(2, 4, 2);
        graph.addDirectedEdge(4, 3, 7);

        double[] distances = Dijkstra.shortestPaths(graph, 0);

        assertArrayEquals(
                new double[]{0.0, 4.0, 3.0, 6.0, 5.0},
                distances
        );
    }

    @Test
    void unreachableVertexHasInfiniteDistance() {
        Graph graph = new Graph(3);
        graph.addDirectedEdge(0, 1, 2);

        double[] distances = Dijkstra.shortestPaths(graph, 0);

        assertEquals(0.0, distances[0]);
        assertEquals(2.0, distances[1]);
        assertEquals(
                Double.POSITIVE_INFINITY,
                distances[2]
        );
    }

    @Test
    void rejectsNegativeEdgeWeight() {
        Graph graph = new Graph(2);
        graph.addDirectedEdge(0, 1, -1);

        assertThrows(
                IllegalArgumentException.class,
                () -> Dijkstra.shortestPaths(graph, 0)
        );
    }

    @Test
    void rejectsInvalidSource() {
        Graph graph = new Graph(3);

        assertThrows(
                IllegalArgumentException.class,
                () -> Dijkstra.shortestPaths(graph, 3)
        );
    }

    @Test
    void worksWithUndirectedGraph() {
        Graph graph = new Graph(3);

        graph.addUndirectedEdge(0, 1, 4);
        graph.addUndirectedEdge(1, 2, 2);

        double[] distances = Dijkstra.shortestPaths(graph, 0);

        assertArrayEquals(
                new double[]{0.0, 4.0, 6.0},
                distances
        );
    }
}
