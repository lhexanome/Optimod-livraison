package lhexanome.optimodlivraison.platform.models;

import java.util.Date;

/**
 * Represent a halt on the map.
 */
public class Halt {
    /**
     * Intersection of the halt.
     */
    private Intersection intersection;

    /**
     * Estimated time of delivery.
     */
    private Date estimateDate = null;

    /**
     * Constructor.
     *
     * @param intersection Location of the halt
     */
    public Halt(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Intersection getter.
     *
     * @return Intersection
     */
    public Intersection getIntersection() {

        return intersection;
    }

    /**
     * Intersection setter.
     *
     * @param intersection Intersection
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Estimated date getter.
     *
     * @return estimate date
     */
    public Date getEstimateDate() {
        return estimateDate;
    }

    /**
     * Estimated date setter.
     *
     * @param estimateDate Estimated date
     */
    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }
}
