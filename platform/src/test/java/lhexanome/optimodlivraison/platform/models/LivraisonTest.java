package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivraisonTest {

    @Test
    void shouldSetDuration(){
        //With
        Livraison deliveries = new Livraison();

        //When
        deliveries.setDuration(5236);

        //Then
        assertEquals(5236,deliveries.getDuration());
    }

    @Test
    void shouldSetIntersection(){
        //With
        Livraison deliveries = new Livraison();
        Intersection intersection = new Intersection(320L,195,1995);

        //When
        deliveries.setIntersection(intersection);

        //Then
        assertEquals(intersection,deliveries.getIntersection());
    }

    @Test
    void shouldSetPlageHoraire() throws ParseException {
        //With
        Livraison deliveries = new Livraison();
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");
        Date end = dateFormat.parse("15-02-17 10:55");
        PlageHoraire slot = new PlageHoraire(start, end);

        //When
        deliveries.setSlot(slot);

        //Then
        assertEquals(slot, deliveries.getSlot());
    }
}
