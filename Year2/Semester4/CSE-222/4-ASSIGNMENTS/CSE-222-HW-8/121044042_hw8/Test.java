public class Test {

    void testPart1() {
        // 0,1 0,3 0,4
        int s1 = 0; int d1 = 1; double[] w1 = {10.0, 100.0, 5.0};
        GeneralizedEdge edge1 = new GeneralizedEdge(s1,d1,w1);
        int s2 = 0; int d2 = 3; double[] w2 = {30.0, 80.0, 5.0};
        GeneralizedEdge edge2 = new GeneralizedEdge(s2,d2,w2);
        int s3 = 0; int d3 = 4; double[] w3 = {100.0, 10.0, 5.0};
        GeneralizedEdge edge3 = new GeneralizedEdge(s3,d3,w3);
        // 1,2
        int s4 = 1; int d4 = 2; double[] w4 = {50.0, 60.0, 5.0};
        GeneralizedEdge edge4 = new GeneralizedEdge(s4,d4,w4);
        // 2,4
        int s5 = 2; int d5 = 4; double[] w5 = {10.0, 100.0, 5.0};
        GeneralizedEdge edge5 = new GeneralizedEdge(s5,d5,w5);
        // 3,2 3,4
        int s6 = 3; int d6 = 2; double[] w6 = {20.0, 90.0, 5.0};
        GeneralizedEdge edge6 = new GeneralizedEdge(s6,d6,w6);
        int s7 = 3; int d7 = 4; double[] w7 = {60.0, 50.0, 5.0};
        GeneralizedEdge edge7 = new GeneralizedEdge(s7,d7,w7);
        
        int numV = 7;
        int numberOfProperties = 3;
        
        GeneralizedListGraph lgraph = new GeneralizedListGraph(numV, false, numberOfProperties);
        lgraph.insert(edge1);
        lgraph.insert(edge2);
        lgraph.insert(edge3);
        lgraph.insert(edge4);
        lgraph.insert(edge5);
        lgraph.insert(edge6);
        lgraph.insert(edge7);
        
        GeneralizedMatrixGraph mgraph = new GeneralizedMatrixGraph(numV, false, numberOfProperties);
        mgraph.insert(edge1);
        mgraph.insert(edge2);
        mgraph.insert(edge3);
        mgraph.insert(edge4);
        mgraph.insert(edge5);
        mgraph.insert(edge6);
        mgraph.insert(edge7);
        
        int start = 0; // Starts vertex 0
        int[] pred = new int[numV];
        double[] dist = new double[numV];
        int priority = 0; // Prioritizes weight[0]
        int combine = 2; // Calculates distance as adding
        
        /*GeneralizedDijkstrasAlgorithm lsp = new GeneralizedDijkstrasAlgorithm();
        lsp.generalizedDijkstrasAlgorithm(mgraph, start, pred, dist, priority, combine);
        for (int i=0; i != numV; i++)
            System.out.println("Source : " + start + " Destination : " + pred[i] + " Distance : " + dist[i]);*/
        
        GeneralizedDijkstrasAlgorithm msp = new GeneralizedDijkstrasAlgorithm();
        msp.generalizedDijkstrasAlgorithm(mgraph, start, pred, dist, priority, combine);
        for (int i=0; i != numV; i++)
            System.out.println("Source : " + start + " Destination : " + pred[i] + " Distance : " + dist[i]);
    }

    void testPart2() {

    }

    void testPart3() {

    }
}
