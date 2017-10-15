package lhexanome.optimodlivraison.platform.models;


import java.util.Map;

public class Plan {

    private Map<Intersection, Troncon> map;

    public void setMap(Map<Intersection, Troncon> map) {
        this.map = map;
    }

    public int getIntersectionCount() {
        return map.size();
    }
}
