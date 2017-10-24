package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.ui.controller.actions.ControllerInterface;

import java.awt.*;

public abstract class AbstractPanel {

    protected final ControllerInterface controller;

    AbstractPanel(ControllerInterface controller) {
        this.controller = controller;
    }

    /**
     * WARNING: This function is not called by the constructor.
     * You have to call it after calling super() !
     * Explications : Intellij GUI designer use an initializer block to initialize components,
     * But the initializer block is called after the super() method.
     */
    public abstract void setup();

    public abstract Container getContentPane();
}
