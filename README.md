# Jest Autograder

This autograder is intended for use with assignments that use Jest,
a test framework for JavaScript.

It borrows heavily from an [autograder written for Maven
by Cole Bergmann](https://github.com/ucsb-gradescope-tools/maven-autograder), which in turn is based on earlier autograders by Phill Conrad.


# References

* [Jest JSON output documentation](https://jestjs.io/docs/en/configuration#testresultsprocessor-string)


# Quick Start

* Create a new empty private repo for your assignment, e.g. `lab01-AUTOGRADER-PRIVATE`
* Add this repo as a remote, e.g.
  ```
  git remote add starter git@github.com:ucsb-gradescope-tools/jest-autograder.git
  ```
* Pull the  repo into yours: `git pull starter main`
* In `localautograder`, put a sample solution to be checked against the autograder.
  * Put a sample solution (reference implementation) in `localautograder/submission/javascript/src/main`
  * If you also are testing a student test suite, optionally add a sample student test suite under
    `localautograder/submission/javascript/src/test`
* In `staging_main`, put your instructor test suite
  * Tests go into `/staging_main/javascript/src/test` replacing
    the files under `/staging_main/javascript/src/test/course`
  * Copy your `package.json` into `staging_main/javascript/package.json`
* You will likely not need to modify the `grading.config` file in the current
  version, since the defaults are what you will almost always want, but
  there is a reference below in case you do.
* To test the autograder locally:
  ```
  ./run_autograder
  cat localautograder/results/results.json
  ```
  
  At first, there will be no graded tests.  We'll add those in the next step.
* The instructor tests are the ones that live under `staging_main/javascript/src/test`.  So in that directory, visit every jest test in your test suite, and for each test that you want to be graded, add a prefix to the test label which indicates the number of points.
  For example, instead of:
  
  ```javascript
      test('(can create a Course object', () => {
      ...
  ```

  Use:
  ```javascript
      test('(5 pts) can create a Course object', () => {
      ...
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
  
  Usually `true`; unless/until we add mutation testing of student
  test suites, there is no reason to set this to anything else.

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
  Student code will be copied into `javascript/src/main` and the instructor will configure the `javascript/package.json` and `javascript/src/test` classes

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
    - `make_autograder` - Zips only the essential autograder files, leaving out any sample solutions or other files

