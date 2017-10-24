package lhexanome.optimodlivraison.ui.view;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.panel.RoadMapViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeliveryOrderPreviewView {
    private final Controller controller;
    private JButton computeTourButton;
    private JPanel mainPanel;
    private RoadMapViewPanel roadMapViewPanel;

    public DeliveryOrderPreviewView(Controller controller){
        this.controller = controller;

        computeTourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clickComputeTour();
            }
        });
    }

    public Component getMainPanel() {
        return mainPanel;
    }

    public void setRoadMap(RoadMap roadMap) {
        roadMapViewPanel.setRoadMap(roadMap);
    }
    public void setDeliveryOrder(DeliveryOrder order) {
        roadMapViewPanel.setDeliveryOrder(order);
    }
}
