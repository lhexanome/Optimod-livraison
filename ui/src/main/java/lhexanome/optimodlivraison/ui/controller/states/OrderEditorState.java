package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.orderaction.OrderEditorWindow;

public class OrderEditorState extends DefaultState {
    public OrderEditorState(Controller controller, OrderEditorWindow window) {
        super("OrderEditorState", controller, window);
    }
}
