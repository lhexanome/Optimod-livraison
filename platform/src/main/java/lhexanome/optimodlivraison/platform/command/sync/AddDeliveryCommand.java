package lhexanome.optimodlivraison.platform.command.sync;

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
     * SimplifiedMap.
     */

    private SimplifiedMap simplifiedMap;
    /**
     * removed path.
     */

    private Path removedPath;


    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param roadMap       Road map
     * @param deliveryToAdd Delivery to add
     * @param index         Index where to add the delivery
     */
    public AddDeliveryCommand(Tour tour, RoadMap roadMap, Delivery deliveryToAdd, int index) {
        super();

        this.index = index;
        this.deliveryToAdd = deliveryToAdd;
        this.tour = tour;
        this.simplifiedMap = new SimplifiedMap(roadMap);
    }

    /**
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {
        List<Path> tourPaths = tour.getPaths();

        Halt previousHalt = tourPaths.get(index).getStart();
        Halt afterHalt = tourPaths.get(index).getEnd();

        removedPath = tourPaths.remove(index);
        tourPaths.add(index, simplifiedMap.shortestPathList(previousHalt, deliveryToAdd));
        tourPaths.add(index + 1, simplifiedMap.shortestPathList(deliveryToAdd, afterHalt));

        tour.forceNotifyObservers();
    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {
        tour.getPaths().remove(index);
        tour.getPaths().remove(index);
        tour.getPaths().add(index, removedPath);

        tour.forceNotifyObservers();
    }

    /**
     * Executed by the redo method.
     */
    @Override
    protected void doRedo() {
        doExecute();
    }
}
