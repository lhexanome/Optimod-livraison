package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.platform.exceptions.ComputeException;
import lhexanome.optimodlivraison.platform.facade.ComputeFacade;
import lhexanome.optimodlivraison.platform.listeners.ComputeListener;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.DeliveryOrderPreviewWindow;
import lhexanome.optimodlivraison.ui.window.OrderEditorWindow;

public class DeliveryOrderPreviewState extends DefaultState {
    public DeliveryOrderPreviewState(Controller controller, DeliveryOrderPreviewWindow window) {
        super("DeliveryOrderPreviewState", controller, window);
    }

    @Override
    public void clickComputeTour(OrderEditorWindow nextWindow) {//TODO

        ComputeFacade computeFacade = new ComputeFacade();
        computeFacade.addOnComputeListener(new ComputeListener() {
            @Override
            public void onComputingTour(Tour tour) {
                controller.tour = tour;

                window.close();
                nextWindow.setRoadMap(controller.roadMap);
                nextWindow.setDeliveryOrder(controller.deliveryOrder);
                nextWindow.setTour(controller.tour);
                controller.setCurrentState(controller.orderEditorState);
                nextWindow.open();
            }

            @Override
            public void onFailCompute(ComputeException e) {

                //TODO window.setLoad(false);
                //TODO window.sendError(e.getMessage());

                //TODO Log
                System.err.println(e.getMessage());

                controller.setCurrentState(controller.deliveryOrderPreviewState);
            }
        });

        computeFacade.computeTour(controller.roadMap, controller.deliveryOrder);

    }
}
