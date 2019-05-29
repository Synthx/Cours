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
 	bank.etat();
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
   case 0: // quitter
   }
  } while (choix!=0);
 System.out.println("au revoir");
 }

 static void menu() {
  System.out.println("\n1: etat des comptes\n2: creer un nouveau compte\n3: crediter un compte\n4: debiter un compte\n5: effectuer un virement\n0: quitter");
 }

 static void menuNouveauCompte() {
  int num;
  num=bank.ouvrirCompte();
  System.out.println("numero= "+num);
 }

 static void menuCrediter() {
  int num;
  System.out.print("\nnumero du compte? ");
  num=in.nextInt();
  System.out.print("somme? ");
  bank.crediter(num, in.nextDouble());
 }

 static void menuDebiter() {
  int num;
  System.out.print("\nnumero du compte? ");
  num=in.nextInt();
  System.out.print("somme? ");
  bank.debiter(num, in.nextDouble());
 }

 static void menuVirement() {
  int from, to;
  System.out.print("\ncompte a debiter? ");
  from=in.nextInt();
  System.out.print("compte a crediter? ");
  to=in.nextInt();
  System.out.print("somme? ");
  bank.virement(from, to, in.nextDouble());
 }
}