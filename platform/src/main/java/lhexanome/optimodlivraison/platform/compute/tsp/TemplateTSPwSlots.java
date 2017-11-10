package lhexanome.optimodlivraison.platform.compute.tsp;

import lhexanome.optimodlivraison.platform.compute.MatriceAdjacence;
import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

//CHECKSTYLE:OFF
public abstract class TemplateTSPwSlots implements TSPwSlots {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(SimplifiedMap.class.getName());
    private Integer[] meilleureSolution;
    private Date[] datesEstimees;

    private int coutMeilleureSolution = 0;
    private Boolean tempsLimiteAtteint;

    public Boolean getTempsLimiteAtteint() {
        return tempsLimiteAtteint;
    }

    @Override
    public void init(int nbSommets) {
        coutMeilleureSolution = Integer.MAX_VALUE;
        meilleureSolution = new Integer[nbSommets];
        this.datesEstimees = new Date[nbSommets];
    }


    @Override
    public void chercheSolution(Tour tour, MatriceAdjacence matrix, int tpsLimite, int nbSommets, int[][] cout, TimeSlot[] plages, Date depart, int[] duree) {

        tempsLimiteAtteint = false;
        init(nbSommets);
        Date[] tempDates = new Date[nbSommets];
        ArrayList<Integer> nonVus = new ArrayList<>();
        for (int i = 1; i < nbSommets; i++) nonVus.add(i);
        ArrayList<Integer> vus = new ArrayList<>(nbSommets);
        vus.add(0); // le premier sommet visite est 0
        branchAndBound(tour, matrix, 0, nonVus, vus, 0, cout, plages, depart, tempDates, duree, System.currentTimeMillis(), tpsLimite);
    }

    /**
     * compute back the results to the tour.
     *
     * @param tour   tour to obtain
     * @param matrix storage for the correspondence between indexes and objects
     */
    private void computeResults(Tour tour, MatriceAdjacence matrix) {

        Path[][] matriceTrajets = matrix.getMatricePaths();
        int indexDepart, indexArrivee;
        indexDepart = this.getMeilleureSolution(0);
        ArrayList<Path> deliveries = new ArrayList<>(meilleureSolution.length);

        for (int i = 1; i < meilleureSolution.length; i++) {
            indexArrivee = this.getMeilleureSolution(i);
            Path trajet = matriceTrajets[indexDepart][indexArrivee];
            trajet.getEnd().setEstimateDate(this.getDateEstimee(indexArrivee));
            deliveries.add(trajet);
            indexDepart = indexArrivee;
        }
        Path trajet = matriceTrajets[indexDepart][0];
        trajet.getEnd().setEstimateDate(this.getDateEstimee(0));
        deliveries.add(trajet); //retour entrepot

        tour.setTime(this.coutMeilleureSolution);
        tour.setPaths(deliveries);

        tour.forceNotifyObservers();
        LOGGER.info("TSP solution found");

    }

    @Override
    public Integer getMeilleureSolution(int i) {
        if ((meilleureSolution == null) || (i < 0) || (i >= meilleureSolution.length)) {
            return null;
        }
        return meilleureSolution[i];
    }

    @Override
    public Date getDateEstimee(int i) {
        if ((datesEstimees == null) || (i < 0) || (i >= datesEstimees.length)) {
            return null;
        }
        return datesEstimees[i];
    }

    @Override
    public int getCoutMeilleureSolution() {
        return coutMeilleureSolution;
    }

