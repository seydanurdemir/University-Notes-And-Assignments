import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GeneralizedListGraph extends GeneralizedAbstractGraph {

    private List<GeneralizedEdge>[] edges;

    public GeneralizedListGraph(int numV, boolean directed, int numberOfProperties) {
        super(numV, directed, numberOfProperties);
        edges = new List[numV];
        for (int i = 0; i < numV; i++) {
            edges[i] = new LinkedList<GeneralizedEdge>();
        }
    }

    @Override
    public boolean isEdge(int source, int dest) {
        return edges[source].contains(new GeneralizedEdge(source, dest));
    }

    @Override
    public void insert(GeneralizedEdge edge) {
        edges[edge.getSource()].add(edge);
        if (!isDirected()) {
            edges[edge.getDest()].add(new GeneralizedEdge(edge.getDest(), edge.getSource(), edge.getWeight()));
        }
    }

    @Override
    public Iterator<GeneralizedEdge> edgeIterator(int source) {
        return edges[source].iterator();
    }

    @Override
    public GeneralizedEdge getEdge(int source, int dest) {
        GeneralizedEdge target = new GeneralizedEdge(source, dest);
        for (GeneralizedEdge edge : edges[source]) {
            if (edge.equals(target)) {
                return edge;
            }
        }
        return target;
    }
}
