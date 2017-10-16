package lhexanome.optimodlivraison.platform.models;

/**
 * Représente une livraison.
 * Contient une durée de livraison, une intersection et une plage horaire.
 */
public class Livraison {

    /**
     * Durée prise pour la livraison.
     * En minutes
     */
    private int duration;

    /**
     * Intersection représentant le lieu de la livraison.
     */
    private Intersection intersection;

    /**
     * Plage horaire durant laquelle la livraison peut s'effectuer.
     */
    private PlageHoraire slot;

    /**
     * Renvoie le temps de livraison en minutes.
     *
     * @return Durée
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Définie la durée de la livraison.
     *
     * @param duration Durée en minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Renvoie la position de la livraison.
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

    /**
     * Renvoie la plage horaire actuelle de la livraison.
     *
     * @return Plage horaire
     */
    public PlageHoraire getSlot() {
        return slot;
    }

    /**
     * Définie la plage horaire de la livraison.
     *
     * @param slot Plage horaire
     */
    public void setSlot(PlageHoraire slot) {
        this.slot = slot;
    }
}
