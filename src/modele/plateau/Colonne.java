package modele.plateau;

import java.util.ArrayList;

public class Colonne {
    private int size;
    private String color;
    private ArrayList<ColonneBloc> listBlocs = new ArrayList<ColonneBloc>();
    private Jeu jeu;

    public Colonne(Jeu _jeu, int size, String color) {
        this.jeu = _jeu;
        this.size = size;
        for (int i = 0; i < size; i++) {
        	this.listBlocs.add(new ColonneBleu(_jeu));
            //if (color == "rouge") this.listBlocs.add(new ColonneRouge(_jeu));
        }
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<ColonneBloc> getListBlocs() {
        return listBlocs;
    }
}
