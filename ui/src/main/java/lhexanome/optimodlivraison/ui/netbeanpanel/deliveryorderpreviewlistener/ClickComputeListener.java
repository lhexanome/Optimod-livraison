package lhexanome.optimodlivraison.ui.netbeanpanel.deliveryorderpreviewlistener;

import lhexanome.optimodlivraison.ui.netbeanpanel.DeliveryOrderPreviewPanel;
import lhexanome.optimodlivraison.ui.netbeanpanel.IDeliveryOrderPreviewController;
import lhexanome.optimodlivraison.ui.netbeanpanel.IRoadMapPreviewController;
import lhexanome.optimodlivraison.ui.netbeanpanel.RoadMapPreviewPanel;
import lhexanome.optimodlivraison.ui.netbeanpanel.roadmappreviewlistener.ClickChooseDeliveryOrderEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickComputeListener implements ActionListener{

    private IDeliveryOrderPreviewController controller;
    private DeliveryOrderPreviewPanel panel;

    public ClickComputeListener(IDeliveryOrderPreviewController controller, DeliveryOrderPreviewPanel panel){

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
        controller.clickCompute(new ClickComputeEvent(e,this));
    }

    public DeliveryOrderPreviewPanel getPanel() {
        return panel;
    }

    public IDeliveryOrderPreviewController getController() {
        return controller;
    }
}
