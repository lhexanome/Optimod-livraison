package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.controller.MainController;
import lhexanome.optimodlivraison.ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main window.
 */
public class MainWindow extends Window {

    /**
     * Constructor.
     * Take the main controller and all the panel needed for displaying.
     *
     * @param controller                     Main controller
     * @param roadMapPanel                   Panel of the roadmap
     * @param deliveryOrderPanel             Panel of the delivery order
     * @param tourPanel                      Panel of the tour
     * @param tourEditorPanel                Panel for the tour editor
     * @param currentInterectionDisplayPanel current interection display panel
     */
    public MainWindow(MainController controller,
                      JPanel roadMapPanel,
                      JPanel deliveryOrderPanel,
                      JPanel tourPanel,
                      JPanel tourEditorPanel,
                      JPanel currentInterectionDisplayPanel) {
        super(controller, "Optimod livraison - H4102");

        MainPanel mainPanel = new MainPanel(
                controller,
                roadMapPanel,
                deliveryOrderPanel,
                tourPanel,
                tourEditorPanel,
                currentInterectionDisplayPanel
        );

        jFrame.setLayout(new BorderLayout());
        jFrame.add(mainPanel.getContentPane(), BorderLayout.CENTER);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }
}
