package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.controller.actions.MainControllerInterface;
import lhexanome.optimodlivraison.ui.window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainController implements MainControllerInterface {

    private final MainWindow mainWindow;

    private RoadMapController roadMapController;
    private DeliveryOrderController deliveryOrderController;
    private TourController tourController;


    public MainController() {
        roadMapController = new RoadMapController(this);
        deliveryOrderController = new DeliveryOrderController(this);
        tourController = new TourController(this);

        mainWindow = new MainWindow(
                this,
                roadMapController.getContentPane()
        );
    }

    @Override
    public void start() {
        mainWindow.open();
    }

    @Override
    public Container getContentPane() {
        return mainWindow.getFrame();
    }

    @Override
    public void closeWindow() {
        mainWindow.close();
    }

    @Override
    public void selectRoadMap(File xmlFile) {
        roadMapController.selectRoadMap(xmlFile);
    }

    @Override
    public void setRoadMap(RoadMap roadMap) {

    }

    @Override
    public void setTour(Tour tour) {

    }

    @Override
    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {

    }

    @Override
    public void notifyError(String errorMessage) {
        JOptionPane.showMessageDialog(mainWindow.getFrame(), errorMessage);
    }
}
