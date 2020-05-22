package edu.ucsb.cs56.ratcalc;

import com.github.tkutcher.jgrade.gradedtest.GradedTest;
import autograder.GradescopeTestClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;


@GradescopeTestClass
public class ExampleTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Test
    @GradedTest(name="greet(String who) works", points=2.0)
    public void greetSomebody() {
        assertEquals("Hello", "Hello");
    }
}