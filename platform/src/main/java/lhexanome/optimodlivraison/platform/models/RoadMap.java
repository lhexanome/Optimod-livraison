package lhexanome.optimodlivraison.platform.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Set of intersections linked by vectors.
 */
public class RoadMap {

    /**
     * Map containing all the vectors of a map.
     * Accessible with an intersection.
     */
    private Map<Intersection, Collection<Vector>> map;

    /**
     * Map containing all the intersections indexed by their ID.
     */
    private Map<Long, Intersection> intersectionMap;

    /**
     * Default constructor.
     */
    public RoadMap() {
        map = new HashMap<>();
        intersectionMap = new HashMap<>();
    }

    /**
     * Add a vector to an intersection
     * Also add the intersection if it does not exist yet.
     *
     * @param vector Vector
     */
    public void addVector(Vector vector) {
        Intersection origin = vector.getOrigin();
        addIntersection(origin);
        origin.addTronconSortant(vector);
        addIntersection(vector.getDestination());
        this.map.get(origin).add(vector);
    }

    /**
     * Add an intersection to the road map.
     * Do nothing if it already exists.
     *
     * @param intersection Intersection to add
     */
    public void addIntersection(Intersection intersection) {
        if (!this.map.containsKey(intersection)) {
            this.map.put(intersection, new ArrayList<>());
            this.intersectionMap.put(intersection.getId(), intersection);
        }
    }

    /**
     * Return a collection of vectors from an intersection.
     *
     * @param start intersection
     * @return Collection of vectors.
     */
    public Collection<Vector> getVectorsFromIntersection(Intersection start) {
        Collection<Vector> res = map.get(start);
        return res == null ? Collections.emptyList() : res;
    }

    /**
     * Return all the vectors of the road map.
     * It create a new collection, so it has a
     * performance cost.
     *
     * @return Collection containing all the vectors in the road map
     */
    public Collection<Vector> getVectors() {
        List<Vector> res = new ArrayList<>();
        map.forEach((intersection, vectors) -> res.addAll(vectors));
        return res;
    }

    /**
     * Intersection count getter.
     *
     * @return Count of intersection in the map
     */
    public int getIntersectionCount() {
        return intersectionMap.size();
    }

    /**
     * Intersections getter.
     *
     * @return Collection of all the intersections.
     */
    public Collection<Intersection> getIntersections() {
        return map.keySet();
    }

    /**
     * Find an intersection with it's id.
     *
     * @param id Intersection ID
     * @return Intersection with the provided ID or null
     */
    public Intersection findIntersectionById(Long id) {
        return intersectionMap.get(id);
    }
}
