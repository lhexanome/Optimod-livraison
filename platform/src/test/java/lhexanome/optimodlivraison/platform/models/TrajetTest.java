package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrajetTest {

    @Test
    void shouldSetTime(){
        //With
        Trajet journey = new Trajet();

        //When
        journey.setTime(12);

        //Then
        assertEquals(12,journey.getTime());
    }

    @Test
    void shouldSetStart(){
        //With
        Trajet journey = new Trajet();
        Intersection intersection = new Intersection(601L,123,456);

        //When
        journey.setStart(intersection);

        //Then
        assertEquals(intersection,journey.getStart());
    }

    @Test
    void shouldSetEnd(){
        //With
        Trajet journey = new Trajet();
        Intersection intersection = new Intersection(602L,789,456);

        //When
        journey.setEnd(intersection);

        //Then
        assertEquals(intersection, journey.getEnd());
    }
}
