package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectionTest {
    Intersection intersectionTested;

    @BeforeEach
    void createTourneeObjects() {
        intersectionTested = new Intersection(20368L, 1024, 1025);
    }

    @Test
    void shouldConstructCorrectly() {
        // With

        // When
        Intersection intersection = new Intersection(20299L, 147, 258);

        // Then
        assertEquals(20299L, intersection.getId());
        assertEquals(147, intersection.getX());
        assertEquals(258, intersection.getY());
    }

    @Test
    void shouldSetX() {
        // With

        // When
        intersectionTested.setX(1234);

        // Then
        assertEquals(1234, intersectionTested.getX());
    }

    @Test
    void shouldSetY() {
        // With

        // When
        intersectionTested.setY(1234);

        // Then
        assertEquals(1234, intersectionTested.getY());
    }

    @Test
    void shouldSetId() {
        // With

        // When
        intersectionTested.setId(1234L);

        // Then
        assertEquals(1234L, intersectionTested.getId());
    }
}
