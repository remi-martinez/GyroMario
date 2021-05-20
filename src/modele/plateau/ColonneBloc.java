package modele.plateau;

public class ColonneBloc extends EntiteDynamique {
    public ColonneBloc(Jeu _jeu) {
        super(_jeu);
    }
    public boolean tueEntite(Entite e) {
        /*if (e instanceof Bot) {
            return jeu.killSmick((Bot)e);
        }
        if (e instanceof Heros) {
            return jeu.respawn();
        }*/
        return false;
    };
    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
    public boolean peutEtreRamasse(Entite e) { return false; }

    @Override
    public void setVitesse(int vitesse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getVitesse() {
        return 0;
    }

    @Override
    public void reinitialiserVitesse() {
        
    }
}
