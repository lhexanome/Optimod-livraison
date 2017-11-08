package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathTest {
    Path pathTested;



    @Test
    void shouldConstructCorrectly() {

        //With
        Intersection i = new Intersection(0l);
        Halt b = new Halt(i);
        Intersection i2 = new Intersection(0l);
        Halt e = new Halt(i2);
        //When
        pathTested = new Path(b,e);

        //Then
        assertEquals(0, pathTested.getTimeToTravel());
        assertEquals(0, pathTested.getVectors().size());
        assertEquals(b, pathTested.getStart());
        assertEquals(e, pathTested.getEnd());

    }

    @Test
    void shouldSetTimeToTravel() {
        //With
        Intersection i = new Intersection(0l);
        Halt b = new Halt(i);
        Intersection i2 = new Intersection(0l);
        Halt e = new Halt(i2);
        pathTested = new Path(b,e);
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
        Halt b = new Halt(i1);
        Halt e = new Halt(i2);
        pathTested = new Path(b,e);
        pathTested.addVector(vector12);

        //Then
        assertEquals(b, pathTested.getStart());

        //When
        pathTested.addVector(vector23);

        //Then
        assertEquals(b, pathTested.getStart());
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
        Halt b = new Halt(i1);
        Halt e = new Halt(i3);
        pathTested = new Path(b,e);
        pathTested.addVector(vector12);

        //Then
        assertEquals(e, pathTested.getEnd());

        //When
        pathTested.addVector(vector23);

        //Then
        assertEquals(e, pathTested.getEnd());
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
        Halt b = new Halt(i1);
        Halt e = new Halt(i3);
        pathTested = new Path(b,e);
        pathTested.addVectorBefore(vector23);

        //Then
        assertEquals(1, pathTested.getVectors().size());
        assertEquals(vector23, pathTested.getVectors().get(0));
        assertEquals(vector23.getTimeToTravel(), pathTested.getTimeToTravel());

        //When
        pathTested.addVectorBefore(vector12);

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
        Halt b = new Halt(i1);
        Halt e = new Halt(i4);
        pathTested = new Path(b,e);
        pathTested.addVector(vector12);

        //When
        try {

            pathTested.addVectorBefore(vector34);

            //Then
            assertTrue(false);

        } catch (RuntimeException ex) {
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
        Halt b = new Halt(i1);
        Halt e = new Halt(i3);
        pathTested = new Path(b,e);

        //When
        pathTested.addVector(vector12);

        //Then
        assertEquals(1, pathTested.getVectors().size());
        assertEquals(vector12, pathTested.getVectors().get(0));
        assertEquals(vector12.getTimeToTravel(), pathTested.getTimeToTravel());

        //When
        pathTested.addVector(vector23);

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
        Halt b = new Halt(i1);
        Halt e = new Halt(i4);
        pathTested = new Path(b,e);
        pathTested.addVector(vector12);

        //When
        try {

            pathTested.addVector(vector34);

            //Then
            assertTrue(false);

        } catch (RuntimeException ex) {
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
        Halt b1 = new Halt(i1);
        Halt e1 = new Halt(i1);
        Halt e2 = new Halt(i3);
        Halt e3 = new Halt(i5);
        pathTested = new Path(b1,e1);

        Path path13 = new Path(e1,e2);
        path13.addVector(vector12);
        path13.addVector(vector23);
        Path path35 = new Path(e2,e3);
        path35.addVector(vector34);
        path35.addVector(vector45);


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
        Halt b1 = new Halt(i1);
        Halt e1 = new Halt(i2);
        Halt e2 = new Halt(i3);
        Halt e3 = new Halt(i5);
        pathTested = new Path(b1,e1);
        Path path35 = new Path(e2,e3);
        path35.addVector(vector34);

        path35.addVector(vector45);

        pathTested.addVector(vector12);

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
