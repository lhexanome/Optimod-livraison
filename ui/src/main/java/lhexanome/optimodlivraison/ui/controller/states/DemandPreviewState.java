package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.FackUtile;
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

        //TODO replace next Line
        controller.tournee = FackUtile.fackTournee(controller.plan, controller.demand, 30);

        this.window.close();

        nextWindow.setPlan(controller.plan);
        nextWindow.setDemand(controller.demand);
        nextWindow.setTournee(controller.tournee);
        controller.setCurrentState(controller.orderEditorState);
        nextWindow.open();
    }
}
