package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourneeTest {

    static Tournee  tourneeTested;

    @BeforeAll
    static void createTourneeObjects(){
        Intersection intersection = new Intersection();
        Calendar c = Calendar.getInstance();
        c.set(2017,01,01,8,30,00);
        Date strat = c.getTime();
        tourneeTested = new Tournee(intersection,strat,5);
    }

    @Test
    void shouldSetdeliveries(){
        //With

        List<Trajet> roundList = new ArrayList<Trajet>();

        //When
        tourneeTested.setDeliveries(roundList);

        //Then
        assertEquals(roundList,tourneeTested.getDeliveries());
    }

    @Test
    void shouldSetStart() throws ParseException {
        //With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");

        //When
        tourneeTested.setStart(start);

        //Then
        assertEquals(start,tourneeTested.getStart());
    }

    @Test
    void shouldSetWarehouse(){
        //With
        Intersection intersection = new Intersection();

        //When
        tourneeTested.setWarehouse(intersection);

        //Then
        assertEquals(intersection,tourneeTested.getWarehouse());
    }

    @Test
    void shouldSetTime(){
        //With

        //When
        tourneeTested.setTime(52);

        //Then
        assertEquals(52, tourneeTested.getTime());
    }


}
