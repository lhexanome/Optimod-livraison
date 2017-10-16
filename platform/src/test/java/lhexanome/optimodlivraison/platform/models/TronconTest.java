package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TronconTest {
    @Test
    void print(){
        Troncon troncon = new Troncon();
        Intersection intersection = new Intersection();
        intersection.setId(1256);
        troncon.setDestination(intersection);
        Intersection intersection1 = new Intersection();
        intersection1 = troncon.getDestination();
        assertEquals(intersection,intersection1);

        intersection.setId(5687);
        troncon.setOrigine(intersection);
        intersection1 = troncon.getOrigine();
        assertEquals(intersection,intersection1);

        troncon.setNameStreet("Rue de la liberte");
        String s = troncon.getNameStreet();
        assertEquals("Rue de la liberte", s);

        troncon.setLength(22);
        float f = troncon.getLength();
        assertEquals(22,f);

        float result = troncon.timeToTravel();
        float trueResult = troncon.getLength()*15;
        assertEquals(trueResult,result);

    }
}
