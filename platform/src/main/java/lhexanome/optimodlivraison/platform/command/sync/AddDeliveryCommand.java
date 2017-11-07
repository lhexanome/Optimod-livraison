package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.InterfaceCalcul;
import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.List;
import java.util.logging.Logger;

/**
 * Command to compute a tour.
 */
public class AddDeliveryCommand extends UndoableCommand {

    private List<Delivery> newDeliveryList;

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
     * RoadMap
     */

    private RoadMap roadMap;
    /**
     * SimplifiedMap
     */

    private SimplifiedMap simplifiedMap;
    /**
     *  removed path
     */

    private Path removedPath;


    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param deliveryToAdd Delivery to add
     * @param index         Index where to add the delivery
     */
    public AddDeliveryCommand(Tour tour, RoadMap roadMap, Delivery deliveryToAdd, int index) {
        super();

        this.index = index;
        this.deliveryToAdd = deliveryToAdd;
        this.tour = tour;
        this.roadMap = roadMap;
        SimplifiedMap simplifiedMap;

        //this.interfaceCalcul = new InterfaceCalcul();
    }

    /**
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {
        Halt previousHalt = tour.getPaths().get(index).getStart();
        Halt afterHalt = tour.getPaths().get(index).getEnd();
        removedPath = tour.getPaths().remove(index);
        tour.getPaths().add(index,simplifiedMap.shortestPathList(previousHalt, deliveryToAdd));
        tour.getPaths().add(index+1,simplifiedMap.shortestPathList(deliveryToAdd, afterHalt));
        //TODO tour.notifyObservers() à rajouter.
    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {
        tour.getPaths().remove(index);
        tour.getPaths().remove(index);
        tour.getPaths().add(index, removedPath);
    }

    /**
     * Executed by the redo method.
     */
    @Override
    protected void doRedo() {
        doExecute();
    }
}
