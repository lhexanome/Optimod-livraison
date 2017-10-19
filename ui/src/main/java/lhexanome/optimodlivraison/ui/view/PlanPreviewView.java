package lhexanome.optimodlivraison.ui.view;

import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.planpanel.PlanViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlanPreviewView {
    private JPanel mainPanel;
    private JButton chargerLivraisonButton;
    private PlanViewPanel planViewPanel;
    private JButton ouvrirUneDemandeButton;

    private Controller controller;

    public PlanPreviewView(Controller controller) {

        this.controller = controller;

        chargerLivraisonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clickChooseDemand();
            }
        });
    }

    public Component getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        planViewPanel = new PlanViewPanel();
    }

    public void setPlan(Plan plan) {
        planViewPanel.setPlan(plan);
    }
}
