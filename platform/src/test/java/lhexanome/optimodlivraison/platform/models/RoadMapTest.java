package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class RoadMapTest {
    RoadMap roadMapTested;
    Intersection i1, i2, i3, i4, i5, i6, i7, i8;
    Intersection[] allI;
    Vector t12, t13, t14, t18, t21, t24, t25, t27, t28, t32, t45, t54, t56, t72, t81, t82;
    Vector[] allT;

    /*
    Particulariter:
        i6 (neud absobant)
        i5+i4+i6 (neud absobant)
        t127/t721 (double sens)
        i7 bout sans suite (mais demis tour possible)
        i1->i2->i3->i1 circuit sans unique
        i1->i2->i8->i1 circuit double sens
     */

    @BeforeEach
    void createTourneeObjects() {
        roadMapTested = new RoadMap();

        i1 = new Intersection(1001L, 123, 126);
        i2 = new Intersection(1002L, 456, 453);
        i3 = new Intersection(1003L, 753, 159);
        i4 = new Intersection(1004L, 483, 326);
        i5 = new Intersection(1005L, 513, 876);
        i6 = new Intersection(1006L, 729, 183);
        i5 = new Intersection(1005L, 513, 876);
        i6 = new Intersection(1006L, 729, 183);
        i7 = new Intersection(1007L, 719, 149);
        i8 = new Intersection(1008L, 861, 953);
        allI = new Intersection[]{i1, i2, i3, i4, i5, i6, i7, i8};

        t12 = new Vector(i1, i2, "avenue double sans");
        roadMapTested.addTroncon(t12);
        t13 = new Vector(i1, i3, "rue sans unique de l'avenue vers l'avenue");
        roadMapTested.addTroncon(t13);
        t14 = new Vector(i1, i4, "rue go i6");
        roadMapTested.addTroncon(t14);
        t18 = new Vector(i1, i8, "rue 1-8-2");
        roadMapTested.addTroncon(t18);
        t21 = new Vector(i2, i1, "avenue double sans");
        roadMapTested.addTroncon(t21);
        t24 = new Vector(i2, i4, "rue go rue go i6 (1)");
        roadMapTested.addTroncon(t24);
        t25 = new Vector(i2, i5, "rue go rue go i6 (2)");
        roadMapTested.addTroncon(t25);
        t27 = new Vector(i2, i7, "avenue double sans");
        roadMapTested.addTroncon(t27);
        t28 = new Vector(i2, i8, "rue 1-8-2");
        roadMapTested.addTroncon(t28);
        t32 = new Vector(i3, i2, "rue sans unique de l'avenue vers l'avenue");
        roadMapTested.addTroncon(t32);
        t45 = new Vector(i4, i5, "rue go i6");
        roadMapTested.addTroncon(t45);
        t54 = new Vector(i5, i4, "rue go i6");
        roadMapTested.addTroncon(t54);
        t56 = new Vector(i5, i6, "rue go i6");
        roadMapTested.addTroncon(t56);
        t72 = new Vector(i7, i2, "avenue double sans");
        roadMapTested.addTroncon(t72);
        t81 = new Vector(i8, i1, "rue 1-8-2");
        roadMapTested.addTroncon(t81);
        t82 = new Vector(i8, i2, "rue 1-8-2");
        roadMapTested.addTroncon(t82);
        allT = new Vector[]{t12, t13, t14, t18, t21, t24, t25, t27, t28, t32, t45, t54, t56, t72, t81, t82};
    }

    @Test
    void shouldAddTronconCorrectly() {

        // TODO spleet test

        //With

        RoadMap roadMap = new RoadMap();

        //fist Vector
        //When
        roadMap.addTroncon(t12);

        Collection<Vector> toncons = roadMap.getVectors();
        Collection<Intersection> intersections = roadMap.getIntersections();

        //Then
        assertEquals(2, intersections.size());
        assertEquals(1, toncons.size());
        assertTrue(intersections.containsAll(Arrays.asList(i1, i2)));
        assertTrue(toncons.containsAll(Arrays.asList(t12)));

        //Vector with know origine
        //When
        roadMap.addTroncon(t13);

        toncons = roadMap.getVectors();
        intersections = roadMap.getIntersections();

        //Then
        assertEquals(3, intersections.size());
        assertEquals(2, toncons.size());
        assertTrue(intersections.containsAll(Arrays.asList(i1, i2, i3)));
        assertTrue(toncons.containsAll(Arrays.asList(t12, t13)));


        //Vector with know destination
        //When
        roadMap.addTroncon(t82);

        toncons = roadMap.getVectors();
        intersections = roadMap.getIntersections();

        //Then
        assertEquals(4, intersections.size());
        assertEquals(3, toncons.size());
        assertTrue(intersections.containsAll(Arrays.asList(i1, i2, i3, i8)));
        assertTrue(toncons.containsAll(Arrays.asList(t12, t13, t82)));


        //Vector alredy added
        //When
        roadMap.addTroncon(t12);

        toncons = roadMap.getVectors();
        intersections = roadMap.getIntersections();

        //Then
        assertEquals(4, intersections.size());
        assertEquals(4, toncons.size());
        assertTrue(intersections.containsAll(Arrays.asList(i1, i2, i3, i8)));
        assertTrue(toncons.containsAll(Arrays.asList(t12, t13, t82)));
        assertEquals(2, Collections.frequency(toncons, t12));
    }


    @Test
    void shouldAddIntersectionCorrectly() {

        // TODO spleet test
        //With

        Intersection newIntersection = new Intersection(589156L, 1536, 4759);
        RoadMap roadMap = new RoadMap();

        //When
        roadMap.addIntersection(newIntersection);

        //Then
        assertEquals(newIntersection, roadMap.findIntersectionById(589156L));
    }

    @Test
    void shouldGetTronconsFromIntersection() {


        // TODO spleet test

        //With

        //When

        Collection<Vector> toncons = roadMapTested.getTronconsFromIntersection(i1);

        //Then
        assertEquals(4, toncons.size());
        assertTrue(toncons.containsAll(Arrays.asList(t12, t13, t14, t18)));

        //When know intersection but without troncon

        toncons = roadMapTested.getTronconsFromIntersection(i6);

        //Then
        assertNotNull(toncons);
        assertEquals(0, toncons.size());


        //When unknow intersection

        toncons = roadMapTested.getTronconsFromIntersection(new Intersection(666L, 426, 684));

        //Then
        assertNotNull(toncons);
        assertEquals(0, toncons.size());
    }

    @Test
    void shouldGetTroncons() {


        // TODO spleet test

        //With
        RoadMap roadMap = new RoadMap();

        //When EmptyPlan

        Collection<Vector> toncons = roadMap.getVectors();



        //Then
        assertNotNull(toncons);
        assertEquals(0, toncons.size());

        //When

        toncons = roadMapTested.getVectors();

        //Then
        assertEquals(allT.length, toncons.size());
        assertTrue(toncons.containsAll(Arrays.asList(allT)));

    }

    @Test
    void shouldGetIntersectionCount() {


        // TODO spleet test

        //With
        RoadMap roadMap = new RoadMap();

        //When EmptyPlan

        int intersectionCount = roadMap.getIntersectionCount();

        //Then

        assertEquals(0, intersectionCount);

        //When

        intersectionCount = roadMapTested.getIntersectionCount();

        //Then
        assertEquals(allI.length, intersectionCount);

    }


    @Test
    void shouldGetIntersections() {


        // TODO spleet test

        //With
        RoadMap roadMap = new RoadMap();

        //When EmptyPlan

        Collection<Intersection> intersections = roadMap.getIntersections();

        //Then

        assertNotNull(intersections);
        assertEquals(0, intersections.size());

        //When

        intersections = roadMapTested.getIntersections();

        //Then

        assertEquals(allI.length, intersections.size());
        assertTrue(intersections.containsAll(Arrays.asList(allI)));

    }

    @Test
    void shouldFindIntersectionById() {


        // TODO spleet test

        //With
        RoadMap roadMap = new RoadMap();

        //When EmptyPlan

        Intersection intersection = roadMap.findIntersectionById(658L);

        //Then

        assertNull(intersection);

        //When unKnowId

        intersection = roadMapTested.findIntersectionById(55555555555L);

        //Then

        assertNull(intersection);

        //When

        intersection = roadMapTested.findIntersectionById(i6.getId());

        //Then

        assertNotNull(intersection);
        assertEquals(i6, intersection);

    }

}
