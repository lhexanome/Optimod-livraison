package lhexanome.optimodlivraison.ui.controller.states;

import lhexanome.optimodlivraison.platform.exceptions.ComputeException;
import lhexanome.optimodlivraison.platform.facade.ComputeFacade;
import lhexanome.optimodlivraison.platform.listeners.ComputeListener;
import lhexanome.optimodlivraison.platform.models.Tournee;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.controller.DefaultState;
import lhexanome.optimodlivraison.ui.window.DemandPreviewWindow;
import lhexanome.optimodlivraison.ui.window.OrderEditorWindow;

public class DemandPreviewState extends DefaultState {
    public DemandPreviewState(Controller controller, DemandPreviewWindow window) {
        super("DemandPreviewState", controller, window);
    }

    @Override
    public void clickComputeTour(OrderEditorWindow nextWindow) {//TODO


        ComputeFacade computeFacade = new ComputeFacade();
        computeFacade.addOnComputeListener(new ComputeListener() {
            @Override
            public void onComputingTour(Tournee tournee) {
                controller.tournee = tournee;

                window.close();
                nextWindow.setPlan(controller.plan);
                nextWindow.setDemand(controller.demand);
                nextWindow.setTournee(controller.tournee);
                controller.setCurrentState(controller.orderEditorState);
                nextWindow.open();
            }

            @Override
            public void onFailCompute(ComputeException e) {

                //TODO window.setLoad(false);
                //TODO window.sendError(e.getMessage());

                //TODO Log
                System.err.println(e.getMessage());

                controller.setCurrentState(controller.demandPreviewState);
            }
        });

        computeFacade.computeTour(controller.plan, controller.demand);

    }
}
