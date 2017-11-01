package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Tour;

/**
 * Remove delivery from a tour.
 */
public class RemoveDeliveryCommand implements UndoableCommand {
    /**
     * Tour.
     */
    private final Tour tour;

    /**
     * Delivery to remove.
     */
    private final Delivery selectedValue;

    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param selectedValue Delivery to remove
     */
    public RemoveDeliveryCommand(Tour tour, Delivery selectedValue) {
        this.tour = tour;
        this.selectedValue = selectedValue;
    }

    /**
     * Called to execute an action.
     */
    @Override
    public void execute() {

    }

    /**
     * Called when the action must be undoed.
     */
    @Override
    public void undo() {

    }

    /**
     * Called when the action must be redoed.
     */
    @Override
    public void redo() {

    }
}
