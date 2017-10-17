package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.planshowdemand.PlanShowDemandWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

import java.io.File;

public class DefaultState implements State {
    protected Controller controller;
    protected Window window;

    public DefaultState(Controller controller, Window window) {
        this.window = window;
        this.controller = controller;
    }
    @Override
    public void clickGoHome(WelcomeWindow nextWindow) {

    }

    @Override
    public void selectDemand(PlanShowDemandWindow nextWindow, File xmlLivraisonsFile) {

    }

    @Override
    public void clickChooseDemand() {

    }

    @Override
    public void clickCancelDemand() {

    }

    @Override
    public void clickComputeTour() {

    }

    @Override
    public void selectPlan(PlanShowDemandWindow nextWindow, File xmlPlanFile) {

    }

    @Override
    public void clickChoosePlan() {

    }

    @Override
    public void clickCancelPlan() {

    }
}
