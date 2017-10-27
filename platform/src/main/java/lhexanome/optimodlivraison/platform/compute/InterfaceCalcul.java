package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.compute.tsp.TSP;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP1;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * classe qui permet d'interagir avec le module compute.
 */
public class InterfaceCalcul {

    /**
     * Temps maximum d'exécution de l'algorithme en millisecondes.
     */
    public static final int TPS_LIMITE = 9999999;

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
     *
     * @param newRoadMap    Le plan général concernant la demande.
     * @param newDemande La demande de livraison à traiter.
     * @return Le plan simplifié contenant les trajets reliant les points de livraison.
     */
    public SimplifiedMap computeSimplifiedRoadMap(RoadMap newRoadMap, DeliveryOrder newDemande) {
        this.roadMap = newRoadMap;
        this.demande = newDemande;
        this.simplifiedMap = new SimplifiedMap(demande, newRoadMap);
        simplifiedMap.computeGraph();
        return this.simplifiedMap;
    }

    /**
     * Calcule la tournée optimale en fonction du roadMap simplifié et de la demande de livraison.
     * Met à jour l'attribut sortie.
     *
     * @param simplifiedMap Le plan simplifié calculé précédemment.
     * @param demande       La demande de livraison à traiter.
     * @return La tournée calculée.
     */
    public Tour computeTour() {
        Warehouse warehouse;
        Date start;
        int time;

        Map<Halt, ArrayList<Path>> graphe = simplifiedMap.getGraph();

        warehouse = demande.getBeginning();
        start = demande.getStart();
        int nbSommets = demande.getDeliveries().size() + 1;

        //sert à assigner chaque sommet à un index.
        ArrayList<Halt> listeSommets = new ArrayList<>();
        listeSommets.add(demande.getBeginning());
        listeSommets.addAll(demande.getDeliveries());

        MatriceAdjacence matrix = grapheToMatrix(graphe, nbSommets, listeSommets);

        int[] listeDurees = demandeToDurees(demande, nbSommets, listeSommets);

        TSP tsp = new TSP1();
        tsp.chercheSolution(TPS_LIMITE, nbSommets, matrix.getMatriceCouts(), listeDurees);
        time = tsp.getCoutMeilleureSolution();

        ArrayList<Path> deliveries = new ArrayList<>(nbSommets);

        Path[][] matriceTrajets = matrix.getMatricePaths();
        for (int i = 0; i < nbSommets - 1; i++) {
            int indexSommet = tsp.getMeilleureSolution(i);
            Path trajet = matriceTrajets[indexSommet][indexSommet + 1];
            deliveries.set(i, trajet);
        }

        this.sortie = new Tour(warehouse, start, time, deliveries);
        return sortie;
    }

    /**
     * Génère la matrice d'adjacence à partir du graphe,
     * pour être traité par l'algorithme de résolution du TSP.
     *
     * @param graphe       Graphe devant être traité.
     * @param nbSommets    Nombre de sommets du graphe.
     * @param listeSommets Liste attribuant chaque sommet à un index.
     * @return La MatriceAdjacence contenant :
     * La matrice des couts de trajets d'un sommet x à un sommet y,
     * La matrice des trajets d'un sommet x à un sommet y.
     */
    private MatriceAdjacence grapheToMatrix(Map<Halt, ArrayList<Path>> graphe, int nbSommets,
                                            ArrayList<Halt> listeSommets) {

        int[][] matriceCouts = new int[nbSommets][nbSommets];
        Path[][] matriceTrajets = new Path[nbSommets][nbSommets];

        //entrepot
        Halt entrepot = demande.getBeginning();
        int inter1 = 0;
        for (Path trajet : graphe.get(entrepot)) {
            int inter2 = listeSommets.indexOf(trajet.getEnd());
            int cout = trajet.getTimeToTravel();
            matriceCouts[inter1][inter2] = cout;
            matriceTrajets[inter1][inter2] = trajet;
        }

        //le reste
        for (Halt arret : graphe.keySet()) {
            inter1 = listeSommets.indexOf(arret.getIntersection());
            for (Path trajet : graphe.get(arret)) {
                int inter2 = listeSommets.indexOf(trajet.getEnd());
                int cout = trajet.getTimeToTravel();
                matriceCouts[inter1][inter2] = cout;
                matriceTrajets[inter1][inter2] = trajet;
            }
        }

        return new MatriceAdjacence(listeSommets, matriceTrajets, matriceCouts);
    }

    /**
     * Crée la liste des coûts des sommets.
     *
     * @param newDemande   La demande de livraison à traiter.
     * @param nbSommets    Nombre de sommets du graphe.
     * @param listeSommets Liste attribuant chaque sommet à un index.
     * @return La liste des coûts des sommets.
     */

    private int[] demandeToDurees(DeliveryOrder newDemande, int nbSommets, ArrayList<Halt> listeSommets) {
        int[] listeDurees = new int[nbSommets];


        //entrepôt
        listeDurees[0] = 0;

        //le reste
        for (Halt halt : demande.getDeliveries()) {
            int index = listeSommets.indexOf(halt);
            listeDurees[index] = ((Delivery) halt).getDuration();
        }

        return listeDurees;
    }
}
