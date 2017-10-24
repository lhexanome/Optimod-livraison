package lhexanome.optimodlivraison.ui.controller.actions;

import lhexanome.optimodlivraison.platform.models.RoadMap;

import java.io.File;

public interface RoadMapControllerInterface extends ControllerInterface {
    void setRoadMap(RoadMap roadMap);

    void selectRoadMap(File xmlFile);

    void reloadMap();
}
