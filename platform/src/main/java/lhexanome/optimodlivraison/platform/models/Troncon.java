package lhexanome.optimodlivraison.platform.models;

/**
 * Arc reliant 2 intersections, avec un nom de rue, une longueur.
 */
public class Troncon {

    /**
     * TODO JavaDoc.
     */
    public static final int SPEED = 15;

    /**
     * TODO JavaDoc.
     */
    private Intersection origine;

    /**
     * TODO JavaDoc.
     */
    private Intersection destination;

    /**
     * TODO JavaDoc.
     */
    private String nameStreet;

    /**
     * TODO JavaDoc.
     */
    private float length;

    /**
     * TODO JavaDoc.
     *
     * @return Destination
     */
    public Intersection getDestination() {

        return destination;
    }

    /**
     * TODO JavaDoc.
     *
     * @param destination Destination
     */
    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    /**
     * TODO JavaDoc.
     *
     * @return Name street
     */
    public String getNameStreet() {
        return nameStreet;
    }

    /**
     * TODO JavaDoc.
     *
     * @param nameStreet Name street
     */
    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    /**
     * TODO JavaDoc.
     *
     * @return Length
     */
    public float getLength() {
        return length;
    }

    /**
     * TODO JavaDoc.
     *
     * @param length Length
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * TODO JavaDoc.
     *
     * @return Origine
     */
    public Intersection getOrigine() {

        return origine;
    }

    /**
     * TODO JavaDoc.
     *
     * @param origine Origine
     */
    public void setOrigine(Intersection origine) {
        this.origine = origine;
    }

    /**
     * TODO JavaDoc.
     *
     * @return Time To Travel
     */
    public float timeToTravel() {
        return length * SPEED;
    }
}
