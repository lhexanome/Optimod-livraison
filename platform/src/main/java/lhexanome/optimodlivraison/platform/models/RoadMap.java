package lhexanome.optimodlivraison.platform.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ensemble d'intersection reliés par des tronçons.
 */
public class RoadMap {

    /**
     * RoadMap contenant les différents tronçons d'une carte.
     */
    private Map<Intersection, Collection<Vector>> map;

    /**
     * RoadMap contenant les intersections indexées par leur id.
     */
    private Map<Long, Intersection> intersectionMap;

    /**
     * Constructeur par défaut.
     * Initialise une RoadMap
     */
    public RoadMap() {
        map = new HashMap<>();
        intersectionMap = new HashMap<>();
    }

    /**
     * Ajoute un tronçon à une intersection.
     * Ajoute les intersections d'origine et de destination au plan si il n'y sont pas
     *
     * @param vector Vector
     */
    public void addVector(Vector vector) {
        Intersection origin = vector.getOrigin();
        addIntersection(origin);
        addIntersection(vector.getDestination());
        this.map.get(origin).add(vector);
    }

    /**
     * Ajoute une intersection.
     * Ne fait rien si l'intersection existe deja;
     *
     * @param intersection intersection a ajouter au plan
     */
    public void addIntersection(Intersection intersection) {
        if (!this.map.containsKey(intersection)) {
            this.map.put(intersection, new ArrayList<>());
            this.intersectionMap.put(intersection.getId(), intersection);
        }
    }

    /**
     * Renvoie une collection de troncon partant d'une intersection.
     *
     * @param start intersection
     * @return Liste de troncon
     */
    public Collection<Vector> getTronconsFromIntersection(Intersection start) {
        Collection<Vector> res = map.get(start);
        return res == null ? Collections.emptyList() : res;
    }

    /**
     * Permet de recuperer tous les troncons du plan.
     * Cette methode genére une nouvelle collection.
     * Elle peut donc étre plutôt couteuse
     *
     * @return Collection de tous les troncons du plan
     */
    public Collection<Vector> getVectors() {
        List<Vector> res = new ArrayList<>();
        map.forEach((intersection, vectors) -> res.addAll(vectors));
        return res;
    }

    /**
     * Renvoie le nombre d'intersection de la carte.
     *
     * @return Nombre d'intersection
     */
    public int getIntersectionCount() {
        return intersectionMap.size();
    }

    /**
     * Permet de recuperer tous les intersections du plan.
     *
     * @return Collection de tous les intersections
     */
    public Collection<Intersection> getIntersections() {
        return map.keySet();
    }

    /**
     * Permet de recuperer une intersections du plan depuis sont id.
     *
     * @param id ID de l'intersection
     * @return L'intersection corespondant a la recherche ou null si l'intersection chercher n'existe pas dans le plan
     */
    public Intersection findIntersectionById(Long id) {
        return intersectionMap.get(id);
    }
}
