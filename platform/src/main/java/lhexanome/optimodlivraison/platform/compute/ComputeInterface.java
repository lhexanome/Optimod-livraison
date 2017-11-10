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
 * Class allowing interaction with compute module.
 */
public class ComputeInterface {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SimplifiedMap.class.getName());

    /**
     * Algorithm max execution time (in milliseconds).
     */
    public static final int TIMEOUT = Integer.MAX_VALUE;

    /**
     * Observed tour.
     */
    private Tour tour;

    /**
     * Empty constructor.
     */
    public ComputeInterface() {
        this.tour = new Tour();
    }

    /**
     * Generates lowest paths
     * Generates the graph of the shortest paths between deliveries.
     *
     * @param roadMap       General map concerning the request.
     * @param deliveryOrder Delivery order to treat.
     * @return Simplified road map containing paths linking delivery points.
     */
    public SimplifiedMap computeSimplifiedRoadMap(RoadMap roadMap, DeliveryOrder deliveryOrder) {
        SimplifiedMap simplifiedMap = new SimplifiedMap(deliveryOrder, roadMap);
        simplifiedMap.computeGraph();
        return simplifiedMap;
    }

    /**
     * Computes the optimal tour depending on the simplified map and the delivery order.
     *
     * @param simplifiedMap Previously computed simplified map.
     * @param deliveryOrder The delivery order.
     * @return Computed tour.
     * @throws ComputeSlotsException when the slots are incompatible.
     */
    public Tour computeTour(SimplifiedMap simplifiedMap,
                            DeliveryOrder deliveryOrder) throws ComputeSlotsException {
        Warehouse warehouse;
        Date start;

        Map<Halt, ArrayList<Path>> graph = simplifiedMap.getGraph();

        warehouse = deliveryOrder.getWarehouse();
        start = deliveryOrder.getStart();

        tour.setWarehouse(warehouse);
        tour.setStart(start);

        int vertexQuantity = deliveryOrder.getDeliveries().size() + 1;

        //sert à assigner chaque sommet à un index.
        ArrayList<Halt> vertexList = new ArrayList<>();
        vertexList.add(deliveryOrder.getWarehouse());
        vertexList.addAll(deliveryOrder.getDeliveries());

        AdjacencyMatrix matrix = grapheToMatrix(graph, vertexQuantity, vertexList);

        int[] weightList = orderToWeights(deliveryOrder, vertexQuantity, vertexList);


        TimeSlot[] slots = new TimeSlot[vertexQuantity];
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i) instanceof Delivery) {
                slots[i] = ((Delivery) vertexList.get(i)).getSlot();
            } else {
                slots[i] = null;
            }
        }
        TSPwSlots tsp = new TSP2wSlots();


        tsp.searchSolution(tour, matrix, TIMEOUT, vertexQuantity, matrix.getWeightMatrix(),
                slots, deliveryOrder.getStart(), weightList);

        if (tour.getPaths() == null) {
            throw new ComputeSlotsException("Can't compute tour because of incompatible slots");

        }
        LOGGER.info("TSP finished");
        return tour;

    }

    /**
     * Generates adjacency matrix based on the graph,
     * to be treated by TSP resolution algorithm.
     *
     * @param graph          Graph to be treated.
     * @param vertexQuantity Number of graph's vertices.
     * @param vertexList     List associating each vertex to its index.
     * @return AdjacencyMatrix containing :
     * The matrix gathering the best path between each vertex.
     * The matrix gathering each path's weight.
     */

    private AdjacencyMatrix grapheToMatrix(Map<Halt, ArrayList<Path>> graph, int vertexQuantity,
                                           ArrayList<Halt> vertexList) {

        int[][] weightMatrix = new int[vertexQuantity][vertexQuantity];
        Path[][] pathMatrix = new Path[vertexQuantity][vertexQuantity];

        AdjacencyMatrix matrix = new AdjacencyMatrix(vertexList, pathMatrix, weightMatrix);
        matrix.initMatrix(graph);
        return matrix;
    }

    /**
     * Creates vertices' weight list.
     *
     * @param deliveryOrder  Delivery order to treat.
     * @param vertexQuantity Number of graph's vertices.
     * @param vertexList     List associating each vertex to its index.
     * @return Vertex weight list.
     */

    private int[] orderToWeights(DeliveryOrder deliveryOrder, int vertexQuantity, ArrayList<Halt> vertexList) {
        int[] weightList = new int[vertexQuantity];

        //warehouse
        weightList[0] = 0;

        //others
        for (Halt halt : deliveryOrder.getDeliveries()) {
            int index = vertexList.indexOf(halt);
            weightList[index] = ((Delivery) halt).getDuration();
        }

        return weightList;
    }

    /**
     * Adds a tour observer.
     *
     * @param o Observer
     */
    public void addTourObserver(Observer o) {
        this.tour.addObserver(o);
    }
}
