package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.exceptions.DeliveryException;
import lhexanome.optimodlivraison.platform.facade.DeliveryFacade;
import lhexanome.optimodlivraison.platform.listeners.DeliveryListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.panel.DeliveryOrderPanel;
import lhexanome.optimodlivraison.ui.popup.FileChooserPopup;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

public class DeliveryOrderController implements ControllerInterface {

    private static final Logger LOGGER = Logger.getLogger(DeliveryOrderController.class.getName());


    private final MainController mainController;

    private final DeliveryOrderPanel deliveryOrderPanel;

    private DeliveryOrder deliveryOrder;

    public DeliveryOrderController(MainController mainController) {
        this.mainController = mainController;

        this.deliveryOrderPanel = new DeliveryOrderPanel(this);
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
        return deliveryOrderPanel.getContentPane();
    }

    private void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
        deliveryOrderPanel.setDeliveryOrder(deliveryOrder);
        mainController.setDeliveryOrder(deliveryOrder);
    }

    public void selectDeliveryOrder(File xmlFile, RoadMap roadMap) {

        DeliveryFacade deliveryFacade = new DeliveryFacade();
        deliveryFacade.addOnUpdateDeliveryListener(new DeliveryListener() {
            /**
             * Fonction appelée quand une demande de livraison est chargée.
             *
             * @param newDeliveryOrder Demande de livraison
             */
            @Override
            public void onUpdateDeliveryOrder(DeliveryOrder newDeliveryOrder) {
                setDeliveryOrder(newDeliveryOrder);
                //TODO deliveryOrderPanel.setLoad(false);
            }

            /**
             * Fonction appelée quand une demande n'arrive pas à être chargée.
             *
             * @param e Exception contenant la cause
             */
            @Override
            public void onFailUpdateDeliveryOrder(DeliveryException e) {
                //TODO deliveryOrderPanel.setLoad(false);

                LOGGER.warning(String.format("Error while updating delivery order :%s", e.getMessage()));
                mainController.notifyError(e.getMessage());
            }
        });

        // TODO deliveryOrderPanel.setLoad(true);
        deliveryFacade.loadDeliveryOrderFromFile(xmlFile, roadMap);
    }

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

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }
}
