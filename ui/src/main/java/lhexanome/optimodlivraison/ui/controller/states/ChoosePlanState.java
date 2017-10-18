package lhexanome.optimodlivraison.ui.controller.states;

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
        /*Element elementPlan = null;
        try {
            MapParser mapParser = new MapParser();
            elementPlan = LoadFile.loadFromFile(xmlPlanFile);
            controller.plan = mapParser.parseMap(elementPlan);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
*/
        this.window.close();

        nextWindow.setPlan(controller.plan);
        nextWindow.open();
        controller.setCurrentState(controller.planPreviewState);
    }
}
