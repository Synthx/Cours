class CompteEpargne extends Compte {
    
    public double tauxInt;

    public CompteEpargne() {
        super();
        this.tauxInt = 0.1;
    }

    public CompteEpargne(double credit, double tauxInt) {
        super(credit);
        this.tauxInt = tauxInt;
    }

    public double interets() {
        return this.tauxInt * this.solde();
    }

    public void echeance() {
        this.crediter(this.interets());
    }

    public void debiter(double x) {
        if (this.solde() > x)
            super.debiter(x);
    }

    public String toString() {
        return super.toString() + "\nTaux d'interets : " + this.tauxInt;
    }
}