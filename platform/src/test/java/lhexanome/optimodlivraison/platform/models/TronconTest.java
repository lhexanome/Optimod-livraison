package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TronconTest {
    @Test
    void shouldSetDestination() {
        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection(123L , 105,110);
        Intersection intersection1 = new Intersection(124L, 115,120);

        //When
        troncon.setDestination(intersection);

        //Then
        assertEquals(intersection, troncon.getDestination());
    }

    @Test
    void shouldSetOrigine() {

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection(125L , 125,130);
        Intersection intersection1 = new Intersection(126L , 135,140);

        //When
        troncon.setOrigine(intersection);

        //Then
        assertEquals(intersection, troncon.getOrigine());
    }

    @Test
    void shouldSetNameStreet() {

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection(127L , 145,150);
        Intersection intersection1 = new Intersection(128L , 155,160);

        //When
        troncon.setNameStreet("Rue de la liberte");

        //Then
        assertEquals("Rue de la liberte", troncon.getNameStreet());

    }

    void shouldSetLenght() {

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection(129L , 165,1570);
        Intersection intersection1 = new Intersection(130L , 175,180);

        //When
        troncon.setLength(22);

        //Then
        assertEquals(22, troncon.getLength());
    }

    void shouldSetTimeToTravel(){

        //With
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection(131L , 185,1590);
        Intersection intersection1 = new Intersection(132L , 195,200);

        //When
        troncon.setLength(22);
        float result = troncon.timeToTravel();
        float trueResult = troncon.getLength()*15;

        //Then
        assertEquals(trueResult,result);

    }
}
