package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivraisonTest {

    @Test
    void print(){
        Livraison deliveries = new Livraison();
        deliveries.setDuration(862);
        int duration = deliveries.getDuration();
        assertEquals(862, duration);

        Intersection intersection = new Intersection();
        intersection.setId(1568);
        deliveries.setIntersection(intersection);
        Intersection intersection1 = deliveries.getIntersection();
        assertEquals(intersection, intersection1);

        PlageHoraire slot = new PlageHoraire();
        slot.setStart(new Date(2047,12,24));
        deliveries.setSlot(slot);
        PlageHoraire slot1 = deliveries.getSlot();
        assertEquals(slot, slot1);
    }
}
