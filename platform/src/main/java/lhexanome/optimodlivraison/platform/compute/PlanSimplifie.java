package lhexanome.optimodlivraison.platform.compute;


import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Livraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Trajet;

import java.util.HashSet;

/**
 * classe qui stocke une version simplifiée du plan,
 * soit un graph orienté et complet avec comme sommets les livraisons,
 * et pour arc des trajets formant les plus courts chemins entre ces livraisons.
 */
public class PlanSimplifie {
    /**
     * reference au plan charge (peut etre pas utile).
     */
    private Plan plan = null;

    /**
     * liste des arcs du graphes
     * (soit les plus courts chemins entre les livraisons).
     */
    private HashSet<Trajet> arcs = null;

    /**
     * liste des sommets du graphe.
     */
    private HashSet<Livraison> sommets = null;

    /***
     * constructeur par defaut.
     */
    public PlanSimplifie() {
        plan = new Plan();
        arcs = new HashSet<>();
        sommets = new HashSet<>();
    }

    /**
     * constructeur qui initialise le plan simplifie.
     *
     * @param demandeLivraison la demande de livraison chargee
     * @param plan             le plan charge
     */
    public PlanSimplifie(DemandeLivraison demandeLivraison, Plan plan) {
        this.plan = plan;
        arcs = new HashSet<>();
        sommets = new HashSet<>();
    }

    /**
     * fonction basique qui calcule le plus court chemin
     * entre begin et end.
     *
     * @param begin Intersection de depart
     * @param end   Intersection d'arrivee
     * @return trajet avec le plus court chemin
     */
    public Trajet shortestPathBetweenTwoIntersection(Intersection begin, Intersection end) {
        //using Dijkstra's Algorithm
        Trajet sortie = new Trajet();
        /*
         * tableau des temps de trajet entre le point de depart et chaque intersection
         * le premier element est celui de l'intersection de depart.
         */
        float[] tempsDijkstra = new float[plan.getIntersectionCount()];

        /*
         * tableau qui memorise l'etat des intersection lors d'une recherche de chemin
         * (visite/ non visite)
         */
        boolean[] etatDijkstra = new boolean[plan.getIntersectionCount()];

        /*
         * tableau listant les intersections du plan
         */
        Intersection[] intersections = new Intersection[plan.getIntersectionCount()];
        //initialisation
        if (plan.getIntersectionCount() > 0) {
            tempsDijkstra[0] = 0;
            etatDijkstra[0] = true;
            intersections[0] = begin;
            for (int i = 1; i < plan.getIntersectionCount(); i++) {
                tempsDijkstra[i] = -1;
                etatDijkstra[i] = false;

            }
        }
        return sortie;
    }


    private void recursiveDijkstra(Intersection begin, Intersection end, float[] tempsDijkstra, boolean[] etatDijkstra) {

    }
}
