package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.exceptions.DeliveryException;
import lhexanome.optimodlivraison.platform.models.DemandeLivraison;

/**
 * Listener pour les livraisons.
 */
public interface DeliveryListener {
    /**
     * Fonction appelée quand une demande de livraison est chargée.
     *
     * @param demandeLivraison Demande de livraison
     */
    void onUpdateDeliveryOrder(DemandeLivraison demandeLivraison);

    /**
     * Fonction appelée quand une demande n'arrive pas à être chargée.
     *
     * @param e Exception contenant la cause
     */
    void onFailUpdateDeliveryOrder(DeliveryException e);
}
