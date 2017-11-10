package lhexanome.optimodlivraison.platform.compute.tsp;

import lhexanome.optimodlivraison.platform.compute.AdjacencyMatrix;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.Date;

/**
 * abstract interface for the TSP.
 */
public interface TSPwSlots {


    /**
     * init the tsp data structures.
     *
     * @param nbNodes number of nodes
     */
    void init(int nbNodes);

    /**
     * Search for a minimal duration circuit going through all nodes.
     *
     * @param tour     global tour that needs to be computed by the TSP.
     * @param matrix   matrix storing data giving links between indexes and nodes.
     * @param cost     : cost[i][j] = duration from i to j, with 0 <= i < nbNodes et 0 <= j < nbNodes
     * @param slots    slots to respect at each node
     * @param duration : duration[i] = duration pour visiter le sommet i, avec 0 <= i < nbSommets
     * @param start    : date de start du dernier sommet
     * @param tpsLimit : timeout for resolution
     * @param nbNodes  number of nodes concerned
     */
    @SuppressWarnings("checkstyle:parameternumber")
    void searchSolution(Tour tour, AdjacencyMatrix matrix, int tpsLimit, int nbNodes, int[][] cost,
                        TimeSlot[] slots, Date start, int[] duration);

    /**
     * get the result of the solution.
     * @param i index
     * @return node number i in the solution found.
     */
    Integer getMeilleureSolution(int i);

    /**
     * Gives the duration of the best solution.
     *
     * @return duration of the solution found.
     */
    int getCostBestSolution();

    /**
     * Get date estimated for the node i.
     *
     * @param i index
     * @return the date
     */
    Date getEstimatedDate(int i);
}
