package test;

import lib.NonDisponibleException;
import lib.Ouvrage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OuvrageTest {

    private Ouvrage ouvrage;

    @Before
    public void initData() {
        this.ouvrage = new Ouvrage("titre1", "auteur1");
    }

    @Test
    public void testEmprunterValeurEmprunte() {
        try {
            Assert.assertFalse(ouvrage.emprunte);
            this.ouvrage.emprunter();
            Assert.assertTrue(ouvrage.emprunte);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testEmprunterValeurCompteur() {
        try {
            Assert.assertEquals(0, ouvrage.getCompteur());
            this.ouvrage.emprunter();
            Assert.assertEquals(1, ouvrage.getCompteur());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = NonDisponibleException.class)
    public void testEmprunterExceptionLancee() throws NonDisponibleException {
        this.ouvrage.emprunter();
        this.ouvrage.emprunter();
    }

    @Test
    public void testRetourner() {
        try {
            Assert.assertFalse(this.ouvrage.emprunte);
            this.ouvrage.emprunter();
            Assert.assertTrue(this.ouvrage.emprunte);
            this.ouvrage.retourner();
            Assert.assertFalse(this.ouvrage.emprunte);
        } catch (NonDisponibleException e) {
            Assert.fail();
        }
    }
}
