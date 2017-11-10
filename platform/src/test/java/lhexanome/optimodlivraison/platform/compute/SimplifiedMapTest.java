package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.command.ParseMapCommand;
import lhexanome.optimodlivraison.platform.listeners.ParseDeliveryOrderListener;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class SimplifiedMapTest {

    RoadMap roadMap;

    Delivery d5, d8, d13;
    Warehouse e7;

    DeliveryOrder demande_e7d8d5;
    SimplifiedMap sm_e7d8d5;

    @BeforeEach
    public void loadMap() throws InterruptedException {
        File map = new File(this.getClass().getResource("/xml/map/planLyonTest.xml").getFile());
        ParseMapCommand command = new ParseMapCommand(map);

        final DeliveryOrder[] deliveryOrder = new DeliveryOrder[1];
        CountDownLatch lock = new CountDownLatch(1);
        final RoadMap[] roadMapPtr = new RoadMap[1];

        command.setListener(new ParseMapListener() {
            @Override
            public void onMapParsed(RoadMap roadMapParsed) {
                roadMapPtr[0] = roadMapParsed;
                lock.countDown();
            }

            @Override
            public void onMapParsingFail(Exception e) {
                fail("Unable to parse", e);
            }
        });

        command.execute();

        lock.await(1, TimeUnit.SECONDS);

        roadMap = roadMapPtr[0];

        d5 = new Delivery(roadMap.findIntersectionById(5L), 20);
        d8 = new Delivery(roadMap.findIntersectionById(8L), 20);
        d13 = new Delivery(roadMap.findIntersectionById(13L), 20);
        e7 = new Warehouse(roadMap.findIntersectionById(7L));

        demande_e7d8d5 = new DeliveryOrder();
        demande_e7d8d5.setWarehouse(e7);
        demande_e7d8d5.addDelivery(d5);
        demande_e7d8d5.addDelivery(d8);
        sm_e7d8d5 = new SimplifiedMap(demande_e7d8d5,roadMap);
    }

    @Test
    public void testDijkstra(){

        DeliveryOrder demande = new DeliveryOrder();
        demande.setWarehouse(e7);
        demande.addDelivery(d8);
        demande.addDelivery(d5);

        SimplifiedMap sm = new SimplifiedMap(demande,roadMap);
        sm.computeGraph();

        //TODO real assert
        assertThat(sm).isEqualTo(sm);
    }

    @Test
    public void testComputeGraphWidthOutDelivery(){
        SimplifiedMap simplifiedMapTested = new SimplifiedMap(roadMap);
        try {
            simplifiedMapTested.computeGraph();
            fail("compute no failed widthOut delivery");
        }catch (Exception e) {

        }

    }

    @Test
    public void testComputeGraph(){
        sm_e7d8d5.computeGraph();
        Map<Halt, ArrayList<Path>> res = sm_e7d8d5.getGraph();

        Map<Halt, Path[]> predicat = new HashMap<>();
        new ArrayList<>();
        predicat.put(e7,new Path[]{
                interssectionsIdToPath(e7, d5, roadMap, 7l, 6L, 5L),
                interssectionsIdToPath(e7, d8, roadMap, 7l, 9L, 8L)
        });
        predicat.put(d8,new Path[]{
                interssectionsIdToPath(d8, d5, roadMap, 8l, 6L, 5L),
                interssectionsIdToPath(d8, e7, roadMap, 8l, 9L, 7L)
        });
        predicat.put(d5,new Path[]{
                interssectionsIdToPath(d5, d8, roadMap, 5l, 10L, 8L),
                interssectionsIdToPath(d5, e7, roadMap, 5l, 6L, 7L)
        });

        assertThat(res).hasSameSizeAs(predicat);
        res.forEach((halt, paths) -> assertThat(paths).containsOnly(predicat.get(halt)));
    }

    @Test
    public void testShortestPathList(){
        Set<Halt> alts = new HashSet<>();
        alts.add(d5);
        alts.add(d8);
        List<Path> res = sm_e7d8d5.shortestPathList(e7, alts);

        Path[] predicat = {
                interssectionsIdToPath(e7, d5, roadMap, 7l, 6L, 5L),
                interssectionsIdToPath(e7, d8, roadMap, 7l, 9L, 8L)
        };

        assertThat(res).containsOnly(predicat);
    }

    @Test
    public void testShortestPathUnrechable(){
        try {
            Path res = sm_e7d8d5.shortestPathList(e7, d13);
            fail("return result without exeption");
        }catch (Exception e){

        }

    }

    @Test
    public void testShortestPath(){

        Path res = sm_e7d8d5.shortestPathList(e7, d5);

        Path predicat = interssectionsIdToPath(e7, d5, roadMap, 7l, 6L, 5L);

        assertThat(res).isEqualTo(predicat);
    }


    public Vector findVector(RoadMap map, Intersection depart, Intersection arriver){
        for(Vector v : map.getVectorsFromIntersection(depart))
            if(v.getDestination() == arriver)
                return v;
        return null;
    }

    public Path interssectionsIdToPath(Halt start, Halt end, RoadMap roadMap, long ... intersectionsId){
        Path p = new Path(start,end);
        Intersection[] intersections = new Intersection[intersectionsId.length];
        for(int i = 0; i < intersectionsId.length; i++) {
            intersections[i] = roadMap.findIntersectionById(intersectionsId[i]);
        }
        for(int i = 1; i < intersections.length; i++)
            p.addVector(findVector(roadMap, intersections[i-1], intersections[i]));

        return p;
    }
}