package lhexanome.optimodlivraison.platform.compute.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Iterator used in TSP.
 */
public class IteratorSeq implements Iterator<Integer> {

    /**
     * candidates to be iterated upon.
     */
    private Integer[] candidates;
    /**
     * number of candidates.
     */
    private int nbCandidates;

    /**
     * Create an iterator to go through all notViewed nodes.
     *
     * @param notViewed nodes that haven't been visited yet.
     * @param currentNode current node.
     */
    public IteratorSeq(Collection<Integer> notViewed, int currentNode) {
        this.candidates = new Integer[notViewed.size()];
        nbCandidates = 0;
        for (Integer s : notViewed) {
            candidates[nbCandidates++] = s;
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidates > 0;
    }

    @Override
    public Integer next() {
        return candidates[--nbCandidates];
    }

    @Override
    public void remove() {
    }

}
