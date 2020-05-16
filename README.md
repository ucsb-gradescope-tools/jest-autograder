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


## Getting Started
Coming soon