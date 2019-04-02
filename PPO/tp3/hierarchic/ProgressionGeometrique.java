public class ProgressionGeometrique extends Progression {

    public ProgressionGeometrique(double terme, double raison) {
        super(terme, raison);
    }

    public void next() {
        this.termes.add(this.getTerme() * this.raison);
        this.rang++;
    }
}