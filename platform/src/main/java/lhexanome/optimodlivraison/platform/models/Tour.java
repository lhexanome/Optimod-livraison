package lhexanome.optimodlivraison.platform.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Following of path, starting from the warehouse, going through each delivery.
 * Must end at the warehouse.
 */
public class Tour extends Observable {

    /**
     * Ordered list of path.
     */
    private List<Path> paths;

    /**
     * Start time of the tour.
     */
    private Date start;

    /**
     * Warehouse.
     */
    private Warehouse warehouse;

    /**
     * Estimated time to complete the tour.
     * In seconds
     */
    private int time;

    /**
     * Constructor.
     *
     * @param warehouse Warehouse
     * @param start     Start date
     * @param time      Initial estimated time
     */
    public Tour(Warehouse warehouse, Date start, int time) {
        this(warehouse, start, time, new ArrayList<>());
    }

    /**
     * Constructor.
     *
     * @param warehouse Warehouse
     * @param start     Start date
     * @param time      Initial estimated time
     * @param paths     Ordered list of paths
     * @see #warehouse
     * @see #start
     * @see #time
     * @see #paths
     */
    public Tour(Warehouse warehouse, Date start, int time, List<Path> paths) {
        this.start = start;
        this.warehouse = warehouse;
        this.time = time;
        this.paths = paths;
    }

    /**
     * Constructor.
     *
     * @param warehouse Warehouse
     * @param start     Start date
     */
    public Tour(Warehouse warehouse, Date start) {
        this(warehouse, start, 0);
    }

    /**
     * Empty constructor.
     */
    public Tour() {

    }

    /**
     * Path getter.
     *
     * @return Deliveries
     */
    public List<Path> getPaths() {
        return paths;
    }

    /**
     * Path setter.
     * <p>
     * This function is synchronised to prevent data corruption
     * when reading the paths variable from other threads.
     *
     * @param paths Path
     */
    public synchronized void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    /**
     * Date getter.
     *
     * @return Start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Start setter.
     *
     * @param start Start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Warehouse getter.
     *
     * @return Warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Warehouse setter.
     *
     * @param warehouse Warehouse
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Estimated time getter.
     *
     * @return Time in seconds
     */
    public int getTime() {
        return time;
    }

    /**
     * Estimated time setter.
     *
     * @param time Time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Return a list of all the halts made in the tour.
     * Start by the Warehouse, and continue until the last delivery.
     * <p>
     * This function is synchronised to prevent a modification of the
     * {@link Tour#paths} list while streaming it.
     *
     * @return Ordered list of halt
     */
    public synchronized java.util.Vector<Delivery> getOrderedDeliveryVector() {
        return paths.stream()
                .map(Path::getStart)
                .filter(halt -> halt instanceof Delivery)
                .map(halt -> (Delivery) halt)
                .collect(Collectors.toCollection(Vector::new));
    }

    /**
     * Force the notifications to observers.
     * Computing is made by an external class, so it doesn't have access to
     * {@link Observable#setChanged()} method.
     */
    public void forceNotifyObservers() {
        setChanged();
        notifyObservers();
    }
}
