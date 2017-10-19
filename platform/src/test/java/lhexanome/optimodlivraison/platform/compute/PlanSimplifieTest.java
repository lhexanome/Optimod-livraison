package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Livraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Trajet;
import lhexanome.optimodlivraison.platform.models.Troncon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlanSimplifieTest {


    //dijkstra

    /**
     * cas avec deux intersections connectees par un trajet
     */
    @Test
    void shouldDijkstraCasBase() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
        ArrayList<Intersection> intersections = new ArrayList<>();
       
         plan.addIntersection(start);
        plan.addIntersection(end);
        intersections.add(start);
        intersections.add(end);
        Troncon t = new Troncon(start,end,"t",10);
       
        plan.addTroncon(t);
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();

        predecesseurs.add(null);
        predecesseurs.add(null);
        chemins.add(null);
        chemins.add(null);
        cheminsExpected.add(null);
        cheminsExpected.add(t);
        predecesseursExpected.add(null);
        predecesseursExpected.add(start);
        float []tempsDijkstra=new float[plan.getIntersectionCount()];
        boolean []etatDijkstra=new boolean[plan.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        planSimplifie.dijkstra(intersections, predecesseurs,chemins,tempsDijkstra,etatDijkstra);
        //Then
        assertIterableEquals(predecesseursExpected, predecesseurs);
        assertIterableEquals(cheminsExpected, chemins);
    }

    /**
     * cas avec deux intersections non connectees
     */
    @Test
    void shouldDijkstraCasBaseNonCo() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
        ArrayList<Intersection> intersections = new ArrayList<>();
         plan.addIntersection(start);
        plan.addIntersection(end);
        intersections.add(start);
        intersections.add(end);

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();
        predecesseurs.add(null);
        predecesseurs.add(null);

        predecesseursExpected.add(null);
        predecesseursExpected.add(null);
        chemins.add(null);
        chemins.add(null);

        cheminsExpected.add(null);
        cheminsExpected.add(null);

        float []tempsDijkstra=new float[plan.getIntersectionCount()];
        boolean []etatDijkstra=new boolean[plan.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        planSimplifie.dijkstra(intersections, predecesseurs,chemins,tempsDijkstra,etatDijkstra);
        //Then
        assertIterableEquals(predecesseursExpected, predecesseurs);
        assertIterableEquals(cheminsExpected, chemins);
    }


    /**
     * cas avec plan xml
     */
    @Test
    void shouldDijkstraCasComplexe() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        //TODO charger un plan xml
        //TODO charger demande xml
        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs attendus
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();
        //TODO remplir les chemins attendus
        planSimplifie.initIntersectionList(demande.getBeginning(),intersections,predecesseurs,chemins);
        float []tempsDijkstra=new float[plan.getIntersectionCount()];
        boolean []etatDijkstra=new boolean[plan.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        planSimplifie.dijkstra(intersections, predecesseurs,chemins,tempsDijkstra,etatDijkstra);
        //Then
        assertIterableEquals(predecesseursExpected, predecesseurs);
        assertIterableEquals(cheminsExpected, chemins);
    }


    //-------------------------------------------------------
    //initIntersectionList

    /**
     * cas vide
     */
    @Test
    void shouldInitIntersectionListCasVide() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);

        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> intersectionsExpected = new ArrayList<>();

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();

        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();


        //When
        //TODO test avec une methode private
        planSimplifie.initIntersectionList(null,intersections, predecesseurs,chemins);
        //Then
        assertIterableEquals(predecesseursExpected, predecesseurs);
        assertIterableEquals(intersectionsExpected, intersections);
        assertIterableEquals(cheminsExpected, chemins);


    }

    /**
     * cas avec deux intersections connectees par un trajet
     */
    @Test
    void shouldInitIntersectionListCasBase() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
         plan.addIntersection(start);
        plan.addIntersection(end);
        Troncon t = new Troncon(start,end,"t",10);
        plan.addTroncon(t);
        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> intersectionsExpected = new ArrayList<>();
        intersectionsExpected.add(start);
        intersectionsExpected.add(end);
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        predecesseursExpected.add(null);
        predecesseursExpected.add(null);
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();
        cheminsExpected.add(null);
        cheminsExpected.add(null);

        //When
        //TODO test avec une methode private
        planSimplifie.initIntersectionList(start,intersections, predecesseurs,chemins);
        //Then
        assertIterableEquals(predecesseursExpected, predecesseurs);
        assertIterableEquals(intersectionsExpected, intersections);
        assertIterableEquals(cheminsExpected, chemins);

    }


    /**
     * cas avec plan xml
     */
    @Test
    void shouldInitIntersectionListCasComplexe() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        //TODO charger un plan xml

        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> intersectionsExpected = new ArrayList<>();
        //TODO remplir les intersections attendus
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs attendus
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();
        //TODO remplir les chemins attendus
        //When
        //TODO test avec une methode private
        planSimplifie.initIntersectionList(demande.getBeginning(),intersections, predecesseurs,chemins);
        //Then
        assertIterableEquals(predecesseursExpected, predecesseurs);
        assertIterableEquals(intersectionsExpected, intersections);
        assertIterableEquals(cheminsExpected, chemins);

    }


    //----------------------------------------------------------------
    //shortestPathList
    /**
     * cas avec 3 livraisons connectees par 2 trajets
     */
    @Test
    void shortestPathListCasBase() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
        Intersection end2 = new Intersection(2l,0,0);

         plan.addIntersection(start);
        plan.addIntersection(end);
        Troncon t = new Troncon(start,end,"t",10);
        plan.addTroncon(t);
        Troncon t2 = new Troncon(start,end2,"t2",20);
        plan.addTroncon(t2);
        ArrayList<Trajet> sortie = new ArrayList<>();
        ArrayList<Trajet> sortieExpected = new ArrayList<>();
        Livraison startLivraison = new Livraison(start,1);
        Livraison l1 = new Livraison(end,1);
        Livraison l2 = new Livraison(end2,1);
        HashSet<Livraison> ends= new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        Trajet tr= new Trajet();
        tr.addTroncon(t);
        sortieExpected.add(tr);
        Trajet tr2= new Trajet();
        tr2.addTroncon(t2);
        sortieExpected.add(tr2);
        //When
        //TODO test avec une methode private
        sortie=planSimplifie.shortestPathList(startLivraison, ends);
        //Then

        assertIterableEquals(sortieExpected, sortie);
    }

    /**
     * cas avec 3 intersections non connectees
     */
    @Test
    void shortestPathListCasBaseNonCo() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
        Intersection end2 = new Intersection(2l,0,0);

        plan.addIntersection(start);
        plan.addIntersection(end);

        ArrayList<Trajet> sortie = new ArrayList<>();
        ArrayList<Trajet> sortieExpected = new ArrayList<>();
        Livraison startLivraison = new Livraison(start,1);
        Livraison l1 = new Livraison(end,1);
        Livraison l2 = new Livraison(end2,1);
        HashSet<Livraison> ends= new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        //When
        //TODO test avec une methode private
        sortie=planSimplifie.shortestPathList(startLivraison, ends);
        //Then
        assertIterableEquals(sortieExpected, sortie);
    }

    /**
     * cas vide
     */
    @Test
    void shortestPathListCasVide() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
        Intersection end2 = new Intersection(2l,0,0);

        plan.addIntersection(start);
        plan.addIntersection(end);
        Troncon t = new Troncon(start,end,"t",10);
        plan.addTroncon(t);
        Troncon t2 = new Troncon(start,end2,"t2",20);
        plan.addTroncon(t2);
        ArrayList<Trajet> sortie = new ArrayList<>();
        ArrayList<Trajet> sortieExpected = new ArrayList<>();
        Livraison startLivraison = new Livraison(start,1);
        Livraison l1 = new Livraison(end,1);
        Livraison l2 = new Livraison(end2,1);
        HashSet<Livraison> ends= new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        //When
        //TODO test avec une methode private
        sortie=planSimplifie.shortestPathList(startLivraison, ends);
        //Then
        assertIterableEquals(sortieExpected, sortie);
    }

    /**
     * cas avec plan xml
     */
    @Test
    void shortestPathListCasComplexe() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection(0l,0,0);
        Intersection end = new Intersection(1l,0,0);
        Intersection end2 = new Intersection(2l,0,0);

        plan.addIntersection(start);
        plan.addIntersection(end);
        Troncon t = new Troncon(start,end,"t",10);
        plan.addTroncon(t);
        Troncon t2 = new Troncon(start,end2,"t2",20);
        plan.addTroncon(t2);
        ArrayList<Trajet> sortie = new ArrayList<>();
        ArrayList<Trajet> sortieExpected = new ArrayList<>();
        Livraison startLivraison = new Livraison(start,1);
        Livraison l1 = new Livraison(end,1);
        Livraison l2 = new Livraison(end2,1);
        HashSet<Livraison> ends= new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        //When
        //TODO test avec une methode private
        sortie=planSimplifie.shortestPathList(startLivraison, ends);
        //Then
        assertIterableEquals(sortieExpected, sortie);
    }
}

