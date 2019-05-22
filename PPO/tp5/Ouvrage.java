public class Ouvrage {

	protected String titre, auteur;
	protected boolean emprunte;
	protected int compteur;

	public Ouvrage(String tit, String aut) {
		this.titre = tit;
		this.auteur = aut;
		this.emprunte = false;
		this.compteur = 0;
	}

	public String toString() {
		return this.titre + " - " + this.auteur;
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