package lhexanome.optimodlivraison.platform.listeners;

import lhexanome.optimodlivraison.platform.models.Tour;

/**
 * Listener for tour compute.
 */
public interface ComputeTourListener {
    /**
     * Called when a tour is computed for the first time.
     * Then, it will be updated and improved.
     *
     * @param firstTour First tour computed
     */
    void onTourFirstTourComputed(Tour firstTour);

    /**
     * Called when the tour was improved.
     * The listener must use the old reference.
     */
    void onTourImproved();

    /**
     * Called at the end of the computing.
     */
    void onComputingTourEnd();

    /**
     * Called when a the application was unable to compute a tour.
     *
     * @param e Exception raised
     */
    void onTourComputingFail(Exception e);
}
