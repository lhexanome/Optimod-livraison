package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;

import java.util.ArrayList;

public class MatriceAdjacence {
    private ArrayList<Halt> listeSommets;
    private Path[][] matricePaths;
    private int[][] matriceCouts;

    public MatriceAdjacence(ArrayList<Halt> listeSommets, Path[][] matriceTrajets, int[][] matriceCouts) {
        this.listeSommets = listeSommets;
        this.matricePaths = matricePaths;
        this.matriceCouts = matriceCouts;
    }


    public ArrayList<Halt> getListeSommets() {
        return listeSommets;
    }

    public Path[][] getMatricePaths() {
        return matricePaths;
    }

    public int[][] getMatriceCouts() {
        return matriceCouts;
    }
}
