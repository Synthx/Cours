import org.junit.Test;

import static org.junit.Assert.*;

public class StringCalculatorTest {

    @Test()
    public void quand2NombresSontUtilisesAucuneException() {
        StringCalculator.add("1,2");
        assertTrue(true);
    }

    /*
    @Test(expected = RuntimeException.class)
    public void quandPlusDe2NombresUneExceptionEstLancee() {
        StringCalculator.add("1,2,3");
    }
     */

    @Test(expected = RuntimeException.class)
    public void quandAutreQueNombreUneExceptionEstLancee() {
        StringCalculator.add("test,2");
    }

    @Test()
    public void quandChaineVideRetourne0() {
        assertEquals(0, StringCalculator.add(""), 0);
    }

    @Test()
    public void quandUnNombreLaValeurDeRetourEstCeNombre() {
        assertEquals(1, StringCalculator.add("1"), 0);
    }

    @Test()
    public void quandDeuxNombresLaValeurDeRetourEstLaSomme() {
        assertEquals(3, StringCalculator.add("1,2"), 0);
        assertEquals(6, StringCalculator.add("1,2, 3"), 0);
    }

    @Test()
    public void quelqueSoitLeNombreDeNombresLaValeurDeRetourEstLaSomme() {
        assertEquals(10, StringCalculator.add("1,2,3,4"), 0);
        assertEquals(15, StringCalculator.add("1,2,3,4,5"), 0);
    }

    @Test()
    public void quandNouvelleLigneEntreNombresLaValeurDeRetourEstLaSomme() {
        assertEquals(10, StringCalculator.add("1\n2,3,4"), 0);
    }

    @Test()
    public void quandUnAutreDelimiteurEstUtilise() {
        assertEquals(10, StringCalculator.add("//;1;2;3;4"), 0);
    }

    @Test()
    public void quandUnNombreNegatifEstUtiliseAvecRetour() {
        try {
            StringCalculator.add("1,2,-3,3,4,-2");
            fail();
        } catch (RuntimeException exception) {
            assertFalse(true);
        }
    }
}
