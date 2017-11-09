package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.ui.controller.MainController;
import lhexanome.optimodlivraison.ui.controller.WelcomeController;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * IHM Entry point.
 * From: https://www.jetbrains.com/help/idea/getting-started-with-gradle.html
 */
public final class HelloMars {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(HelloMars.class.getName());

    /**
     * Disallow instantiation.
     */
    private HelloMars() {
    }

    /**
     * Main method.
     *
     * @param args System args
     */
    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());

        } catch (UnsupportedLookAndFeelException
                | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.severe("Unable to set UI Look & Feels :" + e.getMessage());
        }

        MainController mainController = new MainController();
        WelcomeController welcomeController = new WelcomeController(mainController);

        welcomeController.start();
    }
}
