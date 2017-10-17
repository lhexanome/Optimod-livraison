package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapFacadeTest {

    private MapFacade mapFacade;
    private File littleMap;

    @BeforeEach
    void setup() {
        mapFacade = new MapFacade();

        littleMap = new File(this.getClass().getResource("/xml/map/planLyonPetit.xml").getFile());
    }

    @Test
    void shouldLoadTestFile() throws MapException {
        mapFacade.addOnUpdateMapListener(plan -> {
            assertEquals(209, plan.getIntersectionCount());
        });

        mapFacade.loadMapFromFile(littleMap);
    }
}