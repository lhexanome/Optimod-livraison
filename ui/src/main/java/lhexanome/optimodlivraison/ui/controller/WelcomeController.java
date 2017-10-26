package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.popup.FileChooserPopup;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

import java.awt.*;
import java.io.File;
import java.util.logging.Logger;

/**
 * Welcome controller.
 */
public class WelcomeController implements ControllerInterface {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(WelcomeController.class.getName());

    /**
     * Welcome window.
     */
    private WelcomeWindow welcomeWindow;

    /**
     * Main controller (parent).
     */
    private MainController mainController;

    /**
     * Constructor.
     *
     * @param mainController Main controller
     */
    public WelcomeController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * {@link ControllerInterface#start()}.
     */
    @Override
    public void start() {
        welcomeWindow = new WelcomeWindow(this);
        welcomeWindow.open();
    }

    /**
     * {@link ControllerInterface#getContentPane()}.
     */
    @Override
    public Container getContentPane() {
        return welcomeWindow.getFrame();
    }

    /**
     * Called when an user want to choose a map file.
     */
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

    /**
     * {@link ControllerInterface#closeWindow()}.
     */
    @Override
    public void closeWindow() {
        welcomeWindow.close();
    }
}
