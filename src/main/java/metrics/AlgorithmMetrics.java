
package metrics;

public class AlgorithmMetrics {

    private long edgeExaminations;
    private long comparisons;
    private long dsuFindCalls;
    private long dsuUnionCalls;

    public void recordEdgeExamination() {
        edgeExaminations++;
    }

    public void recordComparison() {
        comparisons++;
    }

    public void recordDsuFind() {
        dsuFindCalls++;
    }

    public void recordDsuUnion() {
        dsuUnionCalls++;
    }

    public long getEdgeExaminations() {
        return edgeExaminations;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getDsuFindCalls() {
        return dsuFindCalls;
    }

    public long getDsuUnionCalls() {
        return dsuUnionCalls;
    }
}
