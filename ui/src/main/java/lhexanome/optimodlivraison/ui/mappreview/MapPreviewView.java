package lhexanome.optimodlivraison.ui.mappreview;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Troncon;
import lhexanome.optimodlivraison.ui.mappanel.MapView;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MapPreviewView {
    private JPanel mainPanel;
    private JPanel mapContainer;

    private MapView mapView;

    //TODO replace MapPreviewView(Map<Intersection, Troncon> planTemp) by MapPreviewView(Plan plan)
    public MapPreviewView(Map<Intersection, Troncon> planTemp){
        mapView = new MapView(planTemp);
        mapContainer.add(mapView.getMainPanel());
    }

    public Component getMainPanel() {
        return mainPanel;
    }
}
