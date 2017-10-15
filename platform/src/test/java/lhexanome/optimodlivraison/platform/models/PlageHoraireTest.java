package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlageHoraireTest {
    @Test
    void print(){
        PlageHoraire slot = new PlageHoraire();
        Date date = new Date(2015,10,21);
        slot.setStart(date);
        Date date1 = slot.getStart();
        assertEquals(date,date1);

        Date date2 = new Date (1955,11,12);
        slot.setEnd(date2);
        Date date3 = slot.getEnd();
        assertEquals(date2,date3);
    }
}