    /**
     * Methode devant etre redefinie par les sous-classes de TemplateTSP
     *
     * @param sommetCourant
     * @param nonVus        : tableau des sommets restant a visiter
     * @param cout          : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
     * @param duree         : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
     * @return une borne inferieure du cout des permutations commencant par sommetCourant,
     * contenant chaque sommet de nonVus exactement une fois et terminant par le sommet 0
     */
    protected abstract int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree);

    /**
     * Methode devant etre redefinie par les sous-classes de TemplateTSP
     *
     * @param sommetCrt
     * @param nonVus    : tableau des sommets restant a visiter
     * @param cout      : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
     * @param duree     : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
     * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
     */
    protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree);

    /**
     * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
     *
     * @param sommetCrt le dernier sommet visite
     * @param nonVus    la liste des sommets qui n'ont pas encore ete visites
     * @param vus       la liste des sommets visites (y compris sommetCrt)
     * @param coutVus   la somme des couts des arcs du chemin passant par tous les sommets de vus + la somme des duree des sommets de vus
     * @param cout      : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
     * @param plages    plages horaires à respecter pour chaque intersection
     * @param duree     : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
     * @param depart    : date de depart du dernier sommet
     * @param tpsDebut  : moment ou la resolution a commence
     * @param tpsLimite : limite de temps pour la resolution
     */
    void branchAndBound(Tour tour, MatriceAdjacence matrix, int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout,
                        TimeSlot[] plages, Date depart, Date[] tempDates, int[] duree, long tpsDebut, int tpsLimite) {
        if (System.currentTimeMillis() - tpsDebut > tpsLimite) {
            tempsLimiteAtteint = true;
            LOGGER.info("Timeout");
            computeResults(tour, matrix);
            return;
        }

        if (nonVus.size() == 0) { // tous les sommets ont ete visites
            Date prochainDepart = new Date();
            prochainDepart.setTime(depart.getTime() + cout[sommetCrt][0] * 1000 + duree[sommetCrt] * 1000);
            coutVus = (int) TimeSlot.getTimescaleBetween(tour.getStart(), prochainDepart) / 1000;
            tempDates[0] = prochainDepart;
            if (coutVus < coutMeilleureSolution) { // on a trouve une solution meilleure que meilleureSolution
                vus.toArray(meilleureSolution);
                datesEstimees = new Date[tempDates.length];

                System.arraycopy(tempDates, 0, datesEstimees, 0, tempDates.length);

                coutMeilleureSolution = coutVus;
                computeResults(tour, matrix);
            }
        } else if (coutVus + bound(sommetCrt, nonVus, cout, duree) < coutMeilleureSolution) {
            Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);

            while (it.hasNext()) {
                Integer prochainSommet = it.next();
                //temps d'attente maximal pour atteindre la plage
                //taking into account the time to deliver at the next node
                // si la plage est inatteignable, la valeur est négative
                TimeSlot prochainSlot = plages[prochainSommet];
                long tempsAttente = 0;
                boolean canGo = true;
                if (prochainSlot != null) {
                    //attention le temps donné par le cout est en seconde
                    tempsAttente = TimeSlot.getTimescaleBetween(depart.getTime() + cout[sommetCrt][prochainSommet] * 1000
                                    + duree[sommetCrt] * 1000 + duree[prochainSommet] * 1000,
                            prochainSlot.getEnd());

                    canGo = tempsAttente >= 0;
                    if (canGo) {
                        //cette fois on prend le temps minimal et on le minore à 0
                        //ca permet d'obtenir le temps d'attente avant l'ouverture de la plage s'il y en a
                        tempsAttente = Math.max(0, TimeSlot.getTimescaleBetween(depart.getTime()
                                        + cout[sommetCrt][prochainSommet] * 1000 + duree[sommetCrt] * 1000,
                                plages[prochainSommet].getStart()));

                    }
                }
                if (canGo) {
                    vus.add(prochainSommet);
                    nonVus.remove(prochainSommet);
                    Date prochainDepart = new Date();
                    Date dateEstimee = new Date();

                    int newCout = 0;
                    if (tempsAttente > 0) {
                        //on doit attendre, donc on arrive au debut de la plage horaire
                        //
                        //prochainSlot !=null car tempsAttente>0
                        prochainDepart.setTime(prochainSlot.getStart().getTime());
                        newCout = (int) ((tempsAttente / 1000) + cout[sommetCrt][prochainSommet] + duree[sommetCrt]);
                    } else {
                        prochainDepart.setTime(depart.getTime() + cout[sommetCrt][prochainSommet] * 1000 + duree[sommetCrt] * 1000);
                        newCout = cout[sommetCrt][prochainSommet] + duree[sommetCrt];
                    }
                    //la date estimee differe du prochainDepart uniquement lorsqu'on doit attendre pour une plage horaire
                    dateEstimee.setTime(depart.getTime() + cout[sommetCrt][prochainSommet] * 1000 + duree[sommetCrt] * 1000);
                    tempDates[prochainSommet] = dateEstimee;
                    branchAndBound(tour, matrix, prochainSommet, nonVus, vus, coutVus + newCout,
                            cout, plages, prochainDepart, tempDates, duree, tpsDebut, tpsLimite);
                    vus.remove(prochainSommet);
                    nonVus.add(prochainSommet);
                }
            }
        }
    }
}

