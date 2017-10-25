package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.DeliveryException;
import lhexanome.optimodlivraison.platform.listeners.DeliveryListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class DeliveryFacadeTest {
    private DeliveryFacade deliveryFacade;
    private File littleDeliveryOrder;

    @BeforeEach
    void setup() {
        deliveryFacade = new DeliveryFacade();

        littleDeliveryOrder = new File(this.getClass().getResource("/xml/deliveries/DLpetit5.xml").getFile());
    }

    @Test
    void shouldLoadTestFile() {
        deliveryFacade.addOnUpdateDeliveryListener(new DeliveryListener() {
            @Override
            public void onUpdateDeliveryOrder(DeliveryOrder deliveryOrder) {
                assertThat(deliveryOrder).isNotNull();
                assertThat(deliveryOrder.getDeliveries())
                        .isNotNull()
                        .hasSize(4);

                assertThat(deliveryOrder.getBeginning()).isNotNull();
            }

            @Override
            public void onFailUpdateDeliveryOrder(DeliveryException e) {
                fail("Call on fail method", e);
            }
        });

        deliveryFacade.loadDeliveryOrderFromFile(littleDeliveryOrder, roadMap);
    }
}