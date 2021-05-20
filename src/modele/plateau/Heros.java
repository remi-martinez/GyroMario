package modele.plateau;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique {
    private int spawnX;
    private int spawnY;
    private int vitesse;

    public Heros(Jeu _jeu, int x, int y) {
        super(_jeu);
        spawnX = x;
        spawnY = y;
        vitesse = 0; 
    }

    public boolean tueEntite(Entite e) {
        return false;
    };
    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
    public boolean peutEtreRamasse(Entite e) { return false; }

    public int getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(int spawnX) {
        this.spawnX = spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(int spawnY) {
        this.spawnY = spawnY;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }
    public void reinitialiserVitesse(){
        vitesse = 0;
    }

    
}
