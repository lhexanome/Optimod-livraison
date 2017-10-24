package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Troncon;

import java.util.Collection;

public class IntersectionWrapper{

    private Intersection intersection;
    private float tempsDijkstra = Float.MAX_VALUE;
    private boolean etatDijkstra= false;
    private IntersectionWrapper predecesseur;
    private Troncon chemin;

    public IntersectionWrapper(Intersection i) {
        intersection=i;
    }
    public void setAsStart(){
        tempsDijkstra=0;
        etatDijkstra=true;
    }
    public void initValuesForDijkstra(Collection<Troncon> cheminsPartants){

    }
}
