package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectionTest {

    @Test
    void print (){
        Intersection intersection = new Intersection();
        intersection.setX(10);
        int x= intersection.getX();
        assertEquals(10,x);

        intersection.setY(20);
        int y= intersection.getY();
        assertEquals(20,y);

        intersection.setId(1524789);
        long id= intersection.getId();
        assertEquals(1524789,id);
    }
}
