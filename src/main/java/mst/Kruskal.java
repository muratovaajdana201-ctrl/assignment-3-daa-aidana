package mst;

import dsu.DSU;
import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kruskal {

    public static MSTResult findMST(Graph graph) {

        List<Edge> edges = new ArrayList<>(graph.getAllEdges());

        edges.sort(Comparator.comparingDouble(Edge::getWeight));

        DSU dsu = new DSU(graph.getVertices());

        List<Edge> mstEdges = new ArrayList<>();

        double totalWeight = 0.0;

        for (Edge edge : edges) {

            int source = edge.getSource();
            int destination = edge.getDestination();

            if (dsu.union(source, destination)) {

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