package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.ui.controller.actions.ControllerInterface;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class Window implements WindowListener {

    protected ControllerInterface controller;
    protected JFrame jFrame;

    public ControllerInterface getController() {
        return controller;
    }

    public Window(ControllerInterface c, String title) {
        controller = c;
        jFrame = new JFrame(title);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void open() {
        jFrame.setVisible(true);
    }

    public void close() {
        jFrame.setVisible(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        controller.closeWindow();
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

    public JFrame getFrame() {
        return jFrame;
    }
}
