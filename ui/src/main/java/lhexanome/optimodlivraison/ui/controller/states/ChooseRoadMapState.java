package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.facade.RoadMapFacade;
import lhexanome.optimodlivraison.platform.listeners.MapListener;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.RoadMapPreviewWindow;
import lhexanome.optimodlivraison.ui.window.WelcomeWindow;

import java.io.File;

public class ChooseRoadMapState extends DefaultState {
    public ChooseRoadMapState(Controller controller, WelcomeWindow window) {
        super("ChooseRoadMapState", controller, window);
    }

    @Override
    public void clickCancelRoadMap() {
        controller.setCurrentState(controller.welcomeState);
    }

    @Override
    public void selectRoadMap(RoadMapPreviewWindow nextWindow, File xmlRoadMapFile) {

        RoadMapFacade mapFacade = new RoadMapFacade();
        mapFacade.addOnUpdateMapListener(new MapListener() {
            @Override
            public void onUpdateMap(RoadMap roadMap) {

                controller.roadMap = roadMap;
                nextWindow.setRoadMap(roadMap);

                window.close();
                //TODO window.setLoad(false);
                nextWindow.open();
                controller.setCurrentState(controller.roadMapPreviewState);
            }

            @Override
            public void onFailUpdateMap(MapException e) {

                //TODO window.setLoad(false);
                //TODO window.sendError(e.getMessage());

                //TODO Log
                System.err.println(e.getMessage());

                controller.setCurrentState(controller.welcomeState);

            }
        });

        // TODO window.setLoad(true);
        mapFacade.loadMapFromFile(xmlRoadMapFile);

    }
}
