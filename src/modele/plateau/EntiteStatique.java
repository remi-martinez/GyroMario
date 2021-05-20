package modele.plateau;

/**
 * Ne bouge pas (murs...)
 */
public abstract class EntiteStatique extends Entite {
    public EntiteStatique(Jeu _jeu) {
        super(_jeu);
    }
    @Override
    public boolean peutEtreRamasse(Entite e) { 
    	return false; 
    }
    @Override
    public boolean peutEtreEcrase() { 
    	return false; 
    }
    @Override
    public boolean peutServirDeSupport() {
    	return true; 
    }
    @Override
    public boolean peutPermettreDeMonterDescendre() { 
    	return false; 
    };
    @Override
    public boolean tueEntite(Entite e) {
        return false;
    }
    
    public abstract boolean traversable();

}