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
        arcs = new HashSet<Trajet>();
        sommets = new HashSet<Livraison>();
    }

    /**
     * constructeur qui initialise le plan simplifie.
     *
     * @param demandeLivraison la demande de livraison chargee
     * @param plan             le plan charge
     */
    public PlanSimplifie(DemandeLivraison demandeLivraison, Plan plan) {
        this.plan = plan;
        arcs = new HashSet<Trajet>();
        sommets = new HashSet<Livraison>();
    }

    /**
     * fonction basique qui calcule le plus court chemin
     * entre begin et end.
     *
     * @param begin Intersection de depart
     * @param end   Intersection d'arrivee
     * @return trajet avec le plus court chemin
     */
    public Trajet shortestPathBetweenTwoIntersection(
            Intersection begin, Intersection end) {
        Trajet sortie = new Trajet();
        return sortie;
    }
}
