package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.listeners.ParseDeliveryOrderListener;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class ParseDeliveryOrderCommandTest {
    private CountDownLatch lock = new CountDownLatch(1);

    private ParseDeliveryOrderCommand command;


    @BeforeEach
    void setup() throws InterruptedException {
        File deliveryOrder = new File(this.getClass().getResource("/xml/deliveries/DLpetit5.xml").getFile());
        File map = new File(this.getClass().getResource("/xml/map/planLyonPetit.xml").getFile());

        final RoadMap[] roadMap = new RoadMap[1];

        ParseMapCommand parseMapCommand = new ParseMapCommand(map);
        parseMapCommand.setListener(new ParseMapListener() {
            @Override
            public void onMapParsed(RoadMap roadMapParsed) {
                roadMap[0] = roadMapParsed;
                lock.countDown();
            }

            @Override
            public void onMapParsingFail(Exception e) {
                fail("Couldn't get a road map for ParseDeliveryOrderCommandTest", e);
            }
        });

        parseMapCommand.execute();

        lock.await(2, TimeUnit.SECONDS);

        command = new ParseDeliveryOrderCommand(deliveryOrder, roadMap[0]);
    }

    @Test
    void shouldLoadTestFile() throws InterruptedException {
        final DeliveryOrder[] deliveryOrder = new DeliveryOrder[1];
        CountDownLatch lock = new CountDownLatch(1);

        command.setListener(new ParseDeliveryOrderListener() {
            @Override
            public void onDeliveryOrderParsed(DeliveryOrder deliveryOrderParsed) {
                deliveryOrder[0] = deliveryOrderParsed;
                lock.countDown();
            }

            @Override
            public void onDeliveryOrderParsingFail(Exception e) {
                fail("Unable to parse", e);
            }
        });

        command.execute();

        lock.await(1, TimeUnit.SECONDS);

        assertThat(deliveryOrder[0]).isNotNull();
        assertThat(deliveryOrder[0].getDeliveries()).hasSize(4);
    }

}