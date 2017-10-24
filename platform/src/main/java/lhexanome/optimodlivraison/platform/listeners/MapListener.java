package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.models.RoadMap;

/**
 * Listener pour les plans.
 */
public interface MapListener {
    /**
     * Fonction appelée quand un roadMap est chargé.
     *
     * @param roadMap RoadMap chargé
     */
    void onUpdateMap(RoadMap roadMap);

    /**
     * Fonction appelée quand un plan n'arrive pas à être chargé.
     *
     * @param e Exception contenant la cause
     */
    void onFailUpdateMap(MapException e);
}
