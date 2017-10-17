package lhexanome.optimodlivraison.platform.models;

/**
 * Arc reliant 2 intersections, avec un nom de rue, une longueur.
 */
public class Troncon {

    /**
     * Vitesse à laquelle va le livreur durant tout la tournée.
     */
    public static final int SPEED = 15;

    /**
     * L'une des intersections se situant au bout du tronçon.
     */
    private Intersection origine;

    /**
     * L'autre intersection se situant au bout du tronçon.
     */
    private Intersection destination;

    /**
     * Nom du troncon d'après le plan fourni.
     */
    private String nameStreet;

    /**
     * Longueur du tronçon.
     */
    private float length;

    /**
     * Renvoie l'une des intersection du troncon.
     *
     * @return Destination
     */
    public Intersection getDestination() {

        return destination;
    }

    /**
     * Définie l'une des intersections du tronçon.
     *
     * @param destination Destination
     */
    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    /**
     * Renvoie le nom du tronçon.
     *
     * @return Name street
     */
    public String getNameStreet() {
        return nameStreet;
    }

    /**
     * Définie le nom du tronçon.
     *
     * @param nameStreet Name street
     */
    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    /**
     * Renvoie la longueur du tronçon.
     *
     * @return Length
     */
    public float getLength() {
        return length;
    }

    /**
     * Définie la longueur du tronçon.
     *
     * @param length Length
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * Renvoie l'autre intersection du tronçon.
     *
     * @return Origine
     */
    public Intersection getOrigine() {
        return origine;
    }

    /**
     * Définie l'autre intersection du tronçon.
     *
     * @param origine Origine
     */
    public void setOrigine(Intersection origine) {
        this.origine = origine;
    }

    /**
     * Renvoie le temps nécessaire pour traverser le tronçon.
     *
     * @return Time To Travel
     */
    public float timeToTravel() {
        return length * SPEED;
    }
}
