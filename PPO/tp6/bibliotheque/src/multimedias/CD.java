package multimedias;

import lib.Ouvrage;

public class CD extends Ouvrage {

    public CD(String tit, String aut) {
        super(tit, aut);
    }

    public String toString() {
        return this + " - CD " + this.titre;
    }
}