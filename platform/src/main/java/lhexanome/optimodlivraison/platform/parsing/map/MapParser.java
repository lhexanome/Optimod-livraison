package lhexanome.optimodlivraison.platform.parsing.map;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Troncon;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapParser {

    public Plan parseMap(Element rootElement) throws ParseMapException {

        Map<Long, Intersection> map = loadNodes(rootElement.getChildren("noeud"));

        return loadTroncon(rootElement.getChildren("troncon"), map);
    }

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


    public Plan loadTroncon(List<Element> listTroncon, Map<Long, Intersection> nodeMap) throws ParseMapException {
        Plan plan = new Plan();

        for (Element node : listTroncon) {
            Long origin = Long.parseLong(node.getAttributeValue("origine"));
            Long destination = Long.parseLong(node.getAttributeValue("destination"));

            if (!nodeMap.containsKey(origin) || !nodeMap.containsKey(destination)) {
                throw new ParseMapException("Troncon has an unknown destination or origin");
            }

            Troncon troncon = new Troncon();
            troncon.setDestination(nodeMap.get(destination));
            troncon.setOrigine(nodeMap.get(origin));
            troncon.setLength(Float.parseFloat(node.getAttributeValue("longueur")));
            troncon.setNameStreet(node.getAttributeValue("nomRue"));
            plan.addTroncon(troncon.getOrigine(), troncon);
        }

        return plan;
    }
}
