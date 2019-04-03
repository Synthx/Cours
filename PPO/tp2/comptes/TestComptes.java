import java.lang.*;
import java.util.*;

public class TestComptes {

    public static void main(String[] args) {
        Compte unCompte = new Compte();
        CompteEpargne unCE = new CompteEpargne();

        unCompte.crediter(100);
        unCompte.debiter(50.5);
        System.out.println("Compte classique :\n" + unCompte.toString());

        unCE.crediter(1000);
        unCE.debiter(200);
        System.out.println("\nCompte epargne :\n" + unCE.toString());

        unCE.echeance();
        System.out.println("\nCompte epargne apres echeance :\n" + unCE.toString());

        System.out.println("\nTypage dynamique :");
        unCompte = new Compte();
        unCE = new CompteEpargne();

        Banque b = new Banque();
        b.crediter(unCompte, 10);
        b.debiter(unCompte, 235);
        System.out.println("\n" + b.etat(unCompte));

        b.crediter(unCE, 10);
        b.debiter(unCE, 235);
        System.out.println("\n" + b.etat(unCE));

        unCompte = unCE;

        b.crediter(unCompte, 10);
        b.debiter(unCompte, 235);
        System.out.println("\n" + b.etat(unCompte));
    }
}