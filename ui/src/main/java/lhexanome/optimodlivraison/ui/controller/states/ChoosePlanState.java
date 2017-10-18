package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.facade.MapFacade;
import lhexanome.optimodlivraison.platform.listeners.MapListener;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.parsing.common.LoadFile;
import lhexanome.optimodlivraison.platform.parsing.map.MapParser;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.demandpreview.DemandPreviewWindow;
import lhexanome.optimodlivraison.ui.planpreview.PlanPreviewWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;
import org.jdom2.Element;

import java.io.File;

public class ChoosePlanState extends DefaultState {
    public ChoosePlanState(Controller controller, WelcomeWindow window) {
        super("ChoosePlanState", controller, window);
    }

    @Override
    public void clickCancelPlan() {
        controller.setCurrentState(controller.welcomeState);
    }

    @Override
    public void selectPlan(PlanPreviewWindow nextWindow, File xmlPlanFile) {

        MapFacade mapFacade = new MapFacade();
        mapFacade.addOnUpdateMapListener(new MapListener() {
            @Override
            public void onUpdateMap(Plan plan) {

                controller.plan = plan;
                nextWindow.setPlan(plan);

                window.close();
                //TODO window.setLoad(false);
                nextWindow.open();
                controller.setCurrentState(controller.planPreviewState);
            }

            @Override
            public void onFailUpdateMap(MapException e) {

                //TODO window.setLoad(false);
                //TODO window.sendError(e.getMessage());

                //TODO Log
                System.err.println(e.getMessage());

                controller.setCurrentState(controller.planPreviewState);

            }
        });

        // TODO window.setLoad(true);
        mapFacade.loadMapFromFile(xmlPlanFile);

    }
}
