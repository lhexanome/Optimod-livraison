package lhexanome.optimodlivraison.platform.editition;

import lhexanome.optimodlivraison.platform.command.sync.UndoableCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EditionInvokerTest {

    class MockableCommand extends UndoableCommand {

        public MockableCommand() {
        }

        @Override
        protected void doExecute() {

        }

        @Override
        protected void doUndo() {

        }

        @Override
        protected void doRedo() {

        }
    }

    private EditionInvoker invoker;


    @BeforeEach
    void setup() {
        invoker = new EditionInvoker();
    }

    @Test
    void shouldStoreAndExecute() {
        // Given

        MockableCommand command = spy(new MockableCommand());

        // When

        invoker.storeAndExecute(command);


        // Then

        verify(command).doExecute();
        assertThat(invoker.getCommands()).hasSize(1);
        assertThat(invoker.getRedoCommands()).isEmpty();
    }

    @Test
    void shouldUndoTheLastCommand() {
        // Given

        MockableCommand command = spy(new MockableCommand());
        invoker.storeAndExecute(command);

        // When

        invoker.undoLastCommand();


        // Then

        verify(command).doUndo();
        assertThat(invoker.getRedoCommands()).hasSize(1);
        assertThat(invoker.getCommands()).isEmpty();

    }

    @Test
    void shouldRedoTheLastUndoCommand() {
        // Given

        MockableCommand command = spy(new MockableCommand());
        invoker.storeAndExecute(command);
        invoker.undoLastCommand();

        // When

        invoker.redoLastUndo();


        // Then

        verify(command).doRedo();
        assertThat(invoker.getCommands()).hasSize(1);
        assertThat(invoker.getRedoCommands()).isEmpty();
    }

    @Test
    void shouldDoNothingIfNoHistoryToUndo() {
        // Given

        MockableCommand command = spy(new MockableCommand());
        invoker.storeAndExecute(command);
        invoker.undoLastCommand();

        // When

        invoker.undoLastCommand();

        // Then

        verify(command, times(1)).doUndo();
    }

    @Test
    void shouldDoNothingIfNoHistoryToRedo() {
        // Given

        MockableCommand command = spy(new MockableCommand());
        invoker.storeAndExecute(command);
        invoker.undoLastCommand();
        invoker.redoLastUndo();

        // When

        invoker.redoLastUndo();

        // Then

        verify(command, times(1)).doRedo();
    }
}