package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.controller.actions.WelcomeControllerInterface;
import lhexanome.optimodlivraison.ui.popup.FileChooserPopup;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

import java.awt.*;
import java.io.File;
import java.util.logging.Logger;

public class WelcomeController implements WelcomeControllerInterface {

    private static final Logger LOGGER = Logger.getLogger(WelcomeController.class.getName());

    private WelcomeWindow welcomeWindow;
    private MainController mainController;

    public WelcomeController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start() {
        welcomeWindow = new WelcomeWindow(this);
        welcomeWindow.open();
    }

    @Override
    public Container getContentPane() {
        return welcomeWindow.getFrame();
    }

    @Override
    public void clickChooseRoadMap() {
        LOGGER.info("Choosing road map");
        FileChooserPopup popup = new FileChooserPopup("Choisissez un plan", "xml");
        File file = popup.show();

        if (file != null) {
            LOGGER.info(String.format("User selected file : %s", file.getName()));
            // Close welcome windows, we don't need it anymore.
            closeWindow();
            // Start main controller and select a file
            mainController.start();
            mainController.selectRoadMap(file);
        } else {
            LOGGER.info("User did not provide a file, doing nothing");
        }
    }

    @Override
    public void closeWindow() {
        welcomeWindow.close();
    }
}
