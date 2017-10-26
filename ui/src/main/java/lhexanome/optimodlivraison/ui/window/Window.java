package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.controller.ControllerInterface;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Abstract class to launch a Swing Window.
 */
public abstract class Window implements WindowListener {

    /**
     * Controller of the window.
     */
    protected final ControllerInterface controller;

    /**
     * Swing window.
     */
    protected final JFrame jFrame;

    /**
     * Constructor.
     * Take a controller and a title for the window.
     *
     * @param c     Controller
     * @param title Window's title
     */
    Window(ControllerInterface c, String title) {
        controller = c;
        jFrame = new JFrame(title);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Controller getter.
     *
     * @return Controller
     */
    public ControllerInterface getController() {
        return controller;
    }

    /**
     * Display the swing window.
     */
    public void open() {
        jFrame.setVisible(true);
    }

    /**
     * Hide the swing window.
     */
    public void close() {
        jFrame.setVisible(false);
    }

    /**
     * Event on window closing.
     *
     * @param e Event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        controller.closeWindow();
    }

    /**
     * Event on window opening.
     *
     * @param e Event
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Event when the window is closed.
     *
     * @param e Event
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }

    /**
     * Event when an icon is set.
     *
     * @param e Event
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * Event when an icon is unset.
     *
     * @param e Event
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * Event when a window is activated.
     *
     * @param e Event
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * Event when a window is deactivated.
     *
     * @param e Event
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    /**
     * Return the frame.
     *
     * @return Frame
     */
    public JFrame getFrame() {
        return jFrame;
    }
}
