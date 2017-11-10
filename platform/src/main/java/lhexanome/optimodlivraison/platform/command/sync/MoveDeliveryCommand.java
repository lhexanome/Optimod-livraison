package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.List;
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
    private int newIndex;

    /**
     * Old index.
     */
    private int oldIndex;

    /**
     * RoadMap.
     */
    private RoadMap roadMap;

    /**
     * SimplifiedMap.
     */
    private SimplifiedMap simplifiedMap;

    /**
     * Removed path.
     */
    private Path removedPath;

    /**
     * first removed path.
     */
    private Path previewRemovedPath;

    /**
     * second removed path.
     */
    private Path afterRemovedPath;

    /**
     * counter.
     */
    private int counter = 0;


    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param roadMap       RoadMap
     * @param selectedValue Delivery to edit
     * @param newIndex      New index
     */
    public MoveDeliveryCommand(Tour tour, RoadMap roadMap, Delivery selectedValue, int newIndex) {
        this.tour = tour;
        this.roadMap = roadMap;
        this.selectedValue = selectedValue;
        this.newIndex = newIndex;
        this.oldIndex = tour.getOrderedDeliveryVector().indexOf(selectedValue);
        this.simplifiedMap = new SimplifiedMap(roadMap);

    }

    /**
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {
        List<Path> paths = tour.getPaths();


        // Create the link between the two neighbour of the delivery
        Path oldNeighbours;

        if (oldIndex == 0) {
            oldNeighbours = simplifiedMap.shortestPathList(
                    tour.getWarehouse(),
                    paths.get(oldIndex + 2).getStart()
            );
        } else if (oldIndex == paths.size() - 2) {
            oldNeighbours = simplifiedMap.shortestPathList(
                    paths.get(oldIndex).getStart(),
                    tour.getWarehouse()
            );
        } else {
            oldNeighbours = simplifiedMap.shortestPathList(
                    paths.get(oldIndex).getStart(),
                    paths.get(oldIndex + 2).getStart()
            );
        }

        // Remove the path

        paths.remove(oldIndex);
        paths.remove(oldIndex);

        // Add shortcut

        paths.add(oldIndex, oldNeighbours);

        // Compute new paths

        Path newPathToDelivery;
        Path newPathFromDelivery;

        if (newIndex == 0) {
            newPathFromDelivery = simplifiedMap.shortestPathList(
                    selectedValue,
                    paths.get(newIndex + 1).getStart()
            );
            newPathToDelivery = simplifiedMap.shortestPathList(
                    tour.getWarehouse(),
                    selectedValue
            );
        } else if (newIndex == paths.size() - 1) {
            newPathFromDelivery = simplifiedMap.shortestPathList(
                    selectedValue,
                    tour.getWarehouse()
            );
            newPathToDelivery = simplifiedMap.shortestPathList(
                    paths.get(newIndex).getStart(),
                    selectedValue
            );
        } else {
            newPathFromDelivery = simplifiedMap.shortestPathList(
                    selectedValue,
                    paths.get(newIndex).getEnd()
            );
            newPathToDelivery = simplifiedMap.shortestPathList(
                    paths.get(newIndex).getStart(),
                    selectedValue
            );
        }

        // Remove new index shortcut

        paths.remove(newIndex);

        // Add detour

        paths.add(newIndex, newPathFromDelivery);
        paths.add(newIndex, newPathToDelivery);

        // Send updates

        this.newIndex = tour.getOrderedDeliveryVector().indexOf(selectedValue);

        tour.refreshEstimateDates();
        tour.forceNotifyObservers();

    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {
        int tmp = this.newIndex;
        this.newIndex = this.oldIndex;
        this.oldIndex = tmp;

        doExecute();

        this.oldIndex = this.newIndex;
        this.newIndex = tmp;

        tour.refreshEstimateDates();
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
