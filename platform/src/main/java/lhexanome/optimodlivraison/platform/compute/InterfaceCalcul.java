package lhexanome.optimodlivraison.platform.compute;

import jdk.internal.util.xml.impl.Pair;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP;
import lhexanome.optimodlivraison.platform.compute.tsp.TSP1;
import lhexanome.optimodlivraison.platform.models.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

/**
 * classe qui permet d'interagir avec le module compute.
 */
public class InterfaceCalcul {

    /**
     * reference du plan charge.
     */
    private Plan plan;

    /**
     * reference de la demande de livraison chargee.
     */
    private DemandeLivraison demande;

    /**
     * Tournee retournee par le calcul de tournee optimise.
     */
    private Tournee sortie;

    /**
     * Graphe des plus courts chemins entre les livraisons.
     * Met à jour les attributs plan, demande et PlanSimplifie.
     */
    private PlanSimplifie planSimplifie;

    /**
     * Génère le graphe des plus courts chemins entre les livraisons.
     * Met à jour les attributs plan, demande et PlanSimplifie.
     */
    public PlanSimplifie calculerPlanSimplifie(Plan plan, DemandeLivraison demande)
    {
        this.plan = plan;
        this.demande = demande;
        this.planSimplifie = new PlanSimplifie(demande, plan);
        planSimplifie.computeGraph();
        return this.planSimplifie;
    }

    /**
     * Calcule la tournée optimale en fonction du plan simplifié et de la demande de livraison.
     * Met à jour l'attribut sortie.
     */
    public Tournee calculerTournee(PlanSimplifie planSimplifie, DemandeLivraison demande)
    {
        Intersection warehouse;
        Date start;
        int time;

        Map<Arret, ArrayList<Trajet>> graphe = planSimplifie.getGraphe();

        warehouse = demande.getBeginning().getIntersection();
        start = demande.getStart();
        int nbSommets = demande.getDeliveries().size() + 1;

        //sert à assigner chaque sommet à un index.
        ArrayList<Arret> listeSommets = new ArrayList<>();
        listeSommets.add(demande.getBeginning());
        listeSommets.addAll(demande.getDeliveries());

        MatriceAdjacence matrix = grapheToMatrix(graphe, nbSommets, listeSommets);
        int[][] matriceCouts = matrix.getMatriceCouts();
        Trajet[][] matriceTrajets = matrix.getMatriceTrajets();

        int[] listeDurees = demandeToDurees(demande, nbSommets, listeSommets);

        TSP tsp = new TSP1();
        tsp.chercheSolution(9999999, nbSommets, matriceCouts, listeDurees);
        time = tsp.getCoutMeilleureSolution();

        ArrayList<Trajet> deliveries = new ArrayList<>(nbSommets);
        for (int i = 0 ; i < nbSommets - 1 ; i++)
        {
            int indexSommet = tsp.getMeilleureSolution(i);
            Trajet trajet = matriceTrajets[indexSommet][indexSommet + 1];
            deliveries.set(i, trajet);
        }

        this.sortie = new Tournee(warehouse, start, time, deliveries);
        return sortie;
    }

    private MatriceAdjacence grapheToMatrix(Map<Arret, ArrayList<Trajet>> graphe, int nbSommets, ArrayList<Arret> listeSommets)
    {
        int[][] matriceCouts = new int[nbSommets][nbSommets];
        Trajet[][] matriceTrajets = new Trajet[nbSommets][nbSommets];

        //entrepot
        Arret entrepot = demande.getBeginning();
        int inter1 = 0;
        for (Trajet trajet : graphe.get(entrepot))
        {
            int inter2 = listeSommets.indexOf(trajet.getEnd());
            int cout = trajet.getTimeToTravel();
            matriceCouts[inter1][inter2] = cout;
            matriceTrajets[inter1][inter2] = trajet;
        }

        //le reste
        for (Arret arret : graphe.keySet())
        {
            inter1 = listeSommets.indexOf(arret.getIntersection());
            for (Trajet trajet : graphe.get(arret))
            {
                int inter2 = listeSommets.indexOf(trajet.getEnd());
                int cout = trajet.getTimeToTravel();
                matriceCouts[inter1][inter2] = cout;
                matriceTrajets[inter1][inter2] = trajet;
            }
        }

        return new MatriceAdjacence(listeSommets, matriceTrajets, matriceCouts);
    }

    private int[] demandeToDurees(DemandeLivraison demande, int nbSommets, ArrayList<Arret> listeSommets) {
        int[] sortie = new int[nbSommets];

        //entrepôt
        sortie[0] = 0;

        //le reste
        for (Arret arret : demande.getDeliveries())
        {
            int index = listeSommets.indexOf(arret);
            sortie[index] = ((Livraison) arret).getDuration();
        }

        return sortie;
    }
}
