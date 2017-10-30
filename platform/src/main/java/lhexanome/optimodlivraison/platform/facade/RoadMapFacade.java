package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.parsing.RoadMapParser;
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
 * Facade de la partie RoadMap.
 */
public class RoadMapFacade {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RoadMapFacade.class.getName());

    /**
     * RoadMap parser.
     */
    private RoadMapParser roadMapParser;

    /**
     * Liste de listeners.
     */
    private Collection<ParseMapListener> listeners;

    /**
     * Constructeur par défaut.
     */
    public RoadMapFacade() {
        roadMapParser = new RoadMapParser();
        listeners = new ArrayList<>();
    }

    /**
     * Ajoute un listener.
     *
     * @param listener Listener
     */
    public void addOnUpdateMapListener(ParseMapListener listener) {
        listeners.add(listener);
    }

    /**
     * Enlève un listener.
     *
     * @param listener Listener
     */
    public void removeOnUpdateMapListner(ParseMapListener listener) {
        listeners.remove(listener);
    }

    /**
     * Charge un fichier contenant un plan d'une ville.
     *
     * @param xmlFile Fichier xml
     */
    public void loadMapFromFile(File xmlFile) {
        try {
            LOGGER.info(MessageFormat.format("Loading map {0}", xmlFile.getName()));
            Element rootElement = LoadFile.loadFromFile(xmlFile);
            LOGGER.info("XML File loaded");

            RoadMap newRoadMap = roadMapParser.parseMap(rootElement);

            LOGGER.warning(MessageFormat.format("RoadMap loaded with {0} intersections",
                    newRoadMap.getIntersectionCount()));

            listeners.forEach(l -> l.onMapParsed(newRoadMap));

            LOGGER.info("Listeners notified !");
        } catch (JDOMException e) {
            LOGGER.warning(MessageFormat.format("Error while parsing XML", e.getCause()));
            failUpdate(e);
        } catch (IOException e) {
            LOGGER.warning(MessageFormat.format("I/O error", e.getCause()));
            failUpdate(e);
        } catch (ParseMapException e) {
            LOGGER.warning(MessageFormat.format("Bad map format", e.getCause()));
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
        listeners.forEach(l -> l.onMapParsingFail(new MapException(e)));
    }
}
