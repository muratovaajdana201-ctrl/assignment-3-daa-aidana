
package connectivity;

import graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SCCTest {

    @Test
    void findsExpectedStronglyConnectedComponents() {
        Graph graph = new Graph(5);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(1, 2, 1);
        graph.addDirectedEdge(2, 0, 1);
        graph.addDirectedEdge(1, 3, 1);
        graph.addDirectedEdge(3, 4, 1);
        graph.addDirectedEdge(4, 3, 1);

        List<List<Integer>> components = SCC.findSCCs(graph);

        Set<Set<Integer>> actual = new HashSet<>();

        for (List<Integer> component : components) {
            actual.add(new HashSet<>(component));
        }

        Set<Set<Integer>> expected = Set.of(
                Set.of(0, 1, 2),
                Set.of(3, 4)
        );

        assertEquals(expected, actual);
    }

    @Test
    void everyVertexBelongsToExactlyOneComponent() {
        Graph graph = new Graph(4);

        graph.addDirectedEdge(0, 1, 1);
        graph.addDirectedEdge(2, 3, 1);

        List<List<Integer>> components = SCC.findSCCs(graph);

        Set<Integer> allVertices = new HashSet<>();

        for (List<Integer> component : components) {
            for (int vertex : component) {
                assertTrue(allVertices.add(vertex));
            }
        }

        assertEquals(Set.of(0, 1, 2, 3), allVertices);
    }

    @Test
    void isolatedVerticesAreSeparateComponents() {
        Graph graph = new Graph(3);

        List<List<Integer>> components = SCC.findSCCs(graph);

        assertEquals(3, components.size());
    }
}
