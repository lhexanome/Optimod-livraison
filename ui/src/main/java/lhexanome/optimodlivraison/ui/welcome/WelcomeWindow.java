package lhexanome.optimodlivraison.ui.welcome;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;

import java.io.File;

public class WelcomeWindow extends Window {


    private WelcomeView welcomeView;

    public WelcomeWindow(Controller c){
        super(c);
        welcomeView = new WelcomeView(this);
        jFrame.add(welcomeView.getMainPanel());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }


    public void clickChoosedFilePlan(){
        controller.clickChoosePlan();
    }

    public void choosedFilePlan(){
        welcomeView.choosedFilePlan();
    }


    public void setFilePlan(File f) {

        controller.selectPlan(f);
    }
}
