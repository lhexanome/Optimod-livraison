package lhexanome.optimodlivraison.platform.parsing.map;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.parsing.MapParser;
import org.jdom2.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class RoadMapParserTest {

    private Element rootElement;
    private MapParser mapParser;
    private RoadMap roadMap;

    @BeforeEach
    void setup() {
        mapParser = new MapParser();

        roadMap = new RoadMap();

        rootElement = new Element("reseau");

        rootElement.addContent(createInter(1L, 1, 1));
        rootElement.addContent(createInter(2L, 1, 2));

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Rue de la paix"));
    }

    private Element createInter(Long id, Integer x, Integer y) {
        Element element = new Element(MapParser.XML_NODE_ELEMENT);

        element.setAttribute("id", id.toString());
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString());

        return element;
    }

    private Element createTroncon(Long destination, Long origin, Float length, String streetName) {
        Element element = new Element("troncon");

        element.setAttribute("destination", destination.toString());
        element.setAttribute("origine", origin.toString());
        element.setAttribute("longueur", length.toString());
        element.setAttribute("nomRue", streetName);

        return element;
    }


    @Test
    void shouldCreateHashMapOfNodes() throws ParseMapException {
        // Given
        RoadMap roadMap = new RoadMap();
        // When

        mapParser.loadNodes(rootElement.getChildren(MapParser.XML_NODE_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);

        Intersection intersection = roadMap.findIntersectionById(1L);

        assertThat(intersection).isNotNull();
        assertThat(intersection.getX()).isEqualTo(1);
        assertThat(intersection.getId()).isEqualTo(1);
    }

    @Test
    void shouldWorkWithEmptyList() throws ParseMapException {
        // Given
        // When

        mapParser.loadNodes(new ArrayList<>(), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(0);
    }

    @Test
    void shouldThrowIfNodeIDAlreadyExists() {
        // With
        rootElement.addContent(createInter(1L, 1, 3));

        // When
        // Then

        assertThatThrownBy(() -> mapParser.loadNodes(rootElement.getChildren(MapParser.XML_NODE_ELEMENT), roadMap))
                .isInstanceOf(ParseMapException.class)
                .hasMessage("Node id already exists!");
    }

    // TODO Add test for all attributes
    // TODO Add test for xml structure

    @Test
    void shouldCreatePlanCorrectlyFromListAndHashMap() throws ParseMapException {
        // Given

        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        // When

        mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getTronconsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(1);
    }

    @Test
    void shouldNotHaveTwoTronconsWithTheSameIntersections() throws ParseMapException {
        // Given

        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Boulevard de la villette"));

        // When

        mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getTronconsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(1)
                .extractingResultOf("getNameStreet")
                .doesNotContain("Boulevard de la villette");
    }

    @Test
    void shouldUpdateTronconWithSameIntersections() throws ParseMapException {
        // With

        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        rootElement.addContent(createTroncon(1L, 2L, (float) 12, "Boulevard de la villette"));

        // When

        mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getTronconsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(1)
                .extractingResultOf("getLength")
                .containsOnly(5.0f);
    }

    @Test
    void shouldNotAddTheSameTronconTwice() throws ParseMapException {
        // Given

        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Rue de la paix"));

        // When

        mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getTronconsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(1);
    }


    @Test
    void shouldThrowIfTronconHasAUnknownDestination() {
        // Given
        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        rootElement.getChildren("troncon").get(0).setAttribute("destination", "3");

        // When
        // Then
        assertThatThrownBy(() -> mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap))
                .isInstanceOf(ParseMapException.class)
                .hasMessage("Vector has an unknown destination or origin");
    }

    @Test
    void shouldThrowIfTronconHasAUnknownOrigin() {
        // Given
        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        rootElement.getChildren("troncon").get(0).setAttribute("origine", "3");

        // When
        // Then
        assertThatThrownBy(() -> mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap))
                .isInstanceOf(ParseMapException.class)
                .hasMessage("Vector has an unknown destination or origin");
    }

    @Test
    void shouldWorkIfDestinationIsOrigin() throws ParseMapException {
        // Given
        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        rootElement.getChildren("vector").get(0).setAttribute("origine", "1");

        // When

        mapParser.loadTroncon(rootElement.getChildren("vector"), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getTronconsFromIntersection(intersection))
                .isNotNull()
                .hasSize(1);

        Vector vector = roadMap.getTronconsFromIntersection(intersection)
                .stream()
                .findFirst()
                .orElse(null);

        assertThat(vector).isNotNull();
        assertThat(vector.getDestination()).isEqualTo(vector.getOrigine());
    }

    @Test
    void shouldWorkWhenIntersectionHasMoreThanOneTroncon() throws ParseMapException {
        // Given
        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);
        Intersection intersection3 = new Intersection(3L, 843, 762);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);
        roadMap.addIntersection(intersection3);

        rootElement.addContent(createTroncon(3L, 2L, (float) 12, "Boulevard de la villette"));

        // When

        mapParser.loadTroncon(rootElement.getChildren("troncon"), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(3);
        assertThat(roadMap.getTronconsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(2)
                .extractingResultOf("getOrigine")
                .containsOnly(intersection2);
    }

    @Test
    void shouldCreatePlanCorrectly() throws ParseMapException {

        // With

        // When

        RoadMap roadMap = mapParser.parseMap(rootElement);

        // Then

        assertThat(roadMap).isNotNull();
        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);

    }
}
