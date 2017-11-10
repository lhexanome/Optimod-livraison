package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;

import java.util.ArrayList;
import java.util.Map;

/**
 * Container classe gathering a weight matrix, a path matrix, and the list
 * associating each vertex to its index.
 */
public class AdjacencyMatrix {

    /**
     * List associating each vertex to its index.
     */
    private ArrayList<Halt> vertexList;

    /**
     * Matrix gathering the best path between each vertex.
     * pathMatrix[x][y] returns the path going from vertex with x index to vertex with y index.
     */
    private Path[][] pathMatrix;

    /**
     * Matrix gathering each path's weight.
     * This information is already contained into pathMatrix,
     * but a int[][] matrix is required for the TSP algorithm we use.
     */
    private int[][] weightMatrix;

    /**
     * Adjacency Matrix constructor.
     *
     * @param vertexList List associating each vertex to its index.
     * @param pathMatrix Matrix gathering the best path between each vertex.
     * @param weightMatrix Matrix gathering each path's weight.
     */
    public AdjacencyMatrix(ArrayList<Halt> vertexList, Path[][] pathMatrix, int[][] weightMatrix) {
        this.vertexList = vertexList;
        this.pathMatrix = pathMatrix;
        this.weightMatrix = weightMatrix;
    }

    /**
     * @return List associating each vertex to its index.
     */
    public ArrayList<Halt> getVertexList() {
        return vertexList;
    }

    /**
     * @return Matrix gathering the best path between each vertex.
     */
    public Path[][] getPathMatrix() {
        return pathMatrix;
    }

    /**
     * @return Matrix gathering each path's weight.
     */
    public int[][] getWeightMatrix() {
        return weightMatrix;
    }



    /**
     * Initializes matrix with the graph from SimplifiedMap.
     *
     * @param graph Graph for the TSP.
     */
    public void initMatrix(Map<Halt, ArrayList<Path>> graph) {
        int inter1;
        for (Halt arret : graph.keySet()) {
            inter1 = vertexList.indexOf(arret);
            for (Path trajet : graph.get(arret)) {
                int inter2 = vertexList.indexOf(trajet.getEnd());
                int cout = trajet.getTimeToTravel();
                weightMatrix[inter1][inter2] = cout;
                pathMatrix[inter1][inter2] = trajet;
            }
        }
    }
}
