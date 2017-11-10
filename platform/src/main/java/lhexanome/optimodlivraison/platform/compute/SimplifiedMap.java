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
 * Classe stocking a simplified roadMap version.
 * It's a directed and complete graph with deliveries as vertices,
 * and shortest paths between these deliveries as edges.
 */
public class SimplifiedMap {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SimplifiedMap.class.getName());

    /**
     * Reference to loaded road map.
     */
    private RoadMap roadMap;

    /**
     * Reference to loaded delivery order.
     */
    private DeliveryOrder deliveryOrder;

    /**
     * Directed graph.
     */
    private Map<Halt, ArrayList<Path>> graph;

    /**
     * id compute iteration.
     */
    private static long idIteration = -1;

    /**
     * Constructor used to do the computing without generating a graph.
     *
     * @param roadMap Roadmap to compute from.
     */
    public SimplifiedMap(RoadMap roadMap) {
        this.roadMap = roadMap;
    }

    /**
     * Constructor initializing the simplified roadmap.
     *
     * @param deliveryOrder Loaded delivery order.
     * @param roadMap       Loaded road map.
     */

    public SimplifiedMap(DeliveryOrder deliveryOrder, RoadMap roadMap) {
        this.roadMap = roadMap;
        this.deliveryOrder = deliveryOrder;
        graph = new HashMap<>();
    }

    /**
     * Computes simplified graph based on the road map.
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
     * Creates tour from all paths in the graph.
     *
     * @return Generated tour.
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
     * Returns shortest paths list.
     * Starting from start and going to each end's delivery.
     *
     * @param start Start intersection.
     * @param ends  Ends list.
     * @return Shortest paths list.
     */
    public ArrayList<Path> shortestPathList(
            Halt start, Set<? extends Halt> ends) {
        ArrayList<Path> sortie = new ArrayList<>();
        LOGGER.info(MessageFormat.format("start compute shortest path for:", start.toString()));

        idIteration++;
        /*
         * list of elements visited by dijkstra (grey ones)
         */
        ArrayList<IntersectionWrapper> visites = new ArrayList<>();
        /*
         * list containing wrappers for ends
         * it's used to accelerate the research of wrappers when rebuilding path
         */
        ArrayList<IntersectionWrapper> endWrappers = new ArrayList<>();

        if (roadMap.getIntersectionCount() > 0) {


            //initialization
            IntersectionWrapper startW = new IntersectionWrapper(start.getIntersection(), start);
            startW.setAsStart();
            visites.add(startW);
            //computing
            dijkstra(start, visites, ends, endWrappers);

            //for each delivery, we build the path
            reconstitutePathFromDijkstra(start, ends, endWrappers, sortie);
        }
        return sortie;
    }

    /**
     * Returns the shortest path from start to end.
     *
     * @param start Start intersection.
     * @param end   End intersection.
     * @return Shortest path.
     */
    public Path shortestPathList(Halt start, Halt end) {
        Set<Halt> ends = new HashSet<>();
        ends.add(end);
        start.getIntersection().resetWrapper();
        //end.getIntersection().resetWrapper();

        ArrayList<Path> sorties = shortestPathList(start, ends);
        //there is normally only one path
        return sorties.get(0);
    }

    /**
     * Function building path from the results of Dijkstra's algorithm.
     *
     * @param start       Path's starting point.
     * @param ends        Set of deliveries from which it creates the paths.
     * @param output      List of paths modified by this function.
     * @param endWrappers One of Dijkstra's algo output, containing the list of intersection wrappers for the ends.
     */
    public void reconstitutePathFromDijkstra(Halt start, Set<? extends Halt> ends,
                                             ArrayList<IntersectionWrapper> endWrappers,
                                             ArrayList<Path> output) {
        for (Halt end : ends) {
            if (!end.equals(start)) {
                Path t = new Path(start, end);

                // we get the wrapper corresponding to the end delivery
                IntersectionWrapper endWrapper = findIntersectionWrapper(endWrappers, end.getIntersection());
                if (endWrapper == null) {
                    LOGGER.warning(MessageFormat.format("no path found for: %s",
                            start.toString() + " and " + end.toString()));
                }
                // we climb back predecessors to get all the paths.
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
     * Internal function searching for the wrapper for an intersection.
     *
     * @param intersections List of wrappers.
     * @param i             Intersection concerned.
     * @return The end's wrapper.
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
     * Internal function checking if a wrapper wrap one of the ends.
     *
     * @param ends                List of halts.
     * @param intersectionWrapper Wrapper concerned.
     * @return The result of the check.
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
     * Function computing the dijkstra algorithm.
     *
     * @param start       Start halt for this execution of Dijkstra.
     * @param visits      List of visited intersections wrappers.
     * @param ends        List of halts where we want to go.
     * @param endWrappers List of their wrappers.
     */
    public void dijkstra(Halt start, ArrayList<IntersectionWrapper> visits,
                         Set<? extends Halt> ends,
                         ArrayList<IntersectionWrapper> endWrappers) {
        LOGGER.info(MessageFormat.format("start Dijkstra algorithm", ""));


        boolean continueDijkstra = true;
        boolean firstTime = true;
        int indexNewVisit = 0;
        IntersectionWrapper current;
        while (continueDijkstra) {
            //search for the lowest time non visited vertex
            float minTime = Float.MAX_VALUE;
            if (!firstTime) {
                indexNewVisit = -1;
                for (int i = 0; i < visits.size(); i++) {
                    if (!visits.get(i).isBlack()) {

                        if (visits.get(i).getDijkstraTime() < minTime) {
                            indexNewVisit = i;
                            minTime = visits.get(i).getDijkstraTime();
                        }

                    }
                }
            } else {
                firstTime = false;
            }
            if (indexNewVisit != -1) {
                //we change its state
                visits.get(indexNewVisit).setBlack(true);

                // we release all arcs
                // from this intersection
                current = visits.get(indexNewVisit);
                Collection<Vector> cheminsPartants =
                        roadMap.getVectorsFromIntersection(current.getIntersection());

                for (Vector t : cheminsPartants) {
                    Intersection successor = t.getDestination();
                    if (successor.getWrapper() != null && successor.getWrapper().getStart() == start) {
                        // case where intersection has already been visited during this run
                        IntersectionWrapper successorWrapper = successor.getWrapper();

                        if (!successorWrapper.isBlack()) {
                            if (successorWrapper.getDijkstraTime()
                                    >= current.getDijkstraTime()
                                    + t.getTimeToTravel()) {
                                // we stock the predecessor
                                successorWrapper.setPredecessor(current);
                                // and the vector separating them
                                successorWrapper.setIncomingVector(t);
                                // we release the arc
                                successorWrapper.setDijkstraTime(
                                        current.getDijkstraTime()
                                                + t.getTimeToTravel());
                            }
                            // we do not change time
                        }
                    } else {
                        // case where intersection has not been visited

                        // we create the wrapper, and add it to the visited list
                        IntersectionWrapper successeurWrapper = new IntersectionWrapper(successor, start);
                        visits.add(successeurWrapper);
                        if (isInEnd(ends, successeurWrapper)) {
                            endWrappers.add(successeurWrapper);
                        }
                        // we stock the predecessor
                        successeurWrapper.setPredecessor(current);
                        // and the vector separating them
                        successeurWrapper.setIncomingVector(t);
                        // we release the arc
                        successeurWrapper.setDijkstraTime(
                                current.getDijkstraTime()
                                        + t.getTimeToTravel());
                    }
                }
            } else {
                continueDijkstra = false;
            }
        }
    }

    /**
     * Getter for the simplified graph.
     *
     * @return The graph
     */
    public Map<Halt, ArrayList<Path>> getGraph() {
        return graph;
    }
}
