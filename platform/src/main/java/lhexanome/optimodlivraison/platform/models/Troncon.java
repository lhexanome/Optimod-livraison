package lhexanome.optimodlivraison.platform.models;

public class Troncon {

    private Intersection origine;
    private Intersection destination;
    private String nameStreet;
    private float lenght;

    public void setOrigine(Intersection origine) {
        this.origine = origine;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public void setLenght(float lenght) {
        this.lenght = lenght;
    }

    public Intersection getDestination() {

        return destination;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public float getLenght() {
        return lenght;
    }

    public Intersection getOrigine() {

        return origine;
    }
}
