package edu.ucsb.cs56.ratcalc;

import com.github.tkutcher.jgrade.Grader;
import com.github.tkutcher.jgrade.gradescope.GradescopeJsonFormatter;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import edu.ucsb.cs56.ratcalc.controllers.HomeControllerTest;
import edu.ucsb.cs56.ratcalc.controllers.OperationsControllerTest;


public class RunGradescopeTests {
    @Test
    public void runUnitTests() throws Exception {
        Grader grader = new Grader();
        grader.runJUnitGradedTests(ExampleTest.class);
        grader.runJUnitGradedTests(HomeControllerTest.class);
        grader.runJUnitGradedTests(OperationsControllerTest.class);

        // write to file
        try {
            PrintStream out = new PrintStream("/autograder/results/results.json");
            GradescopeJsonFormatter formatter = new GradescopeJsonFormatter();
            out.println(formatter.format(grader));
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}