package lhexanome.optimodlivraison.platform.parsing;

import lhexanome.optimodlivraison.platform.exceptions.ParseDeliveryOrderException;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import org.jdom2.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Parser d'un document XML représentant une demande de livraison.
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
     * Parse un document XML représentant une demande de livraison.
     * Utilise le format suivant :
     * {@code
     * <demandeDeLivraisons>
     * <entrepot adresse="1" heureDepart="8:0:0"/>
     * <livraison adresse="2" duree="900"/>
     * <livraison adresse="3" duree="600"/>
     * </demandeDeLivraisons>
     * }
     *
     * @param rootElement Element racine
     * @return Une demande de livraison
     * @throws ParseDeliveryOrderException Si un problème a lieu lors du parsing
     */
    public DeliveryOrder parseDeliveryOrder(Element rootElement) throws ParseDeliveryOrderException {
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

        loadWarehouse(warehouseList.get(0), deliveryOrder);

        LOGGER.info("Warehouse loaded");

        loadDeliveries(deliveries, deliveryOrder);

        LOGGER.info("End parsing delivery order");
        return deliveryOrder;
    }

    /**
     * Charge l'entrepôt et l'heure de départ.
     *
     * @param element       Element représentant l'entrepôt
     * @param deliveryOrder Demande de livraison
     * @throws ParseDeliveryOrderException Si la structure est mauvaise
     */
    public void loadWarehouse(Element element, DeliveryOrder deliveryOrder) throws ParseDeliveryOrderException {
        Long address = Long.valueOf(element.getAttributeValue(XML_ADDRESS_ATRTRIBUTE));
        String startTime = element.getAttributeValue(XML_START_TIME_ATTRIBUTE);

        if (startTime == null) {
            throw new ParseDeliveryOrderException("Warehouse element is missing attribute" + XML_START_TIME_ATTRIBUTE);
        }

        DateFormat dateFormat = new SimpleDateFormat("H:m:s");

        Warehouse warehouse = new Warehouse(new Intersection(address));

        // FIXME Start time in warehouse ?
        try {
            Date date = dateFormat.parse(startTime);

            deliveryOrder.setBeginning(warehouse);
            deliveryOrder.setStart(date);

        } catch (ParseException e) {
            throw new ParseDeliveryOrderException("Unable to parse date");
        }
    }


    /**
     * Charge les livraisons.
     *
     * @param deliveries    Liste d'élements représentant des livraisons
     * @param deliveryOrder Demande de livraison
     * @throws ParseDeliveryOrderException Si la structure est mauvaise
     */
    public void loadDeliveries(List<Element> deliveries, DeliveryOrder deliveryOrder)
            throws ParseDeliveryOrderException {

        for (Element delivery : deliveries) {
            if (delivery.getAttribute(XML_ADDRESS_ATRTRIBUTE) == null
                    || delivery.getAttribute(XML_DURATION_ATTRIBUTE) == null) {
                throw new ParseDeliveryOrderException("A delivery element is missing an attribute");
            }

            Long address = Long.valueOf(delivery.getAttributeValue(XML_ADDRESS_ATRTRIBUTE));
            Integer duration = Integer.valueOf(delivery.getAttributeValue(XML_DURATION_ATTRIBUTE));

            Delivery livraison = new Delivery(new Intersection(address), duration);

            deliveryOrder.addDelivery(livraison);
        }
    }
}
