package modele.plateau;

public class Corde extends EntiteStatique {
    public Corde(Jeu _jeu) { super(_jeu); }

    public boolean peutPermettreDeMonterDescendre() { return true; }

}
