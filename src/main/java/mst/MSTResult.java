package mst;

import graph.Edge;

import java.util.Collections;
import java.util.List;

public class MSTResult {

    private final List<Edge> edges;
    private final double totalWeight;

    public MSTResult(List<Edge> edges, double totalWeight) {
        this.edges = edges;
        this.totalWeight = totalWeight;
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public double getTotalWeight() {
        return totalWeight;
    }
}