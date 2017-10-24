package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.compute.tsp.TSP;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP1;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.ArrayList;
import java.util.Date;
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
    public SimplifiedMap calculerPlanSimplifie(RoadMap roadMap, DeliveryOrder demande) {
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
    public Tour calculerTournee(SimplifiedMap simplifiedMap, DeliveryOrder demande) {
        Intersection warehouse;
        Date start;
        int time;

        Map graphe = simplifiedMap.getGraph();

        warehouse = demande.getBeginning().getIntersection();
        start = demande.getStart();
        int nbSommets = demande.getDeliveries().size() + 1;

        //sert à assigner chaque sommet à un index.
        ArrayList<Halt> listeSommets = new ArrayList<>();
        listeSommets.add(demande.getBeginning());
        listeSommets.addAll(demande.getDeliveries());

        MatriceAdjacence matrix = grapheToMatrix(graphe, nbSommets, listeSommets);
        int[][] matriceCouts = matrix.getMatriceCouts();
        Path[][] matricePaths = matrix.getMatricePaths();

        int[] listeDurees = demandeToDurees(demande, nbSommets, listeSommets);

        TSP tsp = new TSP1();
        tsp.chercheSolution(9999999, nbSommets, matriceCouts, listeDurees);
        time = tsp.getCoutMeilleureSolution();

        ArrayList<Path> deliveries = new ArrayList<>(nbSommets);
        for (int i = 0; i < nbSommets - 1; i++) {
            int indexSommet = tsp.getMeilleureSolution(i);
            Path path = matricePaths[indexSommet][indexSommet + 1];
            deliveries.set(i, path);
        }

        this.sortie = new Tour(warehouse, start, time, deliveries);
        return sortie;
    }

    private MatriceAdjacence grapheToMatrix(Map<Halt, ArrayList<Path>> graphe, int nbSommets, ArrayList<Halt> listeSommets) {
        int[][] matriceCouts = new int[nbSommets][nbSommets];
        Path[][] matricePaths = new Path[nbSommets][nbSommets];

        //entrepot
        Halt entrepot = demande.getBeginning();
        int inter1 = 0;
        for (Path path : graphe.get(entrepot)) {
            int inter2 = listeSommets.indexOf(path.getEnd());
            int cout = path.getTimeToTravel();
            matriceCouts[inter1][inter2] = cout;
            matricePaths[inter1][inter2] = path;
        }

        //le reste
        for (Halt halt : graphe.keySet()) {
            inter1 = listeSommets.indexOf(halt.getIntersection());
            for (Path path : graphe.get(halt)) {
                int inter2 = listeSommets.indexOf(path.getEnd());
                int cout = path.getTimeToTravel();
                matriceCouts[inter1][inter2] = cout;
                matricePaths[inter1][inter2] = path;
            }
        }

        return new MatriceAdjacence(listeSommets, matricePaths, matriceCouts);
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
