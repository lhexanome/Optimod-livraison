package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.Halt;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Vector;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntersectionWrapperTest {
    @Test
    void constructor() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);

        //When
        //Then
        assertThat(i.getWrapper()).isEqualTo(intersectionWrapper);

    }

    @Test
    void setAsStart() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);
        //When
        intersectionWrapper.setAsStart();
        //Then
        assertThat(intersectionWrapper.getDijkstraTime()).isEqualTo(0);
        assertThat(intersectionWrapper.isBlack()).isEqualTo(true);
    }

    @Test
    void setIntersection() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);
        Intersection i2 = new Intersection(1l);
        //When
        intersectionWrapper.setIntersection(i2);
        //Then
        assertThat(intersectionWrapper.getIntersection()).isEqualTo(i2);
        assertThat(i.getWrapper()).isEqualTo(null);
        assertThat(i2.getWrapper()).isEqualTo(intersectionWrapper);

    }

    @Test
    void setTempsDijkstra() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);
        float temps = 1;
        //When
        intersectionWrapper.setDijkstraTime(temps);
        //Then
        assertThat(intersectionWrapper.getDijkstraTime()).isEqualTo(temps);
    }

    @Test
    void setBlack() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);
        boolean b = true;
        //When
        intersectionWrapper.setBlack(b);
        //Then
        assertThat(intersectionWrapper.isBlack()).isEqualTo(b);
    }

    @Test
    void setPredecesseur() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);
        Intersection i2 = new Intersection(1l);
        IntersectionWrapper pred = new IntersectionWrapper(i2, 0);
        //When
        intersectionWrapper.setPredecessor(pred);
        //Then
        assertThat(intersectionWrapper.getPredecessor()).isEqualTo(pred);
    }


    @Test
    void setCheminArrivant() {
        //With
        Intersection i = new Intersection(0l);
        Halt s = new Halt(i);
        IntersectionWrapper intersectionWrapper = new IntersectionWrapper(i, 0);
        Intersection i2 = new Intersection(1l);

        Vector v = new Vector(i2, i, "test");
        //When
        intersectionWrapper.setIncomingVector(v);
        //Then
        assertThat(intersectionWrapper.getIncomingVector()).isEqualTo(v);
    }

}