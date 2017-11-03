package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.logging.Logger;

/**
 * Change time slot of a delivery.
 */
public class MoveDeliveryCommand extends UndoableCommand {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MoveDeliveryCommand.class.getName());

    /**
     * Tour.
     */
    private final Tour tour;

    /**
     * Delivery to move.
     */
    private final Delivery selectedValue;

    /**
     * New index.
     */
    private final int newIndex;


    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param selectedValue Delivery to edit
     * @param newIndex      New index
     */
    public MoveDeliveryCommand(Tour tour, Delivery selectedValue, int newIndex) {
        this.tour = tour;
        this.selectedValue = selectedValue;
        this.newIndex = newIndex;
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
