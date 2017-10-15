package lhexanome.optimodlivraison.platform.models;

public class Livraison {

    private int duration;
    private Intersection intersection;
    private PlageHoraire slot;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public void setSlot(PlageHoraire plage) {
        this.slot = plage;
    }

    public int getDuration() {
        return duration;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public PlageHoraire getSlot() {
        return slot;
    }
}
