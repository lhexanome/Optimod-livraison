package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.listeners.MapListener;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.parsing.common.LoadFile;
import lhexanome.optimodlivraison.platform.parsing.map.MapParser;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Facade de la partie Plan.
 */
public class MapFacade {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MapFacade.class.getName());

    /**
     * Map parser.
     */
    private MapParser mapParser;

    /**
     * Liste de listeners.
     */
    private Collection<MapListener> listeners;

    /**
     * Constructeur par défaut.
     */
    public MapFacade() {
        mapParser = new MapParser();
        listeners = new ArrayList<>();
    }

    /**
     * Ajoute un listener.
     *
     * @param listener Listener
     */
    public void addOnUpdateMapListener(MapListener listener) {
        listeners.add(listener);
    }

    /**
     * Enlève un listener.
     *
     * @param listener Listener
     */
    public void removeOnUpdateMapListner(MapListener listener) {
        listeners.remove(listener);
    }

    /**
     * Charge un fichier contenant un plan d'une ville.
     *
     * @param xmlFile Fichier xml
     * @throws MapException S'il y a un problème lors du chargement
     */
    public void loadMapFromFile(File xmlFile) throws MapException {
        try {
            LOGGER.info(MessageFormat.format("Loading map {0}", xmlFile.getName()));
            Element rootElement = LoadFile.loadFromFile(xmlFile);
            LOGGER.info("XML File loaded");

            Plan newPlan = mapParser.parseMap(rootElement);

            LOGGER.warning(MessageFormat.format("Map loaded with {0} intersections", newPlan.getIntersectionCount()));

            listeners.forEach(listener -> listener.onUpdate(newPlan));

            LOGGER.info("Listeners notified !");
        } catch (JDOMException e) {
            LOGGER.warning(MessageFormat.format("Error while parsing XML", e.getMessage()));
            throw new MapException(e);
        } catch (IOException e) {
            LOGGER.warning(MessageFormat.format("I/O error", e.getMessage()));
            throw new MapException(e);
        } catch (ParseMapException e) {
            LOGGER.warning(MessageFormat.format("Bad map format", e.getMessage()));
            throw new MapException(e);
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getMessage()));
            throw new MapException(e);
        }
    }
}
