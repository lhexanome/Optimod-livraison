package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.view.DeliveryOrderPreviewView;

public class DeliveryOrderPreviewWindow extends Window{

    private DeliveryOrderPreviewView deliveryOrderPreviewView;

    public DeliveryOrderPreviewWindow(Controller c) {
        super(c, "Order preview");
        deliveryOrderPreviewView = new DeliveryOrderPreviewView(controller);

        jFrame.add(deliveryOrderPreviewView.getMainPanel());

        //jFrame.pack();
        jFrame.setSize(1080,720);
        jFrame.setLocationRelativeTo(null);
    }

    public void setRoadMap(RoadMap plan) {
        this.deliveryOrderPreviewView.setRoadMap(plan);
    }
    public void setDeliveryOrder(DeliveryOrder demand) {
        this.deliveryOrderPreviewView.setDeliveryOrder(demand);
    }

}
