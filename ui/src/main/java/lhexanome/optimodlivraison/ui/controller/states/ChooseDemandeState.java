package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.demandpreview.DemandPreviewWindow;
import org.jdom2.Element;

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
        Element elementDemand = null;
        try {
            /* TODO
            DemandParser demandParser = new DemandParser();
            elementDemand = LoadFile.loadFromFile(xmlDemandFile);
            controller.demandPreviewState = demandParser.parseDemand(elementDemand);
            */
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }

        this.window.close();

        nextWindow.setPlan(controller.plan);
        nextWindow.setDemand(controller.demand);
        nextWindow.open();
        controller.setCurrentState(controller.demandPreviewState);
    }
}
