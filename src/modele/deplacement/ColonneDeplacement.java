package modele.deplacement;

import modele.plateau.ColonneBloc;
import modele.plateau.EntiteDynamique;

import java.util.ArrayList;
import java.util.ListIterator;

import modele.deplacement.Direction;
import modele.deplacement.RealisateurDeDeplacement;
import modele.plateau.ColonneBleu;
import modele.plateau.Entite;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ColonneDeplacement extends RealisateurDeDeplacement {
    private static ColonneDeplacement cln;
    private Direction directionCouranteR = Direction.haut;
    private Direction directionCouranteB = Direction.haut;
    private boolean deplacementR = false;
    private boolean deplacementB = false;
    private Direction directionCourante = Direction.haut;

    public static ColonneDeplacement getInstance() {
        if (cln == null) {
            cln = new ColonneDeplacement();
        }
        return cln;
    }
    
    private void setDirectionCourante(Direction _directionCourante) {
        this.directionCourante = _directionCourante;
    }
    public void setDirectionCouranteR() {
        this.deplacementR = true;
        switch (this.directionCouranteR) {
            case bas:
                setDirectionCourante(Direction.haut);
                this.directionCouranteR = Direction.haut;
                break;
            case haut:
                setDirectionCourante(Direction.bas);
                this.directionCouranteR = Direction.bas;
                break;
        }
    }

    public void setDirectionCouranteB() {
        this.deplacementB = true;
        switch (this.directionCouranteB) {
            case bas:
                setDirectionCourante(Direction.haut);
                this.directionCouranteB = Direction.haut;
                break;
            case haut:
                setDirectionCourante(Direction.bas);
                this.directionCouranteB = Direction.bas;
                break;
        }
    }
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;
        ArrayList<ColonneBloc> lstColonnes = new ArrayList<>();
        if (this.directionCourante == Direction.haut) {
            ListIterator<EntiteDynamique> iterator = lstEntitesDynamiques.listIterator();
            while (iterator.hasNext()) {
                Entite e = iterator.next();
                if (e instanceof ColonneBloc) lstColonnes.add((ColonneBloc)e);
            }
        }
        else {
            ListIterator<EntiteDynamique> iterator = lstEntitesDynamiques.listIterator(lstEntitesDynamiques.size());
            while (iterator.hasPrevious()) {
                Entite e = iterator.previous();
                if (e instanceof ColonneBloc) lstColonnes.add((ColonneBloc)e);
            }
        }
        for (ColonneBloc e : lstColonnes) {
            if ((deplacementB && e instanceof ColonneBleu)) {
                Entite cible = e.regarderDansLaDirection(directionCourante);
                //System.out.println(cible);
                if (cible == null) {
                    ret = deplacement(e, directionCourante);
                } else if (cible.peutEtreEcrase()) {
                    e.tueEntite(cible);
                    ret = deplacement(e, directionCourante);
                }
            }
        }
        if (!ret) {
            this.deplacementB = false;
            this.deplacementR = false;
        }
        return ret;
    }

    private boolean deplacement(ColonneBloc e, Direction directionCourante) {
        boolean ret = false;
        switch (directionCourante) {
            case haut:
                if (e.avancerDirectionChoisie(Direction.haut))
                    ret = true;
                break;
            case bas:
                if (e.avancerDirectionChoisie(Direction.bas))
                    ret = true;
                break;
        }
        return ret;
    }
}
