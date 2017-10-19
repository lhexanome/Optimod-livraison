package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.platform.exceptions.DeliveryException;
import lhexanome.optimodlivraison.platform.facade.DeliveryFacade;
import lhexanome.optimodlivraison.platform.listeners.DeliveryListener;
import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.DemandPreviewWindow;

import java.io.File;

public class ChooseDemandeState extends DefaultState {

    public ChooseDemandeState(Controller controller, Window window) {
        super("ChooseDemandeState", controller, window);
    }


    @Override
    public void clickCancelDemand() {
        controller.setCurrentState(controller.planPreviewState);
    }


    @Override
    public void selectDemand(DemandPreviewWindow nextWindow, File xmlDemandFile) {

        DeliveryFacade deliveryFacade = new DeliveryFacade();

        deliveryFacade.addOnUpdateDeliveryListener(new DeliveryListener() {
            @Override
            public void onUpdateDeliveryOrder(DemandeLivraison demandeLivraison) {

                controller.demand = demandeLivraison;
                nextWindow.setDemand(demandeLivraison);

                window.close();
                //TODO window.setLoad(false);
                nextWindow.open();
                controller.setCurrentState(controller.demandPreviewState);
            }

            @Override
            public void onFailUpdateDeliveryOrder(DeliveryException e) {
                //TODO window.setLoad(false);
                //TODO window.sendError(e.getMessage());

                //TODO Log
                System.err.println(e.getMessage());

                controller.setCurrentState(controller.demandPreviewState);
            }
        });

        // TODO window.setLoad(true);
        deliveryFacade.loadDeliveryOrderFromFile(xmlDemandFile);

    }
}
