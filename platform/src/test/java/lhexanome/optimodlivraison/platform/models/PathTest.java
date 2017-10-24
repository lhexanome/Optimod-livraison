package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathTest {
    Path pathTested;

    @BeforeEach
    void createTourneeObjects() {
        pathTested = new Path();
    }

    @Test
    void shouldConstructCorrectly() {

        //With

        //When
        pathTested = new Path();

        //Then
        assertEquals(0, pathTested.getTimeToTravel());
        assertEquals(0, pathTested.getVectors().size());
        assertEquals(null, pathTested.getStart());
        assertEquals(null, pathTested.getEnd());

    }

    @Test
    void shouldSetTimeToTravel() {
        //With

        //When
        pathTested.setTimeToTravel(12);

        //Then
        assertEquals(12, pathTested.getTimeToTravel());
    }

    @Test
    void souldGetStart() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector23 = new Vector(i2, i3, "i2 i3");


        //When

        //Then
        assertEquals(null, pathTested.getStart());

        //When
        pathTested.addTroncon(vector12);

        //Then
        assertEquals(i1, pathTested.getStart());

        //When
        pathTested.addTroncon(vector23);

        //Then
        assertEquals(i1, pathTested.getStart());
    }

    @Test
    void souldGetEnd() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector23 = new Vector(i2, i3, "i2 i3");


        //When

        //Then
        assertEquals(null, pathTested.getStart());

        //When
        pathTested.addTroncon(vector12);

        //Then
        assertEquals(i2, pathTested.getEnd());

        //When
        pathTested.addTroncon(vector23);

        //Then
        assertEquals(i3, pathTested.getEnd());
    }

    @Test
    void souldAddTronconBefore() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector23 = new Vector(i2, i3, "i2 i3");


        //When
        pathTested.addTronconBefore(vector23);

        //Then
        assertEquals(1, pathTested.getVectors().size());
        assertEquals(vector23, pathTested.getVectors().get(0));
        assertEquals(vector23.getTimeToTravel(), pathTested.getTimeToTravel());

        //When
        pathTested.addTronconBefore(vector12);

        //Then
        assertEquals(2, pathTested.getVectors().size());
        assertEquals(vector12, pathTested.getVectors().get(0));
        assertEquals(vector23, pathTested.getVectors().get(1));
        assertEquals(vector12.getTimeToTravel() + vector23.getTimeToTravel(), pathTested.getTimeToTravel());
    }

    @Test
    void souldNotAddTronconBeforeIfNotPresseding() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector34 = new Vector(i3, i4, "i3 i4");

        pathTested.addTroncon(vector12);

        //When
        try {

            pathTested.addTronconBefore(vector34);

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

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector23 = new Vector(i2, i3, "i2 i3");


        //When
        pathTested.addTroncon(vector12);

        //Then
        assertEquals(1, pathTested.getVectors().size());
        assertEquals(vector12, pathTested.getVectors().get(0));
        assertEquals(vector12.getTimeToTravel(), pathTested.getTimeToTravel());

        //When
        pathTested.addTroncon(vector23);

        //Then
        assertEquals(2, pathTested.getVectors().size());
        assertEquals(vector12, pathTested.getVectors().get(0));
        assertEquals(vector23, pathTested.getVectors().get(1));
        assertEquals(vector12.getTimeToTravel() + vector23.getTimeToTravel(), pathTested.getTimeToTravel());
    }

    @Test
    void souldNotAddTronconIfNotPresseding() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector34 = new Vector(i3, i4, "i3 i4");

        pathTested.addTroncon(vector12);

        //When
        try {

            pathTested.addTroncon(vector34);

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

        Vector vector12 = new Vector(i1, i2, "i1 i2");
        Vector vector23 = new Vector(i2, i3, "i2 i3");
        Vector vector34 = new Vector(i3, i4, "i3 i4");
        Vector vector45 = new Vector(i4, i5, "i4 i5");


        Path path13 = new Path();
        path13.addTroncon(vector12);
        path13.addTroncon(vector23);
        Path path35 = new Path();
        path35.addTroncon(vector34);
        path35.addTroncon(vector45);


        //When
        pathTested.addPath(path13);


        //Then
        assertEquals(2, pathTested.getVectors().size());
        assertEquals(vector12, pathTested.getVectors().get(0));
        assertEquals(vector23, pathTested.getVectors().get(1));
        assertEquals(path13.getTimeToTravel(), pathTested.getTimeToTravel());

        //When
        pathTested.addPath(path35);

        //Then
        assertEquals(4, pathTested.getVectors().size());
        assertEquals(vector12, pathTested.getVectors().get(0));
        assertEquals(vector23, pathTested.getVectors().get(1));
        assertEquals(vector34, pathTested.getVectors().get(2));
        assertEquals(vector45, pathTested.getVectors().get(3));
        assertEquals(path13.getTimeToTravel() + path35.getTimeToTravel(), pathTested.getTimeToTravel());
    }

    void souldNotAddTrajetIfNotPresseding() {
        //With
        Intersection i1 = new Intersection(1L, 564, 968451);
        Intersection i2 = new Intersection(2L, 25, 968451);
        Intersection i3 = new Intersection(3L, 24, 968451);
        Intersection i4 = new Intersection(4L, 3008, 968451);
        Intersection i5 = new Intersection(5L, 2548, 968451);

        Vector vector12 = new Vector(i1, i2, "i1 i2");

        Vector vector34 = new Vector(i3, i4, "i3 i4");
        Vector vector45 = new Vector(i4, i5, "i4 i5");
        Path path35 = new Path();
        path35.addTroncon(vector34);
        ;
        path35.addTroncon(vector45);

        pathTested.addTroncon(vector12);

        //When
        try {

            pathTested.addPath(path35);

            //Then
            assertTrue(false);

        } catch (RuntimeException e) {
            //Then
            assertTrue(true);
        }

    }
}
