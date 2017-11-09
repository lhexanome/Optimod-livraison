package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.command.ComputeTourCommand;
import lhexanome.optimodlivraison.platform.listeners.ComputeTourListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.panel.TourPanel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * Tour controller.
 */
public class TourController implements ControllerInterface, ComputeTourListener, Observer {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TourController.class.getName());

    /**
     * Main controller.
     */
    private final MainController mainController;

    /**
     * Tour panel.
     */
    private final TourPanel tourPanel;

    /**
     * Current tour.
     */
    private Tour tour;

    /**
     * Compute tour command.
     */
    private ComputeTourCommand computeTourCommand;

    /**
     * Constructor.
     *
     * @param mainController Main controller
     */
    public TourController(MainController mainController) {
        this.mainController = mainController;

        this.tourPanel = new TourPanel(this);
    }

    /**
     * {@link ControllerInterface#closeWindow()}.
     */
    @Override
    public void closeWindow() {
        mainController.closeWindow();
    }

    /**
     * {@link ControllerInterface#start()}.
     */
    @Override
    public void start() {
        mainController.start();
    }

    /**
     * {@link ControllerInterface#getContentPane()}.
     */
    @Override
    public JPanel getContentPane() {
        return tourPanel.getContentPane();
    }

    /**
     * Compute a tour.
     * Called by the main controller.
     *
     * @param roadMap       Road map
     * @param deliveryOrder Delivery order
     */
    public void computeTour(RoadMap roadMap, DeliveryOrder deliveryOrder) {
        if (isComputationRunning()) {
            mainController.notifyError("Un calcul est déjà en cours !");
            return;
        }
        computeTourCommand = new ComputeTourCommand(roadMap, deliveryOrder, this);
        computeTourCommand.setListener(this);
        computeTourCommand.execute();
        tourPanel.toggleProgressBar(true);
    }

    /**
     * Cancel a tour computation.
     */
    public void cancelComputeTour() {
        if (isComputationRunning()) {
            computeTourCommand.cancel(true);
            computeTourCommand = null;
        }
    }

    /**
     * Tour setter.
     * Call the main controller to notify change
     *
     * @param tour new tour
     */
    private void setTour(Tour tour) {
        this.tour = tour;
        mainController.setTour(tour);
    }

    /**
     * Called by the panel when the user want a new computation.
     */
    public void newComputation() {
        mainController.computeTour();
    }


    /**
     * Called when a tour is computed for the first time.
     * Then, it will be updated and improved.
     *
     * @param firstTour First tour computed
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public void onFirstTourComputed(Tour firstTour) {
        setTour(firstTour);
    }

    /**
     * Called at the end of the computing.
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public void onComputingTourEnd() {
        computeTourCommand = null;
        tourPanel.toggleProgressBar(false);
    }

    /**
     * Called when a the application was unable to compute a tour.
     *
     * @param e Exception raised
     */
    @Override
    public void onTourComputingFail(Exception e) {
        LOGGER.warning(String.format("Error while computing a tour error : %s", e.getMessage()));
        mainController.notifyError(e.getMessage());
    }

    /**
     * Clear current tour.
     */
    public void clearTour() {
        tour = null;
        tourPanel.toggleProgressBar(false);
        if (computeTourCommand != null) {
            computeTourCommand.cancel(true);
            computeTourCommand = null;
        }
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
            mainController.repaintAll();
        }
    }

    /**
     * Return true if there is a tour being calculated.
     *
     * @return true or false
     */
    public boolean isComputationRunning() {
        return computeTourCommand != null && !computeTourCommand.isCancelled();
    }
}
