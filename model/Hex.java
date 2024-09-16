package model;

public class Hex implements SpecifHex {

	private int[][] grille;
	private Etat etat;

	public Hex() {
		grille = new int[Taille][Taille];

	}
	public boolean coordCorrectes(int lig, int col) {
		lig = lig - 1;
		col = col - 1;
		return lig >= 0 && lig < Taille && col >= 0 && col < Taille;
	}

	@Override
	public Etat getEtat() {
		return etat;
	}

	@Override
	public int getJoueur() {
		switch (etat) {
		case J1_JOUE:
			return 1;
		case J2_JOUE:
			return 2;
		default:
			return 0;
		}
	}

	@Override
	public int getVainqueur() {
		switch (etat) {
		case J1_VAINQUEUR:
			return 1;
		case J2_VAINQUEUR:
			return 2;
		default:
			return 0;
		}
	}

	public boolean estTermine() {
		return (etat == Etat.J1_VAINQUEUR || etat == Etat.J2_VAINQUEUR);
	}

	@Override
	public boolean estActionCorrect(int lig, int col) {
		return (this.grille[lig][col] == 0);
	}

	@Override
	public void jouer(int lig, int col) {
		if (!this.estTermine()) {
			if (this.estActionCorrect(lig - 1, col - 1)) {
				switch (etat) {
				case J1_JOUE:
					this.grille[lig - 1][col - 1] = 1;
					etat = Etat.J2_JOUE;
					break;
				case J2_JOUE:
					this.grille[lig - 1][col - 1] = 2;
					etat = Etat.J1_JOUE;
					break;
				default:
					break;
				}
				this.verifierEtatJeu();
			}
		}
	}
	
	public Etat verifierEtatJeu()
	{
		
		
		return etat;
	}
	
	
}
