package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourTest {

    Tour tourTested;

    @BeforeEach
    void createTourneeObjects() {
        Warehouse intersection =new Warehouse(new Intersection(523L, 1024, 1025));
        Calendar c = Calendar.getInstance();
        c.set(2017, Calendar.OCTOBER, 1, 8, 30, 0);
        Date strat = c.getTime();
        tourTested = new Tour(intersection, strat, 5);
    }

    @Test
    void shouldSetdeliveries() {
        //With

        List<Path> roundList = new ArrayList<>();

        //When
        tourTested.setPaths(roundList);

        //Then
        assertEquals(roundList, tourTested.getPaths());
    }

    @Test
    void shouldSetStart() throws ParseException {
        //With
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");

        //When
        tourTested.setStart(start);

        //Then
        assertEquals(start, tourTested.getStart());
    }

    @Test
    void shouldSetWarehouse() {
        //With
        Warehouse intersection = new Warehouse (new Intersection(599L, 102, 103));

        //When
        tourTested.setWarehouse(intersection);

        //Then
        assertEquals(intersection, tourTested.getWarehouse());
    }

    @Test
    void shouldSetTime() {
        //With

        //When
        tourTested.setTime(52);

        //Then
        assertEquals(52, tourTested.getTime());
    }


}
