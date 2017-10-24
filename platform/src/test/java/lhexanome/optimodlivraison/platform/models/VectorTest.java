package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    Vector vectorTested;

    @BeforeEach
    void createTourneeObjects() {
        vectorTested = new Vector(
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
        vectorTested.setDestination(intersection);

        //Then
        assertEquals(intersection, vectorTested.getDestination());
    }

    @Test
    void shouldSetOrigin() {

        //With
        Intersection intersection = new Intersection(125L, 125, 130);

        //When
        vectorTested.setOrigin(intersection);

        //Then
        assertEquals(intersection, vectorTested.getOrigin());
    }

    @Test
    void shouldSetNameStreet() {

        //With

        //When
        vectorTested.setNameStreet("Rue de la liberte");

        //Then
        assertEquals("Rue de la liberte", vectorTested.getNameStreet());

    }

    void shouldSetLenght() {

        //With

        //When
        vectorTested.setLength(22);

        //Then
        assertEquals(22, vectorTested.getLength());
    }

    void shouldGetTimeToTravel() {

        //With

        //When
        vectorTested.setLength(22);
        float result = vectorTested.getTimeToTravel();
        float trueResult = vectorTested.getLength() * 15;

        //Then
        assertEquals(trueResult, result);

    }
}
