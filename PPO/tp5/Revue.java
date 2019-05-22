public class Revue extends Ouvrage {

    protected int date;
    protected String numero;

    public Revue(String tit, String aut, int dat, String num) {
        super(tit, aut);
        this.date = dat;
        this.numero = num;
    }

    public emprunter(String code) throws NonDisponibleException {
        if (this.emprunte)
            throw new NonDisponibleException();
        else {
            this.emprunte = true;
            this.compteur++;
        }
    }
}