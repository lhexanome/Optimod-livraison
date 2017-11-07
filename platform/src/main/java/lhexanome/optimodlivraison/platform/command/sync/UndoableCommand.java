package lhexanome.optimodlivraison.platform.command.sync;

/**
 * Interface for a command capable of being undo and redo.
 * <p>
 * These functions are synchronised !
 * This means that after the end of the {@link #execute()} function,
 * all the necessary logic is done.
 */
public abstract class UndoableCommand {

    /**
     * Command status.
     */
    private UndoableCommandStatus status;

    /**
     * Constructor.
     * Set the status to {@link UndoableCommandStatus#INITIALIZED}
     */
    public UndoableCommand() {
        status = UndoableCommandStatus.INITIALIZED;
    }

    /**
     * Called to execute an action.
     */
    public void execute() {
        if (this.status != UndoableCommandStatus.INITIALIZED) {
            throw new RuntimeException("The command was already executed !");
        }

        doExecute();

        this.status = UndoableCommandStatus.EXECUTED;
    }

    /**
     * Executed by the execute method.
     */
    protected abstract void doExecute();

    /**
     * Called when the action must be undoed.
     */
    public void undo() {
        if (this.status != UndoableCommandStatus.EXECUTED
                && this.status != UndoableCommandStatus.REDOED) {
            throw new RuntimeException("Tried to undo before executing the action !");
        }

        doUndo();

        this.status = UndoableCommandStatus.UNDOED;
    }

    /**
     * Executed by the undo method.
     */
    protected abstract void doUndo();

    /**
     * Called when the action must be redoed.
     */
    public void redo() {
        if (this.status != UndoableCommandStatus.UNDOED) {
            throw new RuntimeException("Tried to redo before undoing the action !");
        }

        doRedo();

        this.status = UndoableCommandStatus.REDOED;
    }

    /**
     * Executed by the redo method.
     */
    protected abstract void doRedo();


    /**
     * Status getter.
     *
     * @return The current status
     */
    public UndoableCommandStatus getStatus() {
        return status;
    }
}
