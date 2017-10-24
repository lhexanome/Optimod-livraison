package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.platform.exceptions.DeliveryException;
import lhexanome.optimodlivraison.platform.facade.DeliveryFacade;
import lhexanome.optimodlivraison.platform.listeners.DeliveryListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.DeliveryOrderPreviewWindow;

import java.io.File;

public class ChooseDeliveryOrderState extends DefaultState {

    public ChooseDeliveryOrderState(Controller controller, Window window) {
        super("ChooseDeliveryOrderState", controller, window);
    }


    @Override
    public void clickCancelDeliveryOrder() {
        controller.setCurrentState(controller.roadMapPreviewState);
    }


    @Override
    public void selectDeliveryOrder(DeliveryOrderPreviewWindow nextWindow, File xmlDeliveryOrderFile) {

        DeliveryFacade deliveryFacade = new DeliveryFacade();

        deliveryFacade.addOnUpdateDeliveryListener(new DeliveryListener() {
            @Override
            public void onUpdateDeliveryOrder(DeliveryOrder deliveryOrder) {

                deliveryOrder.getDeliveries().forEach(delivery -> {
                    delivery.setIntersection(
                            controller.roadMap.findIntersectionById(delivery.getIntersection().getId()));
                });

                deliveryOrder.getBeginning().setIntersection(controller.roadMap
                        .findIntersectionById(deliveryOrder.getBeginning().getIntersection().getId()));

                controller.deliveryOrder = deliveryOrder;
                nextWindow.setDeliveryOrder(deliveryOrder);
                nextWindow.setRoadMap(controller.roadMap);

                window.close();
                //TODO window.setLoad(false);
                nextWindow.open();
                controller.setCurrentState(controller.deliveryOrderPreviewState);
            }

            @Override
            public void onFailUpdateDeliveryOrder(DeliveryException e) {
                //TODO window.setLoad(false);
                //TODO window.sendError(e.getMessage());

                //TODO Log
                System.err.println(e.getMessage());

                controller.setCurrentState(controller.roadMapPreviewState);
            }
        });

        // TODO window.setLoad(true);
        deliveryFacade.loadDeliveryOrderFromFile(xmlDeliveryOrderFile);

    }
}
