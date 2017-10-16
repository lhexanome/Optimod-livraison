package lhexanome.optimodlivraison.platform.models;

import java.util.Date;
import java.util.List;

/**
 * Suite de trajets qui part de l'entrepôt, passe par toutes les livraisons.
 * Les livraisons doivent-être aux bonnes plages horaires,
 * et la tournée doit avoir un temps minimal.
 * Doit finir à l'entrepôt.
 */
public class Tournee {

    /**
     * Liste ordonnée de trajets.
     */
    private List<Trajet> deliveries;

    /**
     * Moment de départ de la tournée.
     */
    private Date start;

    /**
     * Intersection représentant l'entrepôt.
     */
    private Intersection warehouse;

    /**
     * Temps estimé pour compléter une tournée.
     * En minutes
     */
    private int time;

    /**
     * TODO JavaDoc.
     * @return Deliveries
     */
    public List<Trajet> getDeliveries() {
        return deliveries;
    }

    /**
     * TODO JavaDoc.
     * @param deliveries Deliveries
     */
    public void setDeliveries(List<Trajet> deliveries) {
        this.deliveries = deliveries;
    }

    /**
     * TODO JavaDoc.
     * @return Start
     */
    public Date getStart() {
        return start;
    }

    /**
     * TODO JavaDoc.
     * @param start Start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * TODO JavaDoc.
     * @return Warehouse
     */
    public Intersection getWarehouse() {
        return warehouse;
    }

    /**
     * TODO JavaDoc.
     * @param warehouse Warehouse
     */
    public void setWarehouse(Intersection warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * TODO JavaDoc.
     * @return Time
     */
    public int getTime() {
        return time;
    }

    /**
     * TODO JavaDoc.
     * @param time Time
     */
    public void setTime(int time) {
        this.time = time;
    }
}
