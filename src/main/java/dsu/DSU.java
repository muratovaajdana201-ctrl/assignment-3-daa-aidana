package dsu;
import metrics.AlgorithmMetrics;
public class DSU {

    private final int[] parent;
    private final int[] rank;
    private final AlgorithmMetrics metrics;

    public DSU(int size) {
        this(size, null);
    }

    public DSU(int size, AlgorithmMetrics metrics) {
        if (size < 0) {
            throw new IllegalArgumentException(
                    "Size cannot be negative"
            );
        }

        this.metrics = metrics;
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }



    public int find(int x) {
        validateElement(x);

        if (metrics != null) {
            metrics.recordDsuFind();
        }

        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }



    public boolean union(int x, int y) {
        validateElement(x);
        validateElement(y);

        if (metrics != null) {
            metrics.recordDsuUnion();
        }

        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        return true;
    }


    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    private void validateElement(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException(
                    "Invalid element: " + x
            );
        }
    }
}