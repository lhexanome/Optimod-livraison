package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.planshowdemand.PlanShowDemandWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

import java.io.File;
//import lhexanome.optimodlivraison.ui.welcome.WelcomeController;

/**
 * Created by nathan on 17/10/17.
 */
public interface State {
    public void clickGoHome(WelcomeWindow nextWindow);
    public void selectDemand(PlanShowDemandWindow nextWindow, File xmlLivraisonsFile);
    public void clickChooseDemand();
    public void clickCancelDemand();
    public void clickComputeTour();
    public void selectPlan(PlanShowDemandWindow nextWindow, File xmlPlanFile);
    public void clickChoosePlan();
    public void clickCancelPlan();

}
