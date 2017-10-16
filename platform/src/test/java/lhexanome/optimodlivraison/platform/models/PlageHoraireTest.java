package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlageHoraireTest {
    @Test
    void shouldConstructCorrectly() throws ParseException {
        // With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 10:55");
        Date end = dateFormat.parse("15-02-17 11:55");

        // When

        PlageHoraire slot = new PlageHoraire(start, end);

        // Then

        assertEquals(slot.getStart(), start);
        assertEquals(slot.getEnd(), end);
    }

    @Test
    void shouldConstructCorrectlyEvenWithInvertedDate() throws ParseException {
        // With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");
        Date end = dateFormat.parse("15-02-17 10:55");

        // When

        PlageHoraire slot = new PlageHoraire(start, end);

        // Then

        assertEquals(slot.getStart(), end);
        assertEquals(slot.getEnd(), start);
    }


    @Test
    void shouldReturnTrueWhenDateIsIncluded() throws ParseException {
        // With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 10:55");
        Date end = dateFormat.parse("15-02-17 12:55");
        Date middle = dateFormat.parse("15-02-17 11:55");

        // When

        PlageHoraire slot = new PlageHoraire(start, end);

        // Then

        assertTrue(slot.isIncluded(middle));
    }
}
