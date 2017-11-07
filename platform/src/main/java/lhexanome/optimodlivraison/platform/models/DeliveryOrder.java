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
    private Warehouse warehouse;

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
     * Start time getter.
     *
     * @return Departure time
     */
    public Date getStart() {
        return start;
    }

    /**
     * Start time setter.
     *
     * @param start Departure time
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
}
