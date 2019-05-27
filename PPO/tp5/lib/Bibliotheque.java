package lib;

import java.util.*;
import java.util.stream.Collectors;

public class Bibliotheque {

	protected Map<String,Ouvrage> ouvrages = new TreeMap<String,Ouvrage>();
	protected static int date = 20190524;
 
	public void add(String code, Ouvrage o) {
		this.ouvrages.put(code, o);
	}

	public int totalEmprunts() {
		return this.ouvrages.size();
	}

	public void listing() {
		System.out.println("La bibliotheque contient " + this.totalEmprunts() + " ouvrages:");

		for (String code : ouvrages.keySet())
			System.out.println(code + ": " + this.ouvrages.get(code).toString());
	}

	public List<Ouvrage> getOuvrages() {
		return this.ouvrages.values().stream()
			.sorted(Comparator.comparing(Ouvrage::getCompteur).reversed())
			.collect(Collectors.toList());
	}

	public List<Revue> getRevues() {
		return this.ouvrages.values().stream()
			.filter(o -> o instanceof Revue)
			.map(o -> (Revue)o)
			.sorted()
			.collect(Collectors.toList());
	}

	public void emprunter(String code) throws OuvrageInconnuException, NonDisponibleException {
		try {
			this.ouvrages.get(code).emprunter();
		} catch (NullPointerException e) {
			throw new OuvrageInconnuException();
		}
	}

	public void retourner(String code) throws OuvrageInconnuException {
		try {
			this.ouvrages.get(code).retourner();
		} catch (NullPointerException e) {
			throw new OuvrageInconnuException();
		}
	}
}