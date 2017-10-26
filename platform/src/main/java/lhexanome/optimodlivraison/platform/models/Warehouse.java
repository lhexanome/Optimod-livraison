package lhexanome.optimodlivraison.platform.models;

/**
 * point de depart d'une demande de livraison.
 */
public class Warehouse extends Halt {

    /**
     * constructeur.
     *
     * @param intersection location de l'entrepot
     */
    public Warehouse(Intersection intersection) {
        super(intersection);
    }
}
