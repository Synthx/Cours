package applications;

import java.util.Scanner;
import lib.*;

public class Application {
	private static Bibliotheque bib = new Bibliotheque();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] argv) {
		int choice;
		
		do {
			System.out.println("\nQue voulez-vous faire ?");
			menu();
			choice = sc.nextInt();

			switch (choice) {
				case 1:
					ajouter();
					break;
				case 2:
					lister();
					break;
				case 3:
					emprunter();
					break;
				case 4:
					retourner();
					break;
				case 5:
					ouvrages();
					break;
				case 6:
					revues();
					break;
			}
		} while (choice != 0);
	}

	// Afficher le menu
	public static void menu() {
		System.out.println("1: Ajouter des ouvrages\n" +
		"2: Lister les ouvrages\n"+
		"3: Emprunter un ouvrage\n"+
		"4: Retourner un ouvrage\n"+
		"5: Liste des ouvrages tries par nombre d'emprunt\n"+
		"6: Liste des revues\n"+
		"0: Quitter");
	}

	// Ranger des ouvrages
	public static void ajouter() {
		// Ouvrages
		bib.add("I101", new Ouvrage("C", "Kernighan"));
		bib.add("L202", new Ouvrage("Germinal", "Zola"));
		bib.add("S303", new Ouvrage("Parapente", "Ali Gali"));
		bib.add("I345", new Ouvrage("Java", "Eckel"));
		// Revues
		bib.add("I900", new Revue("PPO", "Remi T.", 20190510, "1"));
		bib.add("I901", new Revue("PPO", "Remi T.", 20190516, "2"));
		bib.add("I902", new Revue("PPO", "Remi T.", 20190524, "3"));
		bib.add("I910", new Revue("Gérer des meufs", "Emmanuel L.", 20190523, "1"));
	}

	// Affichage de la bibliotheque
	public static void lister() {
		bib.listing();
	}

	// Affichage de tous les ouvrages
	public static void ouvrages() {
		for (Ouvrage ouvrage : bib.getOuvrages())
			System.out.println(ouvrage.toString());
	}

	// Affichage de toutes les revues
	public static void revues() {
		for (Revue revue : bib.getRevues())
			System.out.println(revue.toString());
	}

	// Emprunter un ouvrage
	public static void emprunter() {
		System.out.println("\nSaisir le code de l'ouvrage à emprunter :");
		String code = sc.next();

		try {
			bib.emprunter(code);
			System.out.println("Ouvrage emprunté.");
		} catch (OuvrageInconnuException e) {
			System.out.println("Ouvrage inconnu...");
		} catch (NonDisponibleException e) {
			System.out.println("Ouvrage non disponible...");
		}
	}

	// Retourner un ouvrage
	public static void retourner() {
		System.out.println("\nSaisir le code de l'ouvrage à retourner :");
		String code = sc.next();

		try {
			bib.retourner(code);
			System.out.println("Ouvrage retourné.");
		} catch (OuvrageInconnuException e) {
			System.out.println("Ouvrage inconnu...");
		}
	}
}