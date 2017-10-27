package lhexanome.optimodlivraison.ui.netbeanpanel.deliveryorderpreviewlistener;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.netbeanpanel.DeliveryOrderPreviewPanel;
import lhexanome.optimodlivraison.ui.netbeanpanel.IDeliveryOrderPreviewController;

import java.awt.event.ActionEvent;

public class ClickComputeEvent {
    private ActionEvent originalEvent;
    private ClickComputeListener listener;

    public ClickComputeEvent(ActionEvent originalEvent, ClickComputeListener listener) {
        this.originalEvent = originalEvent;
        this.listener = listener;
    }

    public ActionEvent getOriginalEvent() {
        return originalEvent;
    }

    public ClickComputeListener getListener() {
        return listener;
    }

    public DeliveryOrderPreviewPanel getPanel(){
        return listener.getPanel();
    }

    public IDeliveryOrderPreviewController getController(){
        return listener.getController();
    }

    public RoadMap getRoadMap(){
        return listener.getPanel().getRoadMap();
    }

    public DeliveryOrder getDeliveryOrder(){
        return listener.getPanel().getDeliveryOrder();
    }
}
