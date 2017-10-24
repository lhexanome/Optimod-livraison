package lhexanome.optimodlivraison.ui.window;


import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.FileTypeFilter;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.view.RoadMapPreviewView;

import javax.swing.*;

public class RoadMapPreviewWindow extends Window{

    RoadMapPreviewView mapPreviewView;


    public RoadMapPreviewWindow(Controller controller){
        super(controller, "Map preview");// TODO Rename frmaeName

        mapPreviewView = new RoadMapPreviewView(controller);

        jFrame.add(mapPreviewView.getMainPanel());
        //jFrame.pack();
        jFrame.setSize(1080,720);
        jFrame.setLocationRelativeTo(null);
    }

    public void chooseRoadMapFile(){

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Ouvrir une deliveryOrder");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileTypeFilter("xml"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //TODO LOG
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

            controller.selectDeliveryOrder(chooser.getSelectedFile());

        } else {
            System.out.println("No Selection ");
            controller.clickCancelDeliveryOrder();
        }
    }

    public void setRoadMap(RoadMap roadMap) {
        mapPreviewView.setRoadMap(roadMap);
    }

}
