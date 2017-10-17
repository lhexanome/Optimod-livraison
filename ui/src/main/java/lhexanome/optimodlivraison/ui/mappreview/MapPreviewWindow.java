package lhexanome.optimodlivraison.ui.mappreview;


import lhexanome.optimodlivraison.platform.models.Plan;

import javax.swing.*;

public class MapPreviewWindow {

    Plan plan;
    JFrame jFrame;
    MapPreviewView welcomeView;
    MapPreviewActions controller;


    public MapPreviewWindow(Plan plan, MapPreviewActions controller){
        this.controller = controller;
        this.plan = plan;

        // TODO Rename frmaeName
        jFrame = new JFrame("testHelloMars");

        // TODO Replace MapPreviewView(fackPlanData()) by MapPreviewView(plan)
        welcomeView = new MapPreviewView(FackUtile.fackPlanDataMoyen());

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
