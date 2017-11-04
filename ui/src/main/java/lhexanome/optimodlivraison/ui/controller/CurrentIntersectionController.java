package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.panel.CurrentIntersectionPanel;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Delivery order controller.
 */
public class CurrentIntersectionController implements ControllerInterface {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DeliveryOrderController.class.getName());

    /**
     * Main controller (parent).
     */
    private final MainController mainController;

    /**
     * Intersection info panel.
     */
    private final CurrentIntersectionPanel currentIntersectionPanel;

    /**
     * Constructor.
     *
     * @param mainController Main controller
     */
    public CurrentIntersectionController(MainController mainController) {
        this.mainController = mainController;

        this.currentIntersectionPanel = new CurrentIntersectionPanel(this);
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
        return currentIntersectionPanel.getContentPane();
    }

    /**
     * intersection to display setter.
     *
     * @param intersectionToDisplay intersection to display
     * @param roadMap               the roadMap
     */
    public void setData(Intersection intersectionToDisplay, RoadMap roadMap) {
        currentIntersectionPanel.setData(intersectionToDisplay, roadMap);
    }

    /**
     * Call by the view.
     * Notify the main controller of a selection.
     *
     * @param selectedValue Selected delivery
     */
    public void selectDeliveryFromList(Delivery selectedValue) {
        mainController.selectDeliveryFromList(selectedValue);
    }

    /**
     * Hide the panel.
     */
    public void hide() {
        currentIntersectionPanel.getContentPane().setVisible(false);
    }

    /**
     * Show the panel.
     */
    public void show() {
        currentIntersectionPanel.getContentPane().setVisible(true);
    }
}
