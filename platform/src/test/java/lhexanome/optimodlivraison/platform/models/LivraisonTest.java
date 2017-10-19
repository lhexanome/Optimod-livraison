package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LivraisonTest {


    private Intersection intersection;
    private Livraison livraisonToTest;
    private PlageHoraire slot;

    @BeforeEach
    void createLivraisonObjects() throws ParseException {
        intersection = new Intersection(3569L, 726, 501);
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("16-08-17 11:25");
        Date end = dateFormat.parse("16-08-17 13:35");
        slot = new PlageHoraire(start, end);
        livraisonToTest = new Livraison(intersection, 35, slot);
    }

    @Test
    void shouldConstructCorrectly() throws ParseException {
        // With

        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("16-08-17 21:25");
        Date end = dateFormat.parse("16-08-17 23:35");
        PlageHoraire slot = new PlageHoraire(start, end);
        Intersection intersection = new Intersection(325L, 194, 478);

        // When
        livraisonToTest = new Livraison(intersection, 65, slot);

        // Then
        assertEquals(intersection, livraisonToTest.getIntersection());
        assertEquals(65, livraisonToTest.getDuration());
        assertEquals(slot, livraisonToTest.getSlot());
    }

    @Test
    void shouldConstructCorrectly2() {
        // With

        Intersection intersection = new Intersection(325L, 194, 478);

        // When
        livraisonToTest = new Livraison(intersection, 43);

        // Then
        assertEquals(intersection, livraisonToTest.getIntersection());
        assertEquals(43, livraisonToTest.getDuration());
        assertNull(livraisonToTest.getSlot());
    }

    @Test
    void shouldSetDuration() {
        //With

        //When
        livraisonToTest.setDuration(5236);

        //Then
        assertEquals(5236, livraisonToTest.getDuration());
    }

    @Test
    void shouldSetIntersection() {
        //With
        Intersection intersection = new Intersection(320L, 195, 1995);

        //When
        livraisonToTest.setIntersection(intersection);

        //Then
        assertEquals(intersection, livraisonToTest.getIntersection());
    }

    @Test
    void shouldSetPlageHoraire() throws ParseException {
        //With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");
        Date end = dateFormat.parse("15-02-17 10:55");
        PlageHoraire slot = new PlageHoraire(start, end);

        //When
        livraisonToTest.setSlot(slot);

        //Then
        assertEquals(slot, livraisonToTest.getSlot());
    }
}
