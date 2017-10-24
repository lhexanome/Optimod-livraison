package lhexanome.optimodlivraison.ui.controller.actions;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.io.File;

public interface MainControllerInterface extends ControllerInterface {
    void selectRoadMap(File xmlFile);

    void setRoadMap(RoadMap roadMap);

    void setTour(Tour tour);

    void setDeliveryOrder(DeliveryOrder deliveryOrder);

    void notifyError(String errorMessage);
}
