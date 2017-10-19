package lhexanome.optimodlivraison.platform.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Cette classe représente une Demande de livraison,
 * Elle contient un ensemble de livraisons à faire,
 * Une date et une intersection de départ.
 */
public class DemandeLivraison {

    /**
     * Ensemble des livraisons à faire pour une demande.
     */
    private Set<Livraison> deliveries;
    /**
     * Date de début de la livraison.
     */
    private Date start;
    /**
     * entrepot de depart de la demande de livraison.
     * Point de départ et d'arriver de la livraison
     */
    private Entrepot beginning;

    /**
     * Constructeur par défaut.
     * Initialise un Set
     */
    public DemandeLivraison() {
        deliveries = new LinkedHashSet<>();
    }

    /**
     * Ajoute une livraison à la demande.
     *
     * @param delivery Livraison à ajouter
     */
    public void addDelivery(Livraison delivery) {
        this.deliveries.add(delivery);
    }

    /**
     * Renvoie un ensemble de livraisons à faire.
     *
     * @return La liste des livraisons
     */
    public Set<Livraison> getDeliveries() {
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
    public Entrepot getBeginning() {
        return beginning;
    }

    /**
     * Règle la position de départ du livreur.
     *
     * @param beginning entrepot de départ
     */
    public void setBeginning(Entrepot beginning) {
        this.beginning = beginning;
    }
}
