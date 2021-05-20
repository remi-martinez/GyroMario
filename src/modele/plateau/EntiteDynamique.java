package modele.plateau;

import modele.deplacement.Direction;

/**
 * Entités amenées à bouger (colonnes, ennemis)
 */
public abstract class EntiteDynamique extends Entite {
    public EntiteDynamique(Jeu _jeu) { super(_jeu); }

    public boolean avancerDirectionChoisie(Direction d) {
        return jeu.deplacerEntite(this, d);
    }
    public abstract void setVitesse(int vitesse);
    public abstract int getVitesse();
    public abstract void reinitialiserVitesse();
}
