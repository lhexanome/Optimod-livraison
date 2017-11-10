package lhexanome.optimodlivraison.platform.models;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Following of vectors connected linking two intersections.
 */
public class Path {

    /**
     * List of vectors.
     */
    private List<Vector> vectors;

    /**
     * Time needed to travel between two intersections.
     * Including the waiting time if needed.
     * In seconds
     */
    private int timeToTravel;

    /**
     * Starting halt of the path.
     */
    private Halt start;

    /**
     * Ending halt of the path.
     */
    private Halt end;

    /**
     * Random color generator.
     */
    private Random randomColorGenerator;

    /**
     * Constructor.
     *
     * @param start starting halt.
     * @param end   ending halt.
     */
    public Path(Halt start, Halt end) {
        vectors = new LinkedList<>();
        this.timeToTravel = 0;
        this.start = start;
        this.end = end;
        this.randomColorGenerator = new Random();
    }

    /**
     * Equals.
     *
     * @param o object to compare
     * @return If the two objects are equals
     */
    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (Integer.compare(path.timeToTravel, timeToTravel) != 0) return false;
        return vectors != null ? vectors.equals(path.vectors) : path.vectors == null;
    }

    /**
     * Hashcode.
     *
     * @return Object's hashcode
     */
    @Override
    public int hashCode() {
        int result = vectors != null ? vectors.hashCode() : 0;
        result = 31 * result + (timeToTravel != +0.0f ? Float.floatToIntBits(timeToTravel) : 0);
        return result;
    }

    /**
     * Time to travel getter.
     *
     * @return Time to travel
     * @see #timeToTravel
     */
    public int getTimeToTravel() {
        return timeToTravel;
    }

    /**
     * Time to travel setter.
     * <p>
     * Use especially in tests.
     *
     * @param timeToTravel Time
     */
    public void setTimeToTravel(int timeToTravel) {
        this.timeToTravel = timeToTravel;
    }

    /**
     * Vectors getter.
     *
     * @return List of vector for this path
     */
    public List<Vector> getVectors() {
        return vectors;
    }

    /**
     * Start getter.
     *
     * @return Start halt
     */
    public Halt getStart() {
        return start;
    }

    /**
     * End getter.
     *
     * @return End halt
     */
    public Halt getEnd() {
        return end;
    }

    /**
     * Add a vector at the end of a path.
     * This operation updates the time to travel.
     *
     * @param vector Vector to add.
     *               Vector's origin intersection must be the same as the last destination intersection.
     * @throws RuntimeException If the origin of the vector is not the same as the current last destination.
     */
    public void addVector(Vector vector) {
        if (vectors.size() == 0 || vectors.get(vectors.size() - 1).getDestination() == vector.getOrigin()) {
            vectors.add(vector);
            timeToTravel += vector.getTimeToTravel();
        } else {
            throw new RuntimeException("The vector is not at the end of the path");
        }

    }

    /**
     * Add a vector at the beginning of a path.
     * This operation updates the time to travel.
     *
     * @param vector Vector to add.
     *               Vector's destination intersection must be the same as the first origin intersection.
     * @throws RuntimeException If the destination of the vector is not the same as the current first origin.
     */
    public void addVectorBefore(Vector vector) {
        if (vectors.size() == 0 || vectors.get(0).getOrigin() == vector.getDestination()) {
            vectors.add(0, vector);
            timeToTravel += vector.getTimeToTravel();
        } else {
            throw new RuntimeException("The vector is not at the beginning of the path");
        }

    }

    /**
     * Add an entire path to the current path.
     * <p>
     * Used especially for the tests.
     *
     * @param path Path to add
     * @throws RuntimeException If the path provided is not compatible with the current path
     */
    public void addPath(Path path) {
        if (vectors.size() == 0 || getEnd() == path.getStart()) {

            vectors.addAll(path.vectors);
            timeToTravel += path.getTimeToTravel();
            end = path.getEnd();
        } else {
            throw new RuntimeException("The vector is not at the end of the path");
        }

    }
}
