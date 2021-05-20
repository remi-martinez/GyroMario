package modele.plateau;

import modele.deplacement.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][][] grilleEntites = new Entite[SIZE_X][SIZE_Y][2]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    private int NbrBombes = 1;
    private int NbrVies = 2;
    private int NbrPoints = 0;
    public String status = "Gyromite";
    public Jeu() {
        reset();
    }
    public void reset() {

        for (RealisateurDeDeplacement r : ordonnanceur.getLstDeplacements()) r.reset();
        ordonnanceur.reset();
        map = new  HashMap<Entite, Point>();
        resetCmptDepl();
        grilleEntites = new Entite[SIZE_X][SIZE_Y][2];
        NbrVies = 3;
        NbrBombes = 0;
        NbrPoints = 0;
        initialisationDesEntites();
    }
    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }
    
    public Entite[][][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHector() {
        return hector;
    }
    
    private void initialisationDesEntites() {
        hector = new Heros(this, 2, 1);
        /*addEntite(hector, hector.getSpawnX(), hector.getSpawnY(), 1);

        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());*/
        addEntite(new Mur(this), 2, 6, 0);
        addEntite(new Mur(this), 3, 6, 0);
        addEntite(new Mur(this), 5, 6, 0);
        addEntite(new Mur(this), 6, 6, 0);
        addEntite(new Mur(this), 8, 6, 0);
        addEntite(new Mur(this), 9, 6, 0);
        addEntite(new Mur(this), 10, 6, 0);


        addEntite(new Mur(this), 10, 8, 0);

        //construireTerrain();
    }

    private void construireTerrain() {
        File file = new File("Images/Map/Map1.png");
        BufferedImage image;

        try {
            image = ImageIO.read(file);
        } catch (IOException ex) {
            //Logger.getLogger((VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        // Getting pixel color by position x and y
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                Color clr = new Color(image.getRGB(x, y), true);
                if(clr.equals(Color.black)) { // Noir : mur
                    addEntite(new Mur(this), x, y, 0);
                }
                if(clr.equals(Color.red)) { // Rouge : ennemi

                }
                if(clr.equals(Color.green)) { // Vert: héros
                    Heros hero = new Heros(this, x, y);
                }
            }
        }

    }
    
    private void addEntite(Entite e, int x, int y, int dynamique) { // Statique = 0, Dynamique = 1
        grilleEntites[x][y][dynamique] = e;
        map.put(e, new Point(x, y));
    }
    private void supprimeEntite(int x, int y, int dynamique){
        map.remove(grilleEntites[x][y][dynamique]);
        grilleEntites[x][y][dynamique] = null;
    }
    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(EntiteDynamique e, Direction d) {
        boolean retour = false;

        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
        Entite eCible = objetALaPosition(pCible);
        if(eCible!=null){
        //System.out.println("eCible.peutEtreRamasse(e)" + eCible.peutEtreRamasse(e)+ "  "+ eCible.getClass());
        
    }
        if (contenuDansGrille(pCible) && (eCible == null  || !eCible.peutServirDeSupport() || /*mur*/ eCible.peutPermettreDeMonterDescendre() || eCible.peutEtreRamasse(e) //corde
        )) { // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            if (cmptDeplV.get(e) == null || e.tueEntite(eCible)) {
                switch (d) {
                    case bas: case haut:
                        if (!e.tueEntite(eCible) || eCible.tueEntite(e)) {
                            cmptDeplV.put(e, 1);
                            retour = true;
                        }
                        break;
                    case gauche: case droite:
                        cmptDeplH.put(e, 1);
                        retour = true;
                        break;
                }
            }
        }
        if (retour) {
            if(e.getVitesse()==0){
                deplacerEntite(pCourant, pCible, e);
                e.reinitialiserVitesse();
            }
            else{
                e.setVitesse(e.getVitesse()-1);
            }
            /*if( eCible!=null && eCible.peutEtreRamasse(e)){
                //System.out.println("ramassé");
                if(eCible instanceof Bombe) {
                    NbrBombes = NbrBombes -1;
                    NbrPoints += 100;
                }
                else if(eCible instanceof Coeur)NbrVies = NbrVies +1;
                else if(eCible instanceof Piece)NbrPoints += 50;
                supprimeEntite(pCible.x, pCible.y, 0);
            }*/
        }
        return retour;
    }
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }
    
    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y][1] = null;
        grilleEntites[pCible.x][pCible.y][1] = e;
        map.put(e, pCible);
    }
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Entite objetALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            if (grilleEntites[p.x][p.y][1] == null)
                retour = grilleEntites[p.x][p.y][0];
            else retour = grilleEntites[p.x][p.y][1];
        }
        
        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }

    public int getNbrBombes() {
        return NbrBombes;
    }

    public int getNbrVies() {
        return NbrVies;
    }
    public int getNbrPoints() {
        return NbrPoints;
    }

    public String[][] getScore() {
        String[][] score = {{
            "Points: ",String.valueOf(this.NbrPoints),
            "Vies: ", String.valueOf(this.NbrVies),
            "Bombes: ", String.valueOf(this.NbrBombes)
        }};
        return score;
    }
   /* public boolean respawn() {
        Point p = map.get(hector);
        supprimeEntite(p.x, p.y, 1);
        addEntite(hector, hector.getSpawnX(), hector.getSpawnY(), 1);
        NbrVies--;
        return true;
    }
    public boolean killSmick(Bot e) {
        Point positionSmick= map.get(e);
        supprimeEntite(positionSmick.x, positionSmick.y, 1);
        e.getIA().removeEntiteDynamique(e);
        Gravite.getInstance().removeEntiteDynamique(e);
        NbrPoints+=20;
        return true;
    }
    public void addBomb() {
        this.NbrBombes++;
    }
    public void generateColonne(Colonne colonne, int x, int y) {
        for (ColonneBloc c : colonne.getListBlocs()) {
            addEntite(c, x, y++, 1); //construction du haut vers le bas
            ColonneDeplacement.getInstance().addEntiteDynamique(c);
        }
    }
    public boolean gameOver() {
        if ((NbrBombes <= 0 || NbrVies <= 0)) {
            if (NbrBombes <= 0) this.status = "Vous avez gagné!";
            if (NbrVies <= 0) this.status = "Vous avez perdu!";
        }
        return (NbrBombes <= 0 || NbrVies <= 0);
    }*/
}
