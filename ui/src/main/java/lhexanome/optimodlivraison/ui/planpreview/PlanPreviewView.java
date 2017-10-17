package lhexanome.optimodlivraison.ui.mappreview;

import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.mappanel.MapViewPanel;

import javax.swing.*;
import java.awt.*;

public class MapPreviewView {
    private JPanel mainPanel;
    private JPanel mapContainer;
    private MapViewPanel mapViewPanelTest;
    private JButton ouvrirUneDemandeButton;


    public Component getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        mapViewPanelTest = new MapViewPanel();
    }

    public void setPlan(Plan plan) {
        mapViewPanelTest.setPlan(plan);
    }
}
