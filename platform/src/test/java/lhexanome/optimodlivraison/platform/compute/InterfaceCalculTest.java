package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Entrepot;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Livraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Tournee;
import lhexanome.optimodlivraison.platform.models.Troncon;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InterfaceCalculTest {
    @Test
    void calculerPlanSimplifie() {
    }

    @Test
    void calculerTournee() {
        InterfaceCalcul ic=new InterfaceCalcul();
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        Intersection A = new Intersection(0l, 0, 0);
        Intersection B = new Intersection(1l, 0, 0);
        Intersection C = new Intersection(2l, 0, 0);
        Intersection D = new Intersection(3l, 0, 0);
        Intersection E = new Intersection(4l, 0, 0);
        Intersection F = new Intersection(5l, 0, 0);

        Troncon t = new Troncon(A, B, "A->B", 1);
        Troncon t1 = new Troncon(A, E, "A->E", 2);
        Troncon t2= new Troncon(B, C, "B->C", 1);
        Troncon t3 = new Troncon(B, D, "B->D", 1);
        Troncon t4 = new Troncon(E, C, "E->C", 1);
        Troncon t5 = new Troncon(C, F, "C->F", 1);
        Troncon t6 = new Troncon(F, B, "F->B", 3);
        Troncon t7 = new Troncon(D, A, "D->A", 1);

        plan.addIntersection(A);
        plan.addIntersection(B);
        plan.addIntersection(C);
        plan.addIntersection(D);
        plan.addIntersection(E);
        plan.addIntersection(F);

        plan.addTroncon(t);
        plan.addTroncon(t1);
        plan.addTroncon(t2);
        plan.addTroncon(t3);
        plan.addTroncon(t4);
        plan.addTroncon(t5);
        plan.addTroncon(t6);
        plan.addTroncon(t7);
        Entrepot e=new Entrepot(A);
        demande.setBeginning(e);
        demande.addDelivery(new Livraison(D,0));
        demande.addDelivery(new Livraison(F,0));
        ic.calculerPlanSimplifie(plan,demande);
        Tournee tournee=ic.calculerTournee();
        Tournee tourneeExpected=new Tournee(e,new Date(),0);
        assertEquals(tourneeExpected,tournee);
    }

}