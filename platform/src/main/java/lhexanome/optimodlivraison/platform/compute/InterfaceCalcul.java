package lhexanome.optimodlivraison.platform.compute;


import lhexanome.optimodlivraison.platform.compute.tsp.TSP2wSlots;
import lhexanome.optimodlivraison.platform.compute.tsp.TSPwSlots;
import lhexanome.optimodlivraison.platform.exceptions.ComputeSlotsException;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * classe qui permet d'interagir avec le module compute.
 */
public class InterfaceCalcul {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SimplifiedMap.class.getName());
    /**
     * Temps maximum d'exécution de l'algorithme en millisecondes.
     */
    public static final int TIMEOUT = Integer.MAX_VALUE;


    /**
     * Observed tour.
     */
    private Tour tour;

    /**
     * Empty constructor.
     */
    public InterfaceCalcul() {
        this.tour = new Tour();
    }

    /**
     * Génère le graphe des plus courts chemins entre les livraisons.
     * Met à jour les attributs roadMap, demande et SimplifiedMap.
     *
     * @param roadMap Le plan général concernant la demande.
     * @param demande La demande de livraison à traiter.
     * @return Le plan simplifié contenant les trajets reliant les points de livraison.
     */
    public SimplifiedMap computeSimplifiedRoadMap(RoadMap roadMap, DeliveryOrder demande) {
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        simplifiedMap.computeGraph();
        return simplifiedMap;
    }

    /**
     * Calcule la tournée optimale en fonction du roadMap simplifié et de la demande de livraison.
     * Met à jour l'attribut sortie.
     *
     * @param simplifiedMap La map simplifié calculée précédemment.
     * @param demande       La demande de livraison.
     * @throws ComputeSlotsException when the slots are incompatible
     * @return La tournée calculée.
     * @throws ComputeSlotsException when the slots are incompatible
     */
    public Tour computeTour(SimplifiedMap simplifiedMap,
                            DeliveryOrder demande) throws ComputeSlotsException {
        Warehouse warehouse;
        Date start;

        Map<Halt, ArrayList<Path>> graphe = simplifiedMap.getGraph();

        warehouse = demande.getWarehouse();
        start = demande.getStart();

        tour.setWarehouse(warehouse);
        tour.setStart(start);

        int nbSommets = demande.getDeliveries().size() + 1;

        //sert à assigner chaque sommet à un index.
        ArrayList<Halt> listeSommets = new ArrayList<>();
        listeSommets.add(demande.getWarehouse());
        listeSommets.addAll(demande.getDeliveries());

        MatriceAdjacence matrix = grapheToMatrix(graphe, nbSommets, listeSommets);

        int[] listeDurees = demandeToDurees(demande, nbSommets, listeSommets);


        TimeSlot[] plages = new TimeSlot[nbSommets];
        for (int i = 0; i < listeSommets.size(); i++) {
            if (listeSommets.get(i) instanceof Delivery) {
                plages[i] = ((Delivery) listeSommets.get(i)).getSlot();
            } else {
                plages[i] = null;
            }
        }
        TSPwSlots tsp = new TSP2wSlots();


        tsp.chercheSolution(tour, matrix, TIMEOUT, nbSommets, matrix.getMatriceCouts(),
                plages, demande.getStart(), listeDurees);

        if (tour.getPaths() == null) {
            throw new ComputeSlotsException("Can't compute tour because of incompatible slots");

        }
        LOGGER.info("TSP finished");
        return tour;

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

        MatriceAdjacence matrix = new MatriceAdjacence(listeSommets, matriceTrajets, matriceCouts);
        matrix.initMatrix(graphe);
        return matrix;
    }

    /**
     * Crée la liste des coûts des sommets.
     *
     * @param demande      La demande de livraison à traiter.
     * @param nbSommets    Nombre de sommets du graphe.
     * @param listeSommets Liste attribuant chaque sommet à un index.
     * @return La liste des coûts des sommets.
     */

    private int[] demandeToDurees(DeliveryOrder demande, int nbSommets, ArrayList<Halt> listeSommets) {
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

    /**
     * Add a tour observer.
     *
     * @param o Observer
     */
    public void addTourObserver(Observer o) {
        this.tour.addObserver(o);
    }
}