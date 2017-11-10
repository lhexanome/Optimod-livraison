package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.command.sync.AddDeliveryCommand;
import lhexanome.optimodlivraison.platform.command.sync.ChangeTimeSlotCommand;
import lhexanome.optimodlivraison.platform.command.sync.MoveDeliveryCommand;
import lhexanome.optimodlivraison.platform.command.sync.RemoveDeliveryCommand;
import lhexanome.optimodlivraison.platform.edition.EditionInvoker;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.panel.TourEditorPanel;
import lhexanome.optimodlivraison.ui.popup.TimeSlotChooserPopup;

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

    private RoadMap roadMap;

    /**
     * Edition manager.
     */
    private EditionInvoker editionInvoker;

    /**
     * Flag to know when a tour is edited.
     */
    private boolean edited;

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
        tourEditorPanel.setTour(tour);

        if (tour == null) {
            hide();
            edited = false;
        } else {
            show();
        }
    }

    /**
     * Hide the panel.
     */
    public void hide() {
        tourEditorPanel.getContentPane().setVisible(false);
    }

    /**
     * Show the panel.
     */
    public void show() {
        tourEditorPanel.getContentPane().setVisible(true);
    }


    /**
     * Undo the last action.
     */
    public void undo() {
        editionInvoker.undoLastCommand();
    }

    /**
     * Redo the last undo command.
     */
    public void redo() {
        editionInvoker.redoLastUndo();
    }

    /**
     * Add a delivery to the current tour.
     */
    public void addDelivery() {
        if (checkIfEditionIsUnavailable()) return;

        // Ask for intersection
        Intersection intersection = mainController.getSelectedIntersection();

        if (intersection == null) {
            mainController.notifyError("Vous devez d'abord sélectionner une intersection sur la carte !");
            return;
        }

        // Ask for the time slot
        TimeSlotChooserPopup popup = new TimeSlotChooserPopup();
        popup.setVisible(true);

        // Here we have a response for the time slot.

        if (popup.wasCanceled()) return;

        // Will be null if does not want one
        TimeSlot timeSlot = popup.getTimeSlot();


        // Ask for the duration
        int duration = -1;
        while (duration == -1) {
            try {
                String durationS = JOptionPane.showInputDialog(
                        getContentPane(),
                        "Durée de la livraison ?",
                        "Ajout d'une livraison",
                        JOptionPane.QUESTION_MESSAGE
                );
                // If null, the dialog was cancelled so we can stop
                if (durationS == null) return;

                duration = Integer.parseInt(durationS);
                if (duration < 0) throw new Exception();
            } catch (Exception e) {
                LOGGER.warning("The provided input is not an int");
                mainController.notifyError("La valeur entrée n'est pas un entier positif");
            }
        }

        Delivery delivery = new Delivery(intersection, duration, timeSlot);

        AddDeliveryCommand command = new AddDeliveryCommand(tour, roadMap, delivery, tour.getPaths().size() - 1);
        editionInvoker.storeAndExecute(command);
        edited = true;
    }

    /**
     * Road map needed for the view.
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {

        this.roadMap = roadMap;
        tourEditorPanel.setRoadMap(roadMap);
    }

    /**
     * Remove a delivery from a tour.
     *
     * @param selectedValue Delivery to remove
     */
    public void removeDelivery(Delivery selectedValue) {
        if (checkIfEditionIsUnavailable()) return;
        if (selectedValue == null) {
            mainController.notifyError("Vous devez d'abord sélectionner une livraison !");
            return;
        }

        RemoveDeliveryCommand command = new RemoveDeliveryCommand(tour, roadMap, selectedValue);
        editionInvoker.storeAndExecute(command);
        edited = true;
    }

    /**
     * Change the time slot of a tour.
     *
     * @param selectedValue Delivery to edit
     */
    public void changeTimeSlot(Delivery selectedValue) {
        if (checkIfEditionIsUnavailable()) return;
        if (selectedValue == null) {
            mainController.notifyError("Vous devez d'abord sélectionner une livraison !");
            return;
        }

        // Ask for the time slot
        TimeSlotChooserPopup popup = new TimeSlotChooserPopup();
        popup.setVisible(true);

        // Here we have a response for the time slot.

        if (popup.wasCanceled()) return;

        // Will be null if does not want one
        TimeSlot timeSlot = popup.getTimeSlot();

        ChangeTimeSlotCommand command = new ChangeTimeSlotCommand(tour, selectedValue, timeSlot);
        editionInvoker.storeAndExecute(command);
        edited = true;
    }

    /**
     * Move a delivery in the tour.
     *
     * @param delivery Delivery moved
     * @param newIndex New index
     */
    public void moveDelivery(Delivery delivery, int newIndex) {
        if (checkIfEditionIsUnavailable()) return;

        MoveDeliveryCommand command = new MoveDeliveryCommand(tour, roadMap, delivery, newIndex);
        editionInvoker.storeAndExecute(command);
        edited = true;
    }

    /**
     * displays a selected delivery on the textual view.
     *
     * @param selectValue the selected delivery
     */
    public void selectDeliveryFromMap(Delivery selectValue) {
        tourEditorPanel.selectDeliveryFromMap(selectValue);
    }

    /**
     * displays a selected delivery on the map.
     *
     * @param selectedValue the selected delivery
     */
    public void selectDeliveryFromList(Delivery selectedValue) {
        mainController.selectDeliveryFromList(selectedValue);
    }


    /**
     * Check if edition is unavailable and notify the user.
     *
     * @return true if edition is unavailable false if there is no problem
     */
    private boolean checkIfEditionIsUnavailable() {
        if (!mainController.isComputationRunning()) return false;

        mainController.notifyError("Vous ne pouvez pas éditer une tournée pendant son calcul.");
        return true;
    }

    /**
     * Return whether the tour is edited or not.
     *
     * @return Boolean
     */
    public boolean isEdited() {
        return edited;
    }

    /**
     * Ask for a ne delivery order.
     */
    public void reloadDeliveryOrder() {
        mainController.reloadDeliveryOrder();
    }
}
