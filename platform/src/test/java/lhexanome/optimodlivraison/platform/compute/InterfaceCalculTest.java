package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.exceptions.ComputeSlotsException;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterfaceCalculTest {
    @Test
    void calculerRoadMapSimplifie() {
    }

   @Test
    void calculerTour() {
        InterfaceCalcul ic = new InterfaceCalcul();
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        Intersection A = new Intersection(0L, 0, 0);
        Intersection B = new Intersection(1L, 0, 0);
        Intersection C = new Intersection(2L, 0, 0);
        Intersection D = new Intersection(3L, 0, 0);
        Intersection E = new Intersection(4L, 0, 0);
        Intersection F = new Intersection(5L, 0, 0);

        Vector t = new Vector(A, B, "A->B", 1000);
        Vector t1 = new Vector(A, E, "A->E", 2000);
        Vector t2 = new Vector(B, C, "B->C", 1000);
        Vector t3 = new Vector(B, D, "B->D", 1000);
        Vector t4 = new Vector(E, C, "E->C", 1000);
        Vector t5 = new Vector(C, F, "C->F", 1000);
        Vector t6 = new Vector(F, B, "F->B", 3000);
        Vector t7 = new Vector(D, A, "D->A", 1000);

        roadMap.addIntersection(A);
        roadMap.addIntersection(B);
        roadMap.addIntersection(C);
        roadMap.addIntersection(D);
        roadMap.addIntersection(E);
        roadMap.addIntersection(F);

        roadMap.addVector(t);
        roadMap.addVector(t1);
        roadMap.addVector(t2);
        roadMap.addVector(t3);
        roadMap.addVector(t4);
        roadMap.addVector(t5);
        roadMap.addVector(t6);
        roadMap.addVector(t7);
        Warehouse e = new Warehouse(A);
        demande.setBeginning(e);
        demande.addDelivery(new Delivery(D, 0));
        demande.addDelivery(new Delivery(F, 0));
        demande.setStart(new Date());
        SimplifiedMap simplifiedMap = ic.computeSimplifiedRoadMap(roadMap, demande);
       try {
           Tour tour = ic.computeTour(simplifiedMap, demande,TspTypes.HEURISTICS_1);
           Tour tourExpected = ic.computeTour(simplifiedMap, demande,TspTypes.NO_HEURISTICS);

       } catch (ComputeSlotsException e1) {
           e1.printStackTrace();
       }
        //assertEquals(tourExpected,tour);
    }

}