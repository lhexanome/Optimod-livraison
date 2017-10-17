package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.ui.controller.Controller;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class Window implements WindowListener {

    protected Controller controller;
    protected JFrame jFrame;

    public Controller getController(){
        return controller;
    }

    public Window(Controller c, String title){
        controller = c;
        jFrame = new JFrame(title);

    }

    public void open(){
        jFrame.setVisible(true);
    }

    public void close(){
        jFrame.setVisible(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        controller.closeWondow();
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
