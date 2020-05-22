# Maven Gradescope Autograder

This is a reimplementation of the UCSB maven autograder using a [more modern Java grading tool](https://github.com/tkutcher/jgrade)

Main idea: Student code is tested by copying their `src/main` directory to an existing maven project (located at `staging/`) with instructor unit tests and running `mvn test` to grade the student's code.

## File structure:
- `build_error_results.py` - Used as a helper to write error messages to a results.json file from the main `run_autograder` script
- `make_autograder` - Zips only the essential autograder files, leaving out any sample solutions or other files
- `staging/` - The maven project used to run tests. Student code will be copied into `src/main` and the instructor will configure the pom.xml and `src/test` classes
- `localautograder/` - `run_autograder` automatically looks here instead of `/autograder/` when the script is run on a dev machine. No configuration is necessary for this to work.
    - `localautograder/submission` = `/autograder/submission`
    - `localautograder/results` = `/autograder/results`


# Setting Up a Gradescope Maven Project
Delete the sample project from `/staging` and copy a full Java project with a `pom.xml` to the staging folder. Edit the `pom.xml` and add the following dependencies:
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

Next, copy the `autograder` package from `lib/autograder` to `staging/src/test/java/autograder`. This contains the `GradescopeTestRunner` that will be called to run the tests, as well as the `GradescopeTestClass` annotation that will be used to identify Gradescope tests. Neither of these files will need to be edited.

# Writing Graded Tests
Add the `@GradescopeTestClass` annotation to classes that should be picked up by the autograder.

On each individiaul test function, the `@Test` and `@GradedTest` annotations must be used in conjunction.

# Notes
Error message 'Gradescope Json must have either tests or score set':
Check to make sure each test case has the `@Test` and `@GradedTest` annotations. Only using `@GradedTest` is not sufficient.