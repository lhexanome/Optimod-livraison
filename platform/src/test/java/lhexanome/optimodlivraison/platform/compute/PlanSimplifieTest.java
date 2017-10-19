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
        Intersection start = new Intersection();
        Intersection end = new Intersection();
        ArrayList<Intersection> intersections = new ArrayList<>();
        //TODO ajouter les intersections au plan
        intersections.add(start);
        intersections.add(end);
        Troncon t = new Troncon();
        t.setDestination(end);
        t.setOrigine(start);
        t.setLength(10);
        plan.addTroncon(start, t);
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
        float []tempsDijkstra={0,-1};
        boolean []etatDijkstra={true,false};
        //When
        //TODO test avec une methode private
        planSimplifie.dijkstra(intersections, predecesseurs,chemins,tempsDijkstra,etatDijkstra);
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(chemins, cheminsExpected);
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
        Intersection start = new Intersection();
        Intersection end = new Intersection();
        ArrayList<Intersection> intersections = new ArrayList<>();
        //TODO ajouter les intersections au plan
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
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(chemins, cheminsExpected);
    }

    /**
     * cas vide
     */
    @Test
    void shouldDijkstraCasVide() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);

        ArrayList<Intersection> intersections = new ArrayList<>();

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();

        float []tempsDijkstra=new float[plan.getIntersectionCount()];
        boolean []etatDijkstra=new boolean[plan.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        planSimplifie.dijkstra(intersections, predecesseurs,chemins,tempsDijkstra,etatDijkstra);
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(chemins, cheminsExpected);
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
        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs attendus
        ArrayList<Troncon> chemins = new ArrayList<>();
        ArrayList<Troncon> cheminsExpected = new ArrayList<>();
        //TODO remplir les chemins attendus
        planSimplifie.initIntersectionList(intersections,predecesseurs,chemins);
        float []tempsDijkstra=new float[plan.getIntersectionCount()];
        boolean []etatDijkstra=new boolean[plan.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        planSimplifie.dijkstra(intersections, predecesseurs,chemins,tempsDijkstra,etatDijkstra);
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(chemins, cheminsExpected);
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
        planSimplifie.initIntersectionList(intersections, predecesseurs,chemins);
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(intersections, intersectionsExpected);
        assertEquals(chemins, cheminsExpected);


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
        Intersection start = new Intersection();
        Intersection end = new Intersection();
        //TODO ajouter les intersections au plan
        Troncon t = new Troncon();
        t.setDestination(end);
        t.setOrigine(start);
        t.setLength(10);
        plan.addTroncon(start, t);
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
        planSimplifie.initIntersectionList(intersections, predecesseurs,chemins);
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(intersections, intersectionsExpected);
        assertEquals(chemins, cheminsExpected);

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
        planSimplifie.initIntersectionList(intersections, predecesseurs,chemins);
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(intersections, intersectionsExpected);
        assertEquals(chemins, cheminsExpected);

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
        Intersection start = new Intersection();
        Intersection end = new Intersection();
        Intersection end2 = new Intersection();

        //TODO ajouter les intersections au plan
        Troncon t = new Troncon();
        t.setDestination(end);
        t.setOrigine(start);
        t.setLength(10);
        plan.addTroncon(start, t);
        Troncon t2 = new Troncon();
        t2.setDestination(end2);
        t2.setOrigine(start);
        t2.setLength(20);
        plan.addTroncon(start, t2);
        ArrayList<Trajet> sortie = new ArrayList<>();
        ArrayList<Trajet> sortieExpected = new ArrayList<>();
        Livraison startLivraison = new Livraison();
        startLivraison.setIntersection(start);
        Livraison l1 = new Livraison();
        l1.setIntersection(end);
        Livraison l2 = new Livraison();
        l2.setIntersection(end2);
        HashSet<Livraison> ends= new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        //When
        //TODO test avec une methode private
        planSimplifie.shortestPathList(startLivraison, ends);
        //Then
        assertEquals(sortie, sortieExpected);
    }

    /**
     * cas avec deux intersections non connectees
     */
    @Test
    void shortestPathListCasBaseNonCo() {
        //With
        Plan plan = new Plan();
        DemandeLivraison demande = new DemandeLivraison();
        PlanSimplifie planSimplifie = new PlanSimplifie(demande, plan);
        Intersection start = new Intersection();
        Intersection end = new Intersection();
        ArrayList<Intersection> intersections = new ArrayList<>();
        //TODO ajouter les intersections au plan
        intersections.add(start);
        intersections.add(end);

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        predecesseurs.add(null);
        predecesseursExpected.add(null);
        predecesseursExpected.add(null);
        //When
        //TODO test avec une methode private
        //planSimplifie.dijkstra(intersection, predecesseurs)
        //planSimplifie
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
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

        ArrayList<Intersection> intersections = new ArrayList<>();

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();

        //When
        //TODO test avec une methode private
        //planSimplifie.dijkstra(intersection, predecesseurs)
        //planSimplifie
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
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
        //TODO charger un plan xml
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs
        //planSimplifie.initIntersectionList(intersections,predecesseurs);
        //When
        //TODO test avec une methode private
        //planSimplifie.dijkstra(intersection, predecesseurs)
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
    }
}

