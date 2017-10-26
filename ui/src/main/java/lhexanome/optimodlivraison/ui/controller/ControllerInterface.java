package lhexanome.optimodlivraison.ui.controller;

import java.awt.*;

/**
 * Controller interface, implemented by all controllers.
 * Provide some base functions.
 */
public interface ControllerInterface {

    /**
     * Close the window related to the controller.
     */
    void closeWindow();

    /**
     * Start the window (open) related to the controller.
     */
    void start();

    /**
     * Used to get the content pane of a controller.
     *
     * @return Content pane
     */
    Container getContentPane();
}
