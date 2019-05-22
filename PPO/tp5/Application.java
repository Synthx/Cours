import java.util.Scanner;

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
			}
		} while (choice != 0);
	}

	// Afficher le menu
	public static void menu() {
		System.out.println("1: Ajouter des ouvrages\n" +
		"2: Lister les ouvrages\n"+
		"3: Emprunter un ouvrage\n"+
		"4: Retourner un ouvrage\n"+
		"0: Quitter");
	}

	// Ranger des ouvrages
	public static void ajouter() {
		bib.add("I101", new Ouvrage("C", "Kernighan"));
		bib.add("L202", new Ouvrage("Germinal", "Zola"));
		bib.add("S303", new Ouvrage("Parapente", "Ali Gali"));
		bib.add("I345", new Ouvrage("Java", "Eckel"));
	}

	// Affichage de la bibliotheque
	public static void lister() {
		bib.listing();
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