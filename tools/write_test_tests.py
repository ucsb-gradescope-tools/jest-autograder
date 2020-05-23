import xml.etree.ElementTree as ET
import argparse
import os
import json

# Initialize command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-x", "--xmldir", help="Results folder to search for xml files")
parser.add_argument("-o", "--output", help="Json file to output")
parser.add_argument("-l", "--label", help="Label for test")
parser.add_argument("--reference", help="Input for reference solution")
parser.add_argument("--student", help="Input for student solution")
parser.add_argument("--max_points", help="Maximum points for the assignment, divided among the test cases")
args = parser.parse_args()

# Returns a tupole of (tests, failed)
def get_test_results(folder):
    print("[write_test_tests] Searching for xml in", folder)
    try:
        xml_files = [f for f in os.listdir(folder) if f.endswith('.xml')]
    except:
        print("[write_test_tests] Search directory does not exist")
        print("FAIL")
        exit(1)
    print("[write_test_tests] Found xml files:", xml_files)
    total_tests = 0
    total_fails = 0
    for xml in xml_files:
        tree = ET.parse(os.path.join(folder, xml))
        root = tree.getroot()
        total_tests += int(root.attrib["tests"])
        total_fails += int(root.attrib["failures"])
    return (total_tests, total_fails)
    
# adds a test to a given json file
def save_test_to_file(filepath, label, passed, failed):
    data = {}
    try:
        with open(filepath, "r") as jsonFile:
            data = json.load(jsonFile)
    except:
        print("[write_test_tests]",filepath,"does not exist! It will be created")
    data[label] = (passed > 0 and failed == 0)
    with open(filepath, "w") as jsonFile:
        json.dump(data, jsonFile)
    print("[write_test_tests] Test written to",filepath)

# compare student and instructor files. Returns a tupole (correct, max)
def compare_results(ref_file, student_file):
    print("[write_test_tests] Comparing instructor solution at",args.reference,"with student solution at",args.student)
    try:
        with open(ref_file, "r") as jsonFile:
            ref = json.load(jsonFile)
        with open(student_file, "r") as jsonFile:
            student = json.load(jsonFile)
        # iterate over the reference dict and compare them to student submission
        incorrect_cases = []
        for test_case in ref:
            if test_case not in student:
                print("[write_test_tests] Test case",test_case,"missing from student submission")
                incorrect_cases.append(test_case)
            else:
                if ref[test_case] != student[test_case]:
                    print("[write_test_tests] Mismatch for",test_case,": expected", ref[test_case], "but got", student[test_case])
                    incorrect_cases.append(test_case)
        correct = len(ref) - len(incorrect_cases)
        print("[write_test_tests] Student output matches",correct,"/",len(ref), "test cases")
        return (correct, len(ref))
    except:
        print("[write_test_tests] Unable to load either student or reference test file, exiting")
        exit(1)
    


# Function 1: write a single test to json file
if args.xmldir != None:
    results = get_test_results(args.xmldir)
    print("[write_test_tests] Tests ran:", results[0], "- tests failed:", results[1])

    if args.output != None and args.label != None:
        save_test_to_file(args.output, args.label, results[0], results[1])


# Function 2: 
if args.reference and args.student:
    diff = compare_results(args.reference, args.student)
    
    if args.max_points != None and args.output != None:
        # write the json file based on points
        score = (float(args.max_points) / diff[1]) * diff[0]
        the_test = {
            "name": "Run student test suite against multiple mutant codebases", 
            "max_score": float(args.max_points), 
            "score": score,
            "output":"Correctly identified " + str(diff[0]) + " of " + str(diff[1]) + " test cases"
            }
        with open(args.output, 'w') as outfile:
            json.dump({ "tests" : [ the_test ] }, outfile)
        print("[write_test_tests] Saved gradescope output to",args.output)
        print("[write_test_tests] Score:",score,"/",args.max_points)