package modele.plateau;

/**
 * Ne bouge pas (murs...)
 */
public abstract class EntiteStatique {
    protected Jeu jeu;

    public EntiteStatique(Jeu _jeu) {
        jeu = _jeu;
    }

    public abstract boolean traversable();

}