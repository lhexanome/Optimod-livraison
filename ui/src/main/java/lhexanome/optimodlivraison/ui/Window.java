package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.ui.controller.Controller;

import javax.swing.*;

public abstract class Window {

    protected Controller controller;
    protected JFrame jFrame;

    public Controller getController(){
        return controller;
    }

    public Window(Controller c){
        controller = c;
        jFrame = new JFrame("testHelloMars");

    }

    public void open(){
        jFrame.setVisible(true);
    }

    public void close(){
        jFrame.setVisible(false);
    }
}
