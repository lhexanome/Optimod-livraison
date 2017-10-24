package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Troncon;

import java.util.ArrayList;

public class IntersectionWrapper{

    private Intersection intersection;
    private float tempsDijkstra = Float.MAX_VALUE;
    private boolean noir = false;
    private IntersectionWrapper predecesseur;
    private Troncon cheminArrivant;
    private ArrayList<IntersectionWrapper> successeursVisites = new ArrayList<>();

    public IntersectionWrapper(Intersection i) {
        intersection=i;
    }
    public void setAsStart(){
        tempsDijkstra=0;
        noir =true;
    }


    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public float getTempsDijkstra() {
        return tempsDijkstra;
    }

    public void setTempsDijkstra(float tempsDijkstra) {
        this.tempsDijkstra = tempsDijkstra;
    }

    public boolean isNoir() {
        return noir;
    }

    public void setNoir(boolean noir) {
        this.noir = noir;
    }

    public IntersectionWrapper getPredecesseur() {
        return predecesseur;
    }

    public void setPredecesseur(IntersectionWrapper predecesseur) {
        this.predecesseur = predecesseur;
    }

    public Troncon getCheminArrivant() {
        return cheminArrivant;
    }
    public boolean isSuccesseurVisite(Intersection successeur){
        for (IntersectionWrapper sv: successeursVisites){
            if(sv.intersection.equals(successeur)){
                return true;
            }
        }
        return false;
    }
    public IntersectionWrapper getSuccesseurVisite(Intersection successeur){
        for (IntersectionWrapper sv: successeursVisites){
            if(sv.intersection.equals(successeur)){
                return sv;
            }
        }
        return null;
    }
    public void addSuccesseurVisite(IntersectionWrapper successeur){
        successeursVisites.add(successeur);
    }

    public void setCheminArrivant(Troncon cheminArrivant) {
        this.cheminArrivant = cheminArrivant;
    }
}
