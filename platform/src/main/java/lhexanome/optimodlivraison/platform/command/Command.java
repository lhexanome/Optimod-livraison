package lhexanome.optimodlivraison.platform.command;

/**
 * Interface for using the Command design pattern.
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
     */
    void onFail(Exception e);
}