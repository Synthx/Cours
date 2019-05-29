package lib;

public class Revue extends Ouvrage implements Comparable<Revue> {

    private int date;
    private String numero;

    public Revue(String tit, String aut, int dat, String num) {
        super(tit, aut);
        this.date = dat;
        this.numero = num;
    }

    public void emprunter() throws NonDisponibleException {
        if (this.emprunte || (Bibliotheque.date - this.date) < 7)
            throw new NonDisponibleException();
        else {
            this.emprunte = true;
            this.compteur++;
        }
    }

    public int compareTo(Revue r) {
        return this.titre.compareTo(r.titre);
    }
}