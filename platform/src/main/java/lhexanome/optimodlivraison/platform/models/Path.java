package lhexanome.optimodlivraison.platform.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Suite de tronçons connectés reliant deux points
 * de livraison (ou entrepôt).
 */
public class Path {

    /**
     * Liste des vectors.
     */
    private List<Vector> vectors;


    /**
     * Temps necessaire pour parcourir le trajet.
     */
    private int timeToTravel;

    /**
     * Constructor.
     */
    public Path() {
        vectors = new LinkedList<>();
        this.timeToTravel = 0;
    }

    /**
     * fonction equals.
     *
     * @param o objet a comparer
     * @return resultat de la comparaison
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (Integer.compare(path.timeToTravel, timeToTravel) != 0) return false;
        return vectors != null ? vectors.equals(path.vectors) : path.vectors == null;
    }

    /**
     * fonction hashCode.
     *
     * @return hashcode de l'objet
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Override
    public int hashCode() {
        int result = vectors != null ? vectors.hashCode() : 0;
        result = 31 * result + (timeToTravel != +0.0f ? Float.floatToIntBits(timeToTravel) : 0);
        return result;
    }

    /**
     * Renvoie le temps nécessaire pour effectuer le trajet.
     *
     * @return Float
     */
    public int getTimeToTravel() {
        return timeToTravel;
    }

    /**
     * Définie le temps nécessaire pour parcourir le trajet.
     *
     * @param timeToTravel Time
     */
    public void setTimeToTravel(int timeToTravel) {
        this.timeToTravel = timeToTravel;
    }

    /**
     * Renvoie la liste des tronçons.
     *
     * @return liste
     */
    public List<Vector> getVectors() {
        return vectors;
    }

    /**
     * Renvoie l'intersection de départ.
     *
     * @return Start
     */
    public Intersection getStart() {
        return (vectors.size() == 0 ? null : vectors.get(0).getOrigin());
    }

    /**
     * Renvoie l'intersection d'arrivée.
     *
     * @return End
     */
    public Intersection getEnd() {
        int size = vectors.size();
        if (size == 0) return null;
        return vectors.get(size - 1).getDestination();
    }

    /**
     * Ajout un vector a la fin du trajet.
     * Cette operation met a jour le temp de parcour en lui ajoutent le temp de parcour du vector passer en paramétre.
     *
     * @param vector vector a ajouter au trajet
     *               L'intersection origine du vector doit étre identique a l'intersection d'arrivée du trajet
     * @throws RuntimeException si l'intersection origine du vector
     *                          n'est pas identique a l'intersection d'arrivée du trajet
     */
    public void addTroncon(Vector vector) {
        if (vectors.size() == 0 || getEnd() == vector.getOrigin()) {
            vectors.add(vector);
            timeToTravel += vector.getTimeToTravel();
        } else {
            throw new RuntimeException("The vector is not at the end of the trajet");
        }

    }

    /**
     * Ajout un vector a du debut du trajet.
     * Cette operation met a jour le temp de parcour en lui ajoutent le temp de parcour du vector passer en paramétre.
     *
     * @param vector vector a ajouter au debut du trajet
     *               L'intersection de destination du vector doit étre identique a l'intersection d'origine du trajet
     * @throws RuntimeException si l'intersection origine du vector
     *                          n'est pas identique a l'intersection d'arrivée du trajet
     */
    public void addTronconBefore(Vector vector) {
        if (vectors.size() == 0 || getStart() == vector.getDestination()) {
            vectors.add(0, vector);
            timeToTravel += vector.getTimeToTravel();
        } else {
            throw new RuntimeException("The vector is not at the end of the trajet");
        }

    }

    /**
     * Ajout un path a la suit du path.
     * Cette operation met a jour le temp de parcour en lui ajoutent le temp de parcour du path passer en paramétre.
     *
     * @param path path a ajouter au path courant
     *             Le depart du path doit étre identique a l'arrivée du path courant
     * @throws RuntimeException si le depart du path passé en paramétre
     *                          n'est pas identique a l'arrivée du path courant
     */
    public void addPath(Path path) {
        if (vectors.size() == 0 || getEnd() == path.getStart()) {

            vectors.addAll(path.vectors);
            timeToTravel += path.getTimeToTravel();
        } else {
            throw new RuntimeException("The troncon is not at the end of the path");
        }

    }

}
