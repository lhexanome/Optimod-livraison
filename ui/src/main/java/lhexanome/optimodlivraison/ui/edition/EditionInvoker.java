package lhexanome.optimodlivraison.ui.edition;

import lhexanome.optimodlivraison.platform.command.UndoableCommand;

import java.util.Stack;

/**
 * Invoker of commands on the ComputeFacade.
 * Only concern edition actions
 */
public class EditionInvoker {

    /**
     * History list.
     */
    private Stack<UndoableCommand> commands;


    /**
     * Redo history list.
     */
    private Stack<UndoableCommand> redoCommands;

    /**
     * Default constructor.
     */
    public EditionInvoker() {
        commands = new Stack<>();
        redoCommands = new Stack<>();
    }

    /**
     * Save the command in history and execute it.
     * Clear the redo list
     *
     * @param command Command to execute
     */
    public void storeAndExecute(UndoableCommand command) {
        this.commands.push(command);
        command.execute();
        redoCommands.clear();
    }


    /**
     * Undo the last command.
     * Add it to redo list.
     * If none was found, does nothing
     */
    public void undoLastCommand() {
        if (!commands.isEmpty()) {
            UndoableCommand command = commands.pop();
            redoCommands.push(command);
            command.unexecute();
        }
    }

    /**
     * Redo the last command to be undoed.
     * If none was found, does nothing
     */
    public void redoLastUndo() {
        if (!redoCommands.isEmpty()) {
            UndoableCommand command = redoCommands.pop();
            commands.push(command);
            command.reexecute();
        }
    }

}
