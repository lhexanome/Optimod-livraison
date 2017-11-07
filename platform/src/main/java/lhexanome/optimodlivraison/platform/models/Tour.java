package lhexanome.optimodlivraison.platform.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Suite de trajets qui part de l'entrepôt, passe par toutes les livraisons.
 * Les livraisons doivent-être aux bonnes plages horaires,
 * et la tournée doit avoir un temps minimal.
 * Doit finir à l'entrepôt.
 */
public class Tour extends Observable {

    /**
     * Liste ordonnée de trajets.
     */
    private List<Path> paths;

    /**
     * Moment de départ de la tournée.
     */
    private Date start;

    /**
     * Intersection représentant l'entrepôt.
     */
    private Warehouse warehouse;

    /**
     * Temps estimé pour compléter une tournée.
     * En minutes
     */
    private int time;

    /**
     * Constructor.
     *
     * @param warehouse Intersection représentant l'entrepôt
     * @param start     Moment de départ de la tournée
     * @param time      Temps estimé pour compléter une tournée. En minutes
     */
    public Tour(Warehouse warehouse, Date start, int time) {
        this(warehouse, start, time, new ArrayList<>());
    }

    /**
     * Constructor.
     *
     * @param warehouse Intersection représentant l'entrepôt
     * @param start     Moment de départ de la tournée
     * @param time      Temps estimé pour compléter une tournée.
     *                  En minutes
     * @param paths     Liste ordonnée de trajets.
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
     * Empty constructor.
     */
    public Tour() {

    }

    /**
     * Renvoie la liste des livraisons.
     *
     * @return Deliveries
     */
    public List<Path> getPaths() {
        return paths;
    }

    /**
     * Définie la liste des livraisons.
     *
     * @param paths Deliveries
     */
    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    /**
     * Renvoie l'intersection de départ.
     *
     * @return Start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Définie l'intersection de départ.
     *
     * @param start Start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Renvoie l'interection correspondant à l'entrepôt.
     *
     * @return Warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Définie l'intersection correspondant à l'entrepôt.
     *
     * @param warehouse Warehouse
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Renvoie le temps nécessaire pour effectuer une tournée.
     *
     * @return Time
     */
    public int getTime() {
        return time;
    }

    /**
     * Définie le temps nécessaire pour effectuer une tournée.
     *
     * @param time Time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Return a list of all the halts made in the tour.
     * Start by the Warehouse, and continue until the last delivery.
     *
     * @return Ordered list of halt
     */
    public java.util.Vector<Delivery> getOrderedDeliveryVector() {
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
