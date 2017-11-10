package lhexanome.optimodlivraison.platform.compute;


import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


/**
 * classe qui stocke une version simplifiée du roadMap,
 * soit un graphe orienté et complet avec comme sommets les livraisons,
 * et pour arc des trajets formant les plus courts chemins entre ces livraisons.
 */
public class SimplifiedMap {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SimplifiedMap.class.getName());
    /**
     * reference au roadMap charge.
     */
    private RoadMap roadMap;
    /**
     * reference a la demande de livraison chargee.
     */
    private DeliveryOrder deliveryOrder;
    /**
     * graphe oriente.
     */
    private Map<Halt, ArrayList<Path>> graph;


    /**
     * id compute iteration.
     */
    private static long idIteration = -1;

    /**
     * Constructor used to do the computing without generating a graph.
     *
     * @param roadMap roadmap to compute from.
     */
    public SimplifiedMap(RoadMap roadMap) {
        this.roadMap = roadMap;

    }

    /**
     * constructeur qui initialise le roadMap simplifie.
     *
     * @param deliveryOrder la demande de livraison chargee
     * @param roadMap       le roadMap charge
     */

    public SimplifiedMap(DeliveryOrder deliveryOrder, RoadMap roadMap) {
        this.roadMap = roadMap;
        this.deliveryOrder = deliveryOrder;
        graph = new HashMap<>();
    }

    /**
     * fonction qui calcule le graph simplifié à partir du roadMap.
     */
    public void computeGraph() {
        if (deliveryOrder != null) {
            LOGGER.info(MessageFormat.format("compute simplified graph", ""));

            HashSet<Halt> ptsHalt = new HashSet<>(deliveryOrder.getDeliveries());

            ptsHalt.add(deliveryOrder.getWarehouse());
            for (Halt s : ptsHalt) {
                ArrayList<Path> listePaths =
                        shortestPathList(s, ptsHalt);

                graph.put(s, listePaths);
            }
        } else {
            LOGGER.warning("Can't compute simplified graph, no delivery order");
        }
    }

    /**
     * creates tour from all path in the graph.
     *
     * @return tour generated.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public Tour generateFakeTour() {
        ArrayList<Path> listPath = new ArrayList<>();
        Tour sortie = null;
        boolean first = true;
        for (Halt s : graph.keySet()) {
            if (s instanceof Warehouse) {
                sortie = new Tour((Warehouse) s, DateUtil.getDate(10, 10));
            }
            ArrayList<Path> list = graph.get(s);
            listPath.addAll(list);
        }
        sortie.setPaths(listPath);
        return sortie;
    }

    /**
     * fonction qui renvoie
     * la liste des chemins les plus courts partant de start
     * et allant à chaque Delivery de ends.
     *
     * @param start intersection de depart
     * @param ends  Liste des arrives
     * @return liste des plus courts chemins
     */
    public ArrayList<Path> shortestPathList(
            Halt start, Set<? extends Halt> ends) {
        ArrayList<Path> sortie = new ArrayList<>();
        LOGGER.info(MessageFormat.format("start compute shortest path for:", start.toString()));

        idIteration++;
        /*
         * tableau les elements visites par dijkstra (les gris)
         */
        ArrayList<IntersectionWrapper> visites = new ArrayList<>();
        /*
         * list containing wrappers for ends
         * it's used to accelerate the research of wrappers when rebuilding path
         */
        ArrayList<IntersectionWrapper> endWrappers = new ArrayList<>();

        if (roadMap.getIntersectionCount() > 0) {


            //initialisation
            IntersectionWrapper startW = new IntersectionWrapper(start.getIntersection(), idIteration);
            startW.setAsStart();
            visites.add(startW);
            //calcul
            dijkstra(start, visites, ends, endWrappers);

            //pour chaque livraison, on construit le path
            reconstitutePathFromDijkstra(start, ends, endWrappers, sortie);
        }
        return sortie;
    }

    /**
     * Fonction statique qui renvoie le
     * plus court chemin de start a end.
     *
     * @param start intersection de depart
     * @param end   arrivee
     * @return liste des plus courts chemins
     */
    public Path shortestPathList(Halt start, Halt end) {
        Set<Halt> ends = new HashSet<>();
        ends.add(end);
        start.getIntersection().resetWrapper();
        //end.getIntersection().resetWrapper();

        ArrayList<Path> sorties = shortestPathList(start, ends);
        //il y a un seul trajet normalement
        return sorties.get(0);
    }

    /**
     * function building path from the results of Dijkstra's algorithm.
     *
     * @param start       Path's starting point
     * @param ends        set of deliveries from which it creates the paths
     * @param output      list of paths modified by this function
     * @param endWrappers one of Dijkstra's algo output, containing the list of intersection wrappers for the ends
     */
    public void reconstitutePathFromDijkstra(Halt start, Set<? extends Halt> ends,
                                             ArrayList<IntersectionWrapper> endWrappers,
                                             ArrayList<Path> output) {
        for (Halt end : ends) {
            if (!end.equals(start)) {
                Path t = new Path(start, end);

                //on recupere le wrapper correspondant a la livraison d'arrivee
                IntersectionWrapper endWrapper = findIntersectionWrapper(endWrappers, end.getIntersection());
                if (endWrapper == null) {
                    LOGGER.warning(MessageFormat.format("no path found for: %s",
                            start.toString() + " and " + end.toString()));
                }
                //on remonte les prececesseurs pour obtenir tout les chemins
                while (endWrapper != null && endWrapper.getPredecessor() != null) {
                    Vector tr = endWrapper.getIncomingVector();
                    t.addVectorBefore(tr);
                    endWrapper = endWrapper.getPredecessor();
                }
                output.add(t);

            }
        }
    }


    /**
     * internal function searching for the wrapper for an intersection.
     *
     * @param intersections list of wrappers
     * @param i             intersection concerned
     * @return the end's wrapper
     */
    private IntersectionWrapper findIntersectionWrapper(ArrayList<IntersectionWrapper> intersections, Intersection i) {

        for (IntersectionWrapper iw : intersections) {
            if (iw.getIntersection().equals(i)) {
                return iw;
            }
        }
        return null;
    }


    /**
     * internal function checking if a wrapper wrap one of the ends.
     *
     * @param ends                list of halts
     * @param intersectionWrapper wrapper concerned
     * @return the result of the check
     */
    private boolean isInEnd(Set<? extends Halt> ends, IntersectionWrapper intersectionWrapper) {

        for (Halt end : ends) {
            if (intersectionWrapper.getIntersection().equals(end.getIntersection())) {
                return true;
            }
        }
        return false;
    }


    /**
     * function computing the dijkstra algorithm.
     *
     * @param start       start halt for this execution of Dijkstra
     * @param visits      list of visited intersections wrappers
     * @param ends        list of halts where we want to go
     * @param endWrappers list of their wrappers
     */
    public void dijkstra(Halt start, ArrayList<IntersectionWrapper> visits,
                         Set<? extends Halt> ends,
                         ArrayList<IntersectionWrapper> endWrappers) {
        LOGGER.info(MessageFormat.format("start Dijkstra algorithm", ""));


        boolean continueDijkstra = true;
        boolean firstTime = true;
        int indexNouvelleVisite = 0;
        IntersectionWrapper courant;
        while (continueDijkstra) {
            //recherche du sommet de plus petit temps et non visite
            float tempsMin = Float.MAX_VALUE;
            if (!firstTime) {
                indexNouvelleVisite = -1;
                for (int i = 0; i < visits.size(); i++) {
                    if (!visits.get(i).isBlack()) {

                        if (visits.get(i).getTempsDijkstra() < tempsMin) {
                            indexNouvelleVisite = i;
                            tempsMin = visits.get(i).getTempsDijkstra();
                        }

                    }
                }
            } else {
                firstTime = false;
            }
            if (indexNouvelleVisite != -1) {
                //on change son etat
                visits.get(indexNouvelleVisite).setBlack(true);

                //on relache tout les arcs
                // partant de cette intersection
                courant = visits.get(indexNouvelleVisite);
                Collection<Vector> cheminsPartants =
                        roadMap.getVectorsFromIntersection(courant.getIntersection());

                for (Vector t : cheminsPartants) {
                    Intersection successeur = t.getDestination();
                    if (successeur.getWrapper() != null && successeur.getWrapper().getIdIteration() == idIteration) {
                        //cas ou l'intersection a deja ete visitee pendant cette execution
                        IntersectionWrapper successeurWrapper = successeur.getWrapper();


                        if (!successeurWrapper.isBlack()) {
                            if (successeurWrapper.getTempsDijkstra()
                                    >= courant.getTempsDijkstra()
                                    + t.getTimeToTravel()) {
                                //on stocke le predecesseur
                                successeurWrapper.setPredecessor(courant);
                                //et le vector qui les separe
                                successeurWrapper.setIncomingVector(t);
                                //on relache l'arc
                                successeurWrapper.setTempsDijkstra(
                                        courant.getTempsDijkstra()
                                                + t.getTimeToTravel());
                            }
                            //on ne change pas le temps
                        }
                    } else {
                        //cas ou l'intersection n'a pas ete visitee

                        //on cree le wrapper, et on l'ajoute a la liste des visites
                        IntersectionWrapper successeurWrapper = new IntersectionWrapper(successeur, idIteration);
                        visits.add(successeurWrapper);
                        if (isInEnd(ends, successeurWrapper)) {
                            endWrappers.add(successeurWrapper);
                        }
                        //on stocke le predecesseur
                        successeurWrapper.setPredecessor(courant);
                        //et le vector qui les separe
                        successeurWrapper.setIncomingVector(t);
                        //on relache l'arc
                        successeurWrapper.setTempsDijkstra(
                                courant.getTempsDijkstra()
                                        + t.getTimeToTravel());
                    }
                }
            } else {
                continueDijkstra = false;
            }
        }
    }

    /**
     * getter for the simplified graph.
     *
     * @return the graph
     */
    public Map<Halt, ArrayList<Path>> getGraph() {
        return graph;
    }
}
