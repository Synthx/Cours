import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;
public class ProgressionGeometriqueTest {

	
	@Test
	public void testConstructeur() throws TestException {
		ProgressionGeometrique pg = new ProgressionGeometrique(2, 1);
		try{
			assertEquals (pg.termes.size(), 1);
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
		ProgressionGeometrique pg = new ProgressionGeometrique(2, 3);
		try{
			assertEquals (pg.termes.size(), 1);
			pg.next();
			assertEquals (pg.termes.size(), 2);
			assertTrue (pg.getTerme() == 6);
		}
		catch(AssertionError e){
			throw new TestException("*****Erreur dans le calcul de next() ou de getTerme()*****");
		}
		catch(NoSuchFieldError e) {
		    throw new TestException("*****La variable d'instance termes doit etre une collection, une ArrayList*****");
	    }
	}
}
