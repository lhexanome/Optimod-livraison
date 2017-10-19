package lhexanome.optimodlivraison.platform.models;

/**
 * Représente une livraison.
 * Contient une durée de livraison, une intersection et une plage horaire.
 * Si la plage horaire est null alors la livraison peut s'efectuer a n'importe quelle heur
 */
public class Livraison extends Arret {

    /**
     * Durée prise pour la livraison.
     * En minutes
     */
    private int duration;


    /**
     * Plage horaire durant laquelle la livraison peut s'effectuer.
     */
    private PlageHoraire slot;

    /**
     * Constructeur de livraison.
     *
     * @param intersection intersection de livraison
     * @param duration     Durée prise pour la livraison
     *                     En minutes
     * @see #duration
     * @see #intersection
     * @see #slot
     * @see #Livraison(Intersection, int, PlageHoraire)
     */
    public Livraison(Intersection intersection, int duration) {
        this(intersection, duration, null);
    }

    /**
     * Constructeur de livraison.
     *
     * @param intersection intersection de livraison
     * @param duration     Durée prise pour la livraison
     *                     En minutes
     * @param slot         Plage horaire durant laquelle la livraison peut s'effectuer.
     * @see #duration
     * @see #intersection
     * @see #slot
     * @see #Livraison(Intersection, int)
     */
    public Livraison(Intersection intersection, int duration, PlageHoraire slot) {
        super(intersection);
        this.duration = duration;
        this.slot = slot;
    }

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
