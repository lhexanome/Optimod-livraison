package lhexanome.optimodlivraison.platform.parsing;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import org.jdom2.Element;

import java.util.List;
import java.util.logging.Logger;

/**
 * Parser d'un document XML représentant un plan.
 */
public class MapParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MapParser.class.getName());

    /**
     * XML Tag name for a node.
     */
    public static final String XML_NODE_ELEMENT = "noeud";

    /**
     * Parse un document XML représentant un plan.
     * Utilise le format suivant :
     * {@code
     * <reseau>
     * <noeud id="1" x="0" y="0"/>
     * <noeud id="2" x="1" y="0"/>
     * <troncon destination="1" origine="2" nomRue="Rue de la paix" longueur="10.0"/>
     * </reseau>
     * }
     *
     * @param rootElement Element racine
     * @return Un plan
     * @throws ParseMapException Si un problème a lieu lors du parsing
     */
    public RoadMap parseMap(Element rootElement) throws ParseMapException {
        RoadMap roadMap = new RoadMap();

        LOGGER.info("Start parsing");

        if (!"reseau".equals(rootElement.getName())) {
            throw new ParseMapException("XML root element name must be `reseau`");
        }
        List<Element> nodes = rootElement.getChildren(XML_NODE_ELEMENT);
        List<Element> troncons = rootElement.getChildren("troncon");

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
     * Génère une roadMap pour faciliter la création d'un roadMap.
     *
     * @param listNode Liste d'élément jdom
     * @param roadMap     RoadMap
     * @throws ParseMapException Si le document contient 2x le même id
     */
    public void loadNodes(List<Element> listNode, RoadMap roadMap) throws ParseMapException {
        for (Element node : listNode) {
            Long id = Long.parseLong(node.getAttributeValue("id"));

            if (roadMap.findIntersectionById(id) != null) {
                throw new ParseMapException("Node id already exists!");
            }

            Intersection intersection = new Intersection(
                    id,
                    Integer.parseInt(node.getAttributeValue("x")),
                    Integer.parseInt(node.getAttributeValue("y"))
            );

            roadMap.addIntersection(intersection);
        }
    }

    /**
     * Génère un roadMap depuis des tronçons et des intersections.
     *
     * @param listTroncon Liste d'éléments jdom
     * @param roadMap        RoadMap
     * @throws ParseMapException Si un troncon a des intersections inconnues
     */
    public void loadTroncon(List<Element> listTroncon, RoadMap roadMap) throws ParseMapException {
        for (Element node : listTroncon) {
            Intersection origin = roadMap.findIntersectionById(
                    Long.parseLong(node.getAttributeValue("origine")));

            Intersection destination = roadMap.findIntersectionById(
                    Long.parseLong(node.getAttributeValue("destination")));


            if (origin == null || destination == null) {
                throw new ParseMapException("Vector has an unknown destination or origin");
            }

            Float length = Float.parseFloat(node.getAttributeValue("longueur"));
            String streetName = node.getAttributeValue("nomRue");

            Vector similar = roadMap.getTronconsFromIntersection(origin)
                    .stream()
                    .filter(troncon -> troncon.getDestination() == destination)
                    .findAny()
                    .orElse(null);


            if (similar == null) {
                roadMap.addTroncon(new Vector(
                        origin,
                        destination,
                        streetName,
                        length
                ));
            } else if (similar.getLength() > length) {
                similar.setLength(length);
                similar.setNameStreet(streetName);
            }
        }
    }
}
