package model;

public interface SpecifHex {

	public static final int Taille = 11;

	public static enum Etat {
		J1_JOUE, J2_JOUE, J1_VAINQUEUR, J2_VAINQUEUR
	};

	public abstract Etat getEtat();

	public abstract int getJoueur();

	public abstract int getVainqueur();

	public abstract boolean estActionCorrect(int lig, int col);

	public abstract void jouer(int lig, int col);

}
