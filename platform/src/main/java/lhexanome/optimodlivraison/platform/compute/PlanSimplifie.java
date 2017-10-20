package lhexanome.optimodlivraison.platform.compute;


import lhexanome.optimodlivraison.platform.models.Arret;
import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Trajet;
import lhexanome.optimodlivraison.platform.models.Troncon;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


/**
 * classe qui stocke une version simplifiée du plan,
 * soit un graph orienté et complet avec comme sommets les livraisons,
 * et pour arc des trajets formant les plus courts chemins entre ces livraisons.
 */
public class PlanSimplifie {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PlanSimplifie.class.getName());
    /**
     * reference au plan charge.
     */
    private Plan plan;
    /**
     * reference a la demande de livraison chargee.
     */
    private DemandeLivraison demandeLivraison;
    private Map<Arret, ArrayList<Trajet>> graphe;

    /***
     * constructeur par defaut.
     */
    public PlanSimplifie() {
        plan = new Plan();
        demandeLivraison = new DemandeLivraison();
        graphe = new HashMap<>();
    }

    /**
     * constructeur qui initialise le plan simplifie.
     *
     * @param demandeLivraison la demande de livraison chargee
     * @param plan             le plan charge
     */
    public PlanSimplifie(DemandeLivraison demandeLivraison, Plan plan) {
        this.plan = plan;
        this.demandeLivraison = demandeLivraison;
        graphe = new HashMap<>();
        //TODO Appeler computeGraph dans le constructeur ?
    }

    /**
     * fonction qui calcule le graphe simplifié à partir du plan.
     */
    public void computeGraph() {
        LOGGER.info(MessageFormat.format("compute simplified graph", ""));
        HashSet<Arret> ptsArret = new HashSet<>();
        ptsArret.addAll(demandeLivraison.getDeliveries());
        ptsArret.add(demandeLivraison.getBeginning());
        for (Arret s : ptsArret) {
            ArrayList<Trajet> listeTrajets =
                    shortestPathList(s, demandeLivraison.getDeliveries());
//            for (Trajet t : listeTrajets) {
//                graphe.put(s, t);
//            }
            graphe.put(s, listeTrajets);
        }
    }

    /**
     * fonction qui renvoie
     * la liste des chemins les plus courts partant de start
     * et allant à chaque Livraison de ends.
     *
     * @param start intersection de depart
     * @param ends  Liste des arrives
     * @return liste des plus courts chemins
     */
    public ArrayList<Trajet> shortestPathList(
            Arret start, Set<? extends Arret> ends) {
        ArrayList<Trajet> sortie = new ArrayList<>();
        LOGGER.info(MessageFormat.format("start compute shortest path for:", start.toString()));

        /*
         * tableau des temps de trajet entre
         * le point de depart et chaque intersection
         * le premier element est celui de l'intersection de depart.
         */
        float[] tempsDijkstra = new float[plan.getIntersectionCount()];
        /*
         * tableau qui memorise l'etat
         * des intersections lors d'une recherche de chemin
         * (visite/ non visite)
         */
        boolean[] etatDijkstra = new boolean[plan.getIntersectionCount()];

        /*
         * tableau listant les intersections du plan
         */
        ArrayList<Intersection> intersections = new ArrayList<>();
        /*
         * tableau contenant à l'indice i
         * le predecesseur de l'intersection i dans le parcours
         */
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
         /*
         * tableau contenant à l'indice i
         * le troncon qui part du predecesseur
         * de l'intersection i vers celle ci
         * (celui du plus court chemin)
         */
        ArrayList<Troncon> chemins = new ArrayList<>();
        //initialisation
        if (plan.getIntersectionCount() > 0) {
            tempsDijkstra[0] = 0;
            etatDijkstra[0] = true;


            //initialisation de la liste d'intersections
            initIntersectionList(start.getIntersection(), intersections, predecesseurs, chemins);
            //calcul
            dijkstra(intersections, predecesseurs,
                    chemins,
                    tempsDijkstra, etatDijkstra);

            //pour chaque livraison, on construit le trajet
            for (Arret end : ends) {
                if (!end.equals(start)) {
                    Trajet t = new Trajet();

                    //on recupere la liste de troncons
                    int indexStart = intersections.indexOf(start.getIntersection());
                    int indexEnd = intersections.indexOf(end.getIntersection());
                    if (indexEnd != -1) {

                        //on remonte par la fin la liste des chemins
                        while (indexStart != indexEnd) {
                            //TODO inverser l'ordre des troncons
                            Troncon tr = chemins.get(indexEnd);
                            t.addTroncon(tr);
                            indexEnd = intersections.indexOf(predecesseurs.get(indexEnd));
                        }
                        sortie.add(t);
                    }
                    LOGGER.warning(MessageFormat.format("no path found for:",
                            start.toString() + " and " + end.toString()));
                }
            }
        }
        return sortie;
    }

    /**
     * fonction qui initialise la liste des intersections
     * en parcourant les troncons connectes dans le plan.
     *
     * @param start         intersection de depart
     * @param intersections liste des intersections
     * @param predecesseurs liste des predecesseurs
     * @param chemins       liste des plus courts troncons
     *                      partant des predecesseurs
     *                      vers les intersections
     */
    public void initIntersectionList(Intersection start, ArrayList<Intersection> intersections,
                                     ArrayList<Intersection> predecesseurs,
                                     ArrayList<Troncon> chemins) {
        if (start != null) {
            intersections.add(start);
            predecesseurs.add(null);
            chemins.add(null);
            for (int i = 0; i < intersections.size(); i++) {
                Collection<Troncon> cheminsPartants =
                        plan.getTronconsFromIntersection(intersections.get(i));
                for (Troncon t : cheminsPartants) {
                    if (!intersections.contains(t.getDestination())) {
                        intersections.add(t.getDestination());
                        predecesseurs.add(null);
                        chemins.add(null);
                    }
                }
            }
        }
    }

    /**
     * fonction qui applique l'algorithme Dijkstra
     * sur la liste des intersections
     * elle remplit la liste des predecesseurs,
     * et les plus courts chemins.
     *
     * @param intersections liste des intersections
     * @param predecesseurs liste des predecesseurs
     *                      (soit pour chaque intersection i,
     *                      l'element i est son predecesseur)
     * @param chemins       liste des plus courts troncons
     *                      partant des predecesseurs
     *                      vers les intersections
     * @param tempsDijkstra tableau stockant
     *                      les temps pour le relachement
     * @param etatDijkstra  tableau stockant
     *                      l'etat de visite d'une intersection
     *                      (visite/non visite)
     */
    public void dijkstra(ArrayList<Intersection> intersections,
                         ArrayList<Intersection> predecesseurs,
                         ArrayList<Troncon> chemins,
                         float[] tempsDijkstra, boolean[] etatDijkstra) {
        LOGGER.info(MessageFormat.format("start Dijkstra algorithm", ""));

        //reset
        tempsDijkstra[0] = 0;
        etatDijkstra[0] = false;
        for (int i = 1; i < plan.getIntersectionCount(); i++) {
            //je represente l'infini par -1
            tempsDijkstra[i] = -1;
            etatDijkstra[i] = false;
        }
        boolean continueDijkstra = true;
        int indexNouvelleVisite = 0;
        Intersection courant = intersections.get(indexNouvelleVisite);
        while (continueDijkstra) {
            //recherche du sommet de plus petit temps et non visite
            float tempsMin = -1;
            indexNouvelleVisite = -1;
            for (int i = 0; i < intersections.size(); i++) {
                if (!etatDijkstra[i]) {
                    if (tempsMin == -1) {
                        indexNouvelleVisite = i;
                        tempsMin = tempsDijkstra[i];

                    } else {
                        if (tempsDijkstra[i] != -1
                                && tempsDijkstra[i] < tempsMin) {
                            indexNouvelleVisite = i;
                            tempsMin = tempsDijkstra[i];
                        }
                    }
                }
            }
            if (indexNouvelleVisite != -1) {
                //on change son etat
                etatDijkstra[indexNouvelleVisite] = true;

                //on relache tout les arcs
                // partant de cette intersection
                courant = intersections.get(indexNouvelleVisite);
                Collection<Troncon> cheminsPartants =
                        plan.getTronconsFromIntersection(courant);
                for (Troncon t : cheminsPartants) {
                    int indexDestination =
                            intersections.indexOf(t.getDestination());
                    if (!etatDijkstra[indexDestination]) {
                        if (tempsDijkstra[indexDestination] != -1) {
                            //on stocke le plus court troncon
                            if (tempsDijkstra[indexDestination]
                                    >= tempsDijkstra[indexNouvelleVisite]
                                    + t.getTimeToTravel()) {
                                //on stocke le predecesseur
                                predecesseurs.set(indexDestination, courant);
                                //et le troncon qui les separe
                                chemins.set(indexDestination, t);
                                //on relache l'arc
                                tempsDijkstra[indexDestination] =
                                        tempsDijkstra[indexNouvelleVisite]
                                                + t.getTimeToTravel();
                            }
                            //on ne change pas le temps

                        } else {
                            //on stocke le predecesseur
                            predecesseurs.set(indexDestination, courant);
                            //et le troncon qui les separe
                            chemins.set(indexDestination, t);
                            //on relache l'arc
                            tempsDijkstra[indexDestination] =
                                    tempsDijkstra[indexNouvelleVisite]
                                            + t.getTimeToTravel();
                        }
                    }
                }
            } else {
                continueDijkstra = false;
            }
        }
    }

    /**
     * map stockant le graphe sous la forme d'une association de livraisons
     * et de trajets partant de ces livraisons.
     */
    public Map<Arret, ArrayList<Trajet>> getGraphe() {
        return graphe;
    }
}
