package lhexanome.optimodlivraison.platform.models;

import java.util.ArrayList;

public class Trajet {
    private ArrayList ListOfTroncon;
    private int time;
    private Intersection start;
    private Intersection end;

    public float getTimeForTravel()
    {
        //TODO
        time = 0;
       return time;
    }

    public ArrayList getListOfTroncon() {
        return ListOfTroncon;
    }

    public int getTime() {
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

    public void setTime(int time) {
        this.time = time;
    }

    public void setStart(Intersection start) {
        this.start = start;
    }

    public void setEnd(Intersection end) {
        this.end = end;
    }
}
