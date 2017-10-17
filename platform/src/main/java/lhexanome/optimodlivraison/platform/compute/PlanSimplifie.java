package lhexanome.optimodlivraison.platform.compute;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Livraison;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Trajet;

import java.util.HashSet;

public class PlanSimplifie {
    protected Plan plan=null;
    protected HashSet<Trajet> arcs=null;
    protected HashSet<Livraison> sommets=null;

    public PlanSimplifie() {
        plan = new Plan();
        arcs = new HashSet<Trajet>();
        sommets = new HashSet<Livraison>();
    }
    public PlanSimplifie(DemandeLivraison demandeLivraison, Plan plan) {
        this.plan = plan;
        arcs = new HashSet<Trajet>();
        sommets = new HashSet<Livraison>();
    }

}
