package lhexanome.optimodlivraison.platform.command.sync;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Path;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.platform.utils.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoveDeliveryCommandTest {
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
        List<Path> paths2 = new ArrayList<>(paths);
        List<Path> paths3 = new ArrayList<>(paths);

        Tour tour = new Tour(warehouse, DateUtil.parseDate("yyyy/MM/dd HH:mm", "2017/12/22 08:22"),3600,paths );
         int index = 3;

        Halt previousHalt = tour.getPaths().get(index).getStart();
        assertThat(previousHalt).isEqualTo(halt3);

        Halt afterHalt = tour.getPaths().get(index).getEnd();
        assertThat(afterHalt).isEqualTo(halt4);

        removedPath = tour.getPaths().remove(index);
        assertThat(removedPath).isEqualTo(path3);

        Path path4 = new Path(previousHalt, deliveryToAdd);
        tour.getPaths().add(index, path4);
        assertThat(path4).isEqualTo(tour.getPaths().get(index));

        index = 0;
        assertThat(tour.getPaths().get(index+2).getStart()).isEqualTo(halt2);
        path4 = new Path(warehouse, paths2.get(index+2).getStart());

        removedPath = paths2.remove(index);
        assertThat(removedPath).isEqualTo(path);

        removedPath = paths2.remove(index);
        assertThat(removedPath).isEqualTo(path1);

        paths2.add(index, path4);
        assertThat(paths2.get(index)).isEqualTo(path4);

        // if index is equal to tour.getPaths().size()-2
        index = tour.getPaths().size()- 2;
        assertThat(tour.getPaths().get(index).getStart()).isEqualTo(halt2);
        Path path5 = new Path(paths3.get(index).getStart(), warehouse);

        removedPath = paths3.remove(index);
        assertThat(removedPath).isEqualTo(path);

        removedPath = paths3.remove(index);
        assertThat(removedPath).isEqualTo(path1);

        paths3.add(index, path4);
        assertThat(paths3.get(index)).isEqualTo(path4);

        // if index not follow the conditions before
        index = 1;
        Path path6 = new Path(paths.get(index).getStart(),paths.get(index + 2).getStart());

        removedPath = paths.remove(index);
        assertThat(removedPath).isEqualTo(path2);

        removedPath = paths.remove(index);
        assertThat(removedPath).isEqualTo(path3);

        paths.add(index, path6);
        assertThat(paths.get(index)).isEqualTo(path6);


    }

}