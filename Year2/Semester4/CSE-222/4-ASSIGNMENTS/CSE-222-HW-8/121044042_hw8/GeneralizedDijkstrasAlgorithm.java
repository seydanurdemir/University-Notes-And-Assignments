import java.util.HashSet;
import java.util.Iterator;

/**
 * This GeneralizedDijkstrasAlgorithm class contains a method that is a generalized form of Dijkstra's Shortest Path Algorithm
 * It takes priority and combine parameters as difference with normal form
 * Also it traverse to graph using iterator to run efficiently for list and matrix representations of graph
 * @author Seyda Nur Demir, 12 10 44 042
 */
public class GeneralizedDijkstrasAlgorithm {
    /**
     * This generalizedDijkstrasAlgorithm method is a generalized form of Dijkstra's Shortest Path Algorithm
     * Takes priority to prioritize weight as given index;
     * Weight is a double array, and keeps several double values, we can prioritizes algorithm according to nth weight property
     * For example weight can carries distance, time, and quality like this way : weight (120 km, 24 hours, 5 quality point)
     * It keeps this values in a double array like this way : weight[120.0, 24.0, 5.0]
     * We can prioritize to calculate shortest path according to distance, as giving 0 to priority value
     * We can prioritize to calculate shortest path according to time, as giving 1 to priority value
     * We can prioritize to calculate shortest path according to quality, as giving 2 to priority value
     * We can prioritize to calculate shortest path according to several properties, as giving only index value for priority value
     * Takes combine to calculate weight with given operator;
     * 0 is multiplication, 1 is star operation, and otherwise calculates addition as general form
     * If we give 0 to combine, it calculates simply multiplication of distances, (a,b,0) : (a * b)
     * Else if we give 1 to combine, it calculates simply star operation of distances, (a,b,1) : (a + b - (a * b))
     * Otherwise it calculates simply addition of distances like normal form of algorithm, (a,b,0) : (a + b)
     * @param graph GeneralizedGraph
     * @param start int Start Vertex
     * @param pred int Predecessor Array
     * @param dist double Distance Array
     * @param priority int Priority of Weight
     * @param combine int Combine Operator
     */
    public void generalizedDijkstrasAlgorithm(GeneralizedGraph graph, int start, int[] pred, double[] dist, int priority, int combine) { // Property 2 : priority  // Property 3 : combine
        int numV = graph.getNumV();
        HashSet< Integer> vMinusS = new HashSet< Integer>(numV);
        for (int i = 0; i < numV; i++) {
            if (i != start) {
                vMinusS.add(i);
            }
        }
        for (int v : vMinusS) {
            pred[v] = start;
            dist[v] = graph.getEdge(start, v).getNthWeight(priority); // Property 2
        }
        while (vMinusS.size() != 0) {
            double minDist = Double.POSITIVE_INFINITY;
            int u = -1;
            for (int v : vMinusS) {
                if (dist[v] < minDist) {
                    minDist = dist[v];
                    u = v;
                }
            }
            vMinusS.remove(u);
            Iterator<GeneralizedEdge> edgeIter = graph.edgeIterator(u); // Property 1
            while (edgeIter.hasNext()) { // Property 1
                GeneralizedEdge edge = edgeIter.next(); // Property 1
                int v = edge.getDest(); // Property 1
                if (vMinusS.contains(v)) { // Property 1
                    double weight = graph.getEdge(u, v).getNthWeight(priority); // Property 2
                    if (combine == 0) { // Property 3
                        if ((dist[u] * weight) < dist[v]) { // Property 3
                            dist[v] = (dist[u] * weight); // Property 3
                            pred[v] = u;
                        }
                    } else if (combine == 1) { // Property 3
                        if ((dist[u] + weight - (dist[u] * weight)) < dist[v]) { // Property 3
                            dist[v] = (dist[u] + weight - (dist[u] * weight)); // Property 3
                            pred[v] = u;
                        }
                    } else { // Property 3
                        if ((dist[u] + weight) < dist[v]) { // Property 3
                            dist[v] = (dist[u] + weight); // Property 3
                            pred[v] = u;
                        }
                    }
                }
            }
        }
    }
}
