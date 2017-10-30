package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class RoadMapFacadeTest {

    private RoadMapFacade roadMapFacade;
    private File littleMap;

    @BeforeEach
    void setup() {
        roadMapFacade = new RoadMapFacade();

        littleMap = new File(this.getClass().getResource("/xml/map/planLyonPetit.xml").getFile());
    }

    @Test
    void shouldLoadTestFile() {
        roadMapFacade.addOnUpdateMapListener(new ParseMapListener() {
            @Override
            public void onMapParsed(RoadMap roadMapParsed) {
                // 217 intersections dans le xml donc il doit y avoir 217 intersections dans le RoadMap!
                assertThat(roadMapParsed.getIntersectionCount()).isEqualTo(217);
            }

            @Override
            public void onMapParsingFail(MapException e) {
                fail("Call on fail method", e);
            }
        });


        roadMapFacade.loadMapFromFile(littleMap);
    }
}