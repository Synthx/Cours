public class Compte {

    public int MAX_OPERATIONS = 10;

    public int dernierCredit, dernierDebit;
    public double[] credits, debits;

    public Compte() {
        this.credits = new double[MAX_OPERATIONS];
        this.debits = new double[MAX_OPERATIONS];
        this.dernierCredit = 0;
        this.dernierDebit = 0;
    }

    public Compte(double credit) {
        this();
        this.crediter(credit);
    }

    public double getCredit() {
        double sum = 0.;

        for (double d : this.credits)
            sum += d;

        return sum;
    }

    public double getDebit() {
        double sum = 0.;

        for (double d : this.debits)
            sum += d;

        return sum;
    }

    public void crediter(double x) {
        this.credits[this.dernierCredit] = x;
        this.dernierCredit++;

        if (this.dernierCredit == MAX_OPERATIONS - 1) {
            double sum = this.getCredit();
            this.credits = new double[MAX_OPERATIONS];
            this.credits[0] = sum;
            this.dernierCredit = 1;
        }
    }

    public void debiter(double x) {
        this.debits[this.dernierDebit] = x;
        this.dernierDebit++;

        if (this.dernierDebit == MAX_OPERATIONS - 1) {
            double sum = this.getDebit();
            this.debits = new double[MAX_OPERATIONS];
            this.debits[0] = sum;
            this.dernierDebit = 1;
        }
    }

    public double solde() {
        return this.getCredit() - this.getDebit();
    }

    public String toString() {
        return "Solde : " + this.solde() + "\nCredit : " + this.getCredit() + "\nDebit : " + this.getDebit();
    }
}