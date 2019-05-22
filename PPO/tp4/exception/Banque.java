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
	
	public Compte getCompte(int i) throws CompteInexistantException {
		try {
			return this.comptes.get(i);
		} catch (IndexOutOfBoundsException exception) {
			throw new CompteInexistantException();
		}
	}
	
	public void crediter(int i, double x) throws CompteInexistantException {
		this.getCompte(i).crediter(x);
	}
	
	public void debiter(int i, double x) throws CompteInexistantException {
		this.getCompte(i).debiter(x);
	}
	
	public double totalSolde() {
		double sum = 0.;

		for (Compte compte : this.comptes)
			sum += compte.solde();

		return sum;
	}
	
	public String etat(int i) throws CompteInexistantException {
		return this.getCompte(i).toString();
	}

	public double interets(int i) throws OperationNonValideException, CompteInexistantException {
		try {
			CompteEpargne compteEpargne = (CompteEpargne) this.getCompte(i);
			return compteEpargne.interets();
		} catch(ClassCastException exception) {
			throw new OperationNonValideException();
		}
	}

	public void echeance(int i) throws OperationNonValideException, CompteInexistantException {
		try {
			CompteEpargne compteEpargne = (CompteEpargne) this.getCompte(i);
			compteEpargne.echeance();
		} catch(ClassCastException exception) {
			throw new OperationNonValideException();
		}
	}
	
	public void etat() throws CompteInexistantException {
		for (int i=0; i < this.nbComptes; i++)
			System.out.println("Compte " + i + "\n" + this.etat(i) + "\n");
	}
	
	public void virement(int numSrc, int numDest, double x) throws CompteInexistantException {
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