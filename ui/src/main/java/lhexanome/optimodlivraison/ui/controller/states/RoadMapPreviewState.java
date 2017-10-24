package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.RoadMapPreviewWindow;

public class RoadMapPreviewState extends DefaultState {

    public RoadMapPreviewState(Controller controller, RoadMapPreviewWindow window) {
        super("RoadMapPreviewState", controller, window);
    }

    @Override
    public void clickChooseDeliveryOrder() {
        controller.setCurrentState(controller.chooseDeliveryOrderState);
        ((RoadMapPreviewWindow) window).chooseRoadMapFile();
    }
}
