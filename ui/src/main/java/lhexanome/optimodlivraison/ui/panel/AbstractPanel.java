package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.ui.controller.ControllerInterface;

import javax.swing.*;

/**
 * This panel force Panel to have a controller.
 * Also add a setup function ti initialize swing component listeners, etc...
 */
public abstract class AbstractPanel {

    /**
     * Controller linked to this panel.
     */
    protected final ControllerInterface controller;

    /**
     * Initialize a panel with a panel.
     *
     * @param controller Controller linked to the panel
     */
    AbstractPanel(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * Initialization function.
     * WARNING: This function is not called by the constructor.
     * You have to call it after calling super() !
     * Explications : Intellij GUI designer use an initializer block to initialize components,
     * But the initializer block is called after the super() method.
     */
    public abstract void setup();

    /**
     * Return the content panel.
     * The field is created by Intellij and must be declared in the child panel
     *
     * @return Content pane
     */
    public abstract JPanel getContentPane();
}
