package modele.deplacement;

import modele.deplacement.Direction;
import modele.deplacement.RealisateurDeDeplacement;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;

public class Gravite extends RealisateurDeDeplacement {
    private static Gravite g;

    public static Gravite getInstance() {
        if (g == null) {
            g = new Gravite();
        }
        return g;
    }
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if (eBas == null || (eBas != null && !eBas.peutServirDeSupport())) {
                if (e.avancerDirectionChoisie(Direction.bas))
                    ret = true;
            }
        }

        return ret;
    }
}
