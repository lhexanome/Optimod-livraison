package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.models.RoadMap;

/**
 * Listener for road map parsing.
 */
public interface ParseMapListener {
    /**
     * Called when a road map is loaded.
     *
     * @param roadMapParsed RoadMap loaded
     */
    void onMapParsed(RoadMap roadMapParsed);

    /**
     * Called when a road map fail to be parsed.
     *
     * @param e Exception raised
     */
    void onMapParsingFail(Exception e);
}
