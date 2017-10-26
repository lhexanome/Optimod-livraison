package lhexanome.optimodlivraison.platform.compute;


import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;

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

    /***
     * constructeur par defaut.
     */
    public SimplifiedMap() {
        roadMap = new RoadMap();
        deliveryOrder = new DeliveryOrder();
        graph = new HashMap<>();
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
        LOGGER.info(MessageFormat.format("compute simplified graph", ""));

        HashSet<Halt> ptsHalt = new HashSet<>();
        ptsHalt.addAll(deliveryOrder.getDeliveries());
        ptsHalt.add(deliveryOrder.getBeginning());
        for (Halt s : ptsHalt) {
            ArrayList<Path> listePaths =
                    shortestPathList(s, ptsHalt);

            graph.put(s, listePaths);
        }
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


        /*
         * tableau les elements visites par dijkstra (les gris)
         */
        ArrayList<IntersectionWrapper> visites = new ArrayList<>();
        /**
         * list containing wrappers for ends
         * it's used to accelerate the research of wrappers when rebuilding path
         */
        ArrayList<IntersectionWrapper> endWrappers = new ArrayList<>();

        if (roadMap.getIntersectionCount() > 0) {


            //initialisation
            IntersectionWrapper startW = new IntersectionWrapper(start.getIntersection());
            startW.setAsStart();
            visites.add(startW);
            //calcul
            dijkstra(visites, ends, endWrappers);

            //pour chaque livraison, on construit le path
            reconstitutePathFromDijkstra(start, ends, endWrappers, sortie);
        }
        return sortie;
    }

    /**
     * function building path from the results of Dijkstra's algorithm.
     *
     * @param start       Path's starting point
     * @param ends        set of deliveries from which it creates the paths
     * @param sortie      list of paths modified by this function
     * @param endWrappers one of Dijkstra's algo output, containing the list of intersection wrappers for the ends
     */
    public void reconstitutePathFromDijkstra(Halt start, Set<? extends Halt> ends,
                                             ArrayList<IntersectionWrapper> endWrappers,
                                             ArrayList<Path> sortie) {
        for (Halt end : ends) {
            if (!end.equals(start)) {
                Path t = new Path();

                //on recupere le wrapper correspondant a la livraison d'arrivee
                IntersectionWrapper endWrapper = findIntersectionWrapper(endWrappers, end);
                if (endWrapper == null) {
                    LOGGER.warning(MessageFormat.format("no path found for:",
                            start.toString() + " and " + end.toString()));
                }
                //on remonte les prececesseurs pour obtenir tout les chemins
                while (endWrapper != null) {
                    //TODO ajouter le debut et la fin au path
                    Vector tr = endWrapper.getCheminArrivant();
                    t.addTronconBefore(tr);
                    endWrapper = endWrapper.getPredecesseur();
                }
                sortie.add(t);

            }
        }
    }

    /**
     * internal function searching for the wrapper for the end.
     *
     * @param intersections list of wrappers
     * @param end           halt concerned
     * @return the end's wrapper
     */
    private IntersectionWrapper findIntersectionWrapper(ArrayList<IntersectionWrapper> intersections, Halt end) {

        for (IntersectionWrapper iw : intersections) {
            if (iw.getIntersection().equals(end.getIntersection())) {
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
     * @param visites      list of visited intersections wrappers
     * @param ends         list of halts where we want to go
     * @param endWrappers list of their wrappers
     */
    public void dijkstra(ArrayList<IntersectionWrapper> visites,
                         Set<? extends Halt> ends,
                         ArrayList<IntersectionWrapper> endWrappers) {
        LOGGER.info(MessageFormat.format("start Dijkstra algorithm", ""));


        boolean continueDijkstra = true;
        int indexNouvelleVisite = 0;
        IntersectionWrapper courant = visites.get(visites.size() - 1);
        while (continueDijkstra) {
            //recherche du sommet de plus petit temps et non visite
            float tempsMin = Float.MAX_VALUE;
            indexNouvelleVisite = -1;
            for (int i = 0; i < visites.size(); i++) {
                if (!visites.get(i).isBlack()) {

                    if (visites.get(i).getTempsDijkstra() < tempsMin) {
                        indexNouvelleVisite = i;
                        tempsMin = visites.get(i).getTempsDijkstra();
                    }

                }
            }
            if (indexNouvelleVisite != -1) {
                //on change son etat
                visites.get(indexNouvelleVisite).setBlack(true);

                //on relache tout les arcs
                // partant de cette intersection
                courant = visites.get(indexNouvelleVisite);
                Collection<Vector> cheminsPartants =
                        roadMap.getTronconsFromIntersection(courant.getIntersection());

                for (Vector t : cheminsPartants) {
                    //pour recuperer les
                    Intersection successeur = t.getDestination();
                    if (courant.isSuccesseurVisite(successeur)) {
                        //cas ou l'intersection a deja ete visitee
                        IntersectionWrapper successeurWrapper = courant.getSuccesseurVisite(successeur);

                        if (!successeurWrapper.isBlack()) {
                            if (successeurWrapper.getTempsDijkstra()
                                    >= courant.getTempsDijkstra()
                                    + t.getTimeToTravel()) {
                                //on stocke le predecesseur
                                successeurWrapper.setPredecesseur(courant);
                                //et le vector qui les separe
                                successeurWrapper.setCheminArrivant(t);
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
                        IntersectionWrapper successeurWrapper = new IntersectionWrapper(successeur);
                        courant.addSuccesseurVisite(successeurWrapper);
                        visites.add(successeurWrapper);
                        if (isInEnd(ends, successeurWrapper)) {
                            endWrappers.add(successeurWrapper);
                        }
                        //on stocke le predecesseur
                        successeurWrapper.setPredecesseur(courant);
                        //et le vector qui les separe
                        successeurWrapper.setCheminArrivant(t);
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
    public java.util.Map getGraph() {
        return graph;
    }
}
