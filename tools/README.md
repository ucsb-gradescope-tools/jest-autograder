# Tools Documentation
* [json_generator.py](#json_generator.py)
* [parse_mutations.py](#parse_mutations.py)
* [make_autograder](#make_autograder)

# json_generator.py
This python script is a helper to generate informational results.json output in the proper Gradescope format. The ideal use is that several results.json files are generated throughout the autograder's run and then they are combined at the very end. These are created with a score of 0/1 or 0/0 only.

**NOTE**: This script does not write to files. We pipe to a file in shell for that functionality.

Arguments:
* `--title <string>`: Result title
* `--body <string>`: Result body
* `--input <filepath>`: Input file to read as results body instead of --body
* `--passtest`: Grade the test as 0/0 (passing). If not set, the grade will be 0/1
* `--combine <dir>`: Combine all the json files in a specified folder and produce a single combined output

Example usage:
```bash
# Missing directory
python3 json_generator.py --title "Missing src/main" > results4.json

# A test that failed with compiler output
python3 json_generator.py --title "Student code compiles" --input /tmp/compile.out > results5.json

# A test that passed
python3 json_generator.py --title "Student code compiles" --passtest > results6.json

# Combine results at the end of script
python3 json_generator.py --combine /autograder/results > /autograder/results/results.json
```


# parse_mutations.py
This python script is a helper to find and convert the mutation results csv to proper Gradescope json output file.

**NOTE**: This script DOES write to a file (unlike json_generator)

Arguments:
* `--reportpath`: Directory for pit-reports. ex: `test_staging/target/pit-reports`
* `--max_points`: Maximum points for the testing portion of assignment, divided among mutants
* `--output`: Json file to output
* `--verbose`: Include specific details for the results of each mutation. If not set, student will just see mutations killed out of total mutations.

Example usage:
```bash
# Convert mutation results
python3 parse_mutations.py --reportpath /autograder/source/staging_test/target/pit-results --maxpoints 50 --verbose --output results7.json
```


# make_autograder
This script cleans unnecessary files and prepares an `Autograder.zip` to upload to Gradescope. The following files get erased:
* *.DS_Store
* staging_main/src/main/java/*
* staging_main/target/
* staging_test/src/*
* staging_test/target/