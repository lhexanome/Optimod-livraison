package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.WelcomeController;
import lhexanome.optimodlivraison.ui.panel.WelcomePanel;

public class WelcomeWindow extends Window {

    public WelcomeWindow(WelcomeController controller) {
        super(controller, "Bienvenue sur Optimod Livraison - H4102");

        jFrame.setContentPane(new WelcomePanel(controller).getContentPane());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }
}
