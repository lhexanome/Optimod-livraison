package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.facade.RoadMapFacade;
import lhexanome.optimodlivraison.platform.listeners.MapListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.panel.RoadMapPanel;
import lhexanome.optimodlivraison.ui.popup.FileChooserPopup;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;


public class RoadMapController implements ControllerInterface {
    private static final Logger LOGGER = Logger.getLogger(RoadMapController.class.getName());


    private final MainController mainController;

    private final RoadMapPanel roadMapPanel;

    private RoadMap roadMap;

    public RoadMapController(MainController mainController) {
        this.mainController = mainController;

        this.roadMapPanel = new RoadMapPanel(this);
    }

    @Override
    public void closeWindow() {
        mainController.closeWindow();
    }

    @Override
    public void start() {
        mainController.start();
    }

    @Override
    public JPanel getContentPane() {
        return roadMapPanel.getContentPane();
    }

    private void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
        roadMapPanel.setRoadMap(roadMap);
        mainController.setRoadMap(roadMap);
    }

    public void selectRoadMap(File xmlFile) {

        RoadMapFacade mapFacade = new RoadMapFacade();
        mapFacade.addOnUpdateMapListener(new MapListener() {
            @Override
            public void onUpdateMap(RoadMap newRoadMap) {
                setRoadMap(newRoadMap);
                //TODO roadMapPanel.setLoad(false);
            }

            @Override
            public void onFailUpdateMap(MapException e) {
                //TODO roadMapPanel.setLoad(false);

                LOGGER.warning(String.format("Error while updating road map :%s", e.getMessage()));
                mainController.notifyError(e.getMessage());
            }
        });

        // TODO roadMapPanel.setLoad(true);
        mapFacade.loadMapFromFile(xmlFile);
    }


    public void reloadMap() {
        LOGGER.info("Reloading road map");
        FileChooserPopup popup = new FileChooserPopup("Choisissez un plan", "xml");
        File file = popup.show();

        if (file != null) {
            LOGGER.info(String.format("User selected file : %s", file.getName()));
            // Send back to main controller to update other controller if needed
            mainController.selectRoadMap(file);
        } else {
            LOGGER.info("User did not provide a file, doing nothing");
        }
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        roadMapPanel.setDeliveryOrder(deliveryOrder);
    }

    public RoadMap getRoadMap() {
        return roadMap;
    }

    public void setTour(Tour tour) {
        roadMapPanel.setTour(tour);
    }
}
