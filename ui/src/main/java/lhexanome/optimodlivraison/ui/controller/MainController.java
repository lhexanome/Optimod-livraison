package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainController implements ControllerInterface {

    private final MainWindow mainWindow;

    private final RoadMapController roadMapController;
    private final DeliveryOrderController deliveryOrderController;
    private final TourController tourController;


    public MainController() {
        roadMapController = new RoadMapController(this);
        deliveryOrderController = new DeliveryOrderController(this);
        tourController = new TourController(this);

        mainWindow = new MainWindow(
                this,
                roadMapController.getContentPane(),
                deliveryOrderController.getContentPane()
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

    public void selectRoadMap(File xmlFile) {
        roadMapController.selectRoadMap(xmlFile);
    }

    public void selectDeliveryOrder(File xmlFile) {
        deliveryOrderController.selectDeliveryOrder(xmlFile, roadMapController.getRoadMap());
    }

    public void setRoadMap(RoadMap roadMap) {
        // TODO Clear delivery and tour data
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        roadMapController.setDeliveryOrder(deliveryOrder);
    }

    public void setTour(Tour tour) {

    }

    public void notifyError(String errorMessage) {
        JOptionPane.showMessageDialog(mainWindow.getFrame(), errorMessage);
    }


}
