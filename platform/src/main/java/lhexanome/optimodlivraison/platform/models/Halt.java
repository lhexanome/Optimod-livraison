package lhexanome.optimodlivraison.platform.models;

import java.util.Date;

/**
 * classe mere representant un point important sur la carte.
 */
public class Halt {
    /**
     * Intersection représentant le lieu de la livraison.
     */
    private Intersection intersection;

    /**
     * date de passage estime.
     */
    private Date estimateDate = null;

    /**
     * constructeur.
     *
     * @param intersection location de l'arret
     */
    public Halt(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Renvoie la position de l'arret.
     *
     * @return Intersection
     */
    public Intersection getIntersection() {

        return intersection;
    }

    /**
     * Définie l'intersection de la livraison.
     *
     * @param intersection Intersection
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * return the estimate date.
     *
     * @return estimate date
     */
    public Date getEstimateDate() {
        return estimateDate;
    }

    /**
     * set the estimateDate.
     *
     * @param estimateDate date to set
     */
    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }
}
