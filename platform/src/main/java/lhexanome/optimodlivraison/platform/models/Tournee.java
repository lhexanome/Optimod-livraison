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
     * Renvoie la liste des livraisons.
     * @return Deliveries
     */
    public List<Trajet> getDeliveries() {
        return deliveries;
    }

    /**
     * Définie la liste des livraisons.
     * @param deliveries Deliveries
     */
    public void setDeliveries(List<Trajet> deliveries) {
        this.deliveries = deliveries;
    }

    /**
     * Renvoie l'intersection de départ.
     * @return Start
     */
    public Date getStart() {
        return start;
    }

    /**
     * Définie l'intersection de départ.
     * @param start Start
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Renvoie l'interection correspondant à l'entrepôt.
     * @return Warehouse
     */
    public Intersection getWarehouse() {
        return warehouse;
    }

    /**
     * Définie l'intersection correspondant à l'entrepôt.
     * @param warehouse Warehouse
     */
    public void setWarehouse(Intersection warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Renvoie le temps nécessaire pour effectuer une tournée
     * @return Time
     */
    public int getTime() {
        return time;
    }

    /**
     * Définie le temps nécessaire pour effectuer une tournée
     * @param time Time
     */
    public void setTime(int time) {
        this.time = time;
    }
}
