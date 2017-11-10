package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RemoveDeliveryCommandTest {
    @Test
    void doExecute() throws ParseException {

        DateUtil.parseDate("yyyy/MM/dd HH:mm", "2017/12/22 08:22");
        Intersection intersection = new Intersection((long)124,128,242);
        Intersection intersection1 = new Intersection((long)741,591,316);
        Intersection intersection2 = new Intersection((long)16,1679,2036);
        Intersection intersection3 = new Intersection((long)56,87,96);
        Intersection intersection4 = new Intersection((long)84,45,76);
        Intersection intersection5 = new Intersection((long)78,156,301);
        Intersection intersection6 = new Intersection((long)84, 564, 159);
        Halt halt = new Halt(intersection);
        Halt halt1 = new Halt(intersection1);
        Halt halt2 = new Halt(intersection2);
        Halt halt3 = new Halt(intersection3);
        Halt halt4 = new Halt(intersection4);
        Warehouse warehouse = new Warehouse(intersection5);
        Path removedPath;
        Delivery deliveryToAdd = new Delivery(intersection6, 8);

        Path path = new Path(halt, halt1);
        Path path1 = new Path(halt1, halt2);
        Path path2 = new Path(halt2, halt3);
        Path path3 = new Path(halt3,halt4);
        List<Path> paths = new ArrayList<>();
        paths.add(path);
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
    }

}