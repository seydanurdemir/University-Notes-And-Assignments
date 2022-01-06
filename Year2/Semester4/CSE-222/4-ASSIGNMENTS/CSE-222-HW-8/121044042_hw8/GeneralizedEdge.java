public class GeneralizedEdge {
    private int source;
    private int dest;
    private double[] weight;
    public GeneralizedEdge(int source, int dest) {
        this.source = source;
        this.dest = dest;
        weight[0] = 1.0;
    }
    public GeneralizedEdge(int source, int dest, double[] w) {
        this.source = source;
        this.dest = dest;
        weight = new double[w.length];
        for (int i = 0; i < w.length ; i++)
            weight[i] = w[i];
    }
    public int getSource() {
        return source;
    }
    public int getDest() {
        return dest;
    }
    public double[] getWeight() {
        return weight;
    }
    public double getNthWeight(int nth) {
        return weight[nth];
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[( ");
        sb.append(Integer.toString(source));
        sb.append(" , ");
        sb.append(Integer.toString(dest));
        sb.append(" ) : ");
        for (int i=0; i < weight.length ; i++) {
            sb.append(Double.toString(weight[i]));
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GeneralizedEdge) {
            GeneralizedEdge edge = (GeneralizedEdge) obj;
            return (source == edge.source && dest == edge.dest);
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        return (source << 16) ^ dest;
    }
}
