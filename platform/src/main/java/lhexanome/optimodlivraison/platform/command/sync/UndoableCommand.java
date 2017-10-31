package lhexanome.optimodlivraison.platform.command.sync;

/**
 * Interface for a command capable of being undo and redo.
 * <p>
 * These functions are synchronised !
 * This means that after the end of the {@link #execute()} function,
 * all the necessary logic is done.
 */
public interface UndoableCommand {

    /**
     * Called to execute an action.
     */
    void execute();

    /**
     * Called when the action must be undoed.
     */
    void undo();

    /**
     * Called when the action must be redoed.
     */
    void redo();
}
