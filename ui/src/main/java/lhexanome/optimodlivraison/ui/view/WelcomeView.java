package lhexanome.optimodlivraison.ui.view;

import lhexanome.optimodlivraison.ui.FileTypeFilter;
import lhexanome.optimodlivraison.ui.controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WelcomeView {
    private JButton ouvrirUnPlanButton;
    private JPanel mainPanel;
    private Controller controller;

    public WelcomeView(Controller controller) {
        this.controller = controller;

        ouvrirUnPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clickChoosePlan();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
