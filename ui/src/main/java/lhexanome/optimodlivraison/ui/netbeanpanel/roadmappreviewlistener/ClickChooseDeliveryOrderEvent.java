package lhexanome.optimodlivraison.ui.netbeanpanel.roadmappreviewlistener;

import lhexanome.optimodlivraison.ui.netbeanpanel.IRoadMapPreviewController;
import lhexanome.optimodlivraison.ui.netbeanpanel.RoadMapPreviewPanel;

import java.awt.event.ActionEvent;

public class ClickChooseDeliveryOrderEvent {
    private ActionEvent originalEvent;
    private ClickChooseDeliveryOrderListener listener;

    public ClickChooseDeliveryOrderEvent(ActionEvent originalEvent, ClickChooseDeliveryOrderListener listener) {
        this.originalEvent = originalEvent;
        this.listener = listener;
    }

    public ClickChooseDeliveryOrderListener getListener() {
        return listener;
    }

    public ActionEvent getOriginalEvent() {
        return originalEvent;
    }

    public RoadMapPreviewPanel getPanel(){
        return listener.getPanel();
    }

    public IRoadMapPreviewController getController(){
        return listener.getController();
    }
}
