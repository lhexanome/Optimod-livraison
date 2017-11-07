package lhexanome.optimodlivraison.platform.models;

/**
 * Arc connecting two intersections, with a street name and a length.
 */
public class Vector {

    /**
     * Average speed to use for computations.
     * In km/h
     */
    public static final int SPEED = 15;

    /**
     * Start intersection.
     */
    private Intersection origin;

    /**
     * End intersection.
     */
    private Intersection destination;

    /**
     * Street name provided by the road map.
     */
    private String streetName;

    /**
     * Length of the vector.
     * In decimetre.
     */
    private float length;

    /**
     * Vector Constructor.
     *
     * @param origin      Start intersection
     * @param destination End intersection
     * @param streetName  Street name of the vector
     * @see #Vector(Intersection, Intersection, String, float)
     */
    public Vector(Intersection origin, Intersection destination, String streetName) {
        this(
                origin,
                destination,
                streetName,
                (float) Math.sqrt(
                        Math.pow(origin.getX() - destination.getX(), 2)
                                + Math.pow(origin.getY() - destination.getY(), 2)
                )
        );
    }

    /**
     * Vector Constructor.
     *
     * @param origin      Start intersection
     * @param destination End intersections
     * @param streetName  Street name of the vector
     * @param length      Length of the vector in decimetre
     * @see #origin
     * @see #destination
     * @see #streetName
     * @see #length
     */
    public Vector(Intersection origin, Intersection destination, String streetName, float length) {
        this.origin = origin;
        this.destination = destination;
        this.streetName = streetName;
        this.length = length;
    }

    /**
     * Equals.
     *
     * @param o object to compare
     * @return Equals or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (Float.compare(vector.length, length) != 0) return false;
        if (!origin.equals(vector.origin)) return false;
        if (!destination.equals(vector.destination)) return false;
        return streetName.equals(vector.streetName);
    }

    /**
     * HashCode.
     *
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        int result = origin.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + streetName.hashCode();
        result = 31 * result + (length != +0.0f ? Float.floatToIntBits(length) : 0);
        return result;
    }


    /**
     * Destination getter.
     *
     * @return Destination
     */
    public Intersection getDestination() {
        return destination;
    }

    /**
     * Destination setter.
     *
     * @param destination Destination
     */
    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    /**
     * Street name getter.
     *
     * @return Street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Street name setter.
     *
     * @param streetName Street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Length getter.
     *
     * @return Length
     */
    public float getLength() {
        return length;
    }

    /**
     * Length setter.
     *
     * @param length Length
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * Start intersection getter.
     *
     * @return Origin intersection
     */
    public Intersection getOrigin() {
        return origin;
    }

    /**
     * Start intersection setter.
     *
     * @param origin Origin intersection
     */
    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    /**
     * Compute the time needed to go through the vector.
     * In seconds
     *
     * @return Time To Travel (s)
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public int getTimeToTravel() {
        return (int) (length / ((float) (SPEED * (1000.0 / 3600.0))));
    }
}
