package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.models.Plan;

/**
 * Listener pour les plans.
 */
public interface MapListener {
    /**
     * Fonction appelée quand un plan est chargé.
     *
     * @param plan Plan chargé
     */
    void onUpdate(Plan plan);
}
