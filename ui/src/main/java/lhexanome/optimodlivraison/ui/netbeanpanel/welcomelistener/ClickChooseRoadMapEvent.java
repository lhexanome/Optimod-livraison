package lhexanome.optimodlivraison.ui.netbeanpanel.welcomelistener;

import lhexanome.optimodlivraison.ui.netbeanpanel.IWelcomeController;
import lhexanome.optimodlivraison.ui.netbeanpanel.WelcomePanel;

import java.awt.event.ActionEvent;

public class ClickChooseRoadMapEvent {
    private ActionEvent originalEvent;
    private ClickChooseRoadMapListener listener;

    public ClickChooseRoadMapEvent(ActionEvent originalEvent, ClickChooseRoadMapListener clickChooseRoadMapListener) {

        this.originalEvent = originalEvent;
        this.listener = clickChooseRoadMapListener;
    }

    public ActionEvent getOriginalEvent() {
        return originalEvent;
    }

    public ClickChooseRoadMapListener getListener() {
        return listener;
    }

    public WelcomePanel getPanel(){
        return listener.getPanel();
    }

    public IWelcomeController getController(){
        return listener.getController();
    }

}
