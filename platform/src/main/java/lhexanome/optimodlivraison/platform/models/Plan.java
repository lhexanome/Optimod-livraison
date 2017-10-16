package lhexanome.optimodlivraison.platform.models;


import java.util.HashMap;
import java.util.Map;

/**
 * Ensemble d'intersection reliés par des tronçons.
 */
public class Plan {

    /**
     * Map contenant les différents tronçons d'une carte.
     */
    private Map<Intersection, Troncon> map;

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
        // FIXME Ajouter plusieurs tronçons pour une intersection
        this.map.put(start, road);
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
