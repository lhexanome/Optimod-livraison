package lhexanome.optimodlivraison.platform.parsing.map;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Troncon;
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
     * Parse un document XML représentant un plan.
     * Utilise le format suivant :
     * {@code
     * <xml>
     * <noeud id="1" x="0" y="0"/>
     * <noeud id="2" x="1" y="0"/>
     * <troncon destination="1" origine="2" nomRue="Rue de la paix" longueur="10.0"/>
     * </xml>
     * }
     *
     * @param rootElement Element racine
     * @return Un plan
     * @throws ParseMapException Si un problème a lieu lors du parsing
     */
    public Plan parseMap(Element rootElement) throws ParseMapException {
        Plan plan = new Plan();

        LOGGER.info("Start parsing");

        if (!"reseau".equals(rootElement.getName())) {
            throw new ParseMapException("XML root element name must be `reseau`");
        }
        List<Element> nodes = rootElement.getChildren("noeud");
        List<Element> troncons = rootElement.getChildren("troncon");

        if (nodes.size() + troncons.size() != rootElement.getChildren().size()) {
            throw new ParseMapException("XML contains unknown elements");
        }

        loadNodes(nodes, plan);

        LOGGER.info("Nodes loaded");

        loadTroncon(troncons, plan);

        LOGGER.info("End parsing");
        return plan;
    }

    /**
     * Génère une map pour faciliter la création d'un plan.
     *
     * @param listNode Liste d'élément jdom
     * @param plan     Plan
     * @throws ParseMapException Si le document contient 2x le même id
     */
    public void loadNodes(List<Element> listNode, Plan plan) throws ParseMapException {
        for (Element node : listNode) {
            Long id = Long.parseLong(node.getAttributeValue("id"));

            if (plan.findIntersectionById(id) != null) {
                throw new ParseMapException("Node id already exists!");
            }

            Intersection intersection = new Intersection(
                    id,
                    Integer.parseInt(node.getAttributeValue("x")),
                    Integer.parseInt(node.getAttributeValue("y"))
            );

            plan.addIntersection(intersection);
        }
    }

    /**
     * Génère un plan depuis des tronçons et des intersections.
     *
     * @param listTroncon Liste d'éléments jdom
     * @param plan        Plan
     * @throws ParseMapException Si un troncon a des intersections inconnues
     */
    public void loadTroncon(List<Element> listTroncon, Plan plan) throws ParseMapException {
        for (Element node : listTroncon) {
            Intersection origin = plan.findIntersectionById(
                    Long.parseLong(node.getAttributeValue("origine")));

            Intersection destination = plan.findIntersectionById(
                    Long.parseLong(node.getAttributeValue("destination")));


            if (origin == null || destination == null) {
                throw new ParseMapException("Troncon has an unknown destination or origin");
            }

            Float length = Float.parseFloat(node.getAttributeValue("longueur"));
            String streetName = node.getAttributeValue("nomRue");

            Troncon similar = plan.getTronconsFromIntersection(origin)
                    .stream()
                    .filter(troncon -> troncon.getDestination() == destination)
                    .findAny()
                    .orElse(null);


            if (similar == null) {
                plan.addTroncon(new Troncon(
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
