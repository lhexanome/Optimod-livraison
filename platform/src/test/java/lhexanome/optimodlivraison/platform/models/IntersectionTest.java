package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectionTest {

    @Test
    void shouldSetX() {
        // With
        Intersection intersection = new Intersection();

        // When
        intersection.setX(1234);

        // Then
        assertEquals(1234, intersection.getX());
    }

    @Test
    void shouldSetY() {
        // With
        Intersection intersection = new Intersection();

        // When
        intersection.setY(1234);

        // Then
        assertEquals(1234, intersection.getY());
    }

    @Test
    void shouldSetId() {
        // With
        Intersection intersection = new Intersection();

        // When
        intersection.setId(1234L);

        // Then
        assertEquals(1234L, intersection.getId());
    }
}
