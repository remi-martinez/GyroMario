/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 * Héros du jeu
 */
public class Heros {
    private int x;
    private int y;

    private Jeu jeu;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Heros(Jeu _jeu, int _x, int _y) {
        jeu = _jeu;
        x = _x;
        y = _y;
    }

    public void droite() {
        if (traversable(x+1, y)) {
            x ++;
            y = gravity(x,y);
        }
    }

    public void gauche() {
        if (traversable(x-1, y)) {
            x --;
            y = gravity(x,y);
        }
    }

    public void bas() {
        if (traversable(x, y+1)) {
            y ++;
            y = gravity(x,y);
        }
    }

    public void haut() {
        if (traversable(x, y-1)) {
            y --;
            //	y = gravity(x,y+1);
        }
    }

    private int gravity(int x, int y) {
    	
		while(traversable(x, y+1)) {
			y ++;
		}
    	return y;
    	
    }
    
    private boolean traversable(int x, int y) {

        if (x >0 && x < jeu.SIZE_X && y > 0 && y < jeu.SIZE_Y) {
            return jeu.getEntite(x, y).traversable();
        } else {
            return false;
        }
    }
}
