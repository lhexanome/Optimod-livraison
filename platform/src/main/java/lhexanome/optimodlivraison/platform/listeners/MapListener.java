package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
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
    void onUpdateMap(Plan plan);

    /**
     * Fonction appelée quand un plan n'arrive pas à être chargé.
     *
     * @param e Exception contenant la cause
     */
    void onFailUpdateMap(MapException e);
}
