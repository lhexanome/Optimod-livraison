package lhexanome.optimodlivraison.ui.controller;

import lhexanome.optimodlivraison.platform.exceptions.ComputeException;
import lhexanome.optimodlivraison.platform.facade.ComputeFacade;
import lhexanome.optimodlivraison.platform.listeners.ComputeListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.ui.panel.TourPanel;

import javax.swing.*;
import java.util.logging.Logger;

public class TourController implements ControllerInterface {
    private static final Logger LOGGER = Logger.getLogger(TourController.class.getName());

    private final MainController mainController;
    private final TourPanel tourPanel;

    private Tour tour;

    public TourController(MainController mainController) {
        this.mainController = mainController;

        this.tourPanel = new TourPanel(this);
    }

    @Override
    public void closeWindow() {
        mainController.closeWindow();
    }

    @Override
    public void start() {
        mainController.start();
    }

    @Override
    public JPanel getContentPane() {
        return tourPanel.getContentPane();
    }

    public void computeTour(RoadMap roadMap, DeliveryOrder deliveryOrder) {

        ComputeFacade computeFacade = new ComputeFacade();
        computeFacade.addOnComputeListener(new ComputeListener() {
            /**
             * Fonction appelée quand le calcul d'une tournée est fini.
             *
             * @param newTour Tournée calculée
             */
            @Override
            public void onComputingTour(Tour newTour) {
                //TODO deliveryOrderPanel.setLoad(false);
                setTour(newTour);
            }

            /**
             * Fonction appelée quand une tournée n'a pas pu être calculée.
             * <p>
             * TODO Ajouter un genre d'enum pour les différents problèmes
             *
             * @param e Exception contenant la cause
             */
            @Override
            public void onFailCompute(ComputeException e) {
                //TODO deliveryOrderPanel.setLoad(false);

                LOGGER.warning(String.format("Error while computing a tour error : %s", e.getMessage()));
                mainController.notifyError(e.getMessage());
            }
        });

        // TODO deliveryOrderPanel.setLoad(true);
        computeFacade.computeTour(roadMap, deliveryOrder);
    }

    private void setTour(Tour tour) {
        this.tour = tour;
        mainController.setTour(tour);
    }

    public void newComputation() {
        mainController.computeTour();
    }
}
