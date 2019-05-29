package lib;

public class Ouvrage {

	public String titre, auteur;
	public boolean emprunte;
	public int compteur;

	public Ouvrage(String tit, String aut) {
		this.titre = tit;
		this.auteur = aut;
		this.emprunte = false;
		this.compteur = 0;
	}

	public String toString() {
		return this.titre + " - " + this.auteur + " - Emprunté " + this.compteur + " fois";
	}

	public void emprunter() throws NonDisponibleException {
		if (this.emprunte)
			throw new NonDisponibleException();
		else {
			this.emprunte = true;
			this.compteur++;
		}
	}

	public void retourner() {
		this.emprunte = false;
	}

	public int getCompteur() {
		return this.compteur;
	}
}
