package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.controller.WelcomeController;
import lhexanome.optimodlivraison.ui.panel.WelcomePanel;

/**
 * Welcome window.
 */
public class WelcomeWindow extends Window {

    /**
     * Constructor.
     *
     * @param controller Welcome controller
     */
    public WelcomeWindow(WelcomeController controller) {
        super(controller, "Bienvenue sur Optimod Livraison - H4102");

        jFrame.setContentPane(new WelcomePanel(controller).getContentPane());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }
}
