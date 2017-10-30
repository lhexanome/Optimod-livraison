package lhexanome.optimodlivraison.platform.command;

/**
 * Interface for a command capable of being undo and redo.
 */
public interface UndoableCommand extends Command {

    /**
     * Called when the action must be undoed.
     */
    void unexecute();

    /**
     * Called when the action must be redoed.
     */
    void reexecute();
}
