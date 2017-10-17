package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.planpreview.PlanPreviewWindow;

public class PlanPreviewState extends DefaultState{

    public PlanPreviewState(Controller controller, PlanPreviewWindow window) {
        super("PlanPreviewState", controller, window);
    }

    @Override
    public void clickChooseDemand() {
        controller.setCurrentState(controller.chooseDemandeState);
        ((PlanPreviewWindow)window).choosedFilePlan();
    }
}
