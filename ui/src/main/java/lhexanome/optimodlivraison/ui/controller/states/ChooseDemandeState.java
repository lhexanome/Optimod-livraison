package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.FackUtile;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.DemandPreviewWindow;

import java.io.File;

public class ChooseDemandeState extends DefaultState{

    public ChooseDemandeState(Controller controller, Window window) {
        super("ChooseDemandeState", controller, window);
    }


    @Override
    public void clickCancelDemand() {
        controller.setCurrentState(controller.planPreviewState);
    }


    @Override
    public void selectDemand (DemandPreviewWindow nextWindow, File xmlDemandFile) {

        //TODO replace nextLine
        controller.demand = FackUtile.fackDemandeLivraison(controller.plan, 10);

        nextWindow.setPlan(controller.plan);
        nextWindow.setDemand(controller.demand);

        window.close();
        //TODO window.setLoad(false);
        nextWindow.open();
        controller.setCurrentState(controller.demandPreviewState);

    }
}
