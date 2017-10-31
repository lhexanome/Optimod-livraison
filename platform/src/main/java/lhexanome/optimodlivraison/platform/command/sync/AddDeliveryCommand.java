package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.InterfaceCalcul;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.logging.Logger;

/**
 * Command to compute a tour.
 */
public class AddDeliveryCommand implements UndoableCommand {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AddDeliveryCommand.class.getName());

    /**
     * Index where to add the delivery.
     */
    private int index;

    /**
     * Delivery to add.
     */
    private Delivery deliveryToAdd;

    /**
     * Tour where to add the delivery.
     */
    private Tour tour;

    /**
     * Compute interface.
     */
    private InterfaceCalcul interfaceCalcul;


    /**
     * Command status.
     */
    private UndoableCommandStatus status;


    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param deliveryToAdd Delivery to add
     * @param index         Index where to add the delivery
     */
    public AddDeliveryCommand(Tour tour, Delivery deliveryToAdd, int index) {
        this.index = index;
        this.deliveryToAdd = deliveryToAdd;
        this.tour = tour;

        this.status = UndoableCommandStatus.INITIALIZED;
        this.interfaceCalcul = new InterfaceCalcul();
    }

    /**
     * Called to execute an action.
     */
    @Override
    public void execute() {
        if (this.status != UndoableCommandStatus.INITIALIZED) {
            throw new RuntimeException("The command was already executed !");
        }

        // TODO Add the delivery
        this.status = UndoableCommandStatus.EXECUTED;
    }

    /**
     * Called when the action must be undoed.
     */
    @Override
    public void undo() {
        if (this.status != UndoableCommandStatus.EXECUTED) {
            throw new RuntimeException("Tried to undo before executing the action !");
        }

        // TODO Remove the delivery
        this.status = UndoableCommandStatus.UNDOED;
    }

    /**
     * Called when the action must be redoed.
     */
    @Override
    public void redo() {
        if (this.status != UndoableCommandStatus.UNDOED) {
            throw new RuntimeException("Tried to redo before undoing the action !");
        }

        // TODO Readd the delivery
        this.status = UndoableCommandStatus.REDOED;
    }
}
