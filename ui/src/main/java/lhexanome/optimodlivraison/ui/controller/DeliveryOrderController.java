package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.command.ParseDeliveryOrderCommand;
import lhexanome.optimodlivraison.platform.listeners.ParseDeliveryOrderListener;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.panel.DeliveryOrderPanel;
import lhexanome.optimodlivraison.ui.popup.FileChooserPopup;

import javax.swing.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Delivery order controller.
 */
public class DeliveryOrderController implements ControllerInterface {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DeliveryOrderController.class.getName());

    /**
     * Main controller (parent).
     */
    private final MainController mainController;

    /**
     * Delivery order panel.
     */
    private final DeliveryOrderPanel deliveryOrderPanel;

    /**
     * Current delivery order.
     */
    private DeliveryOrder deliveryOrder;

    /**
     * Constructor.
     *
     * @param mainController Main controller
     */
    public DeliveryOrderController(MainController mainController) {
        this.mainController = mainController;

        this.deliveryOrderPanel = new DeliveryOrderPanel(this);
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
        return deliveryOrderPanel.getContentPane();
    }

    /**
     * Delivery order setter.
     * Called at the end of delivery order loading
     *
     * @param newDeliveryOrder new Delivery order
     * @param newRoadMap       new Road map
     */
    private void setData(DeliveryOrder newDeliveryOrder, RoadMap newRoadMap) {
        this.deliveryOrder = newDeliveryOrder;
        deliveryOrderPanel.setData(newDeliveryOrder, newRoadMap);
        mainController.setDeliveryOrder(newDeliveryOrder);
    }

    /**
     * Load a delivery order.
     * Called by the main controller.
     *
     * @param xmlFile XML file
     * @param roadMap Road map
     */
    public void selectDeliveryOrder(File xmlFile, RoadMap roadMap) {
        ParseDeliveryOrderCommand command = new ParseDeliveryOrderCommand(xmlFile, roadMap);

        command.setListener(new ParseDeliveryOrderListener() {
            @Override
            public void onDeliveryOrderParsed(DeliveryOrder deliveryOrderParsed) {
                setData(deliveryOrderParsed, roadMap);
            }

            @Override
            public void onDeliveryOrderParsingFail(Exception e) {
                LOGGER.log(Level.WARNING, "Error while updating delivery order ", e);
                mainController.notifyError(e.getMessage());
            }
        });
        command.execute();
    }

    /**
     * Ask for a new file.
     * Called by the panel
     */
    public void reloadDeliveryOrder() {
        LOGGER.info("Reloading delivery order");
        FileChooserPopup popup = new FileChooserPopup("Choisissez une demande de livraison", "xml");
        File file = popup.show();

        if (file != null) {
            LOGGER.info(String.format("User selected file : %s", file.getName()));

            // Send back to main controller to update other controller if needed
            mainController.selectDeliveryOrder(file);
        } else {
            LOGGER.info("User did not provide a file, doing nothing");
        }
    }

    /**
     * Delivery order getter.
     *
     * @return Current delivery order
     */
    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
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
     * Called by the main controller.
     * Notify the view of a selection
     *
     * @param selectValue Selected delivery
     */
    public void selectDeliveryFromMap(Delivery selectValue) {
        deliveryOrderPanel.selectDeliveryFromMap(selectValue);
    }

    /**
     * Clear current delivery order.
     */
    public void clearDeliveryOrder() {
        deliveryOrder = null;
        deliveryOrderPanel.setData(null, null);
    }

    /**
     * Hide the panel.
     */
    public void hide() {
        deliveryOrderPanel.getContentPane().setVisible(false);
    }

    /**
     * Show the panel.
     */
    public void show() {
        deliveryOrderPanel.getContentPane().setVisible(true);
    }
}
