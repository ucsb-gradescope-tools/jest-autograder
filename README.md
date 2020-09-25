# Jest Autograder

This autograder is intended for use with assignments that use Jest,
a test framework for JavaScript.

It borrows heavily from an [autograder written for Maven
by Cole Bergmann](https://github.com/ucsb-gradescope-tools/maven-autograder), which in turn is based on earlier autograders by Phill Conrad.


# BELOW THIS LINE, ORIGINAL README TEXT, PROBABLY OUT OF DATE

<hr></hr>

# Quick Start

* Create a new empty private repo for your assignment, e.g. `lab01-AUTOGRADER-PRIVATE`
* Add this repo as a remote, e.g.
  ```
  git remote add starter git@github.com:ucsb-gradescope-tools/maven-autograder.git
  ```
* Pull the  repo into yours: `git pull remote main`
* In `localautograder`, put a sample solution to be checked against the autograder.
  * Put a sample solution (reference implementation) in `localautograder/submission/src/main`
  * If you also are testing a student test suite, optionally add a sample student test suite under
    `localautograder/submission/src/test`
* In `staging_main`, put your instructor test suite
  * Tests go into `staging_main/src/test/java`, replacing `/src/test/java/edu/ucsb/gradescope/example/AnimalConstructorTest.java`
  * The files under `staging_main/src/test/java/autograder` should remain in place unchanged.
  * You may need to change the `staging_main/pom.xml` if you want to change, for example, the
    needed dependencies, version of Java, etc.
* Modify the `grading.config` file according to your preferences (see below.)
* To test the autograder locally:
  ```
  ./run_autograder
  cat localautograder/results/results.json
  ```
* To generate autograder for Gradescope:
  - run `./tools/make_autograder`
  - upload `Autograder.zip` to Gradescope
  - consider adjusting Gradescope settings to maximize memory/CPU if/when doing mutation testing
* To test on Gradescope:
  - create a separate repo with a sample solution (e.g. lab00-SOLUTION-PRIVATE)
  - either submit from GitHub directly, or use the download .zip feature of GitHub and submit that

# Explanation of `grading.config` options
  
* `CONFIG_OUTPUT_PASSING_SANITY_TESTS=true` mean you want the zero point sanity checks to run.

  This computes sanity checks such as `Student code successfully compiles without tests` and includes
  these as "zero point" tests in the student-facing Gradescope output.
  It is almost always a good idea to set this to `true`.

* `CONFIG_TEST_STUDENT_MAIN=true` means you are testing a student main program against instructor defined
  graded tests and `pom.xml` located in `staging_main`.
  
  Usually `true`; set this to `false` *only* when your assignment is *purely* an assignment to test
  a student test suite with mutation tests, and nothing else.

* `CONFIG_TEST_STUDENT_TESTS=true`  Set to true to test student's test suite against mutations
  of their own implementation.  Uses the `pom.xml` under `staging_test`.
  
  Set this to `false` for simple assignments where you are not expecting or are not testing the student
  tests using mutations.
    
* `CONFIG_MUTATIONS_MAX_SCORE=50` The value here is used when `CONFIG_TEST_STUDENT_TESTS=true` and indicates
  the number of points assigned to the mutations testing portion of the grade.   The total points are
  awarded as a fraction of the total mutants killed by the student's test suite.
    
* `CONFIG_MUTATIONS_VERBOSE=true` Output detailed mutant slaying results to Gradescope.
  If `false`, only the number of mutants killed and number of total mutants will be displayed.

  It is likely a good idea to always set this to true when mutation testing
  is being done, however it might be interesting to experiment with results of setting it to false.


# Testing multiple student solutions

It is good practice to test your autograder with multiple student solutions, for example:

* A perfect solution, such an instructor reference solution
* An almost perfect solution, to make sure that test cases catch bugs
* Various malformed solutions, to ensure that the error messages students will see in Gradescope
  will be helpful, and not too confusing.
* A vacuous solution (e.g. an empty solution) to ensure that the intended number of points
  (possibly zero) is awarded.

To enable this, you can pass an optional parameter to the run_autograder command.  This parameter
is the name of a directory to use for a sample solution other than `./localautograder/submission`.

For example, you might set up:

* `./localautograder/submission_flat`, a directory where the files are in the top level directory, rather
  than under /`src/main/java` as they should be.
* `./localautograder/submission_fail_one_test`, a solution that should fail exactly one of the instructor tests

You can then run these by running:
* `./run_autograder ./localautograder/submission_flat`
* `./run_autograder ./localautograder/submission_fail_one_test`

for example.

# Documentation

## File structure:
- `staging_main/` - The maven project used to run **instructor** tests. 
  Student code will be copied into `src/main` and the instructor will configure the `pom.xml` and `src/test` classes
- `staging_test/` - The maven 
  project used to **test the student's tests**. Student's entire `src` directory will be copied to `staging_test/` and the instructor will provide the `pom.xml`. Student's test code will be tested against mutations of the students
own solution.
- `localautograder/` - This folder is used when doing local testing
  of the autograder.

  `run_autograder` automatically looks here instead of `/autograder/` when the script is run on a dev machine. No configuration is necessary for this to work.
    - `localautograder/submission` = `/autograder/submission`
      This folder typically contains a sample solution that you can
      use to test the autograder locally.   Files in this folder are
      not copied into the `Autograder.zip` file.
    - `localautograder/results` = `/autograder/results`
      This folder is where the results are placed when running the 
      autograder locally to test itl.
- `tools/` - Contains some useful tools. Read more at [tools/README.md](tools/README.md)
    - `json_generator.py` - Used as a helper to write Gradescope json files from the `run_autograder` script
    - `parse_mutations_csv.py` - Used to parse the results of mutation testing and write results to Gradescope json
    - `make_autograder` - Zips only the essential autograder files, leaving out any sample solutions or other files

# Basic Configuration

**NOTE**: Make sure to configure the container specifications to the max level in the Gradescope autograder settings


# Configuring Testing For Student src/main With Instructor Tests
At a high level, this is accomplished by copying the student's entire `src/main` directory to `staging_main` with instructor provided `pom.xml` and graded tests located at `src/test`. Finally, `mvn test -Dtest=GradescopeTestRunner` runs to automatically find GradedTestClasses and grade the student code.

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
To test the student's test suite, we will use mutation testing. At a high level, this is accomplished by copying their entire `src` submission to `staging_test` with an instructor provided `pom.xml` and running `mvn org.pitest:pitest-maven:mutationCoverage`.

## Setup the pom.xml
An instructor provided pom is required at `staging_test/pom.xml`. Add the following to the pom.xml:
```XML
<!-- REQUIRED DEPENDENCY FOR MUTATION TESTING -->
<dependency>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-parent</artifactId>
    <version>1.5.2</version>
    <type>pom</type>
</dependency>

<!-- REQUIRED PLUGIN FOR MUTATION TESTING -->
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.5.2</version>
    <configuration>
        <!-- Classes to mutate-->
        <targetClasses>
            <param>edu.*</param>
        </targetClasses>
        <!-- Test classes to mutate instances of above classes -->
        <targetTests>
            <param>edu.*</param>
        </targetTests>
        <outputFormats>
            <outputFormat>HTML</outputFormat>
            <outputFormat>CSV</outputFormat>
        </outputFormats>
        <!-- Define extra time so Spring Boot tests don't time out-->
        <timeoutConstant>30000</timeoutConstant>
    </configuration>
</plugin>
```

That's it! Just make sure the `grading.config` has a proper points value assigned and that `CONFIG_TEST_STUDENT_TESTS=true` is set.


# Deployment
Run `tools/make_autograder`. This will remove the `staging_main/src/main` folder, and zip the necessary files to upload to Gradescope. The final `Autograder.zip` file will appear in the root of the directory.
