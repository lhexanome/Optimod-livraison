package lhexanome.optimodlivraison.platform.parsing.map;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import org.jdom2.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MapParserTest {

    private Element rootElement;
    private MapParser mapParser;

    @BeforeEach
    void setup() {
        mapParser = new MapParser();

        rootElement = new Element("root");

        rootElement.addContent(createInter(1L, 1, 1));
        rootElement.addContent(createInter(2L, 1, 2));

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Rue de la paix"));
    }

    Element createInter(Long id, Integer x, Integer y) {
        Element element = new Element("noeud");

        element.setAttribute("id", id.toString());
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString());

        return element;
    }

    Element createTroncon(Long destination, Long origin, Float length, String streetName) {
        Element element = new Element("troncon");

        element.setAttribute("destination", destination.toString());
        element.setAttribute("origine", origin.toString());
        element.setAttribute("longueur", length.toString());
        element.setAttribute("nomRue", streetName);

        return element;
    }


    @Test
    void shouldCreateHashMapOfNodes() throws ParseMapException {
        // With
        // When

        Map<Long, Intersection> noeud = mapParser.loadNodes(rootElement.getChildren("noeud"));

        // Then

        assertEquals(noeud.size(), 2);

        Intersection intersection = noeud.get(1L);
        assertNotEquals(intersection, null);
        assertEquals(intersection.getX(), 1);
        assertEquals(intersection.getId(), 1);
    }

    @Test
    void shouldWorkWithEmptyList() throws ParseMapException {
        // With
        // When

        Map<Long, Intersection> noeud = mapParser.loadNodes(new ArrayList<>());

        // Then

        assertEquals(noeud.size(), 0);
    }

    @Test
    void shouldThrowIfNodeIDAlreadyExists() {
        // With
        rootElement.addContent(createInter(1L, 1, 3));

        // When
        // Then

        Throwable exception = assertThrows(ParseMapException.class,
                () -> mapParser.loadNodes(rootElement.getChildren("noeud"))
        );

        assertEquals("Node id already exists!", exception.getMessage());
    }

    // TODO Add test for all attributes

    @Test
    void shouldCreatePlanCorrectlyFromListAndHashMap() throws ParseMapException {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(1, plan.getTronconsFromIntersection(intersection2).size());

    }

    @Test
    void shouldNotHaveTwoTronconsWithTheSameIntersections() throws ParseMapException {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Boulevard de la villette"));

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(1, plan.getTronconsFromIntersection(intersection2).size());

        plan.getTronconsFromIntersection(intersection2)
                .forEach(troncon -> assertNotEquals(troncon.getNameStreet(), "Boulevard de la villette"));
    }

    @Test
    void shouldUpdateTronconWithSameIntersections() throws ParseMapException {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        rootElement.addContent(createTroncon(1L, 2L, (float) 12, "Boulevard de la villette"));

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(1, plan.getTronconsFromIntersection(intersection2).size());

        plan.getTronconsFromIntersection(intersection2)
                .forEach(troncon -> assertEquals(troncon.getLength(), 5));
    }

    @Test
    void shouldNotAddTheSameTronconTwice() throws ParseMapException {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Rue de la paix"));

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(1, plan.getTronconsFromIntersection(intersection2).size());

    }


    @Test
    void shouldThrowIfTronconHasAUnknownDestination() {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        rootElement.getChildren("troncon").get(0).setAttribute("destination", "3");

        // When
        // Then

        Throwable throwable = assertThrows(ParseMapException.class,
                () -> mapParser.loadTroncon(rootElement.getChildren("troncon"), map)
        );

        assertEquals("Troncon has an unknown destination or origin", throwable.getMessage());
    }

    @Test
    void shouldThrowIfTronconHasAUnknownOrigin() {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        rootElement.getChildren("troncon").get(0).setAttribute("origine", "3");

        // When
        // Then

        Throwable throwable = assertThrows(ParseMapException.class,
                () -> mapParser.loadTroncon(rootElement.getChildren("troncon"), map)
        );

        assertEquals("Troncon has an unknown destination or origin", throwable.getMessage());
    }

    @Test
    void shouldWorkIfDestinationIsOrigin() throws ParseMapException {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        rootElement.getChildren("troncon").get(0).setAttribute("origine", "1");

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(1, plan.getTronconsFromIntersection(intersection).size());

        plan.getTronconsFromIntersection(intersection)
                .forEach(troncon -> assertEquals(troncon.getDestination(), troncon.getOrigine()));
    }

    @Test
    void shouldWorkWhenIntersectionHasMoreThanOneTroncon() throws ParseMapException {
        // With

        Map<Long, Intersection> map = new HashMap<>(2);
        Intersection intersection = new Intersection(1L,654,321);
        Intersection intersection2 = new Intersection(2L,852,741);
        Intersection intersection3 = new Intersection(3L,843,762);

        map.put(1L, intersection);
        map.put(2L, intersection2);
        map.put(3L, intersection3);

        rootElement.addContent(createTroncon(3L, 2L, (float) 12, "Boulevard de la villette"));

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(2, plan.getTronconsFromIntersection(intersection2).size());

        plan.getTronconsFromIntersection(intersection2)
                .forEach(troncon -> assertEquals(troncon.getOrigine(), intersection2));
    }

    @Test
    void shouldCreatePlanCorrectly() throws ParseMapException {

        // With
        // When

        Plan plan = mapParser.parseMap(rootElement);

        // Then

        assertEquals(1, plan.getIntersectionCount());

    }
}
