package lhexanome.optimodlivraison.ui.window;


import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.FileTypeFilter;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.view.PlanPreviewView;

import javax.swing.*;

public class PlanPreviewWindow extends Window{

    PlanPreviewView mapPreviewView;


    public PlanPreviewWindow(Controller controller){
        super(controller, "testHelloMars");// TODO Rename frmaeName

        mapPreviewView = new PlanPreviewView(controller);

        jFrame.add(mapPreviewView.getMainPanel());
        //jFrame.pack();
        jFrame.setSize(1080,720);
        jFrame.setLocationRelativeTo(null);
    }

    public void choosedFilePlan(){

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Ouvrir une demand");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileTypeFilter("xml"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //TODO LOG
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

            controller.selectDemand(chooser.getSelectedFile());

        } else {
            System.out.println("No Selection ");
            controller.clickCancelDemand();
        }
    }

    public void setPlan(Plan plan) {
        mapPreviewView.setPlan(plan);
    }

}
