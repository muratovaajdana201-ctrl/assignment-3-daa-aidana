
package shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestPathResult {
    private final int source;
    private final double[] distances;
    private final int[] previous;

    public ShortestPathResult(int source, double[] distances, int[] previous) {
        this.source = source;
        this.distances = distances.clone();
        this.previous = previous.clone();
    }

    public int getSource() {
        return source;
    }

    public double getDistanceTo(int vertex) {
        validateVertex(vertex);
        return distances[vertex];
    }

    public boolean isReachable(int vertex) {
        validateVertex(vertex);
        return !Double.isInfinite(distances[vertex]);
    }

    public List<Integer> getPathTo(int destination) {
        validateVertex(destination);

        if (!isReachable(destination)) {
            return Collections.emptyList();
        }

        List<Integer> path = new ArrayList<>();
        int current = destination;

        while (current != -1) {
            path.add(current);

            if (current == source) {
                break;
            }

            current = previous[current];
        }

        if (path.get(path.size() - 1) != source) {
            return Collections.emptyList();
        }

        Collections.reverse(path);
        return path;
    }

    public double[] getDistances() {
        return distances.clone();
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= distances.length) {
            throw new IllegalArgumentException("Invalid vertex: " + vertex);
        }
    }
}
