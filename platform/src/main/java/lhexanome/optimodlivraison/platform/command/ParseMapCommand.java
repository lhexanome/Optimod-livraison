package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.exceptions.ParseMapException;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.parsing.RoadMapParser;
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
 * Command to parse road map.
 * It use {@link SwingWorker} to work, so the logic part is executed in another thread than the UI Thread.
 */
public class ParseMapCommand extends SwingWorker<RoadMap, Void> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ParseMapCommand.class.getName());

    /**
     * File that will be parsed.
     */
    private File roadMapFile;

    /**
     * Parser used to parse the map.
     */
    private RoadMapParser parser;

    /**
     * Listener to the command.
     */
    private ParseMapListener listener;

    /**
     * Command constructor.
     *
     * @param roadMapFile File to be parsed
     */
    public ParseMapCommand(File roadMapFile) {
        this.roadMapFile = roadMapFile;
        this.parser = new RoadMapParser();
    }


    /**
     * Listener setter.
     * There is only one listener per command.
     *
     * @param listener Listener
     */
    public void setListener(ParseMapListener listener) {
        this.listener = listener;
    }

    /**
     * Parse a road map.
     *
     * @return a parsed road map
     * @throws Exception if the road map could not be parsed
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    protected RoadMap doInBackground() throws Exception {
        try {
            LOGGER.info(MessageFormat.format("Loading map {0}", roadMapFile.getName()));
            Element rootElement = LoadFile.loadFromFile(roadMapFile);
            LOGGER.info("XML File loaded");

            setProgress(30);
            RoadMap newRoadMap = parser.parseMap(rootElement);

            LOGGER.warning(MessageFormat.format("RoadMap loaded with {0} intersections",
                    newRoadMap.getIntersectionCount()));

            setProgress(100);

            return newRoadMap;
        } catch (JDOMException e) {
            LOGGER.warning(MessageFormat.format("Error while parsing XML", e.getCause()));
            throw e;
        } catch (IOException e) {
            LOGGER.warning(MessageFormat.format("I/O error", e.getCause()));
            throw e;
        } catch (ParseMapException e) {
            LOGGER.warning(MessageFormat.format("Bad map format", e.getCause()));
            throw e;
        } catch (Exception e) {
            LOGGER.warning(MessageFormat.format("Unknown error", e.getCause()));
            throw e;
        }
    }


    /**
     * Called by the UI thread.
     * Notify the listener.
     */
    @Override
    protected void done() {
        if (this.listener == null) return;

        try {
            listener.onMapParsed(this.get());
        } catch (InterruptedException | ExecutionException e) {
            listener.onMapParsingFail(e);
        }
    }
}
