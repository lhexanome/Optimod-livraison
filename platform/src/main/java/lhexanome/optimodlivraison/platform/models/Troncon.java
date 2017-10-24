package lhexanome.optimodlivraison.platform.models;

/**
 * Arc reliant 2 intersections, avec un nom de rue, une longueur.
 */
public class Troncon {

    /**
     * Vitesse à laquelle va le livreur durant tout la tournée.
     * En km/h
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
     * En décimètre
     */
    private float length;

    /**
     * Troncon Constructor.
     *
     * @param origine     intersections se situant au debut du tronçon
     * @param destination intersections se situant à la fin du tronçon
     * @param nameStreet  Nom du troncon
     * @see #Troncon(Intersection, Intersection, String, float)
     */
    public Troncon(Intersection origine, Intersection destination, String nameStreet) {
        this(
                origine,
                destination,
                nameStreet,
                (float) Math.sqrt(
                        Math.pow(origine.getX() - destination.getX(), 2)
                                + Math.pow(origine.getY() - destination.getY(), 2)
                )
        );
    }


    /**
     * fonction equals.
     *
     * @param o objet a comparer
     * @return resultat de la comparaison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Troncon troncon = (Troncon) o;

        if (Float.compare(troncon.length, length) != 0) return false;
        if (!origine.equals(troncon.origine)) return false;
        if (!destination.equals(troncon.destination)) return false;
        return nameStreet.equals(troncon.nameStreet);
    }

    /**
     * fonction hashCode.
     *
     * @return hashcode de l'objet
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Override
    public int hashCode() {
        int result = origine.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + nameStreet.hashCode();
        result = 31 * result + (length != +0.0f ? Float.floatToIntBits(length) : 0);
        return result;
    }

    /**
     * Troncon Constructor.
     *
     * @param origine     intersections se situant au debut du tronçon
     * @param destination intersections se situant à la fin du tronçon
     * @param nameStreet  Nom du troncon
     * @param length      Longueur du tronçon
     * @see #origine
     * @see #destination
     * @see #nameStreet
     * @see #length
     */
    public Troncon(Intersection origine, Intersection destination, String nameStreet, float length) {
        this.origine = origine;
        this.destination = destination;
        this.nameStreet = nameStreet;
        this.length = length;
    }

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
    public int getTimeToTravel() {
        return (int) (3600 * ((length / 10.0) / (SPEED * 1000))); //TODO temps en float
    }
}
