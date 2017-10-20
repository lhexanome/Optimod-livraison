package lhexanome.optimodlivraison.ui.planpanel;


import lhexanome.optimodlivraison.platform.models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlanViewPanel extends JPanel {

    public static final String RESOURCENAME_PLAN_MARKER_RED = "/plan/marker/planMarkerRed.png";
    public static final String RESOURCENAME_PLAN_MARKER_ORANGE = "/plan/marker/planMarkerOrange.png";
    public static final String RESOURCENAME_PLAN_COMPASS = "/plan/compass/compass.png";

    public static final int MARKER_RED_OFFSET_X = -32;
    public static final int MARKER_RED_OFFSET_Y = -64;
    public static final int MARKER_ORANGE_OFFSET_X = -32;
    public static final int MARKER_ORANGE_OFFSET_Y = -64;
    public static final int COMPASS_OFFSET_X = 20;
    public static final int COMPASS_OFFSET_Y = 20;

    private Plan plan;
    private DemandeLivraison demande;
    private Tournee tournee;

    private BufferedImage markerRed;
    private BufferedImage markerOrange;
    private BufferedImage compass;

    private float scalX = 1f, scalY = 1f, offsetX = 0, offsetY = 0;

    private boolean moove = false;

    public PlanViewPanel(){
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

        reSacle();

        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);

        g2.drawImage(compass, COMPASS_OFFSET_X ,COMPASS_OFFSET_Y, null);


        if(plan != null) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            plan.getTroncons().forEach((troncon) -> paintComponent(g2, troncon));
        }
        if(tournee != null){
            g2.setColor(new Color(245,124,0));
            g2.setStroke(new BasicStroke(2));
            tournee.getDeliveries().forEach(trajet -> trajet.getTroncons().forEach(troncon -> paintComponent(g2, troncon)));
        }
        if(demande != null){
            paintComponent(g2, demande);
        }
    }

    protected void paintComponent(Graphics2D g2, DemandeLivraison demande){

        Entrepot entrepot = demande.getBeginning();

        int x = (int) (this.offsetY + getSize().width / 2 + entrepot.getIntersection().getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 - entrepot.getIntersection().getX() * scalX);

        g2.drawImage(markerRed, x + MARKER_RED_OFFSET_X,y + MARKER_RED_OFFSET_Y, null);


        demande.getDeliveries().forEach((livraison) ->paintComponent(g2,livraison));

    }
    
    protected void paintComponent(Graphics2D g2, Livraison livraison){

        Intersection intersection = livraison.getIntersection();

        int x = (int) (this.offsetY + getSize().width / 2 + intersection.getY() * scalY);
        int y = (int) (-this.offsetX + getSize().height / 2 + - intersection.getX() * scalX);

        g2.drawImage(markerOrange, x + MARKER_ORANGE_OFFSET_X,y + MARKER_ORANGE_OFFSET_Y, null);


    }

    protected void paintComponent(Graphics2D g2, Troncon troncon){

        Intersection origine = troncon.getOrigine();
        Intersection destination = troncon.getDestination();
        float offsetX = this.offsetX + getSize().width / 2;
        float offsetY = -this.offsetY + getSize().height / 2;
        g2.drawLine(
                (int) (origine.getY() * scalY + offsetX),
                (int) (-origine.getX() * scalX + offsetY),
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

    private Rectangle getPlanSize(Plan p){

        Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        plan.getIntersections().forEach(intersection -> {
            if (min.x > intersection.getX()) min.x = intersection.getX();
            if (min.y > intersection.getY()) min.y = intersection.getY();
            if (max.x < intersection.getX()) max.x = intersection.getX();
            if (max.y < intersection.getY()) max.y = intersection.getY();
        });
        return new Rectangle(min.x,min.y,max.x-min.x,max.y-min.y);
    }

    private void reSacle(){
        Rectangle recPlan = getPlanSize(this.plan);

        float windowsSize = Math.min(getWidth(),getHeight());

        scalX = windowsSize / (recPlan.width);
        scalY = windowsSize / (recPlan.height);
        offsetX = (recPlan.width/2 - recPlan.x - recPlan.width) * scalX;
        offsetY = (recPlan.height/2 - recPlan.y - recPlan.height) * scalY;
    }


    public void setPlan(Plan plan) {
        if(plan != null){
            //TODO remove wacher
        }

        this.plan = plan;
        //TODO add wacher
    }

    public void setDemande(DemandeLivraison demande) {
        if(demande != null){
            //TODO remove wacher
        }

        this.demande = demande;

        //TODO add wacher
    }

    public void setTournee(Tournee tournee) {
        if(tournee != null){
            //TODO remove wacher
        }

        this.tournee = tournee;

        //TODO add wacher
    }
}
