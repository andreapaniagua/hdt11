public class FloydAlgorithm {
    private int[][] distanceMatrix;
    private Integer[][] sequenceMatrix;
    private int numberOfVertices;
    public static final int INFINITY = 999;

    public FloydAlgorithm(int numberOfVertices)
    {
        distanceMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
        this.numberOfVertices = numberOfVertices;
    }

    public void floydwarshall(Integer[][] adjacencymatrix) {
        for (int source = 1; source <= numberOfVertices; source++) {
            for (int destination = 1; destination <= numberOfVertices; destination++) {
                distanceMatrix[source][destination] = adjacencymatrix[source][destination];
            }
        }

        for (int intermediate = 1; intermediate <= numberOfVertices; intermediate++) {
            for (int source = 1; source <= numberOfVertices; source++) {
                for (int destination = 1; destination <= numberOfVertices; destination++) {
                    if (distanceMatrix[source][intermediate] + distanceMatrix[intermediate][destination]
                            < distanceMatrix[source][destination])
                        distanceMatrix[source][destination] = distanceMatrix[source][intermediate]
                                + distanceMatrix[intermediate][destination];
                }
            }
        }

        for (int source = 1; source <= numberOfVertices; source++)
            System.out.print("\t" + source);

        System.out.println();
        for (int source = 1; source <= numberOfVertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberOfVertices; destination++) {
                System.out.print(distanceMatrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

}