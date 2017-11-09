package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
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
     * RoadMap.
     */
    private final RoadMap roadMap;

    /**
     * Delivery to remove.
     */
    private final Delivery selectedValue;
    
    /**
     * first removed path
     */
    private Path previewRemovedPath;
    
    /**
     * second removed path
     */
    private Path afterRemovedPath;
    
    /**
     * simplified map
     */
    private SimplifiedMap simplifiedMap;
    
    /**
     * counter
     */
    private int counter = 0;

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
        this.simplifiedMap = new SimplifiedMap(roadMap);
    }

    /**
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {
        counter = 0;

        for (Path p : tour.getPaths()) {
            if (p.getEnd() == selectedValue) {
                break;
            }
            counter++;
        }
        tour.getPaths().size();
        tour.getPaths().add(counter, simplifiedMap.shortestPathList(tour.getPaths().get(counter).getStart(), tour.getPaths().get(counter + 1).getEnd()));
        previewRemovedPath = tour.getPaths().remove(counter + 1);
        afterRemovedPath = tour.getPaths().remove(counter + 1);

        tour.forceNotifyObservers();
    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {
        tour.getPaths().remove(counter);
        tour.getPaths().add(counter, previewRemovedPath);
        tour.getPaths().add(counter + 1, afterRemovedPath);




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
