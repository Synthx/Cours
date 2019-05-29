package test;

import lib.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BibliothequeTest {

    private Bibliotheque bibliotheque;

    @Before
    public void initData() {
        // Initialisation de la bibliothèque
        this.bibliotheque = new Bibliotheque();

        // Ajout de données dans la bibliothèque
        this.bibliotheque.add("O100", new Ouvrage("titre1", "auteur1"));
        this.bibliotheque.add("O101", new Ouvrage("titre2", "auteur2"));
        this.bibliotheque.add("O102", new Ouvrage("titre3", "auteur1"));
        this.bibliotheque.add("R100", new Revue("titre4", "auteur3", 20190501, "1"));
    }

    @Test
    public void testAdd() {
        Ouvrage ouvrage = new Ouvrage("titre5", "auteur1");
        // Tests
        Assert.assertFalse(this.bibliotheque.ouvrages.isEmpty());
        Assert.assertEquals(4, this.bibliotheque.ouvrages.size());
        this.bibliotheque.add("O103", ouvrage);
        Assert.assertEquals(5, this.bibliotheque.ouvrages.size());
        Assert.assertTrue(this.bibliotheque.ouvrages.keySet().contains("O103"));
        Assert.assertEquals(ouvrage, this.bibliotheque.ouvrages.get("O103"));
    }

    @Test
    public void testTotalEmprunts() throws OuvrageInconnuException, NonDisponibleException {
        this.bibliotheque.emprunter("O100");
        this.bibliotheque.retourner("O100");
        this.bibliotheque.emprunter("O100");
        this.bibliotheque.emprunter("O101");
        this.bibliotheque.emprunter("O102");
        Assert.assertEquals(4, this.bibliotheque.totalEmprunts());
    }

    @Test
    public void testGetOuvrages() {
        List<Ouvrage> ouvrages = this.bibliotheque.getOuvrages();
        // Tests
        Assert.assertFalse(ouvrages.isEmpty());
        Assert.assertEquals(4, ouvrages.size());
    }

    @Test
    public void testGetRevues() {
        List<Revue> revues = this.bibliotheque.getRevues();
        // Tests
        Assert.assertFalse(revues.isEmpty());
        Assert.assertEquals(1, revues.size());
    }

    @Test
    public void testEmprunter() throws OuvrageInconnuException, NonDisponibleException {
        Assert.assertFalse(this.bibliotheque.ouvrages.get("O100").emprunte);
        this.bibliotheque.emprunter("O100");
        Assert.assertTrue(this.bibliotheque.ouvrages.get("O100").emprunte);
    }

    @Test(expected = OuvrageInconnuException.class)
    public void testEmprunterOuvrageInconnu() throws OuvrageInconnuException, NonDisponibleException {
        this.bibliotheque.emprunter("I100");
    }

    @Test
    public void testRetourner() throws OuvrageInconnuException, NonDisponibleException {
        Assert.assertFalse(this.bibliotheque.ouvrages.get("O100").emprunte);
        this.bibliotheque.emprunter("O100");
        Assert.assertTrue(this.bibliotheque.ouvrages.get("O100").emprunte);
        this.bibliotheque.retourner("O100");
        Assert.assertFalse(this.bibliotheque.ouvrages.get("O100").emprunte);
    }

    @Test(expected = OuvrageInconnuException.class)
    public void testRetournerOuvrageInconnu() throws OuvrageInconnuException, NonDisponibleException {
        this.bibliotheque.retourner("I100");
    }
}
