package lhexanome.optimodlivraison.ui.view;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Tournee;
import lhexanome.optimodlivraison.ui.controller.Controller;
import lhexanome.optimodlivraison.ui.planpanel.PlanViewPanel;

import javax.swing.*;

public class OrderEditorView {
    private Controller controller;
    private JPanel mainPanel;
    private PlanViewPanel planViewPanel;

    public OrderEditorView(Controller controller) {
        this.controller = controller;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public void setPlan(Plan plan) {
        planViewPanel.setPlan(plan);
    }
    public void setDemand(DemandeLivraison demand) {
        planViewPanel.setDemande(demand);
    }
    public void setTournee(Tournee tournee) {
        planViewPanel.setTournee(tournee);
    }
}
