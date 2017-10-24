package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeliveryTest {


    private Intersection intersection;
    private Delivery deliveryToTest;
    private TimeSlot slot;

    @BeforeEach
    void createLivraisonObjects() throws ParseException {
        intersection = new Intersection(3569L, 726, 501);
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("16-08-17 11:25");
        Date end = dateFormat.parse("16-08-17 13:35");
        slot = new TimeSlot(start, end);
        deliveryToTest = new Delivery(intersection, 35, slot);
    }

    @Test
    void shouldConstructCorrectly() throws ParseException {
        // With

        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("16-08-17 21:25");
        Date end = dateFormat.parse("16-08-17 23:35");
        TimeSlot slot = new TimeSlot(start, end);
        Intersection intersection = new Intersection(325L, 194, 478);

        // When
        deliveryToTest = new Delivery(intersection, 65, slot);

        // Then
        assertEquals(intersection, deliveryToTest.getIntersection());
        assertEquals(65, deliveryToTest.getDuration());
        assertEquals(slot, deliveryToTest.getSlot());
    }

    @Test
    void shouldConstructCorrectly2() {
        // With

        Intersection intersection = new Intersection(325L, 194, 478);

        // When
        deliveryToTest = new Delivery(intersection, 43);

        // Then
        assertEquals(intersection, deliveryToTest.getIntersection());
        assertEquals(43, deliveryToTest.getDuration());
        assertNull(deliveryToTest.getSlot());
    }

    @Test
    void shouldSetDuration() {
        //With

        //When
        deliveryToTest.setDuration(5236);

        //Then
        assertEquals(5236, deliveryToTest.getDuration());
    }

    @Test
    void shouldSetIntersection() {
        //With
        Intersection intersection = new Intersection(320L, 195, 1995);

        //When
        deliveryToTest.setIntersection(intersection);

        //Then
        assertEquals(intersection, deliveryToTest.getIntersection());
    }

    @Test
    void shouldSetPlageHoraire() throws ParseException {
        //With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");
        Date end = dateFormat.parse("15-02-17 10:55");
        TimeSlot slot = new TimeSlot(start, end);

        //When
        deliveryToTest.setSlot(slot);

        //Then
        assertEquals(slot, deliveryToTest.getSlot());
    }
}
