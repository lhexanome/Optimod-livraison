package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.logging.Logger;

/**
 * Remove delivery from a tour.
 */
public class RemoveDeliveryCommand extends UndoableCommand {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RemoveDeliveryCommand.class.getName());

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
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {

    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {

    }

    /**
     * Executed by the redo method.
     */
    @Override
    protected void doRedo() {

    }
}
