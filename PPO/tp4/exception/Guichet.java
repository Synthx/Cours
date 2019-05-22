import java.util.Scanner;

public class Guichet {
   static Scanner in = new Scanner(System.in);
   // creation de la banque :
   static Banque bank = new Banque();

   public static void main (String[] args) {
      int choix=0;

      do {
         menu();
         System.out.print("votre choix? ");
         choix = in.nextInt();

         switch (choix) {
            case 1 : // etat des comptes
               try {
                  bank.etat();
               } catch (CompteInexistantException e) {
                  System.out.println("Compte inexistant");
               }
            break;
            case 2 : // creer un nouveau compte
               menuNouveauCompte();
            break;
            case 3: // crediter un compte
               menuCrediter();
            break;
            case 4: // debiter un compte
               menuDebiter();
            break;
            case 5: // effectuer un virement
               menuVirement();
            break;
            case 6: // ouvrir un compte epargne
               menuNouveauCompteEpargne();
            break;
            case 7: // todo

            break;
            case 8: // todo

            break;
         }
      } while (choix!=0);

      System.out.println("au revoir");
   }

   static void menu() {
      System.out.println("\n1: etat des comptes\n" +
      "2: creer un nouveau compte\n" +
      "3: crediter un compte\n" +
      "4: debiter un compte\n" +
      "5: effectuer un virement\n" +
      "6: creer un nouveau compte epargne\n" +
      "7: calcule les interets d'un compte" +
      "8: echeance un compte" +
      "0: quitter");
   }

   static void menuNouveauCompte() {
      int num;
      num=bank.ouvrirCompte();
      System.out.println("numero= "+num);
   }

   static void menuNouveauCompteEpargne() {
      int num;
      num=bank.ouvrirCompteEpargne();
      System.out.println("numero= "+num);
   }

   static void menuCrediter() {
      int num;

      System.out.print("\nnumero du compte? ");
      num=in.nextInt();
      System.out.print("somme? ");

      try {
         bank.crediter(num, in.nextDouble());
      } catch (CompteInexistantException e) {
         System.out.println("Compte inexistant");
      }
   }

   static void menuDebiter() {
      int num;

      System.out.print("\nnumero du compte? ");
      num=in.nextInt();
      System.out.print("somme? ");

      try {
         bank.debiter(num, in.nextDouble());
      } catch (CompteInexistantException e) {
         System.out.println("Compte inexistant");
      }
   }

   static void menuVirement() {
      int from, to;

      System.out.print("\ncompte a debiter? ");
      from=in.nextInt();
      System.out.print("compte a crediter? ");
      to=in.nextInt();
      System.out.print("somme? ");

      try {
         bank.virement(from, to, in.nextDouble());
      } catch (CompteInexistantException e) {
         System.out.println("Compte inexistant");
      }
   }
}