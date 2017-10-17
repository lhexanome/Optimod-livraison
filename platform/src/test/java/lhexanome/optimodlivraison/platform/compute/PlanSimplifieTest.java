package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Trajet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanSimplifieTest {
    @Test
    void shortestPathBetweenTwoIntersection() {
        //With
        PlanSimplifie planSimplifie = new PlanSimplifie();
        Intersection start = new Intersection();
        Intersection end = new Intersection();
        Trajet sortieExpected = new Trajet();
        //When
        Trajet sortie=planSimplifie.shortestPathBetweenTwoIntersection(start,end);
        //Then
        assertEquals(sortieExpected,sortie);
    }

}