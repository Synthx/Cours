class Banque {
    
    public void crediter(Compte c, double valeur) {
        c.crediter(valeur);
    }

    public void debiter(Compte c, double valeur) {
        c.debiter(valeur);
    }

    public String etat(Compte c) {
        return c.toString();
    }
}