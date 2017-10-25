package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.DeliveryException;
import lhexanome.optimodlivraison.platform.exceptions.ParseDeliveryOrderException;
import lhexanome.optimodlivraison.platform.listeners.DeliveryListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.parsing.DeliveryOrderParser;
import lhexanome.optimodlivraison.platform.parsing.common.LoadFile;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Facade de la partie livraisons.
 */
public class DeliveryFacade {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DeliveryFacade.class.getName());

    /**
     * Delivery parser.
     */
    private DeliveryOrderParser deliveryOrderParser;

    /**
     * Liste de listeners.
     */
    private Collection<DeliveryListener> listeners;

    /**
     * Constructeur par défaut.
     */
    public DeliveryFacade() {
        deliveryOrderParser = new DeliveryOrderParser();
        listeners = new ArrayList<>();
    }

    /**
     * Ajoute un listener.
     *
     * @param listener Listener
     */
    public void addOnUpdateDeliveryListener(DeliveryListener listener) {
        listeners.add(listener);
    }

    /**
     * Enlève un listener.
     *
     * @param listener Listener
     */
    public void removeOnUpdateDeliveryListner(DeliveryListener listener) {
        listeners.remove(listener);
    }

    /**
     * Charge un fichier contenant une demande de livraison.
     *
     * @param xmlFile Fichier xml
     * @param roadMap RoadMap already initialized (to bind X and Y)
     */
    public void loadDeliveryOrderFromFile(File xmlFile, RoadMap roadMap) {
        try {
            LOGGER.info(MessageFormat.format("Loading delivery order {0}", xmlFile.getName()));
            Element rootElement = LoadFile.loadFromFile(xmlFile);
            LOGGER.info("XML File loaded");

            DeliveryOrder deliveryOrder = deliveryOrderParser.parseDeliveryOrder(rootElement, roadMap);

            LOGGER.warning(MessageFormat.format("Delivery order loaded with {0} deliveries",
                    deliveryOrder.getDeliveries().size()));

            listeners.forEach(l -> l.onUpdateDeliveryOrder(deliveryOrder));

            LOGGER.info("Listeners notified !");
        } catch (JDOMException e) {
            LOGGER.warning(MessageFormat.format("Error while parsing XML", e.getCause()));
            failUpdate(e);
        } catch (IOException e) {
            LOGGER.warning(MessageFormat.format("I/O error", e.getCause()));
            failUpdate(e);
        } catch (ParseDeliveryOrderException e) {
            LOGGER.warning(MessageFormat.format("Bad delivery order format", e.getCause()));
            failUpdate(e);
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getCause()));
            failUpdate(e);
        }
    }

    /**
     * Permet de notifier les listeners d'un problème.
     *
     * @param e Exception générant l'erreur
     */
    private void failUpdate(Exception e) {
        listeners.forEach(l -> l.onFailUpdateDeliveryOrder(new DeliveryException(e)));
    }
}
