package lhexanome.optimodlivraison.platform.models;

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

}
