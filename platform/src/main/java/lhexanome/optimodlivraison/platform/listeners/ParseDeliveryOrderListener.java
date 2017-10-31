package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.models.DeliveryOrder;

/**
 * Delivery order parser listener.
 */
public interface ParseDeliveryOrderListener {
    /**
     * Called when a delivery order is parsed.
     *
     * @param deliveryOrderParsed Delivery order
     */
    void onDeliveryOrderParsed(DeliveryOrder deliveryOrderParsed);

    /**
     * Called when the parsing fail.
     *
     * @param e Exception raised
     */
    void onDeliveryOrderParsingFail(Exception e);
}
