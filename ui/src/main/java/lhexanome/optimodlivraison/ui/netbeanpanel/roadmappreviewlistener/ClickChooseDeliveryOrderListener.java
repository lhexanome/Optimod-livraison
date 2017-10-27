package lhexanome.optimodlivraison.ui.netbeanpanel.roadmappreviewlistener;

import lhexanome.optimodlivraison.ui.netbeanpanel.IRoadMapPreviewController;
import lhexanome.optimodlivraison.ui.netbeanpanel.RoadMapPreviewPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickChooseDeliveryOrderListener implements ActionListener{

    private IRoadMapPreviewController controller;
    private RoadMapPreviewPanel panel;

    public ClickChooseDeliveryOrderListener(IRoadMapPreviewController controller, RoadMapPreviewPanel panel){

        this.controller = controller;
        this.panel = panel;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.clickChooseDeliveryOrder(new ClickChooseDeliveryOrderEvent(e,this));
    }

    public RoadMapPreviewPanel getPanel() {
        return panel;
    }

    public IRoadMapPreviewController getController() {
        return controller;
    }
}
