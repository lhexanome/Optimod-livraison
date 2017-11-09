package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import static org.junit.jupiter.api.Assertions.*;

class ChangeTimeSlotCommandTest {
    @Test
    void doUndoRedoTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String d1String = "31-08-1982 10:00:00";
        String d2String = "31-08-1982 10:20:00";
        String d3String = "31-08-1982 10:30:00";
        Date d1 = null;
        Date d2 = null;
        Date d3 = null;

        try {
            d1 = sdf.parse(d1String);
            d2 = sdf.parse(d2String);
            d3 = sdf.parse(d3String);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeSlot t_old = new TimeSlot(d1, d2);
        Intersection i = new Intersection((long)0, 200, 200);
        Delivery d = new Delivery(i, 10, t_old);
        TimeSlot t_new = new TimeSlot(d2, d3);
        Tour tour = new Tour();
        ChangeTimeSlotCommand c = new ChangeTimeSlotCommand(tour, d, t_new);

        TimeSlot test_1 = d.getSlot();
        c.execute();
        TimeSlot test_2 = d.getSlot();
        c.undo();
        TimeSlot test_3 = d.getSlot();
        c.redo();
        TimeSlot test_4 = d.getSlot();

        assertThat(test_2.toString()).isEqualTo(t_new.toString());
        assertThat(test_4.toString()).isEqualTo(t_new.toString());
        assertThat(test_1.toString()).isEqualTo(test_3.toString());
    }
}