package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class ParseMapCommandTest {

    private ParseMapCommand command;


    @BeforeEach
    void setup() {
        File map = new File(this.getClass().getResource("/xml/map/planLyonPetit.xml").getFile());

        command = new ParseMapCommand(map);
    }

    @Test
    void shouldLoadTestFile() throws InterruptedException {
        final RoadMap[] roadMap = new RoadMap[1];

        CountDownLatch lock = new CountDownLatch(1);

        command.setListener(new ParseMapListener() {
            @Override
            public void onMapParsed(RoadMap roadMapParsed) {
                roadMap[0] = roadMapParsed;
                lock.countDown();
            }

            @Override
            public void onMapParsingFail(Exception e) {
                fail("Unable to parse", e);
            }
        });

        command.execute();

        lock.await(1, TimeUnit.SECONDS);

        assertThat(roadMap[0]).isNotNull();
        assertThat(roadMap[0].getIntersectionCount()).isEqualTo(217);
    }
}