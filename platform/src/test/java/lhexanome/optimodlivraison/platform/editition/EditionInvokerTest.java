package lhexanome.optimodlivraison.platform.editition;

import lhexanome.optimodlivraison.platform.command.sync.AddDeliveryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class EditionInvokerTest {

    private EditionInvoker invoker;


    @BeforeEach
    void setup() {
        invoker = new EditionInvoker();
    }

    @Test
    void shouldStoreAndExecute() {
        // Given

        AddDeliveryCommand command = spy(AddDeliveryCommand.class);


        // When

        invoker.storeAndExecute(command);


        // Then

        verify(command).execute();
        assertThat(invoker.getCommands()).hasSize(1);

    }

    @Test
    void shouldUndoTheLastCommand() {
        // Given


        // When


        // Then

    }

    @Test
    void shouldRedoTheLastUndoCommand() {
        // Given


        // When


        // Then

    }
}