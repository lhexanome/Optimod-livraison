package lhexanome.optimodlivraison.platform.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class ParseDeliveryOrderCommandTest {
    private File littleDeliveryOrder;

    @BeforeEach
    void setup() {

        littleDeliveryOrder = new File(this.getClass().getResource("/xml/deliveries/DLpetit5.xml").getFile());
    }

    @Test
    void shouldLoadTestFile() {

//        deliveryFacade.loadDeliveryOrderFromFile(littleDeliveryOrder, roadMap);
    }

}