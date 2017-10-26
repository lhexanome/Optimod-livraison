package lhexanome.optimodlivraison.platform.models;

import java.util.ArrayList;

/**
 * Représente un point ou noeud sur la carte (identifié).
 */
public class Intersection {

    /**
     * Coordonnée X du point.
     */
    private int x;

    /**
     * Coordonnée Y du point.
     */
    private int y;

    /**
     * Identifiant du point.
     * Récupérer depuis le XML
     */
    private Long id;

    /**
     * liste des intersections sur lesquelles un vector partant
     * de cette intersection arrive.
     */
    private ArrayList<Intersection> successeurs = new ArrayList<>();

    /**
     * Intersection conctructeur.
     *
     * @param id Identifiant du point.
     * @param x  Coordonnée X
     * @param y  Coordonnée Y
     * @see Intersection#id
     * @see Intersection#x
     * @see Intersection#y
     */
    public Intersection(Long id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur.
     *
     * @param id Identifiant du point
     */
    public Intersection(Long id) {
        this.id = id;
    }

    /**
     * Renvoie X.
     *
     * @return X
     */
    public int getX() {
        return x;
    }

    /**
     * Définie X.
     *
     * @param x Coordonnée X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Renvoie Y.
     *
     * @return Y
     */
    public int getY() {
        return y;
    }

    /**
     * Définie Y.
     *
     * @param y Coordonnée Y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Renvoie l'identifiant.
     *
     * @return un identifiant supposé unique
     */
    public long getId() {
        return id;
    }

    /**
     * Définie l'identifiant.
     *
     * @param id Identifiant supposé unique
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * fonction qui ajoute l'arrivee d'un vector dans la
     * liste des successeurs, à l'ajout de celui-ci.
     *
     * @param vector vector qui donne le successeur
     */
    public void addTronconSortant(Vector vector) {
        successeurs.add(vector.getDestination());
    }

    /**
     * Equals.
     *
     * @param o Object
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Intersection that = (Intersection) o;

        return id.equals(that.id);
    }

    /**
     * Hash code.
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
