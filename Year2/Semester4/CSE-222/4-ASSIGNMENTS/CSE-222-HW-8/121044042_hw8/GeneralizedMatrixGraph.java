import java.util.Iterator;

public class GeneralizedMatrixGraph extends GeneralizedAbstractGraph {

    private double[][][] edges;

    public GeneralizedMatrixGraph(int numV, boolean directed, int numberOfProperties) {
        super(numV, directed, numberOfProperties);
        edges = new double[numV][][];
        if (!directed) {
            for (int i = 0; i != numV; ++i) {
                edges[i] = new double[numV][];
                for (int j = 0; j != numV; ++j) {
                    edges[i][j] = new double[numberOfProperties];
                    for (int k = 0; k != numberOfProperties; ++k) {
                        edges[i][j][k] = Double.POSITIVE_INFINITY;
                    }
                }
            }
        } else {
            for (int i = 0; i != numV; ++i) {
                edges[i] = new double[i + 1][];
                for (int j = 0; j != i + 1; ++j) {
                    edges[i][j] = new double[numberOfProperties];
                    for (int k = 0; k != numberOfProperties; ++k) {
                        edges[i][j][k] = Double.POSITIVE_INFINITY;
                    }
                }
            }
        }
    }

    @Override
    public void insert(GeneralizedEdge edge) {
        setEdgeValue(edge.getSource(), edge.getDest(), edge.getWeight());
    }

    @Override
    public GeneralizedEdge getEdge(int source, int dest) {
        return new GeneralizedEdge(source, dest, getEdgeValue(source, dest));
    }

    private double[] getEdgeValue(int source, int dest) {
        if (isDirected() | source <= dest) {
            return edges[source][dest];
        } else {
            return edges[dest][source];
        }
    }

    private void setEdgeValue(int source, int dest, double[] wt) {
        if (isDirected() || source <= dest) {
            edges[source][dest] = wt;
        } else {
            edges[dest][source] = wt;
        }
    }

    @Override
    public boolean isEdge(int source, int dest) {
        return null != getEdgeValue(source, dest);
    }

    @Override
    public Iterator< GeneralizedEdge> edgeIterator(int source) {
        return new Iter(source);
    }

    private class Iter implements Iterator< GeneralizedEdge> {

        private int source;
        private int index;

        public Iter(int source) {
            this.source = source;
            index = -1;
            advanceIndex();
        }

        @Override
        public boolean hasNext() {
            return index != getNumV();
        }

        @Override
        public GeneralizedEdge next() {
            if (index == getNumV()) {
                throw new java.util.NoSuchElementException();
            }
            GeneralizedEdge returnValue = new GeneralizedEdge(source, index, getEdgeValue(source, index));
            advanceIndex();
            return returnValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void advanceIndex() {
            do {
                index++;
            } while (index != getNumV() && null != getEdgeValue(source, index));
        }
    }
}
