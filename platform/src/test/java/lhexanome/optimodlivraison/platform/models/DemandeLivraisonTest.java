package lhexanome.optimodlivraison.platform.models;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DemandeLivraisonTest {

    @Test
    void shouldAddDeliverie(){
        //With
        DemandeLivraison deliveriesRequest = new DemandeLivraison();
        Livraison deliveries = new Livraison();
        deliveries.setDuration(123);

        //When
        deliveriesRequest.addDelivery(deliveries);

        //Then
        assert(deliveriesRequest.getDeliveries().contains(deliveries));

    }

    @Test
    void shouldSetBeginning(){
        //With
        DemandeLivraison deliveriesRequest = new DemandeLivraison();
        Intersection intersection = new Intersection(10101L,1254,1265);

        //When
        deliveriesRequest.setBeginning(intersection);

        //Then
        assertEquals(intersection,deliveriesRequest.getBeginning());
    }

    @Test
    void shouldSetStart() throws ParseException {
        //With
        DemandeLivraison deliveriesrequest = new DemandeLivraison();
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = dateFormat.parse("15-02-17 12:55");

        //When
        deliveriesrequest.setStart(start);

        //Then
        assertEquals(start,deliveriesrequest.getStart());
    }
}
