package lhexanome.optimodlivraison.platform.compute.tsp;

import lhexanome.optimodlivraison.platform.compute.MatriceAdjacence;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;

import java.util.Date;

//CHECKSTYLE:OFF
public interface TSPwSlots {

    /**
     * @return true si chercheSolution() s'est terminee parce que la limite de temps avait ete atteinte, avant d'avoir pu explorer tout l'espace de recherche,
     */
    Boolean getTempsLimiteAtteint();


    /**
     * init the tsp data structures.
     * @param nbSommets number of nodes
     */
    void init(int nbSommets);
    /**
     * Cherche un circuit de duree minimale passant par chaque sommet (compris entre 0 et nbSommets-1)
     *
     * @param tour tour to update
     * @param matrix storage for the data used in the tsp, it's used to compute back the results
     * @param tpsLimite : limite (en millisecondes) sur le temps d'execution de chercheSolution
     * @param nbSommets : nombre de sommets du graphe
     * @param cout      : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
     * @param plages     : plages horaires des sommets
     * @param depart     : date de depart de la recherche
     * @param duree     : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
     */
    void chercheSolution(Tour tour, MatriceAdjacence matrix, int tpsLimite, int nbSommets, int[][] cout, TimeSlot[] plages, Date depart, int[] duree);

    /**
     * @param i
     * @return le sommet visite en i-eme position dans la solution calculee par chercheSolution
     */
    Integer getMeilleureSolution(int i);

    /**
     * @return la duree de la solution calculee par chercheSolution
     */
    int getCoutMeilleureSolution();

    Date getDateEstimee(int i);
}
