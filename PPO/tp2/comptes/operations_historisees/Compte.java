class Compte {
    
    public final int MAX_OPERATIONS = 100;
    public double[] credits;
    public double[] debits;

    public Compte() {
        this.credits = new double[MAX_OPERATIONS];
        this.debits = new double[MAX_OPERATIONS];
    }

    public Compte(double credit) {
        this.credits = credit;
        this.debits = new double[MAX_OPERATIONS];
    }

    public double getCredit() {
        double sum = 0;

        for (double c : this.credits)
            sum += c;

        return sum;
    }

    public double getDebit() {
        double sum = 0;

        for (double c : this.debits)
            sum += c;

        return sum;
    }

    public void crediter(double x) {
        //TODO
    }

    public void debiter(double x) {
        //TODO
    }

    public double solde() {
        return this.getCredit() - this.getDebit();
    }

    public String toString() {
        String res = "";

        return "Solde : " + this.solde() + "\nCredit : " + this.getCredit() + "\nDebit : " + this.getDebit();
    }
}