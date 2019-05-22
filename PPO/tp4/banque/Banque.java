import java.util.*;

public class Banque {

	public List<Compte> comptes;
	public int nbComptes;

	public Banque() {
		this.comptes = new ArrayList<Compte>();
		this.nbComptes = 0;
	}

	public int ouvrirCompte() {
		Compte compte = new Compte();
		this.comptes.add(compte);
		this.nbComptes++;

		return this.comptes.indexOf(compte);
	}

	public int ouvrirCompteEpargne() {
		CompteEpargne compte = new CompteEpargne();
		this.comptes.add(compte);
		this.nbComptes++;

		return this.comptes.indexOf(compte);
	}
	
	public Compte getCompte(int i) {
		if (i >= 0 && i < this.nbComptes)
			return this.comptes.get(i);
		else {
			System.out.println("compte inexistant");
			return null;
		}
	}
	
	public void crediter(int i, double x) {
		Compte compte = this.getCompte(i);

		if (compte != null)
			compte.crediter(x);
	}
	
	public void debiter(int i, double x) {
		Compte compte = this.getCompte(i);

		if (compte != null)
			compte.debiter(x);
	}
	
	public double totalSolde() {
		double sum = 0.;

		for (Compte compte : this.comptes)
			sum += compte.solde();

		return sum;
	}
	
	public String etat(int i) {
		Compte compte = this.getCompte(i);

		if (compte != null)
			return compte.toString();
		else
			return "";
	}

	public double interets(int i) {
		Compte compte = this.getCompte(i);

		if (compte != null) {
			if (compte instanceof CompteEpargne) {
				// Downcast
				CompteEpargne compteEpargne = (CompteEpargne) compte;
	
				return compteEpargne.interets();
			} else {
				System.out.println("operation non valide");
			}
		}

		return 0.;
	}

	public void echeance(int i) {
		Compte compte = this.getCompte(i);

		if (compte != null) {
			if (compte instanceof CompteEpargne) {
				// Downcast
				CompteEpargne compteEpargne = (CompteEpargne) compte;
	
				compteEpargne.echeance();
			} else {
				System.out.println("operation non valide");
			}
		}
	}
	
	public void etat() {
		for (int i=0; i < this.nbComptes; i++)
			System.out.println("Compte " + i + "\n" + this.etat(i) + "\n");
	}
	
	public void virement(int numSrc, int numDest, double x) {
		Compte compteSrc = this.getCompte(numSrc);
		Compte compteDest = this.getCompte(numDest);

		if (compteSrc instanceof CompteEpargne) {
			CompteEpargne compte = (CompteEpargne) compteSrc;
			
			if (compte.solde() >= x)
				compte.virerVers(x, compteDest);
		} else {
			compteSrc.virerVers(x, compteDest);
		}
	}
}