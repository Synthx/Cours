
import java.lang.reflect.Modifier;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;
public class ProgressionHierarchieTest {
	@Test
	public void testAbstractProgression() throws TestException {
		try{
			assertTrue (Modifier.isAbstract(Progression.class.getModifiers()));
		}
		catch(AssertionError e){
			throw new TestException("*****La classe Progression doit etre abstraite*****");
		}
	}
	
	@Test
	public void testProgressionGeometrique() throws TestException {
		try{
			assertTrue (ProgressionGeometrique.class.getDeclaredMethods().length==1);
			assertTrue (ProgressionGeometrique.class.getDeclaredMethod("next").getReturnType()==void.class);
		}
		catch(AssertionError e){
			throw new TestException("*****Dans la classe ProgressionGeometrique vous ne devez redefinir que next()*****");
		} catch (NoSuchMethodException e) {
			throw new TestException("*****Dans la classe ProgressionGeometrique vous ne devez redefinir que next()*****");
		}
	}
	@Test
	public void testProgressionArithmetique() throws TestException {
		try{
			assertTrue (ProgressionArithmetique.class.getDeclaredMethods().length==1);
			assertTrue (ProgressionArithmetique.class.getDeclaredMethod("next").getReturnType()==void.class);
		}
		catch(AssertionError e){
			throw new TestException("*****Dans la classe ProgressionArithmetique vous ne devez redefinir que next()*****");
		} catch (NoSuchMethodException e) {
			throw new TestException("*****Dans la classe ProgressionArithmetique vous ne devez redefinir que next()*****");
		}
	}
}
