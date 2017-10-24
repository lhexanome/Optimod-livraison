package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.platform.models.DemandeLivraison;
import lhexanome.optimodlivraison.platform.models.Intersection;
import lhexanome.optimodlivraison.platform.models.Plan;
import lhexanome.optimodlivraison.platform.models.Tournee;
import lhexanome.optimodlivraison.platform.models.Trajet;
import lhexanome.optimodlivraison.platform.models.Troncon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FackUtile {

    public static Tournee fackTournee(Plan p, DemandeLivraison demandeLivraison, int nbTroncon) {
        Tournee tournee = new Tournee(
                demandeLivraison.getBeginning(),
                demandeLivraison.getStart(),
                500
        );
        // TODO Change to Entrepôt
        List<Trajet> trajets = new ArrayList<>();
        demandeLivraison.getDeliveries().forEach(livraison -> {
            trajets.add(fackTrajet(p, livraison.getIntersection(), nbTroncon));
        });
        trajets.add(fackTrajet(p, demandeLivraison.getBeginning().getIntersection(), nbTroncon));
        tournee.setDeliveries(trajets);
        return tournee;
    }


    public static Trajet fackTrajet(Plan p, Intersection intersection, int iteration) {
        Trajet t = new Trajet();
        Collection<Troncon> tronconsPosible = p.getTronconsFromIntersection(intersection);
        for (int i = 0; i < iteration && !tronconsPosible.isEmpty(); i++) {
            Troncon tr = getRandom(tronconsPosible);
            t.addTroncon(tr);
            tronconsPosible = p.getTronconsFromIntersection(tr.getDestination());
        }
        return t;
    }

    static Random rand = new Random();

    public static <E> E getRandom(Collection<? extends E> coll) {
        if (coll.size() == 0) {
            return null; // or throw IAE, if you prefer
        }

        int index = rand.nextInt(coll.size());
        if (coll instanceof List) { // optimization
            return ((List<? extends E>) coll).get(index);
        } else {
            Iterator<? extends E> iter = coll.iterator();
            for (int i = 0; i < index; i++) {
                iter.next();
            }
            return iter.next();
        }
    }
}
