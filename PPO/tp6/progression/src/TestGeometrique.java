import java.util.*;

public class TestGeometrique {

    public static void main(String[] args) {
        // Initialisation du scanner
        Scanner scanner = new Scanner(System.in);

        // Scan du premier terme et de la raison de la suite
        System.out.print("premier terme? ");
        double terme = scanner.nextDouble();
        System.out.print("raison? ");
        double raison = scanner.nextDouble();

        // Initialisation de la classe
        ProgressionGeometrique prog = new ProgressionGeometrique(terme, raison);

        // Lancement de la boucle
        System.out.print("next (y/n)? ");
        char res = scanner.next().charAt(0);

        while (res == 'y') {
            // Calcul du prochain terme
            prog.next();
            // Affichage de celui-ci
            System.out.println("-> " + prog.getTerme());
            // Continuer ou non ?
            System.out.print("next (y/n)? ");
            res = scanner.next().charAt(0);
        }

        // Nombre de termes à calculer en plus sans les afficher
        System.out.print("nb termes supplementaires? ");
        int n = scanner.nextInt();
        prog.next(n);

        // Affichage du résultat
        System.out.println("progression: " + prog.toString());
    }
}