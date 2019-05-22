public class Compte {
    
    public double credits, debits;

    public Compte() {
        this.credits = 0.;
        this.debits = 0.;
    }

    public Compte(double credit) {
        this();
        this.crediter(credit);
    }

    public void crediter(double x) {
        this.credits += x;
    }

    public void debiter(double x) {
        this.debits += x;
    }

    public double solde() {
        return this.credits - this.debits;
    }

    public void virerVers(double x, Compte dest) {
        this.debiter(x);
        dest.crediter(x);
    }

    public String toString() {
        return "Solde : " + this.solde() + "\nCredit : " + this.credits + "\nDebit : " + this.debits;
    }
}