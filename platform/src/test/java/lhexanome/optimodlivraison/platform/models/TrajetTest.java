package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrajetTest {
    Trajet trajetTested;

    @BeforeEach
    void createTourneeObjects() {
        trajetTested = new Trajet();
    }

    @Test
    void shouldConstructCorrectly() {

        //With

        //When
        trajetTested = new Trajet();

        //Then
        assertEquals(0, trajetTested.getTimeToTravel());
        assertEquals(0, trajetTested.getTroncons().size());
        assertEquals(null, trajetTested.getStart());
        assertEquals(null, trajetTested.getEnd());

    }

    @Test
    void shouldSetTimeToTravel() {
        //With

        //When
        trajetTested.setTimeToTravel(12);

        //Then
        assertEquals(12, trajetTested.getTimeToTravel());
    }

    @Test
    void souldGetStart() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon23 = new Troncon(i2, i3, "i2 i3");


        //When

        //Then
        assertEquals(null, trajetTested.getStart());

        //When
        trajetTested.addTroncon(troncon12);

        //Then
        assertEquals(i1, trajetTested.getStart());

        //When
        trajetTested.addTroncon(troncon23);

        //Then
        assertEquals(i1, trajetTested.getStart());
    }

    @Test
    void souldGetEnd() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon23 = new Troncon(i2, i3, "i2 i3");


        //When

        //Then
        assertEquals(null, trajetTested.getStart());

        //When
        trajetTested.addTroncon(troncon12);

        //Then
        assertEquals(i2, trajetTested.getEnd());

        //When
        trajetTested.addTroncon(troncon23);

        //Then
        assertEquals(i3, trajetTested.getEnd());
    }

    @Test
    void souldAddTronconBefore() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon23 = new Troncon(i2, i3, "i2 i3");


        //When
        trajetTested.addTronconBefore(troncon23);

        //Then
        assertEquals(1, trajetTested.getTroncons().size());
        assertEquals(troncon23, trajetTested.getTroncons().get(0));
        assertEquals(troncon23.getTimeToTravel(), trajetTested.getTimeToTravel());

        //When
        trajetTested.addTronconBefore(troncon12);

        //Then
        assertEquals(2, trajetTested.getTroncons().size());
        assertEquals(troncon12, trajetTested.getTroncons().get(0));
        assertEquals(troncon23, trajetTested.getTroncons().get(1));
        assertEquals(troncon12.getTimeToTravel() + troncon23.getTimeToTravel(), trajetTested.getTimeToTravel());
    }

    @Test
    void souldNotAddTronconBeforeIfNotPresseding() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon34 = new Troncon(i3, i4, "i3 i4");

        trajetTested.addTroncon(troncon12);

        //When
        try {

            trajetTested.addTronconBefore(troncon34);

            //Then
            assertTrue(false);

        } catch (RuntimeException e) {
            //Then
            assertTrue(true);
        }

    }

    @Test
    void souldAddTroncon() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon23 = new Troncon(i2, i3, "i2 i3");


        //When
        trajetTested.addTroncon(troncon12);

        //Then
        assertEquals(1, trajetTested.getTroncons().size());
        assertEquals(troncon12, trajetTested.getTroncons().get(0));
        assertEquals(troncon12.getTimeToTravel(), trajetTested.getTimeToTravel());

        //When
        trajetTested.addTroncon(troncon23);

        //Then
        assertEquals(2, trajetTested.getTroncons().size());
        assertEquals(troncon12, trajetTested.getTroncons().get(0));
        assertEquals(troncon23, trajetTested.getTroncons().get(1));
        assertEquals(troncon12.getTimeToTravel() + troncon23.getTimeToTravel(), trajetTested.getTimeToTravel());
    }

    @Test
    void souldNotAddTronconIfNotPresseding() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon34 = new Troncon(i3, i4, "i3 i4");

        trajetTested.addTroncon(troncon12);

        //When
        try {

            trajetTested.addTroncon(troncon34);

            //Then
            assertTrue(false);

        } catch (RuntimeException e) {
            //Then
            assertTrue(true);
        }

    }

    @Test
    void souldAddTrajet() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);
        Intersection i5 = new Intersection(5L, 2548, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");
        Troncon troncon23 = new Troncon(i2, i3, "i2 i3");
        Troncon troncon34 = new Troncon(i3, i4, "i3 i4");
        Troncon troncon45 = new Troncon(i4, i5, "i4 i5");


        Trajet trajet13 = new Trajet();
        trajet13.addTroncon(troncon12);
        trajet13.addTroncon(troncon23);
        Trajet trajet35 = new Trajet();
        trajet35.addTroncon(troncon34);
        trajet35.addTroncon(troncon45);


        //When
        trajetTested.addTrajet(trajet13);


        //Then
        assertEquals(2, trajetTested.getTroncons().size());
        assertEquals(troncon12, trajetTested.getTroncons().get(0));
        assertEquals(troncon23, trajetTested.getTroncons().get(1));
        assertEquals(trajet13.getTimeToTravel(), trajetTested.getTimeToTravel());

        //When
        trajetTested.addTrajet(trajet35);

        //Then
        assertEquals(4, trajetTested.getTroncons().size());
        assertEquals(troncon12, trajetTested.getTroncons().get(0));
        assertEquals(troncon23, trajetTested.getTroncons().get(1));
        assertEquals(troncon34, trajetTested.getTroncons().get(2));
        assertEquals(troncon45, trajetTested.getTroncons().get(3));
        assertEquals(trajet13.getTimeToTravel() + trajet35.getTimeToTravel(), trajetTested.getTimeToTravel());
    }

    void souldNotAddTrajetIfNotPresseding() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);
        Intersection i5 = new Intersection(5L, 2548, 968451);

        Troncon troncon12 = new Troncon(i1, i2, "i1 i2");

        Troncon troncon34 = new Troncon(i3, i4, "i3 i4");
        Troncon troncon45 = new Troncon(i4, i5, "i4 i5");
        Trajet trajet35 = new Trajet();
        trajet35.addTroncon(troncon34);
        ;
        trajet35.addTroncon(troncon45);

        trajetTested.addTroncon(troncon12);

        //When
        try {

            trajetTested.addTrajet(trajet35);

            //Then
            assertTrue(false);

        } catch (RuntimeException e) {
            //Then
            assertTrue(true);
        }

    }
}
