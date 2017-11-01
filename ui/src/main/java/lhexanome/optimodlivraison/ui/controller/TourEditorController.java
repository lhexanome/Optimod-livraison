package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.command.sync.AddDeliveryCommand;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.edition.EditionInvoker;
import lhexanome.optimodlivraison.ui.panel.TourEditorPanel;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Tour controller.
 */
public class TourEditorController implements ControllerInterface {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(TourEditorController.class.getName());

    /**
     * Main controller.
     */
    private final MainController mainController;

    /**
     * Tour panel.
     */
    private final TourEditorPanel tourEditorPanel;

    /**
     * Current tour.
     */
    private Tour tour;

    /**
     * Edition invoker.
     * Take care of the history of modifications
     */
    private EditionInvoker editionInvoker;

    /**
     * Constructor.
     *
     * @param mainController Main controller
     */
    public TourEditorController(MainController mainController) {
        this.mainController = mainController;

        this.editionInvoker = new EditionInvoker();
        this.tourEditorPanel = new TourEditorPanel(this);
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
        return tourEditorPanel.getContentPane();
    }

    /**
     * Tour setter.
     * Call the main controller to notify change
     *
     * @param tour new tour
     */
    public void setTour(Tour tour) {
        this.tour = tour;
        mainController.setTour(tour);
        tourEditorPanel.setTour(tour);
    }


    /**
     * Undo the last action.
     */
    public void undo() {
        editionInvoker.undoLastCommand();
        // TODO firePropertyChange
    }

    /**
     * Redo the last undo command.
     */
    public void redo() {
        editionInvoker.redoLastUndo();
        // TODO Notify change
    }

    /**
     * Add a delivery to the current tour.
     *
     * @param delivery Delivery to add
     */
    public void addDelivery(Delivery delivery, int index) {
        AddDeliveryCommand command = new AddDeliveryCommand(tour, delivery, index);
        editionInvoker.storeAndExecute(command);
    }
}
