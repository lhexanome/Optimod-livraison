package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourneeTest {

    @Test
    void shouldSetdeliveries(){
        //With
        Tournee round = new Tournee();
        List<Trajet> roundList = new ArrayList<Trajet>();
        Trajet journey = new Trajet();
        journey.setTime(123);
        roundList.add(journey);

        //When
        round.setDeliveries(roundList);

        //Then
        assertEquals(roundList,round.getDeliveries());
    }

    @Test
    void shouldSetStart() throws ParseException {
        //With
        Tournee round = new Tournee();
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");

        //When
        round.setStart(start);

        //Then
        assertEquals(start,round.getStart());
    }

    @Test
    void shouldSetWarehouse(){
        //With
        Tournee round = new Tournee();
        Intersection intersection = new Intersection();
        intersection.setId(5L);

        //When
        round.setWarehouse(intersection);

        //Then
        assertEquals(intersection,round.getWarehouse());
    }

    @Test
    void shouldSetTime(){
        //With
        Tournee round = new Tournee();

        //When
        round.setTime(52);

        //Then
        assertEquals(52, round.getTime());
    }
}
