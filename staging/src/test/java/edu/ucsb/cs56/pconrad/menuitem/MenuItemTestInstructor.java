package edu.ucsb.cs56.pconrad.menuitem;

import static org.junit.Assert.assertEquals;

import autograder.GradescopeTestClass;
import com.github.tkutcher.jgrade.gradedtest.GradedTest;
import org.junit.Test;
import org.junit.Before;

@GradescopeTestClass
public class MenuItemTestInstructor {

    private MenuItem smallPokeBowl;
	private MenuItem banhMi; 
    
    @Before public void setUp() {
		smallPokeBowl = new MenuItem("Small Poke Bowl",1049,"Poke Bowls");
		banhMi = new MenuItem("Banh Mi Sandwich",899,"Sandwiches");
    }
    
    @Test
	@GradedTest(name="test_getName() ", points=5)
    public void test_getName() {
		assertEquals("Small Poke Bowl", smallPokeBowl.getName());
    }

    @Test
	@GradedTest(name="test_getName2() ", points=10)
    public void test_getName2() {
		assertEquals("Banh Mi Sandwich", banhMi.getName());
    }

	
    @Test
    @GradedTest(name="test_getPrice() ", points=5)
	public void test_getPrice()  {
		assertEquals("$10.49",smallPokeBowl.getPrice());
	}

    @Test
    @GradedTest(name="test_getPrice2() ", points=5)
		public void test_getPrice2() {
		assertEquals("$8.99",banhMi.getPrice());
	}

	
	@Test
	@GradedTest(name="test_getPriceInCents() ", points=5)
	public void test_getPriceInCents()  {
		assertEquals(1049,smallPokeBowl.getPriceInCents());
	}

	@Test
	@GradedTest(name="test_getPriceInCents2() ", points=5)
	public void test_getPriceInCents2()  {
		assertEquals(899,banhMi.getPriceInCents());
	}


	@Test
	@GradedTest(name="test_getCategory() ", points=5)
	public void test_getCategory()  {
		assertEquals("Poke Bowls",smallPokeBowl.getCategory());
	}

	@Test
	@GradedTest(name="test_getCategory2() ", points=5)
	public void test_getCategory2()  {
		assertEquals("Sandwiches",banhMi.getCategory());
	}


	@Test
	@GradedTest(name="test_getPrice_10() ", points=5)
	public void test_getPrice_10()  {
		assertEquals("    $10.49",smallPokeBowl.getPrice(10));
	}

	@Test
	@GradedTest(name="test_getPrice_11() ", points=5)
	public void test_getPrice_11()  {
		assertEquals("      $8.99",banhMi.getPrice(11));
	}


	@Test(expected = MenuItem.TooNarrowException.class)
	@GradedTest(name="test_getPrice_0() ", points=10)
	public void test_getPrice_0()  {
		smallPokeBowl.getPrice(0);
	}

	@Test
	@GradedTest(name="test_toString() ", points=5)
	public void test_toString()  {
		assertEquals("Small Poke Bowl,1049,Poke Bowls",smallPokeBowl.toString());
	}

	@Test
	@GradedTest(name="test_toString2() ", points=5)
	public void test_toString2()  {
		assertEquals("Banh Mi Sandwich,899,Sandwiches",banhMi.toString());
	}

}
