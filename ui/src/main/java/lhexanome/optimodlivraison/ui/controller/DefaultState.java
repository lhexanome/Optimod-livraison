package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.demandpreview.DemandPreviewWindow;
import lhexanome.optimodlivraison.ui.orderaction.OrderEditorWindow;
import lhexanome.optimodlivraison.ui.planpreview.PlanPreviewWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;

public class DefaultState implements State {

    private final String stateName;
    protected Controller controller;
    protected Window window;

    public DefaultState(String stateName, Controller controller, Window window) {
        this.stateName = stateName;
        this.window = window;
        this.controller = controller;
    }
    @Override
    public void clickGoHome(WelcomeWindow nextWindow) {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void selectDemand(DemandPreviewWindow nextWindow, File xmlLivraisonsFile) {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void clickChooseDemand() {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void clickCancelDemand() {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void clickComputeTour(OrderEditorWindow nextWindow) {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void selectPlan(PlanPreviewWindow nextWindow, File xmlPlanFile) {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void clickChoosePlan() {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void clickCancelPlan() {
        throw new RuntimeException("INVALID EVENT FOR STATE : "+stateName);
    }

    @Override
    public void closeWindows() {
        System.exit(0);
    }

}
