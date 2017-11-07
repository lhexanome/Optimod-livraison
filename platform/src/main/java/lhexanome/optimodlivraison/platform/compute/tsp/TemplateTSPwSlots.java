package lhexanome.optimodlivraison.platform.compute.tsp;

import lhexanome.optimodlivraison.platform.models.TimeSlot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

//CHECKSTYLE:OFF
public abstract class TemplateTSPwSlots implements TSPwSlots {

    private Integer[] meilleureSolution;
    private Date[] datesEstimees;
    private int coutMeilleureSolution = 0;
    private Boolean tempsLimiteAtteint;

    public Boolean getTempsLimiteAtteint() {
        return tempsLimiteAtteint;
    }

    public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, TimeSlot[] plages, Date depart, int[] duree) {
        tempsLimiteAtteint = false;
        coutMeilleureSolution = Integer.MAX_VALUE;
        meilleureSolution = new Integer[nbSommets];
        this.datesEstimees=new Date[nbSommets];
        Date[] tempDates=new Date[nbSommets];
        ArrayList<Integer> nonVus = new ArrayList<Integer>();
        for (int i = 1; i < nbSommets; i++) nonVus.add(i);
        ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
        vus.add(0); // le premier sommet visite est 0
        branchAndBound(0, nonVus, vus, 0, cout, plages, depart, tempDates, duree, System.currentTimeMillis(), tpsLimite);
    }

    public Integer getMeilleureSolution(int i) {
        if ((meilleureSolution == null) || (i < 0) || (i >= meilleureSolution.length))
            return null;
        return meilleureSolution[i];
    }
    public Date getDateEstimee(int i) {
        if ((datesEstimees == null) || (i < 0) || (i >= datesEstimees.length))
            return null;
        return datesEstimees[i];
    }

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
    void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout,
                        TimeSlot[] plages, Date depart, Date[] tempDates, int[] duree, long tpsDebut, int tpsLimite) {
        if (System.currentTimeMillis() - tpsDebut > tpsLimite) {
            tempsLimiteAtteint = true;
            return;
        }

        if (nonVus.size() == 0) { // tous les sommets ont ete visites
            coutVus += cout[sommetCrt][0];
            Date prochainDepart = new Date();
            prochainDepart.setTime(depart.getTime() + cout[sommetCrt][0] * 1000);
            tempDates[0] = prochainDepart;
            if (coutVus < coutMeilleureSolution) { // on a trouve une solution meilleure que meilleureSolution
                vus.toArray(meilleureSolution);
                datesEstimees=new Date[tempDates.length];
                for(int i = 0;i<tempDates.length;i++){
                    datesEstimees[i]=tempDates[i];
                }
                coutMeilleureSolution = coutVus;
            }
        } else if (coutVus + bound(sommetCrt, nonVus, cout, duree) < coutMeilleureSolution) {
            Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);
            long tempsAttente = 0;
            while (it.hasNext()) {
                Integer prochainSommet = it.next();
                //temps d'attente maximal pour atteindre la plage
                // si la plage est inatteignable, la valeur est négative
                TimeSlot prochainSlot = plages[prochainSommet];
                boolean canGo = true;
                if (prochainSlot != null) {
                    //attention le temps donné par le cout est en seconde
                    tempsAttente = TimeSlot.getTimescaleBetween(depart.getTime() + cout[sommetCrt][prochainSommet] * 1000 + duree[prochainSommet] * 1000,
                            plages[prochainSommet].getEnd());

                    canGo = tempsAttente >= 0;
                    if (canGo) {
                        //cette fois on prend le temps minimal et on le minore à 0
                        //ca permet d'obtenir le temps d'attente avant l'ouverture de la plage s'il y en a
                        tempsAttente = Math.max(0, TimeSlot.getTimescaleBetween(depart.getTime() + cout[sommetCrt][prochainSommet] * 1000 + duree[prochainSommet] * 1000, plages[prochainSommet].getStart()));

                    }
                }
                if (canGo) {
                    vus.add(prochainSommet);
                    nonVus.remove(prochainSommet);
                    Date prochainDepart = new Date();
                    prochainDepart.setTime(depart.getTime() + cout[sommetCrt][prochainSommet] * 1000 + duree[prochainSommet] * 1000 + tempsAttente);
                    tempDates[prochainSommet] = prochainDepart;
                    branchAndBound(prochainSommet, nonVus, vus, coutVus + cout[sommetCrt][prochainSommet] + duree[prochainSommet] + (int) (tempsAttente / 1000.0),
                            cout, plages, prochainDepart, tempDates, duree, tpsDebut, tpsLimite);
                    vus.remove(prochainSommet);
                    nonVus.add(prochainSommet);
                }
            }
        }
    }
}

