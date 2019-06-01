public class FloydNew {
    //Copiado de: https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
    final static int INF = 99999;
    private Integer matrixSize;
    Integer [][] sequence;

    public Integer[][] getSequence() {
        return sequence;
    }

    public FloydNew(Integer size){
        this.matrixSize = size;
    }

    Integer[][] floydWarshall(Integer[][] graph) {
        this.sequence = new Integer[matrixSize][matrixSize];
        Integer[][] dist = new Integer[matrixSize][matrixSize];
        int i, j, k;

        for (i = 0; i < matrixSize; i++)
            for (j = 0; j < matrixSize; j++)
                sequence[i][j] = j;
        /* Initialize the solution matrix same as input graph matrix.
           Or we can say the initial values of shortest distances
           are based on shortest paths considering no intermediate
           vertex. */
        for (i = 0; i < matrixSize; i++)
            for (j = 0; j < matrixSize; j++)
                dist[i][j] = graph[i][j];

        /* Add all vertices one by one to the set of intermediate
           vertices.
          ---> Before start of an iteration, we have shortest
               distances between all pairs of vertices such that
               the shortest distances consider only the vertices in
               set {0, 1, 2, .. k-1} as intermediate vertices.
          ----> After the end of an iteration, vertex no. k is added
                to the set of intermediate vertices and the set
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < matrixSize; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < matrixSize; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < matrixSize; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        sequence[i][j] = k;
                    }
                }
            }
        }
        // Print the shortest distance matrix
        printSolution(sequence);
        return dist;
    }

    void printSolution(Integer dist[][])
    {
        System.out.println("The following matrix shows the shortest "+
                "distances between every pair of vertices");
        for (int i = 0; i< matrixSize; ++i)
        {
            for (int j = 0; j< matrixSize; ++j)
            {
                if (dist[i][j]==INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j]+"   ");
            }
            System.out.println();
        }
    }
}
