package lhexanome.optimodlivraison.platform.command.sync;
import lhexanome.optimodlivraison.platform.command.ParseMapCommand;
import lhexanome.optimodlivraison.platform.compute.SimplifiedMap;
import lhexanome.optimodlivraison.platform.listeners.ParseMapListener;
import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;

import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class AddDeliveryCommandTest {


    @Test
    void doExecute() throws ParseException {


        Date d1 = DateUtil.parseDate("yyyy/MM/dd HH:mm", "2017/12/22 08:22");
        Date d2 = DateUtil.parseDate("yyyy/MM/dd HH:mm", "2017/12/22 08:24");
        Date d3 = DateUtil.parseDate("yyyy/MM/dd HH:mm", "2017/12/22 08:26");
        //Given
        Intersection intersection = new Intersection((long)124,128,242);
        Intersection intersection1 = new Intersection((long)741,591,316);
        Intersection intersection2 = new Intersection((long)741,1000,316);
        Intersection intersection3 = new Intersection((long)200,1000,316);
        Intersection intersection4 = new Intersection((long)1,5000,5000);

        Halt halt = new Halt(intersection);
        halt.setEstimateDate(d1);
        Halt halt1 = new Halt(intersection1);
        halt1.setEstimateDate(d2);

        Warehouse warehouse = new Warehouse(intersection2);
        warehouse.setEstimateDate(d3);

        Delivery deliveryToAdd = new Delivery(intersection3, 8);

        //warehouse to halt1
        Vector v1 = new Vector(intersection2, intersection4, "rue v1");
        Vector v2 = new Vector(intersection4, intersection, "rue v2");

        Vector v3 = new Vector(intersection, intersection4, "rue v3");
        Vector v4 = new Vector(intersection4, intersection1, "rue v4");

        Vector v5 = new Vector(intersection1, intersection4, "rue v5");
        Vector v6 = new Vector(intersection4, intersection2, "rue v6");

        Path path = new Path(warehouse, halt);
        path.setTimeToTravel(2);
        path.addVector(v1);
        path.addVector(v2);

        Path path1 = new Path(halt, halt1);
        path1.setTimeToTravel(2);
        path1.addVector(v3);
        path1.addVector(v4);

        Path path2 = new Path(halt1, warehouse);
        path2.setTimeToTravel(2);
        path2.addVector(v5);
        path2.addVector(v6);

        List<Path> paths = new ArrayList<>();
        paths.add(path);
        paths.add(path1);
        paths.add(path2);

        Tour tour = new Tour(warehouse, DateUtil.parseDate("yyyy/MM/dd HH:mm", "2017/12/22 08:22"),3600, paths);
        int index = tour.getPaths().size()-1;

        RoadMap roadMap = new RoadMap();
        roadMap.addIntersection(intersection);
        roadMap.addIntersection(intersection1);
        roadMap.addIntersection(intersection2);
        roadMap.addIntersection(intersection3);
        roadMap.addIntersection(intersection4);
        roadMap.addVector(v1);
        roadMap.addVector(v2);
        roadMap.addVector(v3);
        roadMap.addVector(v4);
        roadMap.addVector(v5);
        roadMap.addVector(v6);
        AddDeliveryCommand c = new AddDeliveryCommand(tour, roadMap, deliveryToAdd, index);

        // When
        c.execute();

        // Given
        assertThat(paths.size()).isEqualTo(4);

        //When
        //Then

    }

    @Test
    void doUndo() {


    }

}