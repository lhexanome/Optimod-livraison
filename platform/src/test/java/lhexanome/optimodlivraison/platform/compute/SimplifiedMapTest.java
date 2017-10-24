package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class SimplifiedMapTest {


    //dijkstra

    /**
     * cas avec deux intersections connectees par un trajet
     */
    @Test
    void shouldDijkstraCasBase() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        Intersection start = new Intersection(0l, 0, 0);
        Intersection end = new Intersection(1l, 0, 0);
        ArrayList<Intersection> intersections = new ArrayList<>();

        roadMap.addIntersection(start);
        roadMap.addIntersection(end);
        intersections.add(start);
        intersections.add(end);
        Vector t = new Vector(start, end, "t", 10);

        roadMap.addTroncon(t);
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Vector> chemins = new ArrayList<>();
        ArrayList<Vector> cheminsExpected = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();

        predecesseurs.add(null);
        predecesseurs.add(null);
        chemins.add(null);
        chemins.add(null);
        cheminsExpected.add(null);
        cheminsExpected.add(t);
        predecesseursExpected.add(null);
        predecesseursExpected.add(start);
        float[] tempsDijkstra = new float[roadMap.getIntersectionCount()];
        boolean[] etatDijkstra = new boolean[roadMap.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        simplifiedMap.dijkstra(intersections, predecesseurs, chemins, tempsDijkstra, etatDijkstra);
        //Then
        assertThat(predecesseurs).hasSameElementsAs(predecesseursExpected);


        assertThat(chemins).hasSameElementsAs(cheminsExpected);
    }

    /**
     * cas avec deux intersections non connectees
     */
    @Test
    void shouldDijkstraCasBaseNonCo() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        Intersection start = new Intersection(0l, 0, 0);
        Intersection end = new Intersection(1l, 0, 0);
        ArrayList<Intersection> intersections = new ArrayList<>();
        roadMap.addIntersection(start);
        roadMap.addIntersection(end);
        intersections.add(start);
        intersections.add(end);

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        ArrayList<Vector> chemins = new ArrayList<>();
        ArrayList<Vector> cheminsExpected = new ArrayList<>();
        predecesseurs.add(null);
        predecesseurs.add(null);

        predecesseursExpected.add(null);
        predecesseursExpected.add(null);
        chemins.add(null);
        chemins.add(null);

        cheminsExpected.add(null);
        cheminsExpected.add(null);

        float[] tempsDijkstra = new float[roadMap.getIntersectionCount()];
        boolean[] etatDijkstra = new boolean[roadMap.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        simplifiedMap.dijkstra(intersections, predecesseurs, chemins, tempsDijkstra, etatDijkstra);
        //Then
        assertThat(predecesseurs).hasSameElementsAs(predecesseursExpected);
        assertThat(chemins).hasSameElementsAs(cheminsExpected);
    }


    /**
     * cas avec plan xml
     */
    @Test
    void shouldDijkstraCasComplexe() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        //TODO charger un roadMap xml
        //TODO charger demande xml
        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs attendus
        ArrayList<Vector> chemins = new ArrayList<>();
        ArrayList<Vector> cheminsExpected = new ArrayList<>();
        //TODO remplir les chemins attendus
        //simplifiedMap.initIntersectionList(demande.getBeginning().getIntersection(), intersections, predecesseurs, chemins);
        float[] tempsDijkstra = new float[roadMap.getIntersectionCount()];
        boolean[] etatDijkstra = new boolean[roadMap.getIntersectionCount()];
        //When
        //TODO test avec une methode private
        //simplifiedMap.dijkstra(intersections, predecesseurs, chemins, tempsDijkstra, etatDijkstra);
        //Then
        assertThat(predecesseurs).hasSameElementsAs(predecesseursExpected);
        assertThat(chemins).hasSameElementsAs(cheminsExpected);
    }


    //-------------------------------------------------------
    //initIntersectionList

    /**
     * cas vide
     */
    @Test
    void shouldInitIntersectionListCasVide() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);

        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> intersectionsExpected = new ArrayList<>();

        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();

        ArrayList<Vector> chemins = new ArrayList<>();
        ArrayList<Vector> cheminsExpected = new ArrayList<>();


        //When
        //TODO test avec une methode private
        simplifiedMap.initIntersectionList(null, intersections, predecesseurs, chemins);
        //Then
        assertThat(predecesseurs).hasSameElementsAs(predecesseursExpected);
        assertThat(intersections).hasSameElementsAs(intersectionsExpected);
        assertThat(chemins).hasSameElementsAs(cheminsExpected);


    }

    /**
     * cas avec deux intersections connectees par un trajet
     */
    @Test
    void shouldInitIntersectionListCasBase() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        Intersection start = new Intersection(0l, 0, 0);
        Intersection end = new Intersection(1l, 0, 0);
        roadMap.addIntersection(start);
        roadMap.addIntersection(end);
        Vector t = new Vector(start, end, "t", 10);
        roadMap.addTroncon(t);
        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> intersectionsExpected = new ArrayList<>();
        intersectionsExpected.add(start);
        intersectionsExpected.add(end);
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        predecesseursExpected.add(null);
        predecesseursExpected.add(null);
        ArrayList<Vector> chemins = new ArrayList<>();
        ArrayList<Vector> cheminsExpected = new ArrayList<>();
        cheminsExpected.add(null);
        cheminsExpected.add(null);

        //When
        //TODO test avec une methode private
        simplifiedMap.initIntersectionList(start, intersections, predecesseurs, chemins);
        //Then
        assertThat(predecesseurs).hasSameElementsAs(predecesseursExpected);
        assertThat(intersections).hasSameElementsAs(intersectionsExpected);
        assertThat(chemins).hasSameElementsAs(cheminsExpected);

    }


    /**
     * cas avec plan xml
     */
    @Test
    void shouldInitIntersectionListCasComplexe() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        //TODO charger un roadMap xml

        ArrayList<Intersection> intersections = new ArrayList<>();
        ArrayList<Intersection> intersectionsExpected = new ArrayList<>();
        //TODO remplir les intersections attendus
        ArrayList<Intersection> predecesseurs = new ArrayList<>();
        ArrayList<Intersection> predecesseursExpected = new ArrayList<>();
        //TODO remplir les predecesseurs attendus
        ArrayList<Vector> chemins = new ArrayList<>();
        ArrayList<Vector> cheminsExpected = new ArrayList<>();
        //TODO remplir les chemins attendus
        //When
        //TODO test avec une methode private
        //simplifiedMap.initIntersectionList(demande.getBeginning().getIntersection(), intersections, predecesseurs, chemins);
        //Then
        assertThat(predecesseurs).hasSameElementsAs(predecesseursExpected);
        assertThat(intersections).hasSameElementsAs(intersectionsExpected);
        assertThat(chemins).hasSameElementsAs(cheminsExpected);

    }


    //----------------------------------------------------------------
    //shortestPathList

    /**
     * cas avec 3 livraisons connectees par 2 trajets
     */
    @Test
    void shortestPathListCasBase() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        Intersection start = new Intersection(0l, 0, 0);
        Intersection end = new Intersection(1l, 0, 0);
        Intersection end2 = new Intersection(2l, 0, 0);

        roadMap.addIntersection(start);
        roadMap.addIntersection(end);
        Vector t = new Vector(start, end, "t", 10);
        roadMap.addTroncon(t);
        Vector t2 = new Vector(start, end2, "t2", 20);
        roadMap.addTroncon(t2);
        ArrayList<Path> sortie = new ArrayList<>();
        ArrayList<Path> sortieExpected = new ArrayList<>();
        Delivery startDelivery = new Delivery(start, 1);
        Delivery l1 = new Delivery(end, 1);
        Delivery l2 = new Delivery(end2, 1);
        HashSet<Delivery> ends = new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        Path tr = new Path();
        tr.addTroncon(t);
        sortieExpected.add(tr);
        Path tr2 = new Path();
        tr2.addTroncon(t2);
        sortieExpected.add(tr2);
        //When
        //TODO test avec une methode private
        sortie = simplifiedMap.shortestPathList(startDelivery, ends);
        //Then

        assertThat(sortie).hasSameElementsAs(sortieExpected);
    }

    /**
     * cas avec 3 intersections non connectees
     */
    @Test
    void shortestPathListCasBaseNonCo() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        Intersection start = new Intersection(0l, 0, 0);
        Intersection end = new Intersection(1l, 0, 0);
        Intersection end2 = new Intersection(2l, 0, 0);

        roadMap.addIntersection(start);
        roadMap.addIntersection(end);
        roadMap.addIntersection(end2);
        ArrayList<Path> sortie = new ArrayList<>();
        ArrayList<Path> sortieExpected = new ArrayList<>();
        Delivery startDelivery = new Delivery(start, 1);
        Delivery l1 = new Delivery(end, 1);
        Delivery l2 = new Delivery(end2, 1);
        HashSet<Delivery> ends = new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        //When
        //TODO test avec une methode private
        sortie = simplifiedMap.shortestPathList(startDelivery, ends);
        //Then
        assertThat(sortie).hasSameElementsAs(sortieExpected);
    }

    /**
     * cas vide
     */
    @Test
    void shortestPathListCasVide() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        Intersection start = new Intersection(0l, 0, 0);
        Intersection end = new Intersection(1l, 0, 0);
        Intersection end2 = new Intersection(2l, 0, 0);

        roadMap.addIntersection(start);
        roadMap.addIntersection(end);

        ArrayList<Path> sortie = new ArrayList<>();
        ArrayList<Path> sortieExpected = new ArrayList<>();
        Delivery startDelivery = new Delivery(start, 1);
        Delivery l1 = new Delivery(end, 1);
        Delivery l2 = new Delivery(end2, 1);
        HashSet<Delivery> ends = new HashSet<>();
        ends.add(l2);
        ends.add(l1);
        //When
        //TODO test avec une methode private
        sortie = simplifiedMap.shortestPathList(startDelivery, ends);
        //Then
        assertThat(sortie).hasSameElementsAs(sortieExpected);
    }

    /**
     * cas avec plan xml
     */
    @Test
    void shortestPathListCasComplexe() {
        //With
        RoadMap roadMap = new RoadMap();
        DeliveryOrder demande = new DeliveryOrder();
        SimplifiedMap simplifiedMap = new SimplifiedMap(demande, roadMap);
        //TODO ajouter le chargement XML
        ArrayList<Path> sortie = new ArrayList<>();
        ArrayList<Path> sortieExpected = new ArrayList<>();

        //When
        //TODO test avec une methode private
        // sortie = simplifiedMap.shortestPathList(demande.getBeginning(), demande.getDeliveries());
        //Then
        assertThat(sortie).hasSameElementsAs(sortieExpected);
    }
}

