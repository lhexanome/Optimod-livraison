package lhexanome.optimodlivraison.platform.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Représente une plage horaire.
 */
public class TimeSlot {

    /**
     * Début de la plage horaire.
     */
    private Date start;

    /**
     * Fin de la plage horaire.
     */
    private Date end;

    /**
     * Constructeur par défaut.
     * Prend une date de début et une date de fin
     * Les deux dates seront inversées si le début est après la fin.
     *
     * @param start Date de début
     * @param end   Date de fin
     */
    public TimeSlot(Date start, Date end) {
        if (start.before(end)) {
            this.start = start;
            this.end = end;
        } else {
            this.end = start;
            this.start = end;
        }
    }

    /**
     * Renvoie la date de début.
     *
     * @return Date de début
     */
    public Date getStart() {
        return start;
    }

    /**
     * Définie le début de la plage.
     * Doit être avant la fin actuelle, si ce n'est pas le cas, on fait rien
     *
     * @param start Date de début
     */
    public void setStart(Date start) {
        if (start.before(end)) {
            this.start = start;
        }
    }

    /**
     * Renvoie la date de fin.
     *
     * @return Date de fin
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Définir le début de la plage.
     * Doit être après le début, si ce n'est pas le cas, on fait rien
     *
     * @param end Date de fin
     */
    public void setEnd(Date end) {
        if (end.after(start)) {
            this.end = end;
        }
    }

    /**
     * Permet de savoir si une date est incluse dans la plage horaire.
     *
     * @param date Date à tester
     * @return true si la date est incluse, false sinon
     */
    public boolean isIncluded(Date date) {
        return date.equals(start)
                || date.equals(end)
                || (date.after(start) && date.before(end));
    }


    /**
     * To string method.
     *
     * @return String
     */
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH'h'mm");

        return "De " + simpleDateFormat.format(start) + " à " + simpleDateFormat.format(end);
    }
}
