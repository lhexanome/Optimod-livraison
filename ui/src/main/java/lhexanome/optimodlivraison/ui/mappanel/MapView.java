package lhexanome.optimodlivraison.ui.mappanel;


import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Troncon;

import java.awt.*;
import java.util.Map;

public class MapView {

    private MapViewPanel mainPanel;

    // TODO replace MapView(Map<Intersection, Troncon> planTemp) by MapView(Plan plan)
    public MapView(Map<Intersection, Troncon> planTemp){
        mainPanel = new MapViewPanel(planTemp);
    }

    public Component getMainPanel() {
        return mainPanel;
    }
}
