package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.compute.tsp.TSP;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP1;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP2;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP2wSlots;
import lhexanome.optimodlivraison.platform.compute.tsp.TSPwSlots;
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
    public static final int TPS_LIMITE = 30000;

    /**
     * Intervalle d'actualisation de l'affichage pendant le TSP.
     */
    private static final int INTERVAL_NOTIFY = 2500;

    /**
     * conversion en secondes.
     */
    private static final int CONV_SEC_MIN = 60;

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
     * @param type          le type d'heuristique utilisé.
     * @param withSlots     si on utilise les plages horaires
     * @return La tournée calculée.
     */
    public Tour computeTour(SimplifiedMap simplifiedMap, DeliveryOrder demande, TspTypes type, boolean withSlots) {
        Warehouse warehouse;
        Date start;
        int time;

        Map<Halt, ArrayList<Path>> graphe = simplifiedMap.getGraph();

        warehouse = demande.getBeginning();
        start = demande.getStart();

        tour.setWarehouse(warehouse);
        tour.setStart(start);

        int nbSommets = demande.getDeliveries().size() + 1;

        //sert à assigner chaque sommet à un index.
        ArrayList<Halt> listeSommets = new ArrayList<>();
        listeSommets.add(demande.getBeginning());
        listeSommets.addAll(demande.getDeliveries());

        MatriceAdjacence matrix = grapheToMatrix(graphe, nbSommets, listeSommets, demande);

        int[] listeDurees = demandeToDurees(demande, nbSommets, listeSommets);
        Date[] datesEstimees = new Date[nbSommets];

        ArrayList<Path> deliveries = new ArrayList<>(nbSommets);

        if (withSlots) {
            TimeSlot[] plages = new TimeSlot[nbSommets];
            for (int i = 0; i < listeSommets.size(); i++) {
                if (listeSommets.get(i) instanceof Delivery) {
                    plages[i] = ((Delivery) listeSommets.get(i)).getSlot();
                } else {
                    plages[i] = null;
                }
            }
            TSPwSlots tsp = new TSP2wSlots();

            do {

                tsp.chercheSolution(INTERVAL_NOTIFY, nbSommets, matrix.getMatriceCouts(),
                        plages, demande.getStart(), datesEstimees, listeDurees);
                time = tsp.getCoutMeilleureSolution();
                if (time == Integer.MAX_VALUE) {
                    LOGGER.warning("can't compute tour because of incompatible slots");
                    return null;
                }

                Path[][] matriceTrajets = matrix.getMatricePaths();
                int indexDepart, indexArrivee;
                indexDepart = tsp.getMeilleureSolution(0);
                for (int i = 1; i < nbSommets; i++) {
                    indexArrivee = tsp.getMeilleureSolution(i);
                    Path trajet = matriceTrajets[indexDepart][indexArrivee];
                    deliveries.add(trajet);
                    indexDepart = indexArrivee;
                }
                deliveries.add(matriceTrajets[indexDepart][tsp.getMeilleureSolution(0)]); //retour entrepot

                demande.getBeginning().setEstimateDate(datesEstimees[0]);
                int i = 0;
                for (Halt arret : graphe.keySet()) {
                    arret.setEstimateDate(datesEstimees[i]);
                    i++;
                }

                tour.setTime(time);
                tour.setPaths(deliveries);

                tour.forceNotifyObservers();

            } while (tsp.getTempsLimiteAtteint());

        } else {
            TSP tsp = new TSP1();
            if (type == TspTypes.HEURISTICS_1) {
                tsp = new TSP2();
            }

            do {
                tsp.chercheSolution(INTERVAL_NOTIFY, nbSommets, matrix.getMatriceCouts(), listeDurees);
                time = tsp.getCoutMeilleureSolution();

                Path[][] matriceTrajets = matrix.getMatricePaths();

                int indexDepart, indexArrivee;
                indexDepart = tsp.getMeilleureSolution(0);
                for (int i = 1; i < nbSommets; i++) {
                    indexArrivee = tsp.getMeilleureSolution(i);
                    Path trajet = matriceTrajets[indexDepart][indexArrivee];
                    deliveries.add(trajet);
                    indexDepart = indexArrivee;
                }
                deliveries.add(matriceTrajets[indexDepart][tsp.getMeilleureSolution(0)]); //retour entrepot

                tour.setTime(time);
                tour.setPaths(deliveries);

                tour.forceNotifyObservers();
            } while (tsp.getTempsLimiteAtteint());

        }


        return tour;
    }

    /**
     * Génère la matrice d'adjacence à partir du graphe,
     * pour être traité par l'algorithme de résolution du TSP.
     *
     * @param graphe       Graphe devant être traité.
     * @param nbSommets    Nombre de sommets du graphe.
     * @param listeSommets Liste attribuant chaque sommet à un index.
     * @param demande      La demande de livraison.
     * @return La MatriceAdjacence contenant :
     * La matrice des couts de trajets d'un sommet x à un sommet y,
     * La matrice des trajets d'un sommet x à un sommet y.
     */
    private MatriceAdjacence grapheToMatrix(Map<Halt, ArrayList<Path>> graphe, int nbSommets,
                                            ArrayList<Halt> listeSommets, DeliveryOrder demande) {

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
            inter1 = listeSommets.indexOf(arret);
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
