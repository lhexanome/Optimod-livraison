package lhexanome.optimodlivraison.ui.window;

import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.actions.MainControllerInterface;
import lhexanome.optimodlivraison.ui.panel.MainPanel;

import javax.swing.*;

public class MainWindow extends Window {

    public MainWindow(MainControllerInterface controller, JPanel roadMapPanel) {
        super(controller, "Optimod livraison - H4102");

        jFrame.setContentPane(new MainPanel(controller, roadMapPanel).getContentPane());
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
    }
}
