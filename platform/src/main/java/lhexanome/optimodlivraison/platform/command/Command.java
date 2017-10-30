package lhexanome.optimodlivraison.platform.command;

/**
 * Interface for using the Command design pattern.
 *
 * @param <T> Parameter of the onTerminate param
 */
public interface Command<T> {

    /**
     * Function called when the command is executed.
     */
    void execute();

    /**
     * Called when a command is executed without problems.
     *
     * @param o Result of the command
     */
    void onTerminate(T o);

    /**
     * Called when a command is executed and have a problem.
     *
     * @param e Exception raised for the problem.
     */
    void onFail(Exception e);
}
