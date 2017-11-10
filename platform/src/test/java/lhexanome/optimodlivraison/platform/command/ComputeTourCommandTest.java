package lhexanome.optimodlivraison.platform.command;

import lhexanome.optimodlivraison.platform.listeners.ComputeTourListener;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.TimeSlot;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ComputeTourCommandTest {

    Delivery d4, d2, d5, d8, d10, d11, d13;
    Warehouse e7;

    RoadMap roadMap;


    @BeforeEach
    public void loadRoadMap() throws InterruptedException {
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

        d2 = new Delivery(roadMap.findIntersectionById(2L), 20);
        d4 = new Delivery(roadMap.findIntersectionById(4L), 20);
        d5 = new Delivery(roadMap.findIntersectionById(5L), 20);
        d8 = new Delivery(roadMap.findIntersectionById(8L), 20);
        d10 = new Delivery(roadMap.findIntersectionById(10L), 20);
        d11 = new Delivery(roadMap.findIntersectionById(11L), 20);
        d13 = new Delivery(roadMap.findIntersectionById(13L), 20);
        e7 = new Warehouse(roadMap.findIntersectionById(7L));
    }

    @BeforeEach
    void setup() {

    }

    @Test
    void shouldCompute() throws ParseException, InterruptedException {
        CountDownLatch lock = new CountDownLatch(2);

        Tour[] res = new Tour[1];

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setWarehouse(e7);
        deliveryOrder.addDelivery(d10);
        deliveryOrder.addDelivery(d11);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date startedDate = sdf.parse("10:12");
        deliveryOrder.setStart(startedDate);
        Observer emptyObserver = (o, arg) -> {
        };
        ComputeTourCommand ctcTested = new ComputeTourCommand(roadMap, deliveryOrder, emptyObserver);
        ComputeTourListener listener = new ComputeTourListener() {
            @Override
            public void onFirstTourComputed(Tour firstTour) {
                res[0] = firstTour;
                lock.countDown();
            }

            @Override
            public void onComputingTourEnd() {
                lock.countDown();
            }

            @Override
            public void onTourComputingFail(Exception e) {
                fail("tour computing failed");
            }
        };
        ctcTested.setListener(listener);

        ctcTested.execute();

        assertThat(lock.await(3, TimeUnit.SECONDS)).isTrue();

        Tour predictiveTour = new Tour();
        predictiveTour.setStart(startedDate);
        predictiveTour.setWarehouse(e7);
        predictiveTour.setTime(371);
        List<Path> paths = new ArrayList();
        Collections.addAll(paths,
                interssectionsIdToPath(e7, d11, roadMap, 7L, 9L, 11L),
                interssectionsIdToPath(d11, d10, roadMap, 11L, 10L),
                interssectionsIdToPath(d10, e7, roadMap, 10L, 8L, 9L, 7L)
        );
        predictiveTour.setPaths(paths);

        predictiveTour.addObserver(ctcTested);
        predictiveTour.addObserver(emptyObserver);
        assertThat(res[0]).isEqualToComparingFieldByField(predictiveTour);
    }

    @Test
    void failComputeUnrichable() throws ParseException, InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setWarehouse(e7);
        deliveryOrder.addDelivery(d13);
        deliveryOrder.addDelivery(d11);
        Date startedDate = DateUtil.getDate(10, 12);
        deliveryOrder.setStart(startedDate);
        Observer emptyObserver = (o, arg) -> {
        };
        ComputeTourCommand ctcTested = new ComputeTourCommand(roadMap, deliveryOrder, emptyObserver);
        ComputeTourListener listener = new ComputeTourListener() {
            @Override
            public void onFirstTourComputed(Tour firstTour) {
                fail("tour computing success");
            }

            @Override
            public void onComputingTourEnd() {
                fail("tour computing failed");
            }

            @Override
            public void onTourComputingFail(Exception e) {
                lock.countDown();
            }
        };

        ctcTested.setListener(listener);

        ctcTested.execute();

        assertThat(lock.await(3, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    void failComputeIncompatibleSlot() throws ParseException, InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

        Date startedDated10 = DateUtil.getDate(10, 00);;
        Date endDated10 = DateUtil.getDate(10, 10);
        d10.setSlot(new TimeSlot(startedDated10, endDated10));

        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setWarehouse(e7);
        deliveryOrder.addDelivery(d10);
        deliveryOrder.addDelivery(d11);
        Date startedDate = DateUtil.getDate(10, 12);
        deliveryOrder.setStart(startedDate);
        Observer emptyObserver = (o, arg) -> {
        };
        ComputeTourCommand ctcTested = new ComputeTourCommand(roadMap, deliveryOrder, emptyObserver);
        ComputeTourListener listener = new ComputeTourListener() {
            @Override
            public void onFirstTourComputed(Tour firstTour) {
                fail("tour computing success");
            }

            @Override
            public void onComputingTourEnd() {
                fail("tour computing failed");
            }

            @Override
            public void onTourComputingFail(Exception e) {
                lock.countDown();
            }
        };

        ctcTested.setListener(listener);

        ctcTested.execute();

        assertThat(lock.await(3, TimeUnit.SECONDS)).isTrue();
    }

    public Vector findVector(RoadMap map, Intersection depart, Intersection arriver) {
        for (Vector v : map.getVectorsFromIntersection(depart))
            if (v.getDestination() == arriver)
                return v;
        return null;
    }

    public Path interssectionsIdToPath(Halt start, Halt end, RoadMap roadMap, long... intersectionsId) {
        Path p = new Path(start, end);
        Intersection[] intersections = new Intersection[intersectionsId.length];
        for (int i = 0; i < intersectionsId.length; i++) {
            intersections[i] = roadMap.findIntersectionById(intersectionsId[i]);
        }
        for (int i = 1; i < intersections.length; i++)
            p.addVector(findVector(roadMap, intersections[i - 1], intersections[i]));

        return p;
    }
}
