package lhexanome.optimodlivraison.platform.models;

import java.util.Date;
import java.util.Set;

public class DemandeLivraison {

    private Set<Livraison> deliveries;
    private Date start;
    private Intersection Beginning;

    public void setDeliveries(Set<Livraison> deliveries) {
        this.deliveries = deliveries;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setBeginning(Intersection beginning) {
        Beginning = beginning;
    }

    public Set<Livraison> getDeliveries() {
        return deliveries;
    }

    public Date getStart() {
        return start;
    }

    public Intersection getBeginning() {
        return Beginning;
    }
}
