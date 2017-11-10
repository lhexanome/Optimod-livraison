package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Vector;

/**
 * Class which stores additional information needed for Dijkstra.
 */
public class IntersectionWrapper {

    /**
     * the intersection wrapped.
     */
    private Intersection intersection;

    /**
     * time stored, value used as path weight in Dijkstra.
     */
    private float tempsDijkstra = Float.MAX_VALUE;

    /**
     * Dijkstra state: black == every neighbours are done.
     */
    private boolean black = false;

    /**
     * intersection Wrapper which is before in the order for the shortest path.
     */
    private IntersectionWrapper predecessor;
    /**
     * vector used to go from predecessor to this intersection.
     */
    private Vector incomingVector;


    /**
     * reference to the iteration id of the Dijkstra algorithm.
     * it's used to know on which row of the path findings we are,
     * so there is no need to reset all intersections after each.
     */
    private long idIteration;

    /**
     * constructor.
     *
     * @param i     intersection wrapped by the object.
     * @param idIteration iteration id for Dijkstra
     */
    public IntersectionWrapper(Intersection i, long idIteration) {
        intersection = i;
        this.idIteration = idIteration;
        i.setWrapper(this);
    }

    /**
     * function used to set this object as the starting point
     * for the path finding.
     */
    public void setAsStart() {
        tempsDijkstra = 0;
        black = true;
    }

    /**
     * getter for intersection.
     *
     * @return the intersection.
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * setter for intersection.
     *
     * @param intersection the intersection to set
     */
    public void setIntersection(Intersection intersection) {
        this.intersection.setWrapper(null);
        this.intersection = intersection;
        this.intersection.setWrapper(this);
    }

    /**
     * getter for tempsDijkstra.
     *
     * @return tempsDijkstra
     */
    public float getTempsDijkstra() {
        return tempsDijkstra;
    }

    /**
     * setter for tempsDijkstra.
     *
     * @param tempsDijkstra tempsDijkstra given
     */
    public void setTempsDijkstra(float tempsDijkstra) {
        this.tempsDijkstra = tempsDijkstra;
    }

    /**
     * getter for Dijkstra state.
     *
     * @return if is black
     */
    public boolean isBlack() {
        return black;
    }

    /**
     * setter for Dijkstra state.
     *
     * @param black state
     */
    public void setBlack(boolean black) {
        this.black = black;
    }

    /**
     * getter for predecessor.
     *
     * @return predecessor
     */
    public IntersectionWrapper getPredecessor() {
        return predecessor;
    }

    /**
     * setter for predecessor.
     *
     * @param predecessor predecessor given
     */
    public void setPredecessor(IntersectionWrapper predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * getter for the vector.
     *
     * @return the vector
     */
    public Vector getIncomingVector() {
        return incomingVector;
    }


    /**
     * setter for the vector coming here.
     *
     * @param incomingVector vector given
     */
    public void setIncomingVector(Vector incomingVector) {
        this.incomingVector = incomingVector;
    }

    /**
     * getter for idIteration.
     *
     * @return idIteration.
     */
    public long getIdIteration() {
        return idIteration;
    }

    /**
     * setter for idIteration.
     *
     * @param idIteration idIteration to set.
     */
    public void setIdIteration(long idIteration) {
        this.idIteration = idIteration;
    }
}
