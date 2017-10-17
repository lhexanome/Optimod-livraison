package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Livraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Trajet;
import lhexanome.optimodlivraison.platform.models.Troncon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        predecesseurs.add(null);
        predecesseursExpected.add(null);
        predecesseursExpected.add(start);
        //When
        //TODO test avec une methode private
        //planSimplifie.dijkstra(intersection, predecesseurs)
        //planSimplifie
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
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
    void shouldDijkstraCasVide() {
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
    void shouldDijkstraCasComplexe() {
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

        //When
        //TODO test avec une methode private
        //planSimplifie.initIntersectionList(intersection, predecesseurs)
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(intersections, intersectionsExpected);

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

        //When
        //TODO test avec une methode private
        //planSimplifie.initIntersectionList(intersection, predecesseurs)
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(intersections, intersectionsExpected);

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
        //TODO remplir les intersections
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs
        //When
        //TODO test avec une methode private
        //planSimplifie.initIntersectionList(intersection, predecesseurs)
        //Then
        assertEquals(predecesseurs, predecesseursExpected);
        assertEquals(intersections, intersectionsExpected);

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
        //When
        //TODO test avec une methode private
        //planSimplifie.shortestPathList(intersection, predecesseurs)
        //planSimplifie
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

