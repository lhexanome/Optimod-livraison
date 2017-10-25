package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.controller.MainController;
import lhexanome.optimodlivraison.ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends Window {

    public MainWindow(MainController controller, JPanel roadMapPanel, JPanel deliveryOrderPanel) {
        super(controller, "Optimod livraison - H4102");

        jFrame.setLayout(new BorderLayout());
        jFrame.add(
                new MainPanel(controller, roadMapPanel, deliveryOrderPanel).getContentPane(),
                BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }
}
