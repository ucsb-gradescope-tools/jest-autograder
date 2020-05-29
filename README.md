# Maven Gradescope Autograder

This is a reimplementation of the UCSB maven autograder using a [more modern Java grading tool](https://github.com/tkutcher/jgrade)

Main idea: Student code is tested by copying their `src/main` directory to an existing maven project (located at `staging/`) with instructor unit tests and running `mvn test` to grade the student's code.

## File structure:
- `staging_main/` - The maven project used to run tests. Student code will be copied into `src/main` and the instructor will configure the `pom.xml` and `src/test` classes
- `staging_test/` - The maven project used to test the student's tests. Student's entire `src` directory will be copied to `staging_test/` (but the instructor will configure the pom.xml). Student's code will be tested with mutations.
- `localautograder/` - `run_autograder` automatically looks here instead of `/autograder/` when the script is run on a dev machine. No configuration is necessary for this to work.
    - `localautograder/submission` = `/autograder/submission`
    - `localautograder/results` = `/autograder/results`
- `tools/` - Contains some useful tools
    - `json_generator.py` - Used as a helper to write Gradescope json files from the `run_autograder` script
    - `parse_mutations_csv.py` - Used to parse the results of mutation testing and write results to Gradescope json
    - `make_autograder` - Zips only the essential autograder files, leaving out any sample solutions or other files

# Basic Configuration
**NOTE**: Make sure to configure the container specifications to the max level in the Gradescope autograder settings

Define the scope of this autograder by editing `grading.config`:

* **CONFIG_TEST_STUDENT_MAIN**=true/false
    * Set to true to test student's implementation against instructor defined test cases
* **CONFIG_TEST_STUDENT_TESTS**=true/false
    * Set to true to test student's test suite against mutations of their own implementation
* **CONFIG_MUTATIONS_MAX_SCORE**=NUM
    * Required if CONFIG_TEST_STUDENT_TESTS=true. The maximum points achievable if a student successfully kills all mutants.


# Configuring Testing For Student src/main With Instructor Tests
Delete the sample project from `/staging_main` and copy a full Java project with a `pom.xml` to the staging folder. Edit the `pom.xml` and add the following dependencies:
```xml
    <!-- START DEPENDENCIES FOR GRADESCOPE AUTOGRADER -->
    <dependency>
        <groupId>com.github.tkutcher</groupId>
        <artifactId>jgrade</artifactId>
        <version>1.0</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/../lib/jgrade.jar</systemPath>
    </dependency>
    <dependency>
        <groupId>org.reflections</groupId>
        <artifactId>reflections</artifactId>
        <version>0.9.12</version>
    </dependency>
    <!-- END DEPENDENCIES FOR GRADESCOPE AUTOGRADER -->
```

Next, copy the `autograder` package from `lib/autograder` to `staging_main/src/test/java/autograder`, i.e. from root of the repo, do:

```
cp -r lib/autograder staging_main/src/test/java 
```

This contains the `GradescopeTestRunner` that will be called to run the tests, as well as the `GradescopeTestClass` annotation that will be used to identify Gradescope tests. Neither of these files need to be touched.

## Writing Graded Tests
Add the `@GradescopeTestClass` annotation to **classes** that should be picked up by the autograder.

Add `@Test` and `@GradedTest` annotations to each **test method**.

Here is a simple example test class:
```java
import autograder.GradescopeTestClass;
import com.github.tkutcher.jgrade.gradedtest.GradedTest;
import org.junit.Test;

@GradescopeTestClass
public class SampleTest {
    @Test
    @GradedTest(name="Some test title", points=100)
    public void testDefaultConstructor() {
        assert(15 == 15);
    }
}
```
Note: the `name=""` field is required, otherwise the test will display on Gradescope as "Unnamed test". This is due to jgrade's implementation, but this is a good idea for a fork of jgrade (or vscode plugin) in the future.

# Configuring Testing For Student src/test
To test the student's test suite, we will use mutation testing. At a high level, this is accomplished by copying their entire `src` submission to `staging_test` and running `mvn org.pitest:pitest-maven:mutationCoverage`.

## Setup the pom.xml
An instructor provided pom is required at `staging_test/pom.xml`. Add the following to the pom.xml:
```XML
<!-- REQUIRED DEPENDENCY FOR MUTATION TESTING -->
<dependency>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-parent</artifactId>
    <version>1.1.10</version>
    <type>pom</type>
</dependency>

<!-- REQUIRED PLUGIN FOR MUTATION TESTING -->
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.1.10</version>
    <configuration>
        <targetClasses>
            <param>edu.*</param>
        </targetClasses>
        <targetTests>
            <param>edu.*</param>
        </targetTests>
        <outputFormats>
            <outputFormat>HTML</outputFormat>
            <outputFormat>CSV</outputFormat>
        </outputFormats>
    </configuration>
</plugin>
```

That's it! Just make sure the `grading.config` has a proper points value assigned and that `CONFIG_TEST_STUDENT_TESTS=true` is set.


# Deployment
Run `tools/make_autograder`. This will remove the `staging_main/src/main` folder, and zip the necessary files to upload to Gradescope. The final `Autograder.zip` file will appear in the root of the directory.
