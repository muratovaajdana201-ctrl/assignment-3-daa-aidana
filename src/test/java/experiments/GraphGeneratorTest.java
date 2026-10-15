
package experiments;

import graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphGeneratorTest {

    @Test
    void generatesRequestedNumberOfVerticesAndEdges() {
        Graph graph =
                GraphGenerator.generateConnectedUndirectedGraph(
                        10, 30, 42L
                );

        assertEquals(10, graph.getVertices());
        assertEquals(60, graph.getAllEdges().size());
    }

    @Test
    void generatesReproducibleGraphForSameSeed() {
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

        for (int u = 0; u < first.getVertices(); u++) {
            assertEquals(
                    first.getNeighbors(u).toString(),
                    second.getNeighbors(u).toString()
            );
        }
    }

    @Test
    void rejectsImpossibleEdgeCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> GraphGenerator.generateConnectedUndirectedGraph(
                        5, 3, 42L
                )
        );
    }
}
