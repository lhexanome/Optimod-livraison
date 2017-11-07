package lhexanome.optimodlivraison.platform.models;

/**
 * Stating point of a tour.
 */
public class Warehouse extends Halt {

    /**
     * Constructor.
     *
     * @param intersection Intersection of the warehouse
     */
    public Warehouse(Intersection intersection) {
        super(intersection);
    }
}
