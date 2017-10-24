package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.view.OrderEditorView;

public class OrderEditorWindow extends Window{

    OrderEditorView orderEditorView;

    public OrderEditorWindow(Controller c) {
        super(c, "Delivery order editor");

        orderEditorView= new OrderEditorView(controller);

        jFrame.add(orderEditorView.getMainPanel());

        //jFrame.pack();
        jFrame.setSize(1080,720);
        jFrame.setLocationRelativeTo(null);
    }

    public void setRoadMap(RoadMap plan) {
        this.orderEditorView.setRoadMap(plan);
    }
    public void setDeliveryOrder(DeliveryOrder demand) {
        this.orderEditorView.setDeliveryOrder(demand);
    }
    public void setTour(Tour tournee) {
        this.orderEditorView.setTour(tournee);
    }
}
