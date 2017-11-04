package lhexanome.optimodlivraison.ui.component;

import lhexanome.optimodlivraison.platform.models.Delivery;
import lhexanome.optimodlivraison.platform.models.DeliveryOrder;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.RoadMap;
import lhexanome.optimodlivraison.platform.models.Tour;
import lhexanome.optimodlivraison.platform.models.Vector;
import lhexanome.optimodlivraison.platform.models.Warehouse;
import lhexanome.optimodlivraison.ui.controller.RoadMapController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RoadMap swing component.
 */
public class RoadMapComponent extends JComponent implements MouseListener {

    /**
     * A distance greater than any other one during execution.
     */
    private static final double MAX_DISTANCE = Double.MAX_VALUE;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RoadMapComponent.class.getName());

    /**
     * X offset marker red.
     */
    public static final int MARKER_OFFSET_X = -16;
    /**
     * Y offset marker red.
     */
    public static final int MARKER_OFFSET_Y = -32;
    /**
     * X offset compass.
     */
    public static final int COMPASS_OFFSET_X = 20;
    /**
     * Y offset compass.
     */
    public static final int COMPASS_OFFSET_Y = 20;
    /**
     * Red marker for the warehouse.
     */
    private static final String RESOURCE_NAME_PLAN_MARKER_RED = "/plan/marker/planMarkerRed.png";
    /**
     * Orange marker for deliveries.
     */
    private static final String RESOURCE_NAME_PLAN_MARKER_ORANGE = "/plan/marker/planMarkerOrange.png";
    /**
     * Green marker for deliveries.
     */
    private static final String RESOURCE_NAME_PLAN_MARKER_GREEN = "/plan/marker/planMarkerGreen.png";
    /**
     * Compass image.
     */
    private static final String RESOURCE_NAME_PLAN_COMPASS = "/plan/compass/compass.png";
    /**
     * Tour vector color.
     */
    private static final Color TOUR_VECTOR_COLOR = new Color(245, 124, 0);

    /**
     * Marker red image.
     */
    private BufferedImage markerRed;

    /**
     * Marker orange image.
     */
    private BufferedImage markerOrange;

    /**
     * Marker green image.
     */
    private BufferedImage markerGreen;

    /**
     * Compass image.
     */
    private BufferedImage compass;


    /**
     * Offset values.
     */
    private float scalX = 1f, scalY = 1f, offsetX = 0, offsetY = 0;

    /**
     * RoadMap to display.
     */
    private RoadMap roadMap;

    /**
     * Delivery order to display.
     */
    private DeliveryOrder deliveryOrder;

    /**
     * Tour to display.
     */
    private Tour tour;

    /**
     * RoadMap constructor.
     * Load images from jar file.
     */
    private static final double IMAGE_SCALE = 0.5;

    /**
     * RoadMap controller.
     */
    private RoadMapController roadMapController;

    /**
     * Constructor.
     *
     * @param roadMapController the roadMapController you assign to the RoadMapComponent.
     */
    public RoadMapComponent(RoadMapController roadMapController) {
        super();
        try {
            this.roadMapController = roadMapController;
            markerRed = scaleImage(
                    ImageIO.read(getClass().getResource(RESOURCE_NAME_PLAN_MARKER_RED)),
                    IMAGE_SCALE
            );
            markerOrange = scaleImage(
                    ImageIO.read(getClass().getResource(RESOURCE_NAME_PLAN_MARKER_ORANGE)),
                    IMAGE_SCALE
            );
            markerGreen = scaleImage(
                    ImageIO.read(getClass().getResource(RESOURCE_NAME_PLAN_MARKER_GREEN)),
                    IMAGE_SCALE
            );
            compass = scaleImage(
                    ImageIO.read(getClass().getResource(RESOURCE_NAME_PLAN_COMPASS)),
                    IMAGE_SCALE
            );
            addMouseListener(this);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while getting resources", e);
            System.exit(1);
        }
    }

    /**
     * resizes an image.
     *
     * @param markerBefore the image you want to resize.
     * @param scale scale
     * @return the resized image.
     */
    private BufferedImage scaleImage(BufferedImage markerBefore, double scale) {
        int w = markerBefore.getWidth();
        int h = markerBefore.getHeight();
        BufferedImage marker = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(markerBefore, marker);
    }

    /**
     * Compute the scale coefficients to use with the size of the component.
     */
    private void rescale() {
        Rectangle recPlan = getMapSize(this.roadMap);

        float windowsSize = Math.min(getWidth(), getHeight());

        scalX = windowsSize / (recPlan.width);
        scalY = windowsSize / (recPlan.height);
        offsetX = (recPlan.width / 2 - recPlan.x - recPlan.width) * scalX;
        offsetY = (recPlan.height / 2 - recPlan.y - recPlan.height) * scalY;
    }

    /**
     * Return a rectangle of the size of the map.
     *
     * @param map RoadMap
     * @return Rectangle with the size of the map
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private Rectangle getMapSize(RoadMap map) {
        if (map == null) {
            return new Rectangle(0, 0, 400, 400);
        }

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

    /**
     * @return Minimum dimension
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public Dimension getMinimumSize() {
        return new Dimension(400, 400);
    }

    /**
     * @return Preferred dimension
     */
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    /**
     * @return the roadmap
     */
    public RoadMap getRoadMap() {
        return roadMap;
    }

    /**
     * Called by swing, repaint all the component.
     * <p>
     * Before painting data, we paint some GUI elements.
     * First we paint the RoadMap, then the tour and finally the delivery order.
     *
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        rescale();

        Graphics2D g2 = (Graphics2D) g;

        // FIXME Paint gui after data ?
        paintGUI(g2);

        if (roadMap != null) {
            paintComponent(g2, roadMap);
        }
        if (tour != null) {
            paintComponent(g2, tour);
        }
        if (deliveryOrder != null) {
            paintComponent(g2, deliveryOrder);
        }
        if (roadMapController.getSelectedIntersection() != null) {
            paintComponent(g2, roadMapController.getSelectedIntersection());
        }
    }

    /**
     * This function draw a compass in the top left corner.
     *
     * @param g2 Graphics
     */
    private void paintGUI(Graphics2D g2) {
        g2.drawImage(compass, COMPASS_OFFSET_X, COMPASS_OFFSET_Y, null);
    }


    /**
     * Draw a map.
     *
     * @param g2  Graphics
     * @param map RoadMap
     */
    private void paintComponent(Graphics2D g2, RoadMap map) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        // TODO Set stroke with the zoom level
        map.getVectors().forEach(vector -> paintComponent(g2, vector));
    }

    /**
     * Draw a tour.
     *
     * @param g2         Graphics
     * @param tourToDraw Tour
     */
    private void paintComponent(Graphics2D g2, Tour tourToDraw) {

        g2.setColor(TOUR_VECTOR_COLOR);
        g2.setStroke(new BasicStroke(2));
        // TODO Set stroke with the zoom level
        tourToDraw.getPaths().forEach(path ->
                path.getVectors().forEach(vector -> paintComponent(g2, vector))
        );
    }

    /**
     * Draw a delivery order.
     *
     * @param g2    Graphics
     * @param order DeliveryOrder
     */
    private void paintComponent(Graphics2D g2, DeliveryOrder order) {
        Warehouse warehouse = order.getBeginning();

        int x = (int) (this.offsetY + getSize().width / 2 + warehouse.getIntersection().getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 - warehouse.getIntersection().getX() * scalX);

        g2.drawImage(markerRed, x + MARKER_OFFSET_X, y + MARKER_OFFSET_Y, null);

        order.getDeliveries().forEach(delivery -> paintComponent(g2, delivery));
    }

    /**
     * Draw a delivery.
     *
     * @param g2       Graphics
     * @param delivery Delivery
     */
    private void paintComponent(Graphics2D g2, Delivery delivery) {
        Intersection intersection = delivery.getIntersection();

        int x = (int) (this.offsetY + getSize().width / 2 + intersection.getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 + -intersection.getX() * scalX);

        g2.drawImage(markerOrange, x + MARKER_OFFSET_X, y + MARKER_OFFSET_Y, null);
    }

    /**
     * Draw a vector.
     *
     * @param g2     Graphics
     * @param vector Vector
     */
    private void paintComponent(Graphics2D g2, Vector vector) {
        Intersection origin = vector.getOrigin();
        Intersection destination = vector.getDestination();
        g2.drawLine(
                getXFromIntersection(origin),
                getYFromIntersection(origin),
                getXFromIntersection(destination),
                getYFromIntersection(destination)
        );
    }

    /**
     * Draw the current intersection.
     *
     * @param g2           Graphics
     * @param intersection Vector
     */
    private void paintComponent(Graphics2D g2, Intersection intersection) {
        int x = (int) (this.offsetY + getSize().width / 2 + intersection.getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 + -intersection.getX() * scalX);
        g2.drawImage(markerGreen, x + MARKER_OFFSET_X, y + MARKER_OFFSET_Y, null);
    }

    /**
     * Set the road map.
     * Remove old watcher if present and add a new one.
     *
     * @param map RoadMap
     */
    public void setRoadMap(RoadMap map) {
       /* if (map != null) {
            //TODO remove wacher
        }*/

        this.roadMap = map;
        this.tour = null;
        this.deliveryOrder = null;
        repaint();
        //TODO add wacher
    }

    /**
     * Set the delivery order.
     * Remove old watcher if present and add a new one.
     *
     * @param deliveryOrder DeliveryOrder
     */
    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
       /* if (deliveryOrder != null) {
            //TODO remove wacher
        }*/

        this.deliveryOrder = deliveryOrder;
        this.tour = null;

        repaint();
        //TODO add wacher
    }

    /**
     * Set the tour.
     * Remove old watcher if present and add a new one.
     *
     * @param tour Tour
     */
    public void setTour(Tour tour) {
        /*if (tour != null) {
            //TODO remove wacher
        }*/

        this.tour = tour;
        repaint();

        //TODO add wacher
    }

    /**
     * updates the current selected intersection.
     *
     * @param mouseEvent Mouse event
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int xMouse = mouseEvent.getX();
        int yMouse = mouseEvent.getY();
        Intersection intersection = getClosestIntersection(xMouse, yMouse);
        roadMapController.onIntersectionSelected(intersection);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * Return the euclidean distance between two point.
     *
     * @param x1 x1.
     * @param y1 y1.
     * @param x2 x2.
     * @param y2 y2.
     * @return the euclidean distance between two points A(x1, y1) and B(x2, y2).
     */
    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Return the screen x from an intersection.
     *
     * @param intersection an intersection
     * @return the x coordinate on the screen
     */
    private int getXFromIntersection(Intersection intersection) {
        float vOffsetX = this.offsetX + getSize().width / 2;
        return (int) (intersection.getY() * scalY + vOffsetX);
    }

    /**
     * Return the screen y from an intersection.
     *
     * @param intersection an intersection
     * @return the y coordinate on the screen
     */
    private int getYFromIntersection(Intersection intersection) {
        float vOffsetY = -this.offsetY + getSize().height / 2;
        return (int) (-intersection.getX() * scalX + vOffsetY);
    }

    /**
     * Return the closest intersection.
     *
     * @param xMouse the x coordinate of the mouse on the screen.
     * @param yMouse the y coordinate of the mouse on the screen.
     * @return the closest intersection relatively to the mouse position (vous avez compris ? :p)
     */
    private Intersection getClosestIntersection(int xMouse, int yMouse) {
        double minimalDistance = MAX_DISTANCE;
        Intersection closestIntersection = null;
        Collection<Intersection> intersections = roadMap.getIntersections();
        for (Intersection intersection : intersections) {
            int xIntersection = getXFromIntersection(intersection);
            int yIntersection = getYFromIntersection(intersection);
            // TODO use a common distance function
            double distanceIntersectionToMouse =
                    distance(xIntersection, yIntersection, xMouse, yMouse);
            if (distanceIntersectionToMouse <= minimalDistance) {
                minimalDistance = distanceIntersectionToMouse;
                closestIntersection = intersection;
            }
        }
        return closestIntersection;
    }
}
