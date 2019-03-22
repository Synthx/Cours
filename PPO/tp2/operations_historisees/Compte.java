import java.util.*;
import java.lang.*;

class Compte {

    public static int MAX_OPERATIONS = 10;
    
    public int dernierCredit, dernierDebit;
    public double[] credits, debits;

    public Compte() {
        this.credits = new double[MAX_OPERATIONS];
        this.debits = new double[MAX_OPERATIONS];
        this.dernierCredit = -1;
        this.dernierDebit = -1;
    }

    public Compte(double credit) {
        this();
        this.crediter(credit);
    }

    public double getCredit() {
        double sum = 0.;

        for (int i = 0; i <= this.dernierCredit; i++)
            sum += this.credits[i];

        return sum;
    }

    public double getDebit() {
        double sum = 0.;

        for (int i = 0; i <= this.dernierDebit; i++)
            sum += this.debits[i];

        return sum;
    }

    public void crediter(double x) {
        if (this.dernierCredit > MAX_OPERATIONS - 1) {
            this.credits[0] = this.getCredit();
            this.dernierCredit = 0;
        }

        this.dernierCredit++;
        this.credits[this.dernierCredit] = x;
    }

    public void debiter(double x) {
        if (this.dernierDebit > MAX_OPERATIONS - 1) {
            this.debits[0] = this.getCredit();
            this.dernierDebit = 0;
        }

        this.dernierDebit++;
        this.debits[this.dernierDebit] = x;
    }

    public double solde() {
        return this.getCredit() - this.getDebit();
    }

    public String toString() {
        String res = "";

        return "Solde : " + this.solde() + "\nCredit : " + this.getCredit() + "\nDebit : " + this.getDebit();
    }
}