package lhexanome.optimodlivraison.platform.models;


import java.util.Map;

public class Plan {

    private Map<Intersection, Troncon> map;

    public int getIntersectionCount() {
        return map.size();
    }
}
