package lhexanome.optimodlivraison.ui.welcome;

import javax.swing.*;
import java.io.File;

public class WelcomeWindow {

    private JFrame jFrame;
    private WelcomeView welcomeView;
    private WelcomeActions actions;


    public WelcomeWindow(WelcomeActions actions){
        this.actions = actions;
        jFrame = new JFrame("testHelloMars");
        welcomeView = new WelcomeView(this);
        jFrame.add(welcomeView.getMainPanel());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }

    public void open(){
        jFrame.setVisible(true);
    }

    public void close(){
        jFrame.setVisible(false);
    }

    public void clickChoosedFilePlan(){
        actions.clickChosePlan();
    }

    public void choosedFilePlan(){
        welcomeView.choosedFilePlan();
    }


    public void setFilePlan(File f) {

        actions.selectPlan(f);
    }
}
