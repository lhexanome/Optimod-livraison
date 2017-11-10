package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.List;
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
     * RoadMap.
     */
    private final RoadMap roadMap;

    /**
     * Delivery to remove.
     */
    private final Delivery selectedValue;

    /**
     * first removed path.
     */
    private Path previewRemovedPath;

    /**
     * second removed path.
     */
    private Path afterRemovedPath;

    /**
     * simplified map.
     */
    private SimplifiedMap simplifiedMap;


    /**
     * Index to remove.
     */
    private int index;

    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param roadMap       RoadMap
     * @param selectedValue Delivery to remove
     */
    public RemoveDeliveryCommand(Tour tour, RoadMap roadMap, Delivery selectedValue) {
        this.tour = tour;
        this.roadMap = roadMap;
        this.selectedValue = selectedValue;
        this.index = tour.getOrderedDeliveryVector().indexOf(selectedValue);
        this.simplifiedMap = new SimplifiedMap(roadMap);
    }

    /**
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {
        List<Path> paths = tour.getPaths();

        // Create the link between the two neighbour of the delivery
        Path neighbours;

        if (index == 0) {
            neighbours = simplifiedMap.shortestPathList(
                    tour.getWarehouse(),
                    paths.get(index + 2).getStart()
            );
        } else if (index == paths.size() - 2) {
            neighbours = simplifiedMap.shortestPathList(
                    paths.get(index).getStart(),
                    tour.getWarehouse()
            );
        } else {
            neighbours = simplifiedMap.shortestPathList(
                    paths.get(index).getStart(),
                    paths.get(index + 2).getStart()
            );
        }

        // Remove the path

        paths.remove(index);
        paths.remove(index);

        // Add shortcut

        paths.add(index, neighbours);


        tour.refreshEstimateDates();
        tour.forceNotifyObservers();
    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {

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
