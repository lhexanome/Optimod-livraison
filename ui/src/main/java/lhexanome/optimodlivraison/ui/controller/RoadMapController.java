package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.command.ParseMapCommand;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.panel.RoadMapPanel;
import lhexanome.optimodlivraison.ui.popup.FileChooserPopup;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

/**
 * Road map controller.
 */
public class RoadMapController implements ControllerInterface, ParseMapListener {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RoadMapController.class.getName());

    /**
     * Main controller (parent).
     */
    private final MainController mainController;

    /**
     * Road map panel.
     */
    private final RoadMapPanel roadMapPanel;

    /**
     * Current road map.
     */
    private RoadMap roadMap;

    /**
     * Current selected intersection.
     */
    private Intersection currentIntersection;

    /**
     * Current selected delivery.
     */
    private Delivery currentDelivery;

    /**
     * Constructor.
     *
     * @param mainController Main controller
     */
    public RoadMapController(MainController mainController) {
        this.mainController = mainController;

        this.roadMapPanel = new RoadMapPanel(this);
        currentIntersection = null;
    }

    /**
     * {@link ControllerInterface#closeWindow()}.
     */
    @Override
    public void closeWindow() {
        mainController.closeWindow();
    }

    /**
     * {@link ControllerInterface#start()}.
     */
    @Override
    public void start() {
        mainController.start();
    }

    /**
     * {@link ControllerInterface#getContentPane()}.
     */
    @Override
    public JPanel getContentPane() {
        return roadMapPanel.getContentPane();
    }

    /**
     * Load a road map file.
     * Called by the main controller.
     *
     * @param xmlFile XML file
     */
    public void selectRoadMap(File xmlFile) {
        roadMapPanel.setLoading(true);
        ParseMapCommand command = new ParseMapCommand(xmlFile);
        command.setListener(this);
        command.execute();
    }

    /**
     * Ask the user for a new map.
     * Called by the panel
     */
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

    /**
     * Delivery order setter.
     * Called by the main controller.
     *
     * @param deliveryOrder new Delivery order
     */
    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        roadMapPanel.setDeliveryOrder(deliveryOrder);
    }

    /**
     * Road map getter.
     *
     * @return Road map
     */
    public RoadMap getRoadMap() {
        return roadMap;
    }

    /**
     * Road map setter.
     * Called by the {@link #selectRoadMap(File)} function.
     *
     * @param roadMap new Road map
     */
    private void setRoadMap(RoadMap roadMap) {
        this.roadMap = roadMap;
        roadMapPanel.setRoadMap(roadMap);
        mainController.setRoadMap(roadMap);
    }

    /**
     * Tour setter.
     * Called by the main controller
     *
     * @param tour new Tour
     */
    public void setTour(Tour tour) {
        roadMapPanel.setTour(tour);
    }

    /**
     * Called by the main controller when a delivery is selected.
     *
     * @param selectedValue Selected delivery
     */
    public void selectDeliveryFromList(Delivery selectedValue) {
        currentDelivery = selectedValue;
        roadMapPanel.getContentPane().repaint();
    }


    /**
     * Called when map is loaded.
     *
     * @param roadMapParsed RoadMap loaded
     */
    @Override
    public void onMapParsed(RoadMap roadMapParsed) {
        roadMapPanel.setLoading(false);
        setRoadMap(roadMapParsed);
    }

    /**
     * Called when the map could not be loaded.
     *
     * @param e Exception raised
     */
    @Override
    public void onMapParsingFail(Exception e) {
        roadMapPanel.setLoading(false);
        LOGGER.warning(String.format("Error while updating road map :%s", e.getMessage()));
        mainController.notifyError(e.getMessage());
    }

    /**
     * Return the selected intersection on the map.
     *
     * @return Selected intersection
     */
    public Intersection getSelectedIntersection() {
        return currentIntersection;
    }

    /**
     * updates the current selected intersection.
     *
     * @param intersection Selected intersection
     */
    public void onIntersectionSelected(Intersection intersection) {
        currentIntersection = intersection;
        mainController.selectIntersectionFromMap(intersection, roadMap);
    }

    /**
     * Update the selected delivery.
     *
     * @param delivery Delivery selected on the map
     */
    public void onDeliverySelected(Delivery delivery) {
        mainController.selectDeliveryFromMap(delivery);
    }

    /**
     * getter for currentDelivery.
     *
     * @return the current selected delivery
     */
    public Delivery getSelectedDelivery() {
        return currentDelivery;
    }

    /**
     * setter for currentDelivery.
     *
     * @param delivery delivery
     */
    public void setSelectedDelivery(Delivery delivery) {
        mainController.setSelectedDelivery(delivery);
    }
}
