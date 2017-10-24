package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.window.DeliveryOrderPreviewWindow;
import lhexanome.optimodlivraison.ui.window.OrderEditorWindow;
import lhexanome.optimodlivraison.ui.window.RoadMapPreviewWindow;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

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
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void selectDeliveryOrder(DeliveryOrderPreviewWindow nextWindow, File xmlDeliveryOrderFile) {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void clickChooseDeliveryOrder() {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void clickCancelDeliveryOrder() {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void clickComputeTour(OrderEditorWindow nextWindow) {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void selectRoadMap(RoadMapPreviewWindow nextWindow, File xmlRoadMapFile) {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void clickChooseRoadMap() {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void clickCancelRoadMap() {
        throw new RuntimeException("INVALID EVENT FOR STATE : " + stateName);
    }

    @Override
    public void closeWindows() {
        System.exit(0);
    }

}
