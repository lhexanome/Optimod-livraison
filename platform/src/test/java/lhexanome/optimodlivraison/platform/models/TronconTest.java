package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TronconTest {
    @Test
    void shouldSetDestination() {
        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection();
        Intersection intersection1 = new Intersection();

        //When
        intersection.setId(1256);
        troncon.setDestination(intersection);

        //Then
        assertEquals(intersection, troncon.getDestination());
    }

    @Test
    void shouldSetOrigine() {

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection();
        Intersection intersection1 = new Intersection();

        //When
        intersection.setId(5687);
        troncon.setOrigine(intersection);

        //Then
        assertEquals(intersection, troncon.getOrigine());
    }

    @Test
    void shouldSetNameStreet() {

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection();
        Intersection intersection1 = new Intersection();

        //When
        troncon.setNameStreet("Rue de la liberte");

        //Then
        assertEquals("Rue de la liberte", troncon.getNameStreet());

    }

    void shouldSetLenght() {

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection();
        Intersection intersection1 = new Intersection();

        //When
        troncon.setLength(22);

        //Then
        assertEquals(22, troncon.getLength());
    }

    void shouldSetTimeToTravel(){

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection();
        Intersection intersection1 = new Intersection();

        //When
        troncon.setLength(22);
        float result = troncon.timeToTravel();
        float trueResult = troncon.getLength()*15;

        //Then
        assertEquals(trueResult,result);

    }
}
