package lhexanome.optimodlivraison.ui.welcome;

import javax.swing.*;
import java.io.File;

public class WelcomeWindow {

    JFrame jFrame;
    WelcomeView welcomeView;
    WelcomeController controller;


    public WelcomeWindow(WelcomeController controller){
        this.controller = controller;
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
        welcomeView.choosedFilePlan();
    }


    public void setFilePlan(File f) {
        controller.selectMap(f);
    }
}
