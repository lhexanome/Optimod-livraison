package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.command.ComputeTourCommand;
import lhexanome.optimodlivraison.platform.listeners.ComputeTourListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.panel.TourPanel;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Tour controller.
 */
public class TourController implements ControllerInterface, ComputeTourListener {
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
        if (computeTourCommand != null && !computeTourCommand.isCancelled()) {
            mainController.notifyError("Un calcul est déjà en cours !");
            return;
        }
        computeTourCommand = new ComputeTourCommand(roadMap, deliveryOrder);
        computeTourCommand.setListener(this);
        computeTourCommand.execute();
    }

    /**
     * Cancel a tour computation.
     */
    public void cancelComputeTour() {
        if (computeTourCommand != null && !computeTourCommand.isCancelled()) {
            computeTourCommand.cancel(true);
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
        tourPanel.setTour(tour);
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
    public void onTourFirstTourComputed(Tour firstTour) {
        setTour(firstTour);
    }

    /**
     * Called when the tour was improved.
     * The listener must use the old reference.
     */
    @Override
    public void onTourImproved() {
        // TODO Update display
    }

    /**
     * Called at the end of the computing.
     */
    @Override
    public void onComputingTourEnd() {
        // TODO End computation
        computeTourCommand = null;
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
}
