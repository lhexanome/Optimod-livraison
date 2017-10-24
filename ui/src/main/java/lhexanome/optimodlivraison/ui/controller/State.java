package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.window.DeliveryOrderPreviewWindow;
import lhexanome.optimodlivraison.ui.window.OrderEditorWindow;
import lhexanome.optimodlivraison.ui.window.RoadMapPreviewWindow;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

import java.io.File;

/**
 * Created by nathan on 17/10/17.
 */
public interface State {
    void clickGoHome(WelcomeWindow nextWindow);
    void selectDeliveryOrder(DeliveryOrderPreviewWindow nextWindow, File xmlDeliveryOrderFile);
    void clickChooseDeliveryOrder();
    void clickCancelDeliveryOrder();
    void clickComputeTour(OrderEditorWindow nextWindow);
    void selectRoadMap(RoadMapPreviewWindow nextWindow, File xmlRoadMapFile);
    void clickChooseRoadMap();
    void clickCancelRoadMap();
    void closeWindows();

}
