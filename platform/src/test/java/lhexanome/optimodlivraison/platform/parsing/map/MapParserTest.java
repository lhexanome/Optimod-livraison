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
        Intersection intersection = new Intersection();
        intersection.setId(1L);
        Intersection intersection2 = new Intersection();
        intersection.setId(2L);

        map.put(1L, intersection);
        map.put(2L, intersection2);

        // When

        Plan plan = mapParser.loadTroncon(rootElement.getChildren("troncon"), map);

        // Then

        assertEquals(1, plan.getIntersectionCount());
        assertEquals(1, plan.getTronconsFromIntersection(intersection2).size());

    }

    @Test
    void shouldThrowIfTronconHasAUnknownIntersection() {

    }

    @Test
    void shouldWorkIfDestinationIsOrigin() {

    }

    @Test
    void shouldWorkWhenIntersectionHasMoreThanOneTroncon() {

    }

    @Test
    void shouldCreatePlanCorrectly() {

    }
}
