package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Arret;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Trajet;

import java.util.ArrayList;

//CHECKSTYLE:OFF
public class MatriceAdjacence {
    private ArrayList<Intersection> listeSommets;
    private Trajet[][] matriceTrajets;
    private int[][] matriceCouts;

    public MatriceAdjacence(ArrayList<Intersection> listeSommets, Trajet[][] matriceTrajets, int[][] matriceCouts) {
        this.listeSommets = listeSommets;
        this.matriceTrajets = matriceTrajets;
        this.matriceCouts = matriceCouts;
    }


    public ArrayList<Intersection> getListeSommets() {
        return listeSommets;
    }

    public Trajet[][] getMatriceTrajets() {
        return matriceTrajets;
    }

    public int[][] getMatriceCouts() {
        return matriceCouts;
    }
}
