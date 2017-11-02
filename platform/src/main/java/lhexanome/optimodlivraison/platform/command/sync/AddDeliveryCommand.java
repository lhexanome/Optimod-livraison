package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.InterfaceCalcul;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.logging.Logger;

/**
 * Command to compute a tour.
 */
public class AddDeliveryCommand extends UndoableCommand {

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
     * Constructor.
     *
     * @param tour          Tour
     * @param deliveryToAdd Delivery to add
     * @param index         Index where to add the delivery
     */
    public AddDeliveryCommand(Tour tour, Delivery deliveryToAdd, int index) {
        super();

        this.index = index;
        this.deliveryToAdd = deliveryToAdd;
        this.tour = tour;

        this.interfaceCalcul = new InterfaceCalcul();
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
