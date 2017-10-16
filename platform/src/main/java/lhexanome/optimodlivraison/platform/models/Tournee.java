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
     * TODO JavaDoc
     */
    private int time;

    public void setDeliveries(List<Trajet> deliveries) {
        this.deliveries = deliveries;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setWarehouse(Intersection warehouse) {
        this.warehouse = warehouse;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Trajet> getDeliveries() {
        return deliveries;
    }

    public Date getStart() {
        return start;
    }

    public Intersection getWarehouse() {
        return warehouse;
    }

    public int getTime() {
        return time;
    }
}
