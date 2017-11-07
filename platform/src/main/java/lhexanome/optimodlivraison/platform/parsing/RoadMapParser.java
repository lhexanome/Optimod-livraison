package lhexanome.optimodlivraison.platform.parsing;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import org.jdom2.Element;

import java.util.List;
import java.util.logging.Logger;

/**
 * Parse a XML document representing a Road Map.
 */
public class RoadMapParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RoadMapParser.class.getName());

    /**
     * XML Tag name for the root elment.
     */
    public static final String XML_ROOT_ELEMENT = "reseau";

    /**
     * XML Tag name for an intersection.
     */
    public static final String XML_INTERSECTION_ELEMENT = "noeud";

    /**
     * XML Tag name for a vector.
     */
    public static final String XML_VECTOR_ELEMENT = "troncon";

    /**
     * XML Attribute name for id.
     */
    public static final String XML_ID_ATTRIBUTE = "id";

    /**
     * XML Attribute name for x.
     */
    public static final String XML_X_ATTRIBUTE = "x";

    /**
     * XML Attribute name for y.
     */
    public static final String XML_Y_ATTRIBUTE = "y";

    /**
     * XML Attribute name for destination.
     */
    public static final String XML_DESTINATION_ATTRIBUTE = "destination";

    /**
     * XML Attribute name for origin.
     */
    public static final String XML_ORIGIN_ATTRIBUTE = "origine";

    /**
     * XML Attribute name for street name.
     */
    public static final String XML_STREET_NAME_ATTRIBUTE = "nomRue";

    /**
     * XML Attribute name for length.
     */
    public static final String XML_LENGTH_ATTRIBUTE = "longueur";

    /**
     * Parse a XML document representing a road map.
     * Use the following format:
     * {@code
     * <reseau>
     * <noeud id="1" x="0" y="0"/>
     * <noeud id="2" x="1" y="0"/>
     * <troncon destination="1" origine="2" nomRue="Rue de la paix" longueur="10.0"/>
     * </reseau>
     * }
     *
     * @param rootElement Root element
     * @return Parsed road map
     * @throws ParseMapException If the XML document is malformed
     */
    public RoadMap parseMap(Element rootElement) throws ParseMapException {
        RoadMap roadMap = new RoadMap();

        LOGGER.info("Start parsing");

        if (!XML_ROOT_ELEMENT.equals(rootElement.getName())) {
            throw new ParseMapException(String.format("XML root element name must be `%s`", XML_ROOT_ELEMENT));
        }
        List<Element> nodes = rootElement.getChildren(XML_INTERSECTION_ELEMENT);
        List<Element> troncons = rootElement.getChildren(XML_VECTOR_ELEMENT);

        if (nodes.size() + troncons.size() != rootElement.getChildren().size()) {
            throw new ParseMapException("XML contains unknown elements");
        }

        loadNodes(nodes, roadMap);

        LOGGER.info("Nodes loaded");

        loadTroncon(troncons, roadMap);

        LOGGER.info("End parsing");
        return roadMap;
    }

    /**
     * Load nodes from the XML.
     *
     * @param nodeList XML List of nodes
     * @param roadMap  RoadMap to complete
     * @throws ParseMapException If it found the same id twice
     */
    public void loadNodes(List<Element> nodeList, RoadMap roadMap) throws ParseMapException {
        for (Element node : nodeList) {
            Long id = Long.parseLong(node.getAttributeValue(XML_ID_ATTRIBUTE));

            if (roadMap.findIntersectionById(id) != null) {
                throw new ParseMapException("Node id already exists!");
            }

            Intersection intersection = new Intersection(
                    id,
                    Integer.parseInt(node.getAttributeValue(XML_X_ATTRIBUTE)),
                    Integer.parseInt(node.getAttributeValue(XML_Y_ATTRIBUTE))
            );

            roadMap.addIntersection(intersection);
        }
    }

    /**
     * Load vector from XML.
     *
     * @param vectorList Vector list, with tag {@see XML_VECTOR_ELEMENT}
     * @param roadMap     RoadMap to complete
     * @throws ParseMapException If a vector has an unknown intersection
     */
    public void loadTroncon(List<Element> vectorList, RoadMap roadMap) throws ParseMapException {
        for (Element node : vectorList) {
            Intersection origin = roadMap.findIntersectionById(
                    Long.parseLong(node.getAttributeValue(XML_ORIGIN_ATTRIBUTE)));

            Intersection destination = roadMap.findIntersectionById(
                    Long.parseLong(node.getAttributeValue(XML_DESTINATION_ATTRIBUTE)));


            if (origin == null || destination == null) {
                throw new ParseMapException("Vector has an unknown destination or origin");
            }

            Float length = Float.parseFloat(node.getAttributeValue(XML_LENGTH_ATTRIBUTE));
            String streetName = node.getAttributeValue(XML_STREET_NAME_ATTRIBUTE);

            Vector similar = roadMap.getVectorsFromIntersection(origin)
                    .stream()
                    .filter(troncon -> troncon.getDestination() == destination)
                    .findAny()
                    .orElse(null);


            if (similar == null) {
                roadMap.addVector(new Vector(
                        origin,
                        destination,
                        streetName,
                        length
                ));
            } else if (similar.getLength() > length) {
                similar.setLength(length);
                similar.setStreetName(streetName);
            }
        }
    }
}
