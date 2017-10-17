package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.demandpreview.DemandPreviewWindow;
import lhexanome.optimodlivraison.ui.orderaction.OrderEditorWindow;
import lhexanome.optimodlivraison.ui.planpreview.PlanPreviewWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

import java.io.File;
//import lhexanome.optimodlivraison.ui.welcome.WelcomeController;

/**
 * Created by nathan on 17/10/17.
 */
public interface State {
    public void clickGoHome(WelcomeWindow nextWindow);
    public void selectDemand(DemandPreviewWindow nextWindow, File xmlLivraisonsFile);
    public void clickChooseDemand();
    public void clickCancelDemand();
    public void clickComputeTour(OrderEditorWindow nextWindow);
    public void selectPlan(PlanPreviewWindow nextWindow, File xmlPlanFile);
    public void clickChoosePlan();
    public void clickCancelPlan();
    public void closeWindows();

}
