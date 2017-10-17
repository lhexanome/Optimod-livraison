package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.welcome.WelcomeWindow;

public class WelcomeState extends DefaultState {
    public WelcomeState(Controller controller, WelcomeWindow window) {
        super(controller, window);
    }

    @Override
    public void clickChoosePlan() {
        ((WelcomeWindow)window).choosedFilePlan();
    }
}
