package lhexanome.optimodlivraison.ui.netbeanpanel.welcomelistener;

import lhexanome.optimodlivraison.ui.netbeanpanel.IWelcomeController;
import lhexanome.optimodlivraison.ui.netbeanpanel.WelcomePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickChooseRoadMapListener implements ActionListener{


    private IWelcomeController controller;
    private WelcomePanel panel;

    public ClickChooseRoadMapListener(IWelcomeController controller, WelcomePanel panel){

        this.controller = controller;
        this.panel = panel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.clickChooseRoadMap(new ClickChooseRoadMapEvent(e,this));
    }

    public WelcomePanel getPanel() {
        return panel;
    }

    public IWelcomeController getController() {
        return controller;
    }
}
