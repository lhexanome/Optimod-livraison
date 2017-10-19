package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.exceptions.ComputeException;
import lhexanome.optimodlivraison.platform.models.Tournee;

/**
 * Listener pour les calculs de tournée.
 */
public interface ComputeListener {
    /**
     * Fonction appelée quand le calcul d'une tournée est fini.
     *
     * @param tournee Tournée calculée
     */
    void onComputingTour(Tournee tournee);

    /**
     * Fonction appelée quand une tournée n'a pas pu être calculée.
     *
     * TODO Ajouter un genre d'enum pour les différents problèmes
     *
     * @param e Exception contenant la cause
     */
    void onFailCompute(ComputeException e);
}
