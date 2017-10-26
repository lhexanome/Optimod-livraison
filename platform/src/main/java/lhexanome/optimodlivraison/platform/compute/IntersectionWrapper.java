package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Vector;

import java.util.ArrayList;

/**
 * Class which stores additional information needed for Dijkstra.
 */
public class IntersectionWrapper {

    /**
     * the intersection wrapped.
     */
    private Intersection intersection;

    /**
     * time stored, value used as path weight in Dijkstra.
     */
    private float tempsDijkstra = Float.MAX_VALUE;

    /**
     * Dijkstra state: black == every neighbours are done.
     */
    private boolean black = false;

    /**
     * intersection Wrapper which is before in the order for the shortest path.
     */
    private IntersectionWrapper predecesseur;
    /**
     * vector used to go from predecesseur to this intersection.
     */
    private Vector cheminArrivant;

    /**
     * list of neighbours (sons) visited by Dijkstra.
     */
    private ArrayList<IntersectionWrapper> successeursVisites = new ArrayList<>();

    /**
     * constructor.
     *
     * @param i intersection wrapped by the object.
     */
    public IntersectionWrapper(Intersection i) {
        intersection = i;
    }

    /**
     * function used to set this object as the starting point
     * for the path finding.
     */
    public void setAsStart() {
        tempsDijkstra = 0;
        black = true;
    }

    /**
     * getter for intersection.
     *
     * @return the intersection.
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * setter for intersection.
     *
     * @param intersection the intersection to set
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * getter for tempsDijkstra.
     *
     * @return tempsDijkstra
     */
    public float getTempsDijkstra() {
        return tempsDijkstra;
    }

    /**
     * setter for tempsDijkstra.
     *
     * @param tempsDijkstra tempsDijkstra given
     */
    public void setTempsDijkstra(float tempsDijkstra) {
        this.tempsDijkstra = tempsDijkstra;
    }

    /**
     * getter for Dijkstra state.
     *
     * @return if is black
     */
    public boolean isBlack() {
        return black;
    }

    /**
     * setter for Dijkstra state.
     *
     * @param black state
     */
    public void setBlack(boolean black) {
        this.black = black;
    }

    /**
     * getter for predecesseur.
     *
     * @return predecesseur
     */
    public IntersectionWrapper getPredecesseur() {
        return predecesseur;
    }

    /**
     * setter for predecesseur.
     *
     * @param predecesseur predecesseur given
     */
    public void setPredecesseur(IntersectionWrapper predecesseur) {
        this.predecesseur = predecesseur;
    }

    /**
     * getter for the vector.
     *
     * @return the vector
     */
    public Vector getCheminArrivant() {
        return cheminArrivant;
    }

    /**
     * check if the intersection successeur is in the list of visited sons.
     *
     * @param successeur intersection to check.
     * @return the result of the test
     */
    public boolean isSuccesseurVisite(Intersection successeur) {
        for (IntersectionWrapper sv : successeursVisites) {
            if (sv.intersection.equals(successeur)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the wrapper for the intersection successeur in the list of visited sons.
     *
     * @param successeur intersection to get.
     * @return the wrapper (null if is not in)
     */
    public IntersectionWrapper getSuccesseurVisite(Intersection successeur) {
        for (IntersectionWrapper sv : successeursVisites) {
            if (sv.intersection.equals(successeur)) {
                return sv;
            }
        }
        return null;
    }

    /**
     * adds a neighbour to the list of visited ones.
     *
     * @param successeur the neighbour
     */
    public void addSuccesseurVisite(IntersectionWrapper successeur) {
        successeursVisites.add(successeur);
    }

    /**
     * setter for the vector coming here.
     *
     * @param cheminArrivant vector given
     */
    public void setCheminArrivant(Vector cheminArrivant) {
        this.cheminArrivant = cheminArrivant;
    }
}
