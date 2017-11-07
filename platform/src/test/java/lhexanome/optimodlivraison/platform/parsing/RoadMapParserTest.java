package lhexanome.optimodlivraison.platform.parsing;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import org.jdom2.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class RoadMapParserTest {

    private Element rootElement;
    private RoadMapParser roadMapParser;
    private RoadMap roadMap;

    @BeforeEach
    void setup() {
        roadMapParser = new RoadMapParser();

        roadMap = new RoadMap();

        rootElement = new Element(RoadMapParser.XML_ROOT_ELEMENT);

        rootElement.addContent(createInter(1L, 1, 1));
        rootElement.addContent(createInter(2L, 1, 2));

        rootElement.addContent(createTroncon(1L, 2L, (float) 5, "Rue de la paix"));
    }

    private Element createInter(Long id, Integer x, Integer y) {
        Element element = new Element(RoadMapParser.XML_INTERSECTION_ELEMENT);

        element.setAttribute(RoadMapParser.XML_ID_ATTRIBUTE, id.toString());
        element.setAttribute(RoadMapParser.XML_X_ATTRIBUTE, x.toString());
        element.setAttribute(RoadMapParser.XML_Y_ATTRIBUTE, y.toString());

        return element;
    }

    private Element createTroncon(Long destination, Long origin, Float length, String streetName) {
        Element element = new Element("troncon");

        element.setAttribute(RoadMapParser.XML_DESTINATION_ATTRIBUTE, destination.toString());
        element.setAttribute(RoadMapParser.XML_ORIGIN_ATTRIBUTE, origin.toString());
        element.setAttribute(RoadMapParser.XML_LENGTH_ATTRIBUTE, length.toString());
        element.setAttribute(RoadMapParser.XML_STREET_NAME_ATTRIBUTE, streetName);

        return element;
    }


    @Test
    void shouldCreateHashMapOfNodes() throws ParseMapException {
        // Given
        RoadMap roadMap = new RoadMap();
        // When

        roadMapParser.loadNodes(rootElement.getChildren(RoadMapParser.XML_INTERSECTION_ELEMENT), roadMap);

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

        roadMapParser.loadNodes(new ArrayList<>(), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(0);
    }

    @Test
    void shouldThrowIfNodeIDAlreadyExists() {
        // With
        rootElement.addContent(createInter(1L, 1, 3));

        // When
        // Then

        assertThatThrownBy(() -> roadMapParser.loadNodes(rootElement.getChildren(RoadMapParser.XML_INTERSECTION_ELEMENT), roadMap))
                .isInstanceOf(ParseMapException.class)
                .hasMessage("Node id already exists!");
    }

    // TODO Add test for all attributes
    // TODO Add test for xml structure

    @Test
    void shouldCreateRoadMapCorrectlyFromListAndHashMap() throws ParseMapException {
        // Given

        Intersection intersection = new Intersection(1L, 654, 321);
        Intersection intersection2 = new Intersection(2L, 852, 741);

        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection2);

        // When

        roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getVectorsFromIntersection(intersection2))
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

        roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getVectorsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(1)
                .extractingResultOf("getStreetName")
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

        roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getVectorsFromIntersection(intersection2))
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

        roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getVectorsFromIntersection(intersection2))
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

        rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT).get(0)
                .setAttribute(RoadMapParser.XML_DESTINATION_ATTRIBUTE, "3");

        // When
        // Then
        assertThatThrownBy(() -> roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap))
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

        rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT).get(0).setAttribute(RoadMapParser.XML_ORIGIN_ATTRIBUTE, "3");

        // When
        // Then
        assertThatThrownBy(() -> roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap))
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

        rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT).get(0).setAttribute(RoadMapParser.XML_ORIGIN_ATTRIBUTE, "1");

        // When

        roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);
        assertThat(roadMap.getVectorsFromIntersection(intersection))
                .isNotNull()
                .hasSize(1);

        Vector vector = roadMap.getVectorsFromIntersection(intersection)
                .stream()
                .findFirst()
                .orElse(null);

        assertThat(vector).isNotNull();
        assertThat(vector.getDestination()).isEqualTo(vector.getOrigin());
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

        roadMapParser.loadTroncon(rootElement.getChildren(RoadMapParser.XML_VECTOR_ELEMENT), roadMap);

        // Then

        assertThat(roadMap.getIntersectionCount()).isEqualTo(3);
        assertThat(roadMap.getVectorsFromIntersection(intersection2))
                .isNotNull()
                .hasSize(2)
                .extractingResultOf("getOrigin")
                .containsOnly(intersection2);
    }

    @Test
    void shouldCreateRoadMapCorrectly() throws ParseMapException {

        // With

        // When

        RoadMap roadMap = roadMapParser.parseMap(rootElement);

        // Then

        assertThat(roadMap).isNotNull();
        assertThat(roadMap.getIntersectionCount()).isEqualTo(2);

    }
}
