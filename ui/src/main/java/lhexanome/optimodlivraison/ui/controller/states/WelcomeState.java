package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

public class WelcomeState extends DefaultState {
    public WelcomeState(Controller controller, WelcomeWindow window) {
        super("WelcomeState", controller, window);
    }

    @Override
    public void clickChoosePlan() {
        controller.setCurrentState(controller.choosePlanState);
        ((WelcomeWindow)window).choosedFilePlan();
    }
}
