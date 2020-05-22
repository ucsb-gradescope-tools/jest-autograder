package autograder;

import com.github.tkutcher.jgrade.Grader;
import com.github.tkutcher.jgrade.gradescope.GradescopeJsonFormatter;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Set;

import org.reflections.Reflections;

/**
 * This is the only class that should be run for the Gradescope autograder. Run it with:
 *          mvn test -Dtest=GradescopeTestRunner
 */
public class GradescopeTestRunner {

    /**
     * The main gradescope test runner
     * Annotated with @Test so it can be discovered by mvn test
     * This function uses reflection to find all classes annotated with @GradescopeTestClass, runs the tests, and
     * saves the results json to whatever file is defined in
     */
    @Test
    public void runGradescopeTests() {
        // Create grader instance
        Grader grader = new Grader();

        // Use reflection to find classes in the package "edu.*" annotated with @GradescopeTestClass
        Reflections reflections = new Reflections("");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(GradescopeTestClass.class);
        for (Class<?> testClass : annotated) {
            grader.runJUnitGradedTests(testClass);
        }


        // Write test results to file
        try {
            String resultFile = System.getProperty("AUTOGRADER_RESULTS", "/autograder/results/results.json");
            PrintStream out = new PrintStream(resultFile);
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