package lhexanome.optimodlivraison.platform.command;

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
