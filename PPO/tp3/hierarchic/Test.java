import java.util.*;

public class Test {

    public static void manip(Progression p, Scanner sc) {
        // Lancement de la boucle
        System.out.print("next (y/n)? ");
        char res = sc.next().charAt(0);

        while (res == 'y') {
            // Calcul du prochain terme
            p.next();
            // Affichage de celui-ci
            System.out.println("-> " + p.getTerme());
            // Continuer ou non ?
            System.out.print("next (y/n)? ");
            res = sc.next().charAt(0);
        }

        // Nombre de termes à calculer en plus sans les afficher
        System.out.print("nb termes supplementaires? ");
        int n = sc.nextInt();
        p.next(n);

        // Affichage du résultat
        System.out.println("progression: " + p.toString());
    }

    public static void main(String[] args) {
        // Initialisation du scanner
        Scanner scanner = new Scanner(System.in);
        Progression p;

        // Choix de la progression
        System.out.print("progression (arithmetique: 1 / geometrique: 2)? ");
        int choix = scanner.nextInt();

        // Scan du premier terme et de la raison de la suite
        System.out.print("premier terme? ");
        double terme = scanner.nextDouble();
        System.out.print("raison? ");
        double raison = scanner.nextDouble();

        // Initialisation de la progression
        p = choix == 1 ? new ProgressionArithmetique(terme, raison) : new ProgressionGeometrique(terme, raison) ;

        // Manipulation de la progression
        manip(p, scanner);
    }
}