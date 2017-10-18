package lhexanome.optimodlivraison.platform.facade;

import lhexanome.optimodlivraison.platform.exceptions.MapException;
import lhexanome.optimodlivraison.platform.listeners.MapListener;
import lhexanome.optimodlivraison.platform.models.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MapFacadeTest {

    private MapFacade mapFacade;
    private File littleMap;

    @BeforeEach
    void setup() {
        mapFacade = new MapFacade();

        littleMap = new File(this.getClass().getResource("/xml/map/planLyonPetit.xml").getFile());
    }

    @Test
    void shouldLoadTestFile() {
        mapFacade.addOnUpdateMapListener(new MapListener() {
            @Override
            public void onUpdateMap(Plan plan) {
                assertEquals(209, plan.getIntersectionCount());
            }

            @Override
            public void onFailUpdateMap(MapException e) {
                fail("Call on fail method");
            }
        });

        mapFacade.loadMapFromFile(littleMap);
    }
}