package lhexanome.optimodlivraison.platform.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensemble d'intersection reliés par des tronçons.
 */
public class Plan {

    /**
     * Map contenant les différents tronçons d'une carte.
     */
    private Map<Intersection, Collection<Troncon>> map;

    /**
     * Constructeur par défaut.
     * Initialise une Map
     */
    public Plan() {
        map = new HashMap<>();
    }

    /**
     * Ajoute un tronçon à une intersection.
     *
     * @param start Intersection
     * @param road  Troncon
     */
    public void addTroncon(Intersection start, Troncon road) {
        if (!this.map.containsKey(start)) {
            this.map.put(start, new ArrayList<>());
        }
        this.map.get(start).add(road);
    }

    /**
     * Renvoie une collection de troncon partant d'une intersection
     * @param start intersection
     * @return Liste de troncon
     */
    public Collection<Troncon> getTronconsFromIntersection(Intersection start) {
        return map.get(start);
    }

    /**
     * Renvoie le nombre d'intersection de la carte.
     *
     * @return Nombre d'intersection
     */
    public int getIntersectionCount() {
        return map.size();
    }
}
