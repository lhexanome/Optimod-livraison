package lhexanome.optimodlivraison.platform.models;

import java.util.Date;
import java.util.List;

public class Tournee {

    private List<Trajet> deliveries;
    private Date start;
    private Intersection warehouse;
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
