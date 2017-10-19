package lhexanome.optimodlivraison.platform.models;


import java.util.*;

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
     * Ajoute les intersections d'origine et de destination au plan si il n'y sont pas
     *
     * @param troncon  Troncon
     */
    public void addTroncon(Troncon troncon) {
        Intersection origine = troncon.getOrigine();
        addIntersection(origine);
        addIntersection(troncon.getDestination());
        this.map.get(origine).add(troncon);
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
        }
    }

    /**
     * Renvoie une collection de troncon partant d'une intersection.
     *
     * @param start intersection
     * @return Liste de troncon
     */
    public Collection<Troncon> getTronconsFromIntersection(Intersection start) {
        Collection<Troncon> res = map.get(start);
        return  res == null ? Collections.emptyList() : res;
    }

    /**
     * Permet de recuperer tous les troncons du plan.
     * Cette methode genére une nouvelle collection.
     * Elle peut donc étre plutôt couteuse
     *
     * @return Collection de tous les troncons du plan
     */
    public Collection<Troncon> getTroncons(){
        List<Troncon> res = new ArrayList<Troncon>();
        map.forEach((intersection, troncons) -> res.addAll(troncons));
        return res;
    }

    /**
     * Renvoie le nombre d'intersection de la carte.
     *
     * @return Nombre d'intersection
     */
    public int getIntersectionCount() {
        return map.size();
    }

    /**
     * Permet de recuperer tous les intersections du plan
     *
     * @return Collection de tous les intersections
     */
    public Collection<Intersection> getIntersections() {
        return map.keySet();
    }

    /**
     * Permet de recuperer une intersections du plan depuis sont id.
     * Cette recherche consiste a parcourir toutes les intersections pour trouver la bonne.
     * Elle peut donc donc vite devenir trés couteuse.
     *
     * @param id
     *
     * @return L'intersection corespondant a la recherche ou null si l'intersection chercher n'existe pas dasn le plan
     */
    public Intersection findIntersectionById(Long id){
        for(Intersection i : map.keySet()){
            if(i.getId() == id) return  i;
        }
        return null;
    }
}
