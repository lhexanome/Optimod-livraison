package lhexanome.optimodlivraison.ui.orderaction;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Tournee;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;

public class OrderEditorWindow extends Window{

    OrderEditorView orderEditorView;

    public OrderEditorWindow(Controller c) {
        super(c, "title");

        orderEditorView= new OrderEditorView(controller);

        jFrame.add(orderEditorView.getMainPanel());

        //jFrame.pack();
        jFrame.setSize(1080,720);
        jFrame.setLocationRelativeTo(null);
    }

    public void setPlan(Plan plan) {
        this.orderEditorView.setPlan(plan);
    }
    public void setDemand(DemandeLivraison demand) {
        this.orderEditorView.setDemand(demand);
    }
    public void setTournee(Tournee tournee) {
        this.orderEditorView.setTournee(tournee);
    }
}
