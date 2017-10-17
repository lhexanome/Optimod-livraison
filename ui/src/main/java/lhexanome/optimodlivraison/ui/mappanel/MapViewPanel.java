package lhexanome.optimodlivraison.ui.mappanel;


import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Troncon;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

class MapViewPanel extends JPanel {

    private Plan plan; private Map<Intersection, Troncon> planTemp;// TODO remove planTemp
    //private float scalX=0.05f, scalY=0.05f, offsetX=-763, offsetY=-1096;
    private float scalX=0.0189f, scalY=0.0189f, offsetX=-757, offsetY=-1079;

    //TODO replace MapViewPanel(Map<Intersection, Troncon> planTemp) by MapViewPanel(Plan plan)
    public MapViewPanel(Map<Intersection, Troncon> planTemp){
        this.planTemp = planTemp;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO replace planTemp by plan.[getData]()
        planTemp.forEach((intersection, troncon) -> paintComponent(g,troncon));
    };

    protected void paintComponent(Graphics g, Troncon troncon){
        Intersection origine = troncon.getOrigine();
        Intersection destination = troncon.getDestination();
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
}
