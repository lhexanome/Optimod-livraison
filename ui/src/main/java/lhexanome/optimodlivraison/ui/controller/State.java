package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.window.DemandPreviewWindow;
import lhexanome.optimodlivraison.ui.window.OrderEditorWindow;
import lhexanome.optimodlivraison.ui.window.PlanPreviewWindow;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

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
