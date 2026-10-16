package connectivity;
import graph.Edge; import graph.Graph;
import java.util.ArrayList; import java.util.Collections; import java.util.List;
public class SCC {
    private SCC() {
    }

    public static List<List<Integer>> findSCCs(Graph graph) {
        int vertices = graph.getVertices();

        boolean[] visited = new boolean[vertices];
        List<Integer> finishOrder = new ArrayList<>();


        for (int v = 0; v < vertices; v++) {
            if (!visited[v]) {
                fillOrder(graph, v, visited, finishOrder);
            }
        }


        Graph transpose = new Graph(vertices);

        for (int u = 0; u < vertices; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                transpose.addDirectedEdge(
                        edge.getDestination(),
                        u,
                        edge.getWeight()
                );
            }
        }


        List<List<Integer>> components = new ArrayList<>();
        java.util.Arrays.fill(visited, false);

        Collections.reverse(finishOrder);

        for (int v : finishOrder) {
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                collectComponent(
                        transpose, v, visited, component
                );
                components.add(component);
            }
        }

        return components;
    }

    private static void fillOrder(
            Graph graph,
            int vertex,
            boolean[] visited,
            List<Integer> finishOrder
    ) {
        visited[vertex] = true;

        for (Edge edge : graph.getNeighbors(vertex)) {
            int next = edge.getDestination();

            if (!visited[next]) {
                fillOrder(graph, next, visited, finishOrder);
            }
        }

        finishOrder.add(vertex);
    }

    private static void collectComponent(
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
                collectComponent(
                        graph, next, visited, component
                );
            }
        }
    }
}