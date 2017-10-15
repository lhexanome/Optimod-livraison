package lhexanome.optimodlivraison.platform.models;

import java.util.ArrayList;

public class Trajet {
    private ArrayList<Troncon> ListOfTroncon;
    private float time;
    private Intersection start;
    private Intersection end;

    public float getTimeForTravel()
    {
        for (Troncon t:ListOfTroncon) {
            time = time + t.timeToTravel();
        }
       return time;
    }

    public ArrayList getListOfTroncon() {
        return ListOfTroncon;
    }

    public float getTime() {
        return time;
    }

    public Intersection getStart() {
        return start;
    }

    public Intersection getEnd() {
        return end;
    }

    public void setListOfTroncon(ArrayList listOfTroncon) {
        ListOfTroncon = listOfTroncon;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setStart(Intersection start) {
        this.start = start;
    }

    public void setEnd(Intersection end) {
        this.end = end;
    }
}
