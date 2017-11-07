package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Main controller.
 */
public class MainController implements ControllerInterface {

    /**
     * Main window.
     */
    private final MainWindow mainWindow;

    /**
     * Road map controller.
     */
    private final RoadMapController roadMapController;

    /**
     * DeliveryOrder controller.
     */
    private final DeliveryOrderController deliveryOrderController;

    /**
     * Tour controller.
     */
    private final TourController tourController;


    /**
     * Tour editor controller.
     */
    private final TourEditorController tourEditorController;

    /**
     * current displayed intersection controller.
     */
    private final CurrentIntersectionController currentIntersectionController;


    /**
     * Constructor.
     */
    public MainController() {
        roadMapController = new RoadMapController(this);
        deliveryOrderController = new DeliveryOrderController(this);
        tourController = new TourController(this);
        tourEditorController = new TourEditorController(this);
        currentIntersectionController = new CurrentIntersectionController(this);

        mainWindow = new MainWindow(
                this,
                roadMapController.getContentPane(),
                deliveryOrderController.getContentPane(),
                tourController.getContentPane(),
                tourEditorController.getContentPane(),
                currentIntersectionController.getContentPane()
        );
    }

    /**
     * {@link ControllerInterface#start()}.
     */
    @Override
    public void start() {
        mainWindow.open();
    }

    /**
     * {@link ControllerInterface#getContentPane()}.
     */
    @Override
    public Container getContentPane() {
        return mainWindow.getFrame();
    }

    /**
     * {@link ControllerInterface#closeWindow()}.
     */
    @Override
    public void closeWindow() {
        mainWindow.close();
    }

    /**
     * Select a new road map.
     *
     * @param xmlFile Map file
     */
    public void selectRoadMap(File xmlFile) {
        roadMapController.selectRoadMap(xmlFile);
    }

    /**
     * Select a delivery order.
     *
     * @param xmlFile Delivery order file
     */
    public void selectDeliveryOrder(File xmlFile) {
        deliveryOrderController.selectDeliveryOrder(xmlFile, roadMapController.getRoadMap());
    }

    /**
     * Road map setter.
     *
     * @param roadMap Road map
     */
    public void setRoadMap(RoadMap roadMap) {
        deliveryOrderController.clearDeliveryOrder();
        tourController.clearTour();
        tourEditorController.setRoadMap(roadMap);
        tourEditorController.setTour(null);
        deliveryOrderController.show();
    }

    /**
     * Delivery order setter.
     *
     * @param deliveryOrder Delivery order
     */
    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        roadMapController.setDeliveryOrder(deliveryOrder);
        tourController.clearTour();
    }

    /**
     * Tour setter.
     *
     * @param tour Tour
     */
    public void setTour(Tour tour) {
        roadMapController.setTour(tour);
        tourEditorController.setTour(tour);
        deliveryOrderController.hide();
    }

    /**
     * Notify a error to the user.
     *
     * @param errorMessage Error message
     */
    public void notifyError(String errorMessage) {
        JOptionPane.showMessageDialog(mainWindow.getFrame(), errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Compute a tour.
     */
    public void computeTour() {
        if (deliveryOrderController.getDeliveryOrder() == null
                || roadMapController.getRoadMap() == null) {
            notifyError("Vous devez d'abord charger une carte et une demande de livraison.");
            return;
        }
        tourController.computeTour(
                roadMapController.getRoadMap(),
                deliveryOrderController.getDeliveryOrder()
        );
    }

    /**
     * Select a value from the list.
     * Notify the road map
     *
     * @param selectedValue Selected delivery
     */
    public void selectDeliveryFromList(Delivery selectedValue) {
        roadMapController.selectDeliveryFromList(selectedValue);
    }

    /**
     * Select a value from the road map.
     * Notify the list
     *
     * @param selectValue Selected delivery
     */
    public void selectDeliveryFromMap(Delivery selectValue) {
        deliveryOrderController.selectDeliveryFromMap(selectValue);
        tourEditorController.selectDeliveryFromMap(selectValue);
    }

    /**
     * Return the selected intersection in the road map controller.
     *
     * @return Selected intersection
     */
    public Intersection getSelectedIntersection() {
        return roadMapController.getSelectedIntersection();
    }

    /**
     * Select an intersection from the road map.
     *
     * @param selectedIntersection Selected intersection
     * @param roadMap              the roadMap
     */
    public void selectIntersectionFromMap(Intersection selectedIntersection, RoadMap roadMap) {
        currentIntersectionController.setData(selectedIntersection, roadMap);
    }

    /**
     * Select a delivery from the road map.
     *
     * @param delivery selected delivery.
     */
    public void setSelectedDelivery(Delivery delivery) {
        roadMapController.onDeliverySelected(delivery);
    }

    /**
     * Repaint all components.
     */
    public void repaintAll() {
        mainWindow.getFrame().repaint();
    }
}
