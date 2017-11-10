package lhexanome.optimodlivraison.platform.compute.tsp;
import lhexanome.optimodlivraison.platform.compute.tsp.IteratorSeq;
import lhexanome.optimodlivraison.platform.compute.tsp.TemplateTSPwSlots;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * heuristique 1.
 */
public class TSP2 extends TemplateTSPwSlots {

    /**
     * iterator de base.
     *
     * @param sommetCrt
     * @param nonVus    : tableau des sommets restant a visiter
     * @param cout      : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
     * @param duree     : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
     * @return un iterateur classique
     */
    @Override
    protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
        return new IteratorSeq(nonVus, sommetCrt);
    }

    /**
     * calcule le plus faible cout d'ajout d'un sommet (le cout du déplacement et de l'arrêt sur place).
     *
     * @param sommetCourant
     * @param nonVus        : tableau des sommets restant a visiter
     * @param cout          : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
     * @param duree         : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
     * @return le cout le plus faible parmi les sommets atteignables
     */
    @Override
    public int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
        int coutMin = Integer.MAX_VALUE;
        int coutCurrent = 0;
        for (int i = 0; i < nonVus.size(); i++) {
            coutCurrent = cout[sommetCourant][i] + duree[i];
            if (coutMin > coutCurrent) {
                coutMin = coutCurrent;
            }
        }
        return coutMin;
    }
}
