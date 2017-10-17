package lhexanome.optimodlivraison.platform.parsing.map;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Troncon;
import org.jdom2.Element;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser d'un document XML représentant un plan.
 */
public class MapParser {

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

        Map<Long, Intersection> map = loadNodes(rootElement.getChildren("noeud"));

        return loadTroncon(rootElement.getChildren("troncon"), map);
    }

    /**
     * Génère une map pour faciliter la création d'un plan.
     *
     * @param listNode Liste d'élément jdom
     * @return Map <id, intersection>
     * @throws ParseMapException Si le document contient 2x le même id
     */
    public Map<Long, Intersection> loadNodes(List<Element> listNode) throws ParseMapException {
        Map<Long, Intersection> nodeMap = new HashMap<>(listNode.size());

        for (Element node : listNode) {
            Long id = Long.parseLong(node.getAttributeValue("id"));

            if (nodeMap.containsKey(id)) {
                throw new ParseMapException("Node id already exists!");
            }

            Intersection intersection = new Intersection();
            intersection.setId(id);
            intersection.setX(Integer.parseInt(node.getAttributeValue("x")));
            intersection.setY(Integer.parseInt(node.getAttributeValue("y")));

            nodeMap.put(id, intersection);
        }

        return nodeMap;
    }

    /**
     * Génère un plan depuis des tronçons et des intersections.
     *
     * @param listTroncon Liste d'éléments jdom
     * @param nodeMap     Map d'intrersection
     * @return Un plan
     * @throws ParseMapException Si un troncon a des intersections inconnues
     */
    public Plan loadTroncon(List<Element> listTroncon, Map<Long, Intersection> nodeMap) throws ParseMapException {
        Plan plan = new Plan();

        for (Element node : listTroncon) {
            Long origin = Long.parseLong(node.getAttributeValue("origine"));
            Long destination = Long.parseLong(node.getAttributeValue("destination"));

            if (!nodeMap.containsKey(origin) || !nodeMap.containsKey(destination)) {
                throw new ParseMapException("Troncon has an unknown destination or origin");
            }

            Float length = Float.parseFloat(node.getAttributeValue("longueur"));
            String streetName = node.getAttributeValue("nomRue");

            Collection<Troncon> sameTroncons = plan.getTronconsFromIntersection(nodeMap.get(origin));

            final boolean[] toAdd = {true};

            if (sameTroncons != null) {
                sameTroncons
                        .stream()
                        .filter(troncon -> troncon.getDestination().getId() == destination)
                        .findFirst()
                        .ifPresent(troncon -> {
                            toAdd[0] = false;
                            if (troncon.getLength() > length) {
                                troncon.setLength(length);
                                troncon.setNameStreet(streetName);
                            }
                        });
            }

            if (toAdd[0]) {
                Troncon troncon = new Troncon();
                troncon.setDestination(nodeMap.get(destination));
                troncon.setOrigine(nodeMap.get(origin));
                troncon.setLength(length);
                troncon.setNameStreet(streetName);
                plan.addTroncon(troncon.getOrigine(), troncon);
            }
        }

        return plan;
    }
}
