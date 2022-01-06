public abstract class GeneralizedAbstractGraph implements GeneralizedGraph {

    private int numV;
    private boolean directed;
    private int numberOfProperties;
    
    public GeneralizedAbstractGraph(int numV, boolean directed, int numberOfProperties) {
        this.numV = numV;
        this.directed = directed;
        this.numberOfProperties = numberOfProperties;
    }

    @Override
    public int getNumV() {
        return numV;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
    
    @Override
    public int getNumberOfProperties() {
        return numberOfProperties;
    }
}
