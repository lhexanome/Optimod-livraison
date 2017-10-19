package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TronconTest {

    Troncon tronconTested;

    @BeforeEach
    void createTourneeObjects() {
        tronconTested = new Troncon(
                new Intersection(1L, 101, 102),
                new Intersection(2L, 201, 202),
                "Street test"
        );
    }

    @Test
    void shouldSetDestination() {
        //With
        Intersection intersection = new Intersection(123L, 105, 110);

        //When
        tronconTested.setDestination(intersection);

        //Then
        assertEquals(intersection, tronconTested.getDestination());
    }

    @Test
    void shouldSetOrigine() {

        //With
        Intersection intersection = new Intersection(125L, 125, 130);

        //When
        tronconTested.setOrigine(intersection);

        //Then
        assertEquals(intersection, tronconTested.getOrigine());
    }

    @Test
    void shouldSetNameStreet() {

        //With

        //When
        tronconTested.setNameStreet("Rue de la liberte");

        //Then
        assertEquals("Rue de la liberte", tronconTested.getNameStreet());

    }

    void shouldSetLenght() {

        //With

        //When
        tronconTested.setLength(22);

        //Then
        assertEquals(22, tronconTested.getLength());
    }

    void shouldGetTimeToTravel() {

        //With

        //When
        tronconTested.setLength(22);
        float result = tronconTested.getTimeToTravel();
        float trueResult = tronconTested.getLength() * 15;

        //Then
        assertEquals(trueResult, result);

    }
}
