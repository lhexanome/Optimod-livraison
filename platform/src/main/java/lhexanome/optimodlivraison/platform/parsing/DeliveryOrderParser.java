package lhexanome.optimodlivraison.platform.parsing;

import lhexanome.optimodlivraison.platform.exceptions.ParseDeliveryOrderException;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
import org.jdom2.Element;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Parse XML document representing a delivery order.
 */
public class DeliveryOrderParser {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DeliveryOrderParser.class.getName());

    /**
     * XML Root element.
     */
    public static final String XML_ROOT_ELEMENT = "demandeDeLivraisons";

    /**
     * XML Warehouse element.
     */
    public static final String XML_WAREHOUSE_ELEMENT = "entrepot";

    /**
     * XML delivery element.
     */
    public static final String XML_DELIVERY_ELEMENT = "livraison";

    /**
     * XML address attribute.
     */
    public static final String XML_ADDRESS_ATRTRIBUTE = "adresse";

    /**
     * XML duration attribute.
     */
    public static final String XML_DURATION_ATTRIBUTE = "duree";

    /**
     * XML start time attribute.
     */
    public static final String XML_START_TIME_ATTRIBUTE = "heureDepart";

    /**
     * XML start time slot attribute.
     */
    public static final String XML_START_TIME_SLOT_ATTRIBUTE = "debutPlage";

    /**
     * XML end time slot attribute.
     */
    public static final String XML_END_TIME_SLOT_ATTRIBUTE = "finPlage";


    /**
     * Parse a document representing a road map.
     * Use this following format :
     * {@code
     * <demandeDeLivraisons>
     * <entrepot adresse="1" heureDepart="8:0:0"/>
     * <livraison adresse="2" duree="900"/>
     * <livraison adresse="3" duree="600"/>
     * </demandeDeLivraisons>
     * }
     *
     * @param rootElement Element racine
     * @param roadMap     RoadMap already initialized to bind X and Y
     * @return A parsed delivery order
     * @throws ParseDeliveryOrderException If unable to parse the delivery order
     */
    public DeliveryOrder parseDeliveryOrder(Element rootElement, RoadMap roadMap) throws ParseDeliveryOrderException {
        DeliveryOrder deliveryOrder = new DeliveryOrder();

        LOGGER.info("Start parsing delivery order");

        if (!XML_ROOT_ELEMENT.equals(rootElement.getName())) {
            throw new ParseDeliveryOrderException(
                    String.format("XML root element name must be `%s`", XML_ROOT_ELEMENT));
        }
        rootElement.getChildren(XML_ROOT_ELEMENT);

        List<Element> warehouseList = rootElement.getChildren(XML_WAREHOUSE_ELEMENT);
        List<Element> deliveries = rootElement.getChildren(XML_DELIVERY_ELEMENT);

        if (warehouseList.size() > 1) {
            throw new ParseDeliveryOrderException("XML contains too many warehouses");
        }

        if (warehouseList.size() == 0) {
            throw new ParseDeliveryOrderException("XML does not have a warehouse");
        }

        if (warehouseList.size() + deliveries.size() != rootElement.getChildren().size()) {
            throw new ParseDeliveryOrderException("XML contains unknown elements");
        }

        loadWarehouse(warehouseList.get(0), deliveryOrder, roadMap);

        LOGGER.info("Warehouse loaded");

        loadDeliveries(deliveries, deliveryOrder, roadMap);

        LOGGER.info("End parsing delivery order");
        return deliveryOrder;
    }

    /**
     * Load the warehouse with the starting time.
     *
     * @param element       Warehouse XML element
     * @param deliveryOrder Delivery order to complete
     * @param roadMap       RoadMap used to find intersection
     * @throws ParseDeliveryOrderException If the XML is malformed
     */
    public void loadWarehouse(Element element, DeliveryOrder deliveryOrder, RoadMap roadMap)
            throws ParseDeliveryOrderException {

        String startTime = element.getAttributeValue(XML_START_TIME_ATTRIBUTE);

        if (startTime == null) {
            throw new ParseDeliveryOrderException("Warehouse element is missing attribute" + XML_START_TIME_ATTRIBUTE);
        }

        Warehouse warehouse = new Warehouse(findIntersectionFromElement(element, roadMap));

        try {
            Date date = DateUtil.parseDate("H:m:s", startTime);

            deliveryOrder.setBeginning(warehouse);
            deliveryOrder.setStart(date);

        } catch (ParseException e) {
            throw new ParseDeliveryOrderException("Unable to parse the start time of the warehouse");
        }
    }

    /**
     * Load deliveries from XML.
     *
     * @param deliveries    XML elements representing delivery
     * @param deliveryOrder Delivery order to complete
     * @param roadMap       RoadMap used to find intersection
     * @throws ParseDeliveryOrderException If the XML is malformed
     */
    public void loadDeliveries(List<Element> deliveries, DeliveryOrder deliveryOrder, RoadMap roadMap)
            throws ParseDeliveryOrderException {

        for (Element delivery : deliveries) {
            if (delivery.getAttribute(XML_DURATION_ATTRIBUTE) == null) {
                throw new ParseDeliveryOrderException(
                        String.format("A delivery element is missing the `%s` attribute", XML_DURATION_ATTRIBUTE));
            }

            Intersection intersection = findIntersectionFromElement(delivery, roadMap);
            Integer duration = Integer.valueOf(delivery.getAttributeValue(XML_DURATION_ATTRIBUTE));

            Delivery delivery1 = new Delivery(intersection, duration);


            if (delivery.getAttribute(XML_START_TIME_SLOT_ATTRIBUTE) != null
                    && delivery.getAttribute(XML_END_TIME_SLOT_ATTRIBUTE) != null) {
                Date startTime;
                Date endTime;

                try {

                    startTime = DateUtil.parseDate("H:m:s", delivery.getAttributeValue(XML_START_TIME_SLOT_ATTRIBUTE));
                    endTime = DateUtil.parseDate("H:m:s", delivery.getAttributeValue(XML_END_TIME_SLOT_ATTRIBUTE));

                    TimeSlot timeSlot = new TimeSlot(startTime, endTime);

                    delivery1.setSlot(timeSlot);

                } catch (ParseException e) {
                    throw new ParseDeliveryOrderException("Unable to parse the time slot of a delivery");
                }
            }

            deliveryOrder.addDelivery(delivery1);
        }
    }

    /**
     * This function parse an XML element and return an intersection from RoadMap.
     * Raise an exception if the intersection is not found in the RoadMap or `element` is missing attributes.
     *
     * @param element XML Element
     * @param roadMap RoadMap
     * @return Intersection
     * @throws ParseDeliveryOrderException If the address is not found.
     */
    private Intersection findIntersectionFromElement(Element element, RoadMap roadMap)
            throws ParseDeliveryOrderException {

        String address = element.getAttributeValue(XML_ADDRESS_ATRTRIBUTE);

        if (address == null) {
            throw new ParseDeliveryOrderException(
                    String.format("An intersection is missing the `%s` attribute", XML_ADDRESS_ATRTRIBUTE));
        }

        Intersection intersection = roadMap.findIntersectionById(
                Long.valueOf(address));

        if (intersection == null) {
            throw new ParseDeliveryOrderException(
                    String.format("The intersection with id `%s` doesn't exists in the provided road map", address));
        }

        return intersection;
    }

}
