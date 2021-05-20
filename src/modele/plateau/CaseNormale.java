package modele.plateau;

public class CaseNormale extends EntiteStatique {
    public CaseNormale(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return true;
    }

}
