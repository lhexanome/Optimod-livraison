package lhexanome.optimodlivraison.ui.planpanel;


import lhexanome.optimodlivraison.platform.models.*;
import lhexanome.optimodlivraison.ui.planpreview.FackUtile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

public class PlanViewPanel extends JPanel {

    private Plan plan;
    private DemandeLivraison demande;
    private Tournee tournee;

    //private float scalX=0.05f, scalY=0.05f, offsetX=-763, offsetY=-1096;
    private float scalX=0.0189f, scalY=0.0189f, offsetX=-1100, offsetY=-1620;

    private boolean moove = false;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

            // TODO replace planTemp by plan.[getData]()
            getPlan(plan).forEach((intersection, troncon) -> paintComponent(g, troncon));
    }

    // TODO REMOVE THIS!!!!!!!!
    private Map<Intersection, Troncon> getPlan(Plan plan) {
        return FackUtile.fackPlanDataMoyen();
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
