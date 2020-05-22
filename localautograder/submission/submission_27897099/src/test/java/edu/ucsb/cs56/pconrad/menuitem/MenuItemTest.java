package edu.ucsb.cs56.pconrad.menuitem;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;



public class MenuItemTest {

    private MenuItem smallPokeBowl;

    @Before
    public void setUp() {
        smallPokeBowl = new MenuItem("Small Poke Bowl", 1049, "Poke Bowls");
    }

    @Test
    public void test_getName(){
	assertEquals("Small Poke Bowl", smallPokeBowl.getName());
    }

    @Test
    public void test_getCategory(){
	assertEquals("Poke Bowls", smallPokeBowl.getCategory());
    }

    @Test
    public void test_getPriceInCents(){
	assertEquals(1049, smallPokeBowl.getPriceInCents());
    }

    @Test
    public void test_getPrice() {
        assertEquals("$10.49", smallPokeBowl.getPrice());
    }

    @Test
    public void test_getPrice_10() {
        assertEquals("    $10.49", smallPokeBowl.getPrice(10));
    }

    @Test
    public void test_getPrice_7() {
        assertEquals(" $10.49", smallPokeBowl.getPrice(7));
    }

    @Test
    public void test_getPrice_6() {
        assertEquals("$10.49", smallPokeBowl.getPrice(6));
    }

    @Test(expected = MenuItem.TooNarrowException.class)
    public void test_getPrice_5() {
        smallPokeBowl.getPrice(5);
    }

    @Test(expected = MenuItem.TooNarrowException.class)
    public void test_getPrice_0() {
        smallPokeBowl.getPrice(0);
    }

    @Test
    public void test_toString() {
        assertEquals("Small Poke Bowl,1049,Poke Bowls", smallPokeBowl.toString());
    }

}
