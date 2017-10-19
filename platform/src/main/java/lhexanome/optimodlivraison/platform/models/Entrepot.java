package lhexanome.optimodlivraison.platform.models;

/**
 * point de depart d'une demande de livraison.
 */
public class Entrepot extends Arret {

    /**
     * constructeur.
     * @param intersection location de l'entrepot
     */
    public Entrepot(Intersection intersection) {
        super(intersection);
    }
}
