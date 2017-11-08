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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Command to compute a tour.
 * It use {@link SwingWorker} to work, so the logic part is executed in another thread than the UI Thread.
 */
public class ComputeTourCommand extends SwingWorker<Void, Tour> implements Observer {

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
     * Tour observer.
     */
    private Observer tourObserver;

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
     * @param tourObserver  Tour observer
     */
    public ComputeTourCommand(RoadMap roadMap, DeliveryOrder deliveryOrder, Observer tourObserver) {
        this.roadMap = roadMap;
        this.deliveryOrder = deliveryOrder;
        this.tourObserver = tourObserver;

        this.interfaceCalcul = new InterfaceCalcul();
    }

    /**
     * Compute a tour with the provided road map and delivery order.
     * Progressive solution. Use publish at least one time.
     */
    @Override
    protected Void doInBackground() throws Exception {
        try {
            this.interfaceCalcul.addTourObserver(this);

            LOGGER.info("Compute tour");
            long start = System.currentTimeMillis();

            SimplifiedMap simplifiedMap = interfaceCalcul.computeSimplifiedRoadMap(roadMap, deliveryOrder);
            LOGGER.info(String.format("time: %ds", (System.currentTimeMillis() - start) / 1000));

            LOGGER.info("Simplified roadMap computed");

            interfaceCalcul.computeTour(simplifiedMap, deliveryOrder);

            LOGGER.warning("Tour computed");
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getCause()));
            throw e;
        }
        return null;
    }


    /**
     * Notify of a new improved tour.
     * The first, call the `onFirstTourComputed` and then
     * call `onTourImproved`.
     *
     * @param chunks Computed tour (every chunk are the same and first Tour instance)
     */
    @Override
    protected void process(List<Tour> chunks) {
        if (listener == null) return;

        if (tour == null) {
            // The first time we have a tour object,
            // we have to tell caller class that a new tour was computed.
            this.tour = chunks.get(0);
            listener.onFirstTourComputed(tour);
            tour.addObserver(tourObserver);
            tour.notifyObservers(tourObserver);
        }
        // Otherwise the listener is set so no problem
    }

    /**
     * Notify of the end of the computation.
     */
    @Override
    protected void done() {
        if (listener == null) return;
        try {
            try {
                get();
            } catch (CancellationException ignored) {
                // Do nothing with this,
                // just catch it so it doesn't
                // skip the call to onComputingTourEnd()
            }
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

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Tour) {
            publish((Tour) o);
        }
    }
}
