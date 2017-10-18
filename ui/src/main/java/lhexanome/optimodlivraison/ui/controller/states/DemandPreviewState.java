package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.demandpreview.DemandPreviewWindow;
import lhexanome.optimodlivraison.ui.orderaction.OrderEditorWindow;

public class DemandPreviewState extends DefaultState {
    public DemandPreviewState(Controller controller, DemandPreviewWindow window) {
        super("DemandPreviewState", controller, window);
    }

    @Override
    public void clickComputeTour(OrderEditorWindow nextWindow) {//TODO

        nextWindow.open();
        nextWindow.setPlan(controller.plan);
        nextWindow.setDemand(controller.demand);
        nextWindow.setTournee(controller.tournee);
        this.window.close();
        controller.setCurrentState(controller.orderEditorState);
    }
}
