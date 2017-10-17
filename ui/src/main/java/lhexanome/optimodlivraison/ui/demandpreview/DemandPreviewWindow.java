package lhexanome.optimodlivraison.ui.demandpreview;
import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.ui.Window;
import lhexanome.optimodlivraison.ui.controller.Controller;

public class DemandPreviewWindow extends Window{

    private DemandPreviewView demandPreviewView;

    public DemandPreviewWindow(Controller c) {
        super(c, "testHelloMars");
        demandPreviewView = new DemandPreviewView(controller);

        jFrame.add(demandPreviewView.getMainPanel());

        //jFrame.pack();
        jFrame.setSize(1080,720);
        jFrame.setLocationRelativeTo(null);
    }

    public void setPlan(Plan plan) {
        this.demandPreviewView.setPlan(plan);
    }
    public void setDemand(DemandeLivraison demand) {
        this.demandPreviewView.setDemand(demand);
    }

}
