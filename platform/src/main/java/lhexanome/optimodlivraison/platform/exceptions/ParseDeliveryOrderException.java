package lhexanome.optimodlivraison.platform.exceptions;

/**
 * Exception thrown when unable to parse a delivery order.
 */
public class ParseDeliveryOrderException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ParseDeliveryOrderException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParseDeliveryOrderException(String message) {
        super(message);
    }
}
