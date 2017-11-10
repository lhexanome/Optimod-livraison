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
    private float dijkstraTime = Float.MAX_VALUE;

    /**
     * Dijkstra state: black == every neighbours are done.
     */
    private boolean black = false;

    /**
     * intersection Wrapper which is before in the order for the shortest path.
     */
    private IntersectionWrapper predecessor;
    /**
     * Vector used to go from predecessor to this intersection.
     */
    private Vector incomingVector;


    /**
     * Reference to the start point of the Dijkstra algorithm.
     * It's used to know on which row of the path findings we are,
     * so there is no need to reset all intersections after each.
     */
    private long idIteration;

    /**
     * Constructor.
     *
     * @param i     Intersection wrapped by the object.
     * @param start Starting Halt for Dijkstra
     */
    public IntersectionWrapper(Intersection i, long idIteration) {
        intersection = i;
        this.idIteration = idIteration;
        i.setWrapper(this);
    }

    /**
     * Function used to set this object as the starting point
     * for the path finding.
     */
    public void setAsStart() {
        dijkstraTime = 0;
        black = true;
    }

    /**
     * Getter for intersection.
     *
     * @return the intersection.
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Setter for intersection.
     *
     * @param intersection The intersection to set.
     */
    public void setIntersection(Intersection intersection) {
        this.intersection.setWrapper(null);
        this.intersection = intersection;
        this.intersection.setWrapper(this);
    }

    /**
     * Getter for dijkstraTime.
     *
     * @return dijkstraTime.
     */
    public float getDijkstraTime() {
        return dijkstraTime;
    }

    /**
     * Setter for dijkstraTime.
     *
     * @param dijkstraTime Given dijkstraTime.
     */
    public void setDijkstraTime(float dijkstraTime) {
        this.dijkstraTime = dijkstraTime;
    }

    /**
     * getter for Dijkstra state.
     *
     * @return If is black.
     */
    public boolean isBlack() {
        return black;
    }

    /**
     * Setter for Dijkstra state.
     *
     * @param black State.
     */
    public void setBlack(boolean black) {
        this.black = black;
    }

    /**
     * Getter for predecessor.
     *
     * @return Predecessor.
     */
    public IntersectionWrapper getPredecessor() {
        return predecessor;
    }

    /**
     * Setter for predecessor.
     *
     * @param predecessor Given predecessor.
     */
    public void setPredecessor(IntersectionWrapper predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * Getter for the vector.
     *
     * @return the vector.
     */
    public Vector getIncomingVector() {
        return incomingVector;
    }

    /**
     * Setter for the vector coming here.
     *
     * @param incomingVector Vector given.
     */
    public void setIncomingVector(Vector incomingVector) {
        this.incomingVector = incomingVector;
    }

    /**
     * Getter for start.
     *
     * @return Start.
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
