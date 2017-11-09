package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
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
     * RoadMap
     */
    private RoadMap roadMap;

    /**
     * SimplifiedMap
     */
    private SimplifiedMap simplifiedMap;

    /**
     * Removed path
     */
    private Path removedPath;

    /**
     *  first removed path
     */
    private Path previewRemovedPath;

    /**
     * second removed path
     */
    private Path afterRemovedPath;

    /**
      * counter
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
        tour.getPaths().add(counter, simplifiedMap.shortestPathList(tour.getPaths().get(counter).getStart(), tour.getPaths().get(counter + 1).getEnd()));

        if (counter < newIndex){
            previewRemovedPath = tour.getPaths().remove(counter + 1);
            afterRemovedPath = tour.getPaths().remove(counter + 1);

            Halt previousHalt = tour.getPaths().get(newIndex-1).getStart();
            Halt afterHalt = tour.getPaths().get(newIndex-1).getEnd();
            removedPath = tour.getPaths().remove(newIndex-1);
            tour.getPaths().add(newIndex-1, simplifiedMap.shortestPathList(previousHalt, selectedValue));
            tour.getPaths().add(newIndex , simplifiedMap.shortestPathList(selectedValue, afterHalt));

        }
        else  {
            previewRemovedPath = tour.getPaths().remove(counter -1);
            afterRemovedPath = tour.getPaths().remove(counter -1);

            Halt previousHalt = tour.getPaths().get(newIndex).getStart();
            Halt afterHalt = tour.getPaths().get(newIndex).getEnd();
            removedPath = tour.getPaths().remove(newIndex);
            tour.getPaths().add(newIndex, simplifiedMap.shortestPathList(previousHalt, selectedValue));
            tour.getPaths().add(newIndex + 1, simplifiedMap.shortestPathList(selectedValue, afterHalt));

        }

        tour.refreshEstimateDates();
        tour.forceNotifyObservers();

    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {
        if (counter < newIndex){
            tour.getPaths().remove(newIndex);
            tour.getPaths().remove(newIndex);
            tour.getPaths().add(newIndex, removedPath);
            tour.getPaths().remove(counter);
            tour.getPaths().add(counter-1, previewRemovedPath);
            tour.getPaths().add(counter, afterRemovedPath);

        }else{
            tour.getPaths().remove(newIndex);
            tour.getPaths().remove(newIndex);
            tour.getPaths().add(newIndex, removedPath);
            tour.getPaths().remove(counter);
            tour.getPaths().add(counter, previewRemovedPath);
            tour.getPaths().add(counter+1, afterRemovedPath);
        }

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
