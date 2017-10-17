package lhexanome.optimodlivraison.ui.planpreview;

import lhexanome.optimodlivraison.platform.models.Plan;
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


    public PlanPreviewView() {
        chargerLivraisonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
