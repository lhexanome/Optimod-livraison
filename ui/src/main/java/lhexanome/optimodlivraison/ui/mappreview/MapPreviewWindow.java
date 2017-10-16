package lhexanome.optimodlivraison.ui.mappreview;


import lhexanome.optimodlivraison.platform.models.Plan;

import javax.swing.*;

public class MapPreviewWindow {

    Plan plan;
    JFrame jFrame;
    MapPreviewView welcomeView;
    MapPreviewController controller;


    public MapPreviewWindow(Plan plan, MapPreviewController controller){
        this.controller = controller;
        this.plan = plan;

        jFrame = new JFrame("testHelloMars");
        welcomeView = new MapPreviewView();
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

}
