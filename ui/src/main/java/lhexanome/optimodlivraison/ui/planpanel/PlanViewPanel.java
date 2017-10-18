package lhexanome.optimodlivraison.ui.planpanel;


import lhexanome.optimodlivraison.platform.models.*;
import lhexanome.optimodlivraison.ui.planpreview.FackUtile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;
import java.util.function.Consumer;

public class PlanViewPanel extends JPanel {

    private Plan plan;
    private DemandeLivraison demande;
    private Tournee tournee;

    //private float scalX=0.05f, scalY=0.05f, offsetX=-763, offsetY=-1096;
    private float scalX=0.0189f, scalY=0.0189f, offsetX=-1100, offsetY=-1620;

    private boolean moove = false;

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);

            // TODO replace planTemp by plan.[getData]()
            if(plan != null) {
                g.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
                plan.getTroncons().forEach((troncon) -> paintComponent(g, troncon));
            }
            if(tournee != null){
                g.setColor(Color.GREEN);
                g2.setStroke(new BasicStroke(2));
                tournee.getDeliveries().forEach(trajet -> trajet.getListOfTroncon().forEach(troncon -> paintComponent(g, troncon)));
            }
            if(demande != null){
                g.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(3));
                demande.getDeliveries().forEach(livraison -> paintComponent(g, livraison.getIntersection()));
                g.setColor(Color.RED);
                paintComponent(g, demande.getBeginning());
            }
    }

    protected void paintComponent(Graphics g, Intersection intersection){

        float x = this.offsetX + getSize().width / 2 + intersection.getX() * scalX;
        float y = this.offsetY + getSize().height / 2 + intersection.getY() * scalY;

        g.drawOval((int)x-2,(int)y-2,4,4);

    }
    protected void paintComponent(Graphics g, Troncon troncon){

        Intersection origine = troncon.getOrigine();
        Intersection destination = troncon.getDestination();
        float offsetX = this.offsetX + getSize().width / 2;
        float offsetY = this.offsetY + getSize().height / 2;
        g.drawLine(
                (int) (origine.getX() * scalX + offsetX),
                (int) (origine.getY() * scalY + offsetY),
                (int) (destination.getX() * scalX + offsetX),
                (int) (destination.getY() * scalY + offsetY)
            );
        System.out.println(""+(int) (origine.getX() * scalX + offsetX)+" : "+
                (int) (origine.getY() * scalY + offsetY)+"      "+
                (int) (destination.getX() * scalX + offsetX)+" : "+
                (int) (destination.getY() * scalY + offsetY));
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

    public void setPlan(Plan plan) {
        if(plan != null){
            //TODO remove wacher
        }

        this.plan = plan;

        if(plan.getIntersectionCount()>0) {
            Point min = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
            Point max = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
            plan.getIntersections().forEach(intersection -> {
                if (min.x > intersection.getX()) min.x = intersection.getX();
                if (min.y > intersection.getY()) min.y = intersection.getY();
                if (max.x < intersection.getX()) max.x = intersection.getX();
                if (max.y < intersection.getY()) max.y = intersection.getY();
            });
            float scal = Math.min(
                    1080f / (max.x-min.x),
                    720f / (max.y-min.y)
            );

            scalX = scal;
            scalY = scal;
            offsetX = ((min.x-max.x)/2 - min.x) * scal;
            offsetY = ((min.y-max.y)/2 - min.y) * scal;


        }
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
