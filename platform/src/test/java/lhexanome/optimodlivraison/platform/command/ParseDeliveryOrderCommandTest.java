package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.listeners.ParseDeliveryOrderListener;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class ParseDeliveryOrderCommandTest {
    private CountDownLatch lock = new CountDownLatch(1);

    private ParseDeliveryOrderCommand commandLittleDelivery;
    private ParseDeliveryOrderCommand commandMiddleDeliveryWithTimeSlot;


    @BeforeEach
    void setup() throws InterruptedException {
        File littleDeliveryOrder = new File(this.getClass().getResource("/xml/deliveries/DLpetit5.xml").getFile());
        File middleDeliveryOrder = new File(this.getClass().getResource("/xml/deliveries/DLmoyen5TW1.xml").getFile());
        File map = new File(this.getClass().getResource("/xml/map/planLyonMoyen.xml").getFile());

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

        commandLittleDelivery = new ParseDeliveryOrderCommand(littleDeliveryOrder, roadMap[0]);
        commandMiddleDeliveryWithTimeSlot = new ParseDeliveryOrderCommand(middleDeliveryOrder, roadMap[0]);
    }

    @Test
    void shouldLoadSimpleDeliveryOrder() throws InterruptedException {
        final DeliveryOrder[] deliveryOrder = new DeliveryOrder[1];
        CountDownLatch lock = new CountDownLatch(1);

        commandLittleDelivery.setListener(new ParseDeliveryOrderListener() {
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

        commandLittleDelivery.execute();

        lock.await(1, TimeUnit.SECONDS);

        assertThat(deliveryOrder[0]).isNotNull();
        assertThat(deliveryOrder[0].getDeliveries()).hasSize(4);
    }

    @Test
    void shouldLoadDeliveryOrderWithTimeSlot() throws InterruptedException {
        final DeliveryOrder[] deliveryOrder = new DeliveryOrder[1];
        CountDownLatch lock = new CountDownLatch(1);

        commandMiddleDeliveryWithTimeSlot.setListener(new ParseDeliveryOrderListener() {
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

        commandMiddleDeliveryWithTimeSlot.execute();

        lock.await(1, TimeUnit.SECONDS);

        assertThat(deliveryOrder[0]).isNotNull();
        assertThat(deliveryOrder[0].getDeliveries())
                .hasSize(4)
                .extractingResultOf("getSlot")
                .doesNotContain(null, Index.atIndex(0));

    }

}