package test;

import lib.Bibliotheque;
import lib.NonDisponibleException;
import lib.Revue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RevueTest {

    private Revue revue;

    @Before
    public void initData() {
        this.revue = new Revue("titre1", "auteur1", 20190501, "1");
    }

    @Test
    public void testEmprunterAfter1Week() {
        try {
            Bibliotheque.date = 20190531;
            Assert.assertFalse(this.revue.emprunte);
            this.revue.emprunter();
            Assert.assertTrue(this.revue.emprunte);
        } catch (NonDisponibleException e) {
            Assert.fail();
        }
    }

    @Test(expected = NonDisponibleException.class)
    public void testEmprunterBefore1Week() throws NonDisponibleException {
        Bibliotheque.date = 20190503;
        this.revue.emprunter();
    }
}
