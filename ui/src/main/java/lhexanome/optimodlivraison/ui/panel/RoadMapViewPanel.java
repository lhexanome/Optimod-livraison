package lhexanome.optimodlivraison.ui.panel;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoadMapViewPanel extends JPanel {

    public static final String RESOURCENAME_PLAN_MARKER_RED = "/plan/marker/planMarkerRed.png";
    public static final String RESOURCENAME_PLAN_MARKER_ORANGE = "/plan/marker/planMarkerOrange.png";
    public static final String RESOURCENAME_PLAN_COMPASS = "/plan/compass/compass.png";

    public static final int MARKER_RED_OFFSET_X = -32;
    public static final int MARKER_RED_OFFSET_Y = -64;
    public static final int MARKER_ORANGE_OFFSET_X = -32;
    public static final int MARKER_ORANGE_OFFSET_Y = -64;
    public static final int COMPASS_OFFSET_X = 20;
    public static final int COMPASS_OFFSET_Y = 20;

    private RoadMap roadMap;
    private DeliveryOrder deliveryOrder;
    private Tour tour;

    private BufferedImage markerRed;
    private BufferedImage markerOrange;
    private BufferedImage compass;

    private float scalX = 1f, scalY = 1f, offsetX = 0, offsetY = 0;

    private boolean moove = false;

    public RoadMapViewPanel() {
        super();
        try {
            markerRed = ImageIO.read(getClass().getResource(RESOURCENAME_PLAN_MARKER_RED));
            markerOrange = ImageIO.read(getClass().getResource(RESOURCENAME_PLAN_MARKER_ORANGE));
            compass = ImageIO.read(getClass().getResource(RESOURCENAME_PLAN_COMPASS));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected void paintComponent(Graphics g) {

        reScale();

        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);

        g2.drawImage(compass, COMPASS_OFFSET_X, COMPASS_OFFSET_Y, null);


        if (roadMap != null) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            roadMap.getVectors().forEach(vector -> paintComponent(g2, vector));
        }
        if (tour != null) {
            g2.setColor(new Color(245, 124, 0));
            g2.setStroke(new BasicStroke(2));
            tour.getDeliveries().forEach(path -> path.getVectors().forEach(vector -> paintComponent(g2, vector)));
        }
        if (deliveryOrder != null) {
            paintComponent(g2, deliveryOrder);
        }
    }

    protected void paintComponent(Graphics2D g2, DeliveryOrder order) {

        Warehouse warehouse = order.getBeginning();

        int x = (int) (this.offsetY + getSize().width / 2 + warehouse.getIntersection().getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 - warehouse.getIntersection().getX() * scalX);

        g2.drawImage(markerRed, x + MARKER_RED_OFFSET_X, y + MARKER_RED_OFFSET_Y, null);


        order.getDeliveries().forEach(delivery -> paintComponent(g2, delivery));

    }

    protected void paintComponent(Graphics2D g2, Delivery delivery) {

        Intersection intersection = delivery.getIntersection();

        int x = (int) (this.offsetY + getSize().width / 2 + intersection.getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 + -intersection.getX() * scalX);

        g2.drawImage(markerOrange, x + MARKER_ORANGE_OFFSET_X, y + MARKER_ORANGE_OFFSET_Y, null);


    }

    protected void paintComponent(Graphics2D g2, Vector vector) {

        Intersection origin = vector.getOrigin();
        Intersection destination = vector.getDestination();
        float offsetX = this.offsetX + getSize().width / 2;
        float offsetY = -this.offsetY + getSize().height / 2;
        g2.drawLine(
                (int) (origin.getY() * scalY + offsetX),
                (int) (-origin.getX() * scalX + offsetY),
                (int) (destination.getY() * scalY + offsetX),
                (int) (-destination.getX() * scalX + offsetY)
        );
    }

    public float getScalX() {
        return scalX;
    }

    public void setScalX(float scalX) {
        this.scalX = scalX;
    }

    public float getScalY() {
        return scalY;
    }

    public void setScalY(float scalY) {
        this.scalY = scalY;
    }

    public void setScal(float scalX, float scalY) {
        this.scalX = scalX;
        this.scalY = scalY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffset(float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    private Rectangle getPlanSize(RoadMap map) {

        Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        map.getIntersections().forEach(intersection -> {
            if (min.x > intersection.getX()) min.x = intersection.getX();
            if (min.y > intersection.getY()) min.y = intersection.getY();
            if (max.x < intersection.getX()) max.x = intersection.getX();
            if (max.y < intersection.getY()) max.y = intersection.getY();
        });
        return new Rectangle(min.x, min.y, max.x - min.x, max.y - min.y);
    }

    private void reScale() {
        Rectangle recPlan = getPlanSize(this.roadMap);

        float windowsSize = Math.min(getWidth(), getHeight());

        scalX = windowsSize / (recPlan.width);
        scalY = windowsSize / (recPlan.height);
        offsetX = (recPlan.width / 2 - recPlan.x - recPlan.width) * scalX;
        offsetY = (recPlan.height / 2 - recPlan.y - recPlan.height) * scalY;
    }


    public void setRoadMap(RoadMap map) {
        if (map != null) {
            //TODO remove wacher
        }

        this.roadMap = map;
        //TODO add wacher
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        if (deliveryOrder != null) {
            //TODO remove wacher
        }

        this.deliveryOrder = deliveryOrder;

        //TODO add wacher
    }

    public void setTour(Tour tour) {
        if (tour != null) {
            //TODO remove wacher
        }

        this.tour = tour;

        //TODO add wacher
    }
}
