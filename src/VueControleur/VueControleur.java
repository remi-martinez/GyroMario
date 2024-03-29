package VueControleur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import modele.plateau.Jeu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;
    
    private int resX; //resolution du jeu
    private int resY;

    // icones affichées dans la grille
    private ImageIcon icoHeroRight;
    private ImageIcon icoHeroBottom;
    private ImageIcon icoHeroLeft;
    private ImageIcon icoCaseNormale;
    private ImageIcon icoMur;
    private ImageIcon icoColonne;

    private JTable score;
    private JComponent grilleJLabels;
    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)
    private JLabel status;

    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;
        
        resX = 400;
        resY = 250;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            /*@Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : jeu.getHeros().gauche(); break;
                    case KeyEvent.VK_RIGHT : jeu.getHeros().droite();break;
                    case KeyEvent.VK_DOWN : jeu.getHeros().bas(); break;
                    case KeyEvent.VK_UP : jeu.getHeros().haut(); break;

                }
            }*/
        });
    }


    private void chargerLesIcones() {
    	icoHeroRight = chargerIcone("Images/Pacman_Right.png");
    	icoHeroLeft = chargerIcone("Images/Pacman_Left.png");
    	icoHeroBottom = chargerIcone("Images/Pacman_Bottom.png");
        icoCaseNormale = chargerIcone("Images/Vide.png");
        icoMur = chargerIcone("Images/Mur.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private JPanel placerLesComposantsGraphiques() {
        JPanel game = new JPanel(new BorderLayout());
        setTitle("GyroLego");
        setSize(resX, resY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        tabJLabel = new JLabel[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        String data[][] = this.jeu.getScore();
        String columns[] = {"","","","","",""};
        score = new JTable(data, columns);
        score.setFocusable(false);
        score.setRowHeight(resY/10);
        grilleJLabels.setBackground(Color.BLACK);
        game.add(score, BorderLayout.NORTH);
        game.add(grilleJLabels, BorderLayout.CENTER);
        return game;
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                System.out.println(tabJLabel[x][y]);
				if (jeu.getGrille()[x][y][1] instanceof Heros) { 
					// TODO si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    tabJLabel[x][y].setIcon(icoHeroRight);
                } else if (jeu.getGrille()[x][y][0] instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                }else {
                    tabJLabel[x][y].setIcon(icoCaseNormale);
                }
            }
        }



        //tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroRight);

    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mettreAJourAffichage();
            }
        }); 
        */

    }
}
