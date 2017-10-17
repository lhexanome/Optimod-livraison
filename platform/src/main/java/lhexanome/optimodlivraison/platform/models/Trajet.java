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
     * Temps necessaire pour parcourir le trajet.
     */
    private float time;

    /**
     * Intersection de depart du trajet.
     */
    private Intersection start;

    /**
     * Intersection finale du trajet.
     */
    private Intersection end;


    /**
     * Renvoie le temps nécessaire pour effectuer le trajet.
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
     * Renvoie la liste des tronçons.
     *
     * @return liste
     */
    public List<Troncon> getListOfTroncon() {
        return listOfTroncon;
    }

    /**
     * Renvoie le temps nécessaire pour poarcourir le trajet.
     *
     * @return time
     */
    public float getTime() {
        return time;
    }

    /**
     * Définie le temps nécessaire pour parcourir le trajet.
     *
     * @param time Time
     */
    public void setTime(float time) {
        this.time = time;
    }

    /**
     * Renvoie l'intersection de départ.
     *
     * @return Start
     */
    public Intersection getStart() {
        return start;
    }

    /**
     * Définie l'intersection de départ.
     *
     * @param start Start
     */
    public void setStart(Intersection start) {
        this.start = start;
    }

    /**
     * Renvoie l'intersection d'arrivée.
     *
     * @return End
     */
    public Intersection getEnd() {
        return end;
    }

    /**
     * Définie l'intersection d'arrivée.
     *
     * @param end End
     */
    public void setEnd(Intersection end) {
        this.end = end;
    }
}
