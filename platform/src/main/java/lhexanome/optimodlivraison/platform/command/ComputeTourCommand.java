package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.compute.InterfaceCalcul;
import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.listeners.ComputeTourListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Command to compute a tour.
 */
public class ComputeTourCommand extends SwingWorker<Void, Tour> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ComputeTourCommand.class.getName());

    /**
     * Compute interface.
     */
    private InterfaceCalcul interfaceCalcul;

    /**
     * Road map.
     */
    private RoadMap roadMap;

    /**
     * Delivery order.
     */
    private DeliveryOrder deliveryOrder;

    /**
     * Tour.
     */
    private Tour tour;

    /**
     * Listener.
     */
    private ComputeTourListener listener;

    /**
     * Command constructor.
     *
     * @param roadMap       Road map
     * @param deliveryOrder Delivery order
     */
    public ComputeTourCommand(RoadMap roadMap, DeliveryOrder deliveryOrder) {
        this.roadMap = roadMap;
        this.deliveryOrder = deliveryOrder;

        this.interfaceCalcul = new InterfaceCalcul();
    }

    /**
     * Compute a tour with the provided road map and delivery order.
     * Progressive solution. Use publish at least one time.
     */
    @Override
    protected Void doInBackground() throws Exception {
        try {
            LOGGER.info("Compute tour");
            long start = System.currentTimeMillis();

            SimplifiedMap simplifiedMap = interfaceCalcul.computeSimplifiedRoadMap(roadMap, deliveryOrder);
            LOGGER.info(String.format("time: %ds", (System.currentTimeMillis() - start) / 1000));

            LOGGER.info("Simplified roadMap computed");

            Tour computedTour = interfaceCalcul.computeTour(simplifiedMap, deliveryOrder);
            //Tour tour = simplifiedMap.generateFakeTour();

            // TODO Send multiple time tour updates.
            // TODO Set progress (by time)
            publish(computedTour);

            LOGGER.warning("Tour computed");
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getCause()));
            throw e;
        }
        return null;
    }


    /**
     * Notify of a new improved tour.
     * The first, call the `onTourFirstTourComputed` and then
     * call `onTourImproved`.
     *
     * @param chunks Computed tour (every chunk are the same and first Tour instance)
     */
    @Override
    protected void process(List<Tour> chunks) {
        if (listener == null) return;

        if (tour == null) {
            this.tour = chunks.get(0);
            listener.onTourFirstTourComputed(tour);
        } else {
            listener.onTourImproved();
        }
    }

    /**
     * Notify of the end of the computation.
     */
    @Override
    protected void done() {
        if (listener == null) return;

        try {
            get();
            listener.onComputingTourEnd();
        } catch (InterruptedException | ExecutionException e) {
            listener.onTourComputingFail(e);
        }
    }

    /**
     * Listener setter.
     * There is only one listener per command.
     *
     * @param listener Listener
     */
    public void setListener(ComputeTourListener listener) {
        this.listener = listener;
    }
}
