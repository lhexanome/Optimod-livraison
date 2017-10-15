package lhexanome.optimodlivraison.platform.models;

import java.util.Date;

public class PlageHoraire {

    private Date start;
    private Date end;

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
