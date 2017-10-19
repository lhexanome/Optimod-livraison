package lhexanome.optimodlivraison.ui;

import lhexanome.optimodlivraison.platform.models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FackUtile {

    public static Tournee fackTournee(Plan p, DemandeLivraison demandeLivraison, int nbTroncon) {
        Tournee tournee = new Tournee(
                demandeLivraison.getBeginning(),
                demandeLivraison.getStart(),
                500
        );
        List<Trajet> trajets =new ArrayList<>();
        demandeLivraison.getDeliveries().forEach(livraison -> {
            trajets.add(fackTrajet(p,livraison.getIntersection(),nbTroncon));
        });
        trajets.add(fackTrajet(p,demandeLivraison.getBeginning(),nbTroncon));
        tournee.setDeliveries(trajets);
        return tournee;
    }


    public static Trajet fackTrajet(Plan p, Intersection intersection, int iteration){
        Trajet t = new Trajet();
        Collection<Troncon> tronconsPosible = p.getTronconsFromIntersection(intersection);
        for(int i=0;i<iteration && !tronconsPosible.isEmpty();i++){
            Troncon tr = getRandom(tronconsPosible) ;
            t.addTroncon(tr);
            tronconsPosible = p.getTronconsFromIntersection(tr.getDestination());
        }
        return t;
    }

    public static DemandeLivraison fackDemandeLivraison(Plan p, int nbLivraison) {
        DemandeLivraison res = new DemandeLivraison();
        res.setBeginning(getRandom(p.getIntersections()));
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YY HH:mm");
        Date start = null;
        try {
            start = dateFormat.parse("15-02-17 10:55");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        res.setStart(start);
        
        for(int i = 0; i< nbLivraison; i++)
            res.addDelivery(new Livraison(getRandom(p.getIntersections()), 30));

        return res;
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
