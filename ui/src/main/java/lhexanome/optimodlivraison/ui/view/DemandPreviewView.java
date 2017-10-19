package lhexanome.optimodlivraison.ui.view;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.planpanel.PlanViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemandPreviewView {
    private final Controller controller;
    private JButton calculerTourneeButton;
    private JPanel mainPanel;
    private PlanViewPanel planViewPanel;

    public DemandPreviewView(Controller controller){
        this.controller = controller;

        calculerTourneeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clickComputeTour();
            }
        });
    }

    public Component getMainPanel() {
        return mainPanel;
    }

    public void setPlan(Plan plan) {
        planViewPanel.setPlan(plan);
    }
    public void setDemand(DemandeLivraison demand) {
        planViewPanel.setDemande(demand);
    }
}
