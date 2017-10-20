package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Arret;
import lhexanome.optimodlivraison.platform.models.Trajet;

import java.util.ArrayList;

public class MatriceAdjacence {
    private ArrayList<Arret> listeSommets;
    private Trajet[][] matriceTrajets;
    private int[][] matriceCouts;

    public MatriceAdjacence(ArrayList<Arret> listeSommets, Trajet[][] matriceTrajets, int[][] matriceCouts)
    {
        this.listeSommets = listeSommets;
        this.matriceTrajets = matriceTrajets;
        this.matriceCouts = matriceCouts;
    }


    public ArrayList<Arret> getListeSommets() {
        return listeSommets;
    }

    public Trajet[][] getMatriceTrajets() {
        return matriceTrajets;
    }

    public int[][] getMatriceCouts() {
        return matriceCouts;
    }
}
