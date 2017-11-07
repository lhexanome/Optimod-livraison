package lhexanome.optimodlivraison.platform.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represent a delivery order.
 * It contains a set of delivery to do,
 * A start time and a warehouse.
 */
public class DeliveryOrder {

    /**
     * Set of delivery.
     */
    private Set<Delivery> deliveries;
    /**
     * Start time of the delivery order.
     */
    private Date start;
    /**
     * Warehouse.
     * Start of a tour.
     */
    private Warehouse beginning;

    /**
     * Default constructor.
     */
    public DeliveryOrder() {
        deliveries = new LinkedHashSet<>();
    }

    /**
     * Add a delivery.
     *
     * @param delivery Delivery to add
     */
    public void addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
    }

    /**
     * Deliveries getter.
     *
     * @return Set of delivery
     */
    public Set<Delivery> getDeliveries() {
        return deliveries;
    }

    /**
     * Renvoie la date de départ du livreur.
     *
     * @return Date de départ
     */
    public Date getStart() {
        return start;
    }

    /**
     * Permet de régler la date des livraisons.
     *
     * @param start Date de départ
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Renvoie l'entrepot de départ du livreur.
     *
     * @return entrepot de départ
     */
    public Warehouse getBeginning() {
        return beginning;
    }

    /**
     * Règle la position de départ du livreur.
     *
     * @param beginning entrepot de départ
     */
    public void setBeginning(Warehouse beginning) {
        this.beginning = beginning;
    }
}
