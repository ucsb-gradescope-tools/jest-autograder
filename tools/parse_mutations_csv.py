import argparse
import os
import csv
import json

# Initialize command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-p", "--reportpath", help="Directory for pit-reports")
parser.add_argument("--max_points", help="Maximum points for the assignment, divided among the test cases")
parser.add_argument("-o", "--output", help="Json file to output")
args = parser.parse_args()

if args.reportpath != None and args.max_points != None and args.output != None:
    killed = 0
    total = 0
    specific_folder = [x[0] for x in os.walk(args.reportpath)][1]
    with open(os.path.join(specific_folder, "mutations.csv"), newline='') as csvfile:
        f = csv.reader(csvfile, delimiter=',')
        for r in f:
            total += 1
            if r[5] == "SURVIVED":
                killed += 1

    score = (killed/total) * float(args.max_points)
    the_test = {
        "name": "Run student test suite against multiple mutant codebases", 
        "max_score": float(args.max_points), 
        "score": round(score, 2),
        "output":"Correctly identified " + str(killed) + " mutants of " + str(total) + " total mutants"
        }
    with open(args.output, 'w') as outfile:
        json.dump({ "tests" : [ the_test ] }, outfile)