package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.ArrayList;
import java.util.Map;

/**
 * classe qui permet d'interagir avec le module compute.
 */
//CHECKSTYLE:OFF
public class InterfaceCalcul {

    /**
     * reference du roadMap charge.
     */
    private RoadMap roadMap;

    /**
     * reference de la demande de livraison chargee.
     */
    private DeliveryOrder demande;

    /**
     * Tour retournee par le calcul de tournee optimise.
     */
    private Tour sortie;

    /**
     * Graphe des plus courts chemins entre les livraisons.
     * Met à jour les attributs roadMap, demande et SimplifiedMap.
     */
    private SimplifiedMap simplifiedMap;

    /**
     * Génère le graphe des plus courts chemins entre les livraisons.
     * Met à jour les attributs roadMap, demande et SimplifiedMap.
     */
    public SimplifiedMap computeSimplifiedRoadMap(RoadMap roadMap, DeliveryOrder demande) {
        this.roadMap = roadMap;
        this.demande = demande;
        this.simplifiedMap = new SimplifiedMap(demande, roadMap);
        simplifiedMap.computeGraph();
        return this.simplifiedMap;
    }

    /**
     * Calcule la tournée optimale en fonction du roadMap simplifié et de la demande de livraison.
     * Met à jour l'attribut sortie.
     */
    public Tour computeTour() {
        return sortie;
    }

    private MatriceAdjacence grapheToMatrix(Map<Halt, ArrayList<Path>> graphe, int nbSommets, ArrayList<Halt> listeSommets) {
        return null;
    }

    private int[] demandeToDurees(DeliveryOrder demande, int nbSommets, ArrayList<Halt> listeSommets) {
        int[] sortie = new int[nbSommets];

        //entrepôt
        sortie[0] = 0;

        //le reste
        for (Halt halt : demande.getDeliveries()) {
            int index = listeSommets.indexOf(halt);
            sortie[index] = ((Delivery) halt).getDuration();
        }

        return sortie;
    }
}
