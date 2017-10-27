package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.compute.InterfaceCalcul;
import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.exceptions.ComputeException;
import lhexanome.optimodlivraison.platform.listeners.ComputeListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

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
     * Interface de calcul.
     */
    private InterfaceCalcul interfaceCalcul;

    /**
     * Liste de listeners.
     */
    private Collection<ComputeListener> listeners;

    /**
     * Constructeur par défaut.
     */
    public ComputeFacade() {
        interfaceCalcul = new InterfaceCalcul();
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
     * @param roadMap       RoadMap
     * @param deliveryOrder Demande de livraison
     */
    public void computeTour(RoadMap roadMap, DeliveryOrder deliveryOrder) {
        try {
            LOGGER.info("Compute tour");
            long start = System.currentTimeMillis();

            SimplifiedMap simplifiedMap = interfaceCalcul.computeSimplifiedRoadMap(roadMap, deliveryOrder);
            LOGGER.info(String.format("time: %ds", (System.currentTimeMillis() - start) / 1000));

            LOGGER.info("Simplified roadMap computed");

            // FIXME Remove deliveryOrder
            //Tour tour = interfaceCalcul.computeTour();
            Tour tour = simplifiedMap.generateFakeTour();
            LOGGER.warning("Tour computed");

            listeners.forEach(l -> l.onComputingTour(tour));

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
