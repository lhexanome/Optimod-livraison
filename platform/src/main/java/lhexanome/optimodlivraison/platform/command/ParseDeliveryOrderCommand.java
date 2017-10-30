package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.exceptions.ParseDeliveryOrderException;
import lhexanome.optimodlivraison.platform.listeners.ParseDeliveryOrderListener;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.parsing.DeliveryOrderParser;
import lhexanome.optimodlivraison.platform.parsing.common.LoadFile;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Command to parse a delivery order.
 */
public class ParseDeliveryOrderCommand extends SwingWorker<DeliveryOrder, Delivery> {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ParseDeliveryOrderCommand.class.getName());

    /**
     * XML Delivery file.
     */
    private File xmlDeliveryOrderFile;

    /**
     * Delivery Order parser.
     */
    private DeliveryOrderParser parser;

    /**
     * Road map.
     */
    private RoadMap roadMap;

    /**
     * Listener to the command.
     */
    private ParseDeliveryOrderListener listener;


    /**
     * Command constructor.
     *
     * @param xmlDeliveryOrderFile XML file
     * @param roadMap              Road map
     */
    public ParseDeliveryOrderCommand(File xmlDeliveryOrderFile, RoadMap roadMap) {
        this.xmlDeliveryOrderFile = xmlDeliveryOrderFile;
        this.roadMap = roadMap;

        this.parser = new DeliveryOrderParser();
    }


    /**
     * Listener setter.
     * There is only one listener per command
     *
     * @param listener Listener
     */
    public void setListener(ParseDeliveryOrderListener listener) {
        this.listener = listener;
    }

    /**
     * Parse a delivery order.
     *
     * @return a parsed delivery order
     * @throws Exception if the delivery could not be parsed
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    protected DeliveryOrder doInBackground() throws Exception {
        try {
            LOGGER.info(MessageFormat.format("Loading delivery order {0}", xmlDeliveryOrderFile.getName()));
            Element rootElement = LoadFile.loadFromFile(xmlDeliveryOrderFile);
            LOGGER.info("XML File loaded");
            setProgress(30);

            DeliveryOrder deliveryOrder = parser.parseDeliveryOrder(rootElement, roadMap);

            LOGGER.warning(MessageFormat.format("Delivery order loaded with {0} deliveries",
                    deliveryOrder.getDeliveries().size()));

            setProgress(100);
            return deliveryOrder;
        } catch (JDOMException e) {
            LOGGER.warning(MessageFormat.format("Error while parsing XML", e.getCause()));
            throw e;
        } catch (IOException e) {
            LOGGER.warning(MessageFormat.format("I/O error", e.getCause()));
            throw e;
        } catch (ParseDeliveryOrderException e) {
            LOGGER.warning(MessageFormat.format("Bad delivery order format", e.getCause()));
            throw e;
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getCause()));
            throw e;
        }
    }

    /**
     * Notify listener if set.
     */
    @Override
    protected void done() {
        if (listener == null) return;

        try {
            listener.onDeliveryOrderParsed(this.get());
        } catch (InterruptedException | ExecutionException e) {
            listener.onDeliveryOrderParsingFail(e);
        }
    }
}
