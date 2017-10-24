package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.FileTypeFilter;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.view.WelcomeView;

import javax.swing.*;

public class WelcomeWindow extends Window {


    private WelcomeView welcomeView;

    public WelcomeWindow(Controller controller){
        super(controller, "Welcome window");

        welcomeView = new WelcomeView(controller);
        jFrame.add(welcomeView.getMainPanel());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }

    public void chooseFileRoadMap(){

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Ouvrir un roadMap");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileTypeFilter("xml"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //TODO LOG
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

            controller.selectRoadMap(chooser.getSelectedFile());

        } else {
            System.out.println("No Selection ");
            controller.clickCancelRoadMap();
        }
    }


}
