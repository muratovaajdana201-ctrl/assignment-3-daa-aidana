
package mst;

import dsu.DSU;
import graph.Edge;
import graph.Graph;
import metrics.AlgorithmMetrics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static MSTResult findMST(Graph graph) {
        return findMST(graph, null);
    }

    public static MSTResult findMST(
            Graph graph,
            AlgorithmMetrics metrics
    ) {
        List<Edge> edges = new ArrayList<>(graph.getAllEdges());

        if (metrics == null) {
            edges.sort(Comparator.comparingDouble(Edge::getWeight));
        } else {
            edges.sort((a, b) -> {
                metrics.recordComparison();
                return Double.compare(a.getWeight(), b.getWeight());
            });
        }

        DSU dsu = new DSU(graph.getVertices(), metrics);
        List<Edge> mstEdges = new ArrayList<>();
        double totalWeight = 0.0;

        for (Edge edge : edges) {
            if (metrics != null) {
                metrics.recordEdgeExamination();
            }

            if (dsu.union(edge.getSource(), edge.getDestination())) {
                mstEdges.add(edge);
                totalWeight += edge.getWeight();

                if (mstEdges.size() == graph.getVertices() - 1) {
                    break;
                }
            }
        }

        if (mstEdges.size() != graph.getVertices() - 1) {
            throw new IllegalArgumentException(
                    "Graph must be connected to have an MST"
            );
        }

        return new MSTResult(mstEdges, totalWeight);
    }
}
