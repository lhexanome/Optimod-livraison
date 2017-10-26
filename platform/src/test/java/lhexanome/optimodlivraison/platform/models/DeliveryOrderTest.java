package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryOrderTest {

    @Test
    void shouldAddDeliverie() {
        //With
        DeliveryOrder deliveriesRequest = new DeliveryOrder();
        Delivery deliveries = new Delivery(
                new Intersection(587L, 689, 754),
                30
        );
        deliveries.setDuration(123);

        //When
        deliveriesRequest.addDelivery(deliveries);

        //Then
        assert (deliveriesRequest.getDeliveries().contains(deliveries));

    }

    @Test
    void shouldSetBeginning() {
        //With
        DeliveryOrder deliveriesRequest = new DeliveryOrder();
        Intersection intersection = new Intersection(10101L, 1254, 1265);
        Warehouse warehouse = new Warehouse(intersection);
        //When
        deliveriesRequest.setBeginning(warehouse);

        //Then
        assertEquals(warehouse, deliveriesRequest.getBeginning());
    }

    @Test
    void shouldSetStart() throws ParseException {
        //With
        DeliveryOrder deliveriesrequest = new DeliveryOrder();
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");

        //When
        deliveriesrequest.setStart(start);

        //Then
        assertEquals(start, deliveriesrequest.getStart());
    }
}
