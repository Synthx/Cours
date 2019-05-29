
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProgressionArithmetiqueTest {

	
	@Test
	public void testConstructeur() throws TestException {
		ProgressionArithmetique pa = new ProgressionArithmetique(2, 1);
		try{
			assertEquals (pa.termes.size(), 1);
		}
		catch(AssertionError e){
			throw new TestException("*****Le premier terme doit etre place dans la liste de termes*****");
		}
		catch(NoSuchFieldError e) {
		    throw new TestException("*****La variable d'instance termes doit etre une collection, une ArrayList*****");
	    }
	}
	@Test
	public void testNext() throws TestException {
		ProgressionArithmetique pa = new ProgressionArithmetique(2, 1);
		try{
			assertEquals (pa.termes.size(), 1);
			pa.next();
			assertEquals (pa.termes.size(), 2);
			assertTrue (pa.getTerme() == 3);
		}
		catch(AssertionError e){
			throw new TestException("*****Erreur dans le calcul de next() ou de getTerme()*****");
		}
		catch(NoSuchFieldError e) {
		    throw new TestException("*****La variable d'instance termes doit etre une collection, une ArrayList*****");
	    }
	}
}
