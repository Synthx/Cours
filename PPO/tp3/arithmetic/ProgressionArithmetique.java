import java.util.*;

public class ProgressionArithmetique {

    public ArrayList<Double> termes;
    public double raison;
    public int rang;

    public ProgressionArithmetique(double terme, double raison) {
        this.rang = 0;
        this.termes = new ArrayList<Double>();
        this.termes.add(terme);
        this.raison = raison;
    }

    public void next() {
        this.termes.add(this.getTerme() + this.raison);
        this.rang++;
    }

    public void next(int n) {
        for (int i=1; i <= n; i++)
            this.next();
    }

    public double getTerme() {
        return this.termes.get(this.rang);
    }

    public String toString() {
        String res = "";

        for (int i=0; i <= this.rang; i++)
            res += this.termes.get(i) + " ";

        return res;
    }
}