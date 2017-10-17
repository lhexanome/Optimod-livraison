package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.mappreview.MapPreviewActions;
import lhexanome.optimodlivraison.ui.mappreview.MapPreviewView;
import lhexanome.optimodlivraison.ui.mappreview.MapPreviewWindow;
import lhexanome.optimodlivraison.ui.welcome.WelcomeActions;
import lhexanome.optimodlivraison.ui.welcome.WelcomeView;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

import java.io.File;

/**
 * Created by nathan on 17/10/17.
 */
public class Controller implements WelcomeActions, MapPreviewActions{

    WelcomeWindow welcomeWindow;
    MapPreviewWindow mapPreviewWindow;

    public Controller() {

        welcomeWindow = new WelcomeWindow(this);
        welcomeWindow.open();

    }
    public State currentState;

    @Override
    public void selectPlan(File xmlMapFile) {

        mapPreviewWindow = new MapPreviewWindow(null, this);
        welcomeWindow.close();
        mapPreviewWindow.open();
    }

    @Override
    public void clickChosePlan() {
        welcomeWindow.choosedFilePlan();
    }

    @Override
    public void clickCancelPlan() {

    }

    @Override
    public void clickGoHome() {

    }

    @Override
    public void selectLivraisons(File xmlLivraisonsFile) {

    }

    @Override
    public void clickChoseLivraisons() {

    }

    @Override
    public void clickCancelLivraisons() {

    }
}
