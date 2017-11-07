package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Path;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe conteneur regroupant une matrice de coûts, une matrice de trajets,
 * et la liste associant chaque sommet à son indice.
 */
public class MatriceAdjacence {

    /**
     * Liste associant chaque sommet à son indice.
     */
    private ArrayList<Halt> listeSommets;

    /**
     * Matrice regroupant le meilleur trajet entre chaque sommet.
     * matriceTrajets[x][y] renvoie le trajet allant du sommet d'indice x au sommet d'indice y.
     */
    private Path[][] matricePaths;

    /**
     * Matrice contenant les coûts de chaque trajet.
     * Cette information est déjà contenue dans matriceTrajets,
     * mais une matrice int[][] est nécessaire pour l'algorithme de TSP.
     */
    private int[][] matriceCouts;

    /**
     * Constructeur de la matrice d'adjacence.
     *
     * @param listeSommets Liste associant chaque sommet à son indice.
     * @param matricePaths Matrice regroupant le meilleur trajet entre chaque sommet.
     * @param matriceCouts Matrice contenant les coûts de chaque trajet.
     */
    public MatriceAdjacence(ArrayList<Halt> listeSommets, Path[][] matricePaths, int[][] matriceCouts) {
        this.listeSommets = listeSommets;
        this.matricePaths = matricePaths;
        this.matriceCouts = matriceCouts;
    }

    /**
     * @return Liste associant chaque sommet à son indice.
     */
    public ArrayList<Halt> getListeSommets() {
        return listeSommets;
    }

    /**
     * @return Matrice regroupant le meilleur trajet entre chaque sommet.
     */
    public Path[][] getMatricePaths() {
        return matricePaths;
    }

    /**
     * @return Matrice contenant les coûts de chaque trajet.
     */
    public int[][] getMatriceCouts() {
        return matriceCouts;
    }



    /**
     * initialise matrix with the graph from SimplifiedMap.
     *
     * @param graph graph for the tsp.
     */
    public void initMatrix(Map<Halt, ArrayList<Path>> graph) {
        int inter1;
        for (Halt arret : graph.keySet()) {
            inter1 = listeSommets.indexOf(arret);
            for (Path trajet : graph.get(arret)) {
                int inter2 = listeSommets.indexOf(trajet.getEnd());
                int cout = trajet.getTimeToTravel();
                matriceCouts[inter1][inter2] = cout;
                matricePaths[inter1][inter2] = trajet;
            }
        }
    }
}
