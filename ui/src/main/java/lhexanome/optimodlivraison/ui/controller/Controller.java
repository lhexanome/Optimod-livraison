package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.controller.states.*;
import lhexanome.optimodlivraison.ui.window.OrderEditorWindow;
import lhexanome.optimodlivraison.ui.window.RoadMapPreviewActions;
import lhexanome.optimodlivraison.ui.window.RoadMapPreviewWindow;
import lhexanome.optimodlivraison.ui.window.DeliveryOrderPreviewActions;
import lhexanome.optimodlivraison.ui.window.DeliveryOrderPreviewWindow;
import lhexanome.optimodlivraison.ui.window.WelcomeActions;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

import java.io.File;

/**
 * Created by nathan on 17/10/17.
 */
public class Controller implements WelcomeActions, RoadMapPreviewActions, DeliveryOrderPreviewActions {

    private WelcomeWindow welcomeWindow;
    private RoadMapPreviewWindow roadMapPreviewWindow;
    private DeliveryOrderPreviewWindow deliveryOrderPreviewWindow;
    private OrderEditorWindow orderEditorWindow;
    
    private State currentState;


    public WelcomeState welcomeState;
    public ChooseRoadMapState chooseRoadMapState;

    public RoadMapPreviewState roadMapPreviewState;
    public ChooseDeliveryOrderState chooseDeliveryOrderState;

    public DeliveryOrderPreviewState deliveryOrderPreviewState;

    public OrderEditorState orderEditorState;

    public RoadMap roadMap;
    public DeliveryOrder deliveryOrder;
    public Tour tour;


    public Controller() {

        welcomeWindow = new WelcomeWindow(this);
        roadMapPreviewWindow = new RoadMapPreviewWindow(this);
        deliveryOrderPreviewWindow = new DeliveryOrderPreviewWindow(this);
        orderEditorWindow = new OrderEditorWindow(this);

        // WELCOME WINDOWS STATES
        welcomeState = new WelcomeState(this, welcomeWindow);
        chooseRoadMapState = new ChooseRoadMapState(this, welcomeWindow);

        // PLAN_PREVIEW WINDOWS STATES
        roadMapPreviewState = new RoadMapPreviewState(this, roadMapPreviewWindow);
        chooseDeliveryOrderState = new ChooseDeliveryOrderState(this, roadMapPreviewWindow);

        // DEMAND_PREVIEW WINDOWS STATES
        deliveryOrderPreviewState = new DeliveryOrderPreviewState(this, deliveryOrderPreviewWindow);

        // ORDER_EDITOR WINDOWS STATES
        orderEditorState = new OrderEditorState(this, orderEditorWindow);


        currentState = welcomeState;
        welcomeWindow.open();
    }

    public void setCurrentState(State state){
        this.currentState = state;
    }


    @Override
    public void selectRoadMap(File xmlRoadMapFile) {
        currentState.selectRoadMap(roadMapPreviewWindow, xmlRoadMapFile);
    }

    @Override
    public void clickChooseRoadMap() {
        currentState.clickChooseRoadMap();
    }

    @Override
    public void clickCancelRoadMap() {
        currentState.clickCancelRoadMap();
    }

    @Override
    public void clickGoHome() {
        currentState.clickGoHome(welcomeWindow);
    }

    @Override
    public void clickComputeTour() {
        currentState.clickComputeTour(orderEditorWindow);
    }

    @Override
    public void selectDeliveryOrder(File xmlDeliveryOrderFile) {
        currentState.selectDeliveryOrder(deliveryOrderPreviewWindow, xmlDeliveryOrderFile);
    }

    @Override
    public void clickChooseDeliveryOrder() {
        currentState.clickChooseDeliveryOrder();
    }

    @Override
    public void clickCancelDeliveryOrder() {
        currentState.clickChooseDeliveryOrder();
    }

    public void closeWindow() {
        currentState.closeWindows();
    }
}
