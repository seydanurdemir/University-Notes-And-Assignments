import java.util.Iterator;

public interface GeneralizedGraph {

    int getNumV();

    boolean isDirected();
    
    int getNumberOfProperties();
    
    void insert(GeneralizedEdge edge);

    boolean isEdge(int source, int dest);

    GeneralizedEdge getEdge(int source, int dest);

    Iterator< GeneralizedEdge> edgeIterator(int source);
}
