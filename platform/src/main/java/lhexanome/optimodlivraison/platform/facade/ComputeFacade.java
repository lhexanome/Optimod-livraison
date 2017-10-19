package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.compute.PlanSimplifie;
import lhexanome.optimodlivraison.platform.exceptions.ComputeException;
import lhexanome.optimodlivraison.platform.listeners.ComputeListener;
import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Tournee;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Facade de la partie livraisons.
 */
public class ComputeFacade {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ComputeFacade.class.getName());


    /**
     * Liste de listeners.
     */
    private Collection<ComputeListener> listeners;

    /**
     * Constructeur par défaut.
     */
    public ComputeFacade() {
        listeners = new ArrayList<>();
    }

    /**
     * Ajoute un listener.
     *
     * @param listener Listener
     */
    public void addOnComputeListener(ComputeListener listener) {
        listeners.add(listener);
    }

    /**
     * Enlève un listener.
     *
     * @param listener Listener
     */
    public void removeOnComputeListner(ComputeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Calcul une tournée.
     *
     * @param plan             Plan
     * @param demandeLivraison Demande de livraison
     */
    public void computeTour(Plan plan, DemandeLivraison demandeLivraison) {
        try {
            LOGGER.info("Compute tour");

            PlanSimplifie planSimplifie = null; // ... .computeSimplifiedMap(plan, demandeLivraison);

            LOGGER.info("Simplified map computed");

            Tournee tournee = null; // ... .computeTour(planSimplifie);

            LOGGER.warning("Tour computed");

            listeners.forEach(l -> l.onComputingTour(null));

            LOGGER.info("Listeners notified !");
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getCause()));
            failUpdate(e);
        }
    }

    /**
     * Permet de notifier les listeners d'un problème.
     *
     * @param e Exception générant l'erreur
     */
    private void failUpdate(Exception e) {
        listeners.forEach(l -> l.onFailCompute(new ComputeException(e)));
    }
}
