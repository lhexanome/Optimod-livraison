package lhexanome.optimodlivraison.platform.models;

import java.util.List;

/**
 * Suite de tronçons connectés reliant deux points
 * de livraison (ou entrepôt).
 */
public class Trajet {

    /**
     * Liste des troncons.
     */
    private List<Troncon> listOfTroncon;

    /**
     * TODO JavaDoc.
     */
    private float time;

    /**
     * TODO JavaDoc.
     */
    private Intersection start;

    /**
     * TODO JavaDoc.
     */
    private Intersection end;


    /**
     * TODO JavaDoc.
     *
     * @return Float
     */
    public float getTimeForTravel() {
        for (Troncon t : listOfTroncon) {
            time = time + t.timeToTravel();
        }
        return time;
    }

    /**
     * TODO JavaDoc.
     *
     * @return liste
     */
    public List<Troncon> getListOfTroncon() {
        return listOfTroncon;
    }

    /**
     * TODO JavaDoc.
     *
     * @return time
     */
    public float getTime() {
        return time;
    }

    /**
     * TODO JavaDoc.
     *
     * @param time Time
     */
    public void setTime(float time) {
        this.time = time;
    }

    /**
     * TODO JavaDoc.
     *
     * @return Start
     */
    public Intersection getStart() {
        return start;
    }

    /**
     * TODO JavaDoc.
     *
     * @param start Start
     */
    public void setStart(Intersection start) {
        this.start = start;
    }

    /**
     * TODO JavaDoc.
     *
     * @return End
     */
    public Intersection getEnd() {
        return end;
    }

    /**
     * TODO JavaDoc.
     *
     * @param end End
     */
    public void setEnd(Intersection end) {
        this.end = end;
    }
}
