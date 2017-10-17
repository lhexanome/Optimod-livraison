package lhexanome.optimodlivraison.ui.mappreview;


import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;

import javax.swing.*;

public class MapPreviewWindow extends Window{

    JFrame jFrame;
    MapPreviewView mapPreviewView;
    Controller controller;


    public MapPreviewWindow(Controller controller){
        super(controller);

        this.controller = controller;

        jFrame = new JFrame("testHelloMars");// TODO Rename frmaeName
        mapPreviewView = new MapPreviewView();

        jFrame.add(mapPreviewView.getMainPanel());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }

    public void open(){
        jFrame.setVisible(true);
    }

    public void close(){
        jFrame.setVisible(false);
    }

    public void setPlan(Plan plan) {
        mapPreviewView.setPlan(plan);
    }

}
