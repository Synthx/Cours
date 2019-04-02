public class ProgressionArithmetique extends Progression {

    public ProgressionArithmetique(double terme, double raison) {
        super(terme, raison);
    }

    public void next() {
        this.termes.add(this.getTerme() + this.raison);
        this.rang++;
    }
}