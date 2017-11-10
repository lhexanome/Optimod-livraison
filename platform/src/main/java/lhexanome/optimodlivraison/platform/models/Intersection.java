package lhexanome.optimodlivraison.platform.models;

import lhexanome.optimodlivraison.platform.compute.IntersectionWrapper;

import java.util.ArrayList;

/**
 * Represent a point on the map.
 */
public class Intersection {

    /**
     * X coordinate.
     */
    private int x;

    /**
     * Y coordinate.
     */
    private int y;

    /**
     * Id of the intersection.
     * From the provided map
     */
    private Long id;

    /**
     * list of intersections where vectors leaving
     * this intersection arrive.
     */
    private ArrayList<Intersection> successors = new ArrayList<>();


    /**
     * Wrapper reference.
     * used to remove the need of O(n) researches in the compute module
     */
    private IntersectionWrapper wrapper = null;

    /**
     * Constructor.
     *
     * @param id Point id
     * @param x  X coordinate
     * @param y  Y coordinate
     * @see Intersection#id
     * @see Intersection#x
     * @see Intersection#y
     */
    public Intersection(Long id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor.
     *
     * @param id Point id
     */
    public Intersection(Long id) {
        this.id = id;
    }

    /**
     * X getter.
     *
     * @return X
     */
    public int getX() {
        return x;
    }

    /**
     * X setter.
     *
     * @param x X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Y getter.
     *
     * @return Y
     */
    public int getY() {
        return y;
    }

    /**
     * Y setter.
     *
     * @param y Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * ID getter.
     *
     * @return A supposed unique ID
     */
    public long getId() {
        return id;
    }

    /**
     * ID setter.
     *
     * @param id Supposed unique ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Function adding the end of a vector to the successors
     * when a vector is added to the roadmap.
     *
     * @param vector vector added
     */
    public void addTronconSortant(Vector vector) {
        successors.add(vector.getDestination());
    }

    /**
     * Getter for wrapper.
     *
     * @return wrapper
     */
    public IntersectionWrapper getWrapper() {
        return wrapper;
    }

    /**
     * setter for wrapper.
     *
     * @param wrapper ref given
     */
    public void setWrapper(IntersectionWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * Equals.
     *
     * @param o Object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Intersection that = (Intersection) o;

        return id.equals(that.id);
    }

    /**
     * Hash code.
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * reset wrapper for dijkstra.
     */
    public void resetWrapper() {
        wrapper = null;
    }
}
