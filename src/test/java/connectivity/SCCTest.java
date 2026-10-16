
package connectivity;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SCCTest {

    @Test
    void findsAllStronglyConnectedComponents() {
        Graph graph = new Graph(5);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 0, 1);

        graph.addDirectedEdge(1, 2, 1);

        graph.addDirectedEdge(2, 3, 1);
        graph.addDirectedEdge(3, 2, 1);

        graph.addDirectedEdge(3, 4, 1);

        List<List<Integer>> components = SCC.findSCCs(graph);

        assertEquals(3, components.size());

        Set<Set<Integer>> actual = new HashSet<>();

        for (List<Integer> component : components) {
            actual.add(new HashSet<>(component));
        }

        assertTrue(actual.contains(Set.of(0, 1)));
        assertTrue(actual.contains(Set.of(2, 3)));
        assertTrue(actual.contains(Set.of(4)));

        Set<Integer> allVertices = new HashSet<>();

        for (List<Integer> component : components) {
            for (int vertex : component) {
                assertTrue(allVertices.add(vertex),
                        "Vertex appears in multiple components");
            }
        }

        assertEquals(Set.of(0, 1, 2, 3, 4), allVertices);
    }

    @Test
    void everyVertexIsItsOwnComponentWithoutEdges() {
        Graph graph = new Graph(3);

        List<List<Integer>> components = SCC.findSCCs(graph);

        assertEquals(3, components.size());
    }

    @Test
    void handlesEmptyGraph() {
        Graph graph = new Graph(0);

        assertTrue(SCC.findSCCs(graph).isEmpty());
    }
}
