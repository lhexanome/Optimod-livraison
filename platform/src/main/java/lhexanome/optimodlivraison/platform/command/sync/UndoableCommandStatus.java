package lhexanome.optimodlivraison.platform.command.sync;

/**
 * Represent the different status that a {@link UndoableCommand} can take.
 */
public enum UndoableCommandStatus {
    /**
     * Initial state.
     */
    INITIALIZED,

    /**
     * After the command is executed.
     */
    EXECUTED,

    /**
     * After the command is undoed.
     */
    UNDOED,

    /**
     * After the command is redoed.
     */
    REDOED
}
