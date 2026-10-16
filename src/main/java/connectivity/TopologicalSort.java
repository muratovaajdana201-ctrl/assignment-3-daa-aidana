package connectivity;
import graph.Edge; import graph.Graph;
import java.util.ArrayDeque; import java.util.ArrayList; import java.util.List; import java.util.Queue;
public class TopologicalSort {
    private TopologicalSort() {
    }

    public static List<Integer> sort(Graph graph) {
        int vertices = graph.getVertices();
        int[] indegree = new int[vertices];

        for (int u = 0; u < vertices; u++) {
            for (Edge edge : graph.getNeighbors(u)) {
                indegree[edge.getDestination()]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();

        for (int v = 0; v < vertices; v++) {
            if (indegree[v] == 0) {
                queue.add(v);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!queue.isEmpty()) {
            int u = queue.remove();
            order.add(u);

            for (Edge edge : graph.getNeighbors(u)) {
                int v = edge.getDestination();
                indegree[v]--;

                if (indegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        if (order.size() != vertices) {
            throw new IllegalArgumentException(
                    "Topological ordering is impossible: graph contains a cycle"
            );
        }

        return order;
    }
}