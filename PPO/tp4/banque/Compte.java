public class Compte {
    
    public double credit, debit;

    public Compte() {
        this.credit = 0.;
        this.debit = 0.;
    }

    public Compte(double credit) {
        this();
        this.crediter(credit);
    }

    public void crediter(double x) {
        this.credit += x;
    }

    public void debiter(double x) {
        this.debit += x;
    }

    public double solde() {
        return this.credit - this.debit;
    }

    public void virerVers(double x, Compte dest) {
        this.debiter(x);
        dest.crediter(x);
    }

    public String toString() {
        String res = "";

        return "Solde : " + this.solde() + "\nCredit : " + this.credit + "\nDebit : " + this.debit;
    }
}