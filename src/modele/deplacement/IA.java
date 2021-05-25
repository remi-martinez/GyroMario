package modele.deplacement;

import modele.plateau.*;

import java.util.ArrayList;

public class IA extends RealisateurDeDeplacement {
    protected boolean realiserDeplacement() {
        boolean ret = false;
        boolean horizontal = true;
        ArrayList<Smicks> lstBots = new ArrayList<>();
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e instanceof Smicks) lstBots.add((Smicks)e);
        }
        for (Smicks e : lstBots) {
            Direction d = e.getDirection();
            Entite eNext = e.regarderDansLaDirection(d); //case vers laquelle on se dirige

            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            Entite[] eAdjacents = {null, null}; //cases adjacentes a celle vers laquelle on se dirige

            if(eBas.regarderDansLaDirection(d) == null || eBas.regarderDansLaDirection(d) instanceof CaseNormale) { //Si vide en dessous de la prochaine case : tourne
                e.changeDirectionHorizontal();
            }
            if (eNext != null) {
                if(eNext.peutServirDeSupport() == false){
                    System.out.println('e');
                    e.changeDirectionHorizontal();
                    e.avancerDirectionChoisie(d);
                }
                if (e.tueEntite(eNext)) {
                    if (e.avancerDirectionChoisie(d)) {
                        ret = true;
                    }
                }

                //if (d == Direction.haut) e.changeDirection(e.getDirection(false));
                //if (e.avancerDirectionChoisie(d))ret = true;

            }else{

                if (e.avancerDirectionChoisie(d)) {
                    ret = true;
                }
            }

        }
        return ret;
    }

}
