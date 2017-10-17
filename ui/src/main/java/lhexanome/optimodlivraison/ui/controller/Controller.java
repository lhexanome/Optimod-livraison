package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.parsing.common.LoadFile;
import lhexanome.optimodlivraison.platform.parsing.map.MapParser;
import lhexanome.optimodlivraison.ui.planpreview.PlanPreviewActions;
import lhexanome.optimodlivraison.ui.planpreview.PlanPreviewWindow;
import lhexanome.optimodlivraison.ui.planshowdemand.PlanShowDemandWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeActions;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;
import org.jdom2.Element;

import java.io.File;

/**
 * Created by nathan on 17/10/17.
 */
public class Controller implements WelcomeActions, PlanPreviewActions {

    private WelcomeWindow welcomeWindow;
    private PlanPreviewWindow planPreviewWindow;
    private PlanShowDemandWindow planShowDemandWindow;
    private State currentState;



    public Controller() {

        welcomeWindow = new WelcomeWindow(this);
        welcomeWindow.open();

    }

    public void setCurrentState(State s){
        this.currentState = s;
    }
    @Override
    public void selectPlan(File xmlPlanFile) {
        Element elementPlan = null;
        Plan plan = null;
        try {
            MapParser mapParser = new MapParser();
            elementPlan = LoadFile.loadFromFile(xmlPlanFile);
            plan = mapParser.parseMap(elementPlan);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }


        planPreviewWindow = new PlanPreviewWindow(this);
        welcomeWindow.close();
        planPreviewWindow.open();
    }

    @Override
    public void clickChoosePlan() {
        welcomeWindow.choosedFilePlan();
    }

    @Override
    public void clickCancelPlan() {

    }

    @Override
    public void clickGoHome() {

    }

    @Override
    public void selectDemand(File xmlDemandFile) {

    }

    @Override
    public void clickChooseDemand() {

    }

    @Override
    public void clickCancelDemand() {

    }
}
