package lhexanome.optimodlivraison.platform.compute.tsp;

import lhexanome.optimodlivraison.platform.compute.AdjacencyMatrix;
import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import java.util.logging.Logger;

/**
 * Abstract class that add the basis for the TSP algorithm.
 */
public abstract class TemplateTSPwSlots implements TSPwSlots {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SimplifiedMap.class.getName());
    /**
     *  list of nodes indexes giving the best solution found.
     */
    private Integer[] bestSolution;
    /**
     * estimated dates for the nodes in the best solution.
     */
    private Date[] estimatedDates;

    /**
     * sum of costs in the best solution list.
     */
    private int costBestSolution = 0;
    /**
     * if the timeout given for the algorithm has been reached.
     */
    private Boolean timeoutReached;

    /**
     * getter for timeoutReached.
     * @return if the timeout has been reached
     */
    public Boolean getTimeoutReached() {
        return timeoutReached;
    }


    @Override
    public void init(int nbNodes) {
        costBestSolution = Integer.MAX_VALUE;
        bestSolution = new Integer[nbNodes];
        this.estimatedDates = new Date[nbNodes];
    }


    @Override
    @SuppressWarnings("checkstyle:parameternumber")
    public void searchSolution(Tour tour, AdjacencyMatrix matrix, int tpsLimit, int nbNodes, int[][] cost,
                               TimeSlot[] slots, Date start, int[] duration) {

        timeoutReached = false;
        init(nbNodes);
        Date[] tempDates = new Date[nbNodes];
        ArrayList<Integer> nonVus = new ArrayList<>();
        for (int i = 1; i < nbNodes; i++) nonVus.add(i);
        ArrayList<Integer> vus = new ArrayList<>(nbNodes);
        vus.add(0); // le premier sommet visite est 0
        branchAndBound(tour, matrix, 0, nonVus, vus, 0, cost, slots, start,
                tempDates, duration, System.currentTimeMillis(), tpsLimit);
    }

    /**
     * compute back the results to the tour.
     *
     * @param tour   tour to obtain
     * @param matrix storage for the correspondence between indexes and objects
     */
    private void computeResults(Tour tour, AdjacencyMatrix matrix) {

        Path[][] matriceTrajets = matrix.getPathMatrix();
        int indexDepart, indexArrivee;
        indexDepart = this.getMeilleureSolution(0);
        ArrayList<Path> deliveries = new ArrayList<>(bestSolution.length);

        for (int i = 1; i < bestSolution.length; i++) {
            indexArrivee = this.getMeilleureSolution(i);
            Path trajet = matriceTrajets[indexDepart][indexArrivee];
            trajet.getEnd().setEstimateDate(this.getEstimatedDate(indexArrivee));
            deliveries.add(trajet);
            indexDepart = indexArrivee;
        }
        Path trajet = matriceTrajets[indexDepart][0];
        trajet.getEnd().setEstimateDate(this.getEstimatedDate(0));
        deliveries.add(trajet); //retour entrepot

        tour.setTime(this.costBestSolution);
        tour.setPaths(deliveries);

        tour.forceNotifyObservers();
        LOGGER.info("TSP solution found");

        if (Thread.interrupted()) throw new CancellationException("Task was cancelled");
    }

    @Override
    public Integer getMeilleureSolution(int i) {
        if ((bestSolution == null) || (i < 0) || (i >= bestSolution.length)) {
            return null;
        }
        return bestSolution[i];
    }

    @Override
    public Date getEstimatedDate(int i) {
        if ((estimatedDates == null) || (i < 0) || (i >= estimatedDates.length)) {
            return null;
        }
        return estimatedDates[i];
    }

    @Override
    public int getCostBestSolution() {
        return costBestSolution;
    }

    /**
     * Cost function to add heuristics in the TSP.
     * This have to be overridden by sub-classes of TemplateTSP.
     *
     * @param currentNode current node in the iteration
     * @param notViewed        : list of nodes having still to be called
     * @param cost          : cost[i][j] = duration from i to j, with 0 <= i < nbNodes et 0 <= j < nbNodes
     * @param duration         : duration[i] = duration for node i, with 0 <= i < nbNodes
     * @return infimum for permutations cost starting with currentNode,
     * all nodes in notViewed only once and ending with node 0
     */
    protected abstract int bound(Integer currentNode, ArrayList<Integer> notViewed, int[][] cost, int[] duration);

    /**
     * This have to be overridden by sub-classes of TemplateTSP.
     *
     * @param currentNode current node in the iteration
     * @param notViewed        : list of nodes having still to be called
     * @param cost          : cost[i][j] = duration from i to j, with 0 <= i < nbNodes et 0 <= j < nbNodes
     * @param duration         : duration[i] = duration for node i, with 0 <= i < nbNodes
     * @return iterator go through all not viewed nodes
     */
    protected abstract Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> notViewed,
                                                  int[][] cost, int[] duration);

    /**
     * Template for a branch and bound resolution for the TSP.
     * @param tour global tour that needs to be computed by the TSP.
     * @param matrix matrix storing data giving links between indexes and nodes.
     * @param currentNode current node in the iteration
     * @param notViewed        : list of nodes having still to be called
     * @param cost          : cost[i][j] = duration from i to j, with 0 <= i < nbNodes et 0 <= j < nbNodes
     * @param viewed       list of nodes having already being called
     * @param costViewed   sum of all cost for nodes in viewed + durations
     * @param slots    slots to respect at each node
     * @param duration     : duration[i] = duration pour visiter le sommet i, avec 0 <= i < nbSommets
     * @param start    : date de start du dernier sommet
       @param tempDates : list of temporary estimated dates
     * @param tpsStart  : resolution start
     * @param tpsLimit : timeout for resolution
     */
    @SuppressWarnings("checkstyle:parameternumber")
    void branchAndBound(Tour tour, AdjacencyMatrix matrix, int currentNode, ArrayList<Integer> notViewed,
                        ArrayList<Integer> viewed, int costViewed, int[][] cost,
                        TimeSlot[] slots, Date start, Date[] tempDates, int[] duration,
                        long tpsStart, int tpsLimit) {
        if (System.currentTimeMillis() - tpsStart > tpsLimit) {
            timeoutReached = true;
            LOGGER.info("Timeout");
            computeResults(tour, matrix);
            return;
        }

        if (notViewed.size() == 0) { // all nodes have been visited
            Date nextStart = new Date();
            nextStart.setTime(start.getTime() + cost[currentNode][0] * 1000 + duration[currentNode] * 1000);
            costViewed = (int) TimeSlot.getTimescaleBetween(tour.getStart(), nextStart) / 1000;
            tempDates[0] = nextStart;
            if (costViewed < costBestSolution) { // we've found a better solution than the current one
                viewed.toArray(bestSolution);
                estimatedDates = new Date[tempDates.length];

                System.arraycopy(tempDates, 0, estimatedDates, 0, tempDates.length);

                costBestSolution = costViewed;
                computeResults(tour, matrix);
            }
        } else if (costViewed + bound(currentNode, notViewed, cost, duration) < costBestSolution) {
            Iterator<Integer> it = iterator(currentNode, notViewed, cost, duration);

            while (it.hasNext()) {
                Integer nextNode = it.next();
                //max waiting time to get to the slot
                //taking into account the time to deliver at the next node
                // if the slot is not reachable, waitingTime<=0
                TimeSlot nextSlot = slots[nextNode];
                long waitingTime = 0;
                boolean canGo = true;
                if (nextSlot != null) {
                    //warning cost is in sec
                    waitingTime = TimeSlot.getTimescaleBetween(start.getTime()
                                    + cost[currentNode][nextNode] * 1000
                                    + duration[currentNode] * 1000 + duration[nextNode] * 1000,
                            nextSlot.getEnd());

                    canGo = waitingTime >= 0;
                    if (canGo) {
                        //cette fois on prend le temps minimal et on le minore à 0
                        //ca permet d'obtenir le temps d'attente avant l'ouverture de la plage s'il y en a
                        waitingTime = Math.max(0, TimeSlot.getTimescaleBetween(start.getTime()
                                        + cost[currentNode][nextNode] * 1000 + duration[currentNode] * 1000,
                                slots[nextNode].getStart()));

                    }
                }
                if (canGo) {
                    viewed.add(nextNode);
                    notViewed.remove(nextNode);
                    Date prochainDepart = new Date();
                    Date dateEstimee = new Date();

                    int newCout = 0;
                    if (waitingTime > 0) {
                        //on doit attendre, donc on arrive au debut de la plage horaire
                        //
                        //nextSlot !=null car waitingTime>0
                        prochainDepart.setTime(nextSlot.getStart().getTime());
                        newCout = (int) ((waitingTime / 1000)
                                + cost[currentNode][nextNode] + duration[currentNode]);
                    } else {
                        prochainDepart.setTime(start.getTime() + cost[currentNode][nextNode] * 1000
                                + duration[currentNode] * 1000);
                        newCout = cost[currentNode][nextNode] + duration[currentNode];
                    }
                    //la date estimee differe du prochainDepart uniquement
                    // lorsqu'on doit attendre pour une plage horaire
                    dateEstimee.setTime(start.getTime()
                            + cost[currentNode][nextNode] * 1000 + duration[currentNode] * 1000);
                    tempDates[nextNode] = dateEstimee;
                    branchAndBound(tour, matrix, nextNode, notViewed, viewed, costViewed + newCout,
                            cost, slots, prochainDepart, tempDates, duration, tpsStart, tpsLimit);
                    viewed.remove(nextNode);
                    notViewed.add(nextNode);
                }
            }
        }
    }
}

