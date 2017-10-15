package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

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
        deliveries.setIntersection(intersection);
        Intersection intersection1 = deliveries.getIntersection();
        assertEquals(intersection, intersection1);

        PlageHoraire slot = new PlageHoraire();
        deliveries.setSlot(slot);
        PlageHoraire slot1 = deliveries.getSlot();
        assertEquals(slot, slot1);
    }
}
