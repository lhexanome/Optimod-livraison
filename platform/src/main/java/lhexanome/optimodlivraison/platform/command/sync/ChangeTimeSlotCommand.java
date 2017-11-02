package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.logging.Logger;

/**
 * Change time slot of a delivery.
 */
public class ChangeTimeSlotCommand extends UndoableCommand {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ChangeTimeSlotCommand.class.getName());

    /**
     * Tour.
     */
    private final Tour tour;

    /**
     * Delivery to edit.
     */
    private final Delivery selectedValue;

    /**
     * New time slot.
     */
    private final TimeSlot timeSlot;


    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param selectedValue Delivery to edit
     * @param timeSlot      New time slot
     */
    public ChangeTimeSlotCommand(Tour tour, Delivery selectedValue, TimeSlot timeSlot) {
        this.tour = tour;
        this.selectedValue = selectedValue;
        this.timeSlot = timeSlot;
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
