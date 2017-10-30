package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.listeners.ParseDeliveryOrderListener;
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
        deliveryFacade.addOnUpdateDeliveryListener(new ParseDeliveryOrderListener() {
            @Override
            public void onDeliveryOrderParsed(DeliveryOrder deliveryOrderParsed) {
                assertThat(deliveryOrderParsed).isNotNull();
                assertThat(deliveryOrderParsed.getDeliveries())
                        .isNotNull()
                        .hasSize(4);

                assertThat(deliveryOrderParsed.getBeginning()).isNotNull();
            }

            @Override
            public void onDeliveryOrderParsingFail(Exception e) {
                fail("Call on fail method", e);
            }
        });

//        deliveryFacade.loadDeliveryOrderFromFile(littleDeliveryOrder, roadMap);
    }
}