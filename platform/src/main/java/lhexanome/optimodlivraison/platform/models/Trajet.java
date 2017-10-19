package lhexanome.optimodlivraison.platform.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Suite de tronçons connectés reliant deux points
 * de livraison (ou entrepôt).
 */
public class Trajet {

    /**
     * Liste des troncons.
     */
    private List<Troncon> troncons;

    /**
     * Temps necessaire pour parcourir le trajet.
     *
     */
    private float timeToTravel;

    public Trajet(){
        troncons = new LinkedList<>();
        timeToTravel = 0;
    }

    /**
     * Renvoie le temps nécessaire pour effectuer le trajet.
     *
     * @return Float
     */
    public float getTimeToTravel() {
        return timeToTravel;
    }

    /**
     * Renvoie la liste des tronçons.
     *
     * @return liste
     */
    public List<Troncon> getTroncons() {
        return troncons;
    }

    /**
     * Définie le temps nécessaire pour parcourir le trajet.
     *
     * @param timeToTravel Time
     */
    public void setTimeToTravel(float timeToTravel) {
        this.timeToTravel = timeToTravel;
    }

    /**
     * Renvoie l'intersection de départ.
     *
     * @return Start
     */
    public Intersection getStart() {
        return (troncons.size()==0 ? null : troncons.get(0).getOrigine());
    }

    /**
     * Renvoie l'intersection d'arrivée.
     *
     * @return End
     */
    public Intersection getEnd() {
        int size = troncons.size();
        if (size == 0) return null;
        return troncons.get(size-1).getDestination();
    }

    /**
     * Ajout un troncon a la fin du trajet.
     * Cette operation met a jour le temp de parcour en lui ajoutent le temp de parcour du troncon passer en paramétre.
     *
     * @param troncon troncon a ajouter au trajet
     * L'intersection origine du troncon doit étre identique a l'intersection d'arrivée du trajet
     *
     * @throws RuntimeException si l'intersection origine du troncon n'est pas identique a l'intersection d'arrivée du trajet
     */
    public void addTroncon(Troncon troncon){
        if(troncons.size() == 0 || getEnd() == troncon.getOrigine()){
            troncons.add(troncon);
            timeToTravel += troncon.getTimeToTravel();
        }else {
            throw new RuntimeException("The troncon is not at the end of the trajet");
        }

    }

    /**
     * Ajout un troncon a du debut du trajet.
     * Cette operation met a jour le temp de parcour en lui ajoutent le temp de parcour du troncon passer en paramétre.
     *
     * @param troncon troncon a ajouter au debut du trajet
     * L'intersection de destination du troncon doit étre identique a l'intersection d'origine du trajet
     *
     * @throws RuntimeException si l'intersection origine du troncon n'est pas identique a l'intersection d'arrivée du trajet
     */
    public void addTronconBefore(Troncon troncon){
        if(troncons.size() == 0 || getStart() == troncon.getDestination()){
            troncons.set(0, troncon);
            timeToTravel += troncon.getTimeToTravel();
        }else {
            throw new RuntimeException("The troncon is not at the end of the trajet");
        }

    }

    /**
     * Ajout un trajet a la suit du trajet.
     * Cette operation met a jour le temp de parcour en lui ajoutent le temp de parcour du trajet passer en paramétre.
     *
     * @param trajet trajet a ajouter au trajet courant
     * Le depart du trajet doit étre identique a l'arrivée du trajet courant
     *
     * @throws RuntimeException si le depart du trajet passé en paramétre n'est pas identique a l'arrivée du trajet courant
     */
    public void addTrajet(Trajet trajet){
        if(troncons.size() == 0 || getEnd() == trajet.getStart()){

            troncons.addAll(trajet.troncons);
            timeToTravel += trajet.getTimeToTravel();
        }else {
            throw new RuntimeException("The troncon is not at the end of the trajet");
        }

    }

}
