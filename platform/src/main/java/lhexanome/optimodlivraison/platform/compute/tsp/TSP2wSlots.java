package lhexanome.optimodlivraison.platform.compute.tsp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * heuristic 1.
 */
public class TSP2wSlots extends TemplateTSPwSlots {

    /**
     * Basic iterator.
     *
     * @param currentNode current node in the iteration
     * @param notViewed        : list of nodes having still to be called
     * @param cost          : cost[i][j] = duration from i to j, with 0 <= i < nbNodes et 0 <= j < nbNodes
     * @param duration         : duration[i] = duration for node i, with 0 <= i < nbNodes
     * @return iterator go through all not viewed nodes
     */
    @Override
    protected Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> notViewed,
                                         int[][] cost, int[] duration) {
        return new IteratorSeq(notViewed, currentNode);
    }

    /**
     * compute the lowest cost for the addition of a node.
     *
     * @param currentNode current node in the iteration
     * @param notViewed        : list of nodes having still to be called
     * @param cost          : cost[i][j] = duration from i to j, with 0 <= i < nbNodes et 0 <= j < nbNodes
     * @param duration         : duration[i] = duration for node i, with 0 <= i < nbNodes
     * @return infimum for permutations cost starting with currentNode,
     * all nodes in notViewed only once and ending with node 0
     */
    @Override
    public int bound(Integer currentNode, ArrayList<Integer> notViewed, int[][] cost, int[] duration) {
        int coutMin = Integer.MAX_VALUE;
        int coutCurrent;
        for (int i = 0; i < notViewed.size(); i++) {
            coutCurrent = cost[currentNode][i] + duration[i];
            if (coutMin > coutCurrent) {
                coutMin = coutCurrent;
            }
        }
        return coutMin;
    }
}
