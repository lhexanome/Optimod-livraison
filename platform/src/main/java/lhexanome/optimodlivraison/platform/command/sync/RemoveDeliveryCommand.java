package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.Tour;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
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
     * Delivery to remove.
     */
    private final Delivery selectedValue;

    private Path previewRemovedPath;

    private Path afterRemovedPath;

    private SimplifiedMap simplifiedMap;

    private int compteur = 0;

    /**
     * Constructor.
     *
     * @param tour          Tour
     * @param selectedValue Delivery to remove
     */
    public RemoveDeliveryCommand(Tour tour, Delivery selectedValue) {
        this.tour = tour;
        this.selectedValue = selectedValue;
    }

    /**
     * Executed by the execute method.
     */
    @Override
    protected void doExecute() {

        for (Path p:tour.getPaths()) {
            if(p.getEnd()==selectedValue){
                break;
            }
            compteur++;
        }

        tour.getPaths().add(compteur, simplifiedMap.shortestPathList(tour.getPaths().get(compteur).getStart(), tour.getPaths().get(compteur+1).getEnd()));
       previewRemovedPath =  tour.getPaths().remove(compteur+1);
        afterRemovedPath = tour.getPaths().remove(compteur+1);
    }

    /**
     * Executed by the undo method.
     */
    @Override
    protected void doUndo() {
        tour.getPaths().remove(compteur);
        tour.getPaths().add(compteur, previewRemovedPath);
        tour.getPaths().add(compteur+1, afterRemovedPath);
    }

    /**
     * Executed by the redo method.
     */
    @Override
    protected void doRedo() {
        doExecute();
    }
}
