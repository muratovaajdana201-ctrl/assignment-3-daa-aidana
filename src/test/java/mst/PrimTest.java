
package mst;

import graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimTest {

    @Test
    void findsCorrectMSTWeight() {
        Graph graph = new Graph(4);

        graph.addUndirectedEdge(0, 1, 1);
        graph.addUndirectedEdge(0, 2, 4);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(1, 3, 5);
        graph.addUndirectedEdge(2, 3, 3);

        MSTResult result = Prim.findMST(graph);

        assertEquals(6.0, result.getTotalWeight());
    }

    @Test
    void MSTContainsVMinusOneEdges() {
        Graph graph = new Graph(4);

        graph.addUndirectedEdge(0, 1, 1);
        graph.addUndirectedEdge(0, 2, 4);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(1, 3, 5);
        graph.addUndirectedEdge(2, 3, 3);

        MSTResult result = Prim.findMST(graph);

        assertEquals(3, result.getEdges().size());
    }

    @Test
    void disconnectedGraphThrowsException() {
        Graph graph = new Graph(4);

        graph.addUndirectedEdge(0, 1, 1);
        graph.addUndirectedEdge(2, 3, 2);

        assertThrows(
                IllegalArgumentException.class,
                () -> Prim.findMST(graph)
        );
    }

    @Test
    void agreesWithKruskalOnSameGraph() {
        Graph graph = new Graph(5);

        graph.addUndirectedEdge(0, 1, 4);
        graph.addUndirectedEdge(0, 2, 1);
        graph.addUndirectedEdge(1, 2, 2);
        graph.addUndirectedEdge(1, 3, 1);
        graph.addUndirectedEdge(2, 3, 5);
        graph.addUndirectedEdge(3, 4, 3);
        graph.addUndirectedEdge(2, 4, 8);

        MSTResult primResult = Prim.findMST(graph);
        MSTResult kruskalResult = Kruskal.findMST(graph);

        assertEquals(
                kruskalResult.getTotalWeight(),
                primResult.getTotalWeight()
        );
    }
}
