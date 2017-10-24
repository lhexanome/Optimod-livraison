package lhexanome.optimodlivraison.platform.models;

/**
 * classe mere representant un point important sur la carte.
 */
public class Halt {
    /**
     * Intersection représentant le lieu de la livraison.
     */
    private Intersection intersection;

    /**
     * constructeur.
     * @param intersection location de l'arret
     */
    public Halt(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Renvoie la position de l'arret.
     *
     * @return Intersection
     */
    public Intersection getIntersection() {

        return intersection;
    }
    /**
     * Définie l'intersection de la livraison.
     *
     * @param intersection Intersection
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

}
