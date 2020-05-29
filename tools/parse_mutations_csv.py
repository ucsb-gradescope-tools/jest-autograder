import argparse
import os
import csv
import json

# Used in argument parsing for verbose option
def str2bool(v):
    if isinstance(v, bool):
       return v
    if v.lower() in ('yes', 'true', 't', 'y', '1'):
        return True
    elif v.lower() in ('no', 'false', 'f', 'n', '0'):
        return False
    else:
        raise argparse.ArgumentTypeError('Boolean value expected.')

# Initialize command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-p", "--reportpath", help="Directory for pit-reports")
parser.add_argument("--max_points", help="Maximum points for the assignment, divided among the test cases")
parser.add_argument("-o", "--output", help="Json file to output")
parser.add_argument("-v", "--verbose", help="Include specific details for the results of mutation tests versus just showing quantity passed", type=str2bool, nargs='?', const=True, default=False)
args = parser.parse_args()

if args.reportpath != None and args.max_points != None and args.output != None:
    killed = 0
    total = 0
    verbose_log = ""
    specific_folder = [x[0] for x in os.walk(args.reportpath)][1]
    with open(os.path.join(specific_folder, "mutations.csv"), newline='') as csvfile:
        f = csv.reader(csvfile, delimiter=',')
        for r in f:
            total += 1
            if r[5] == "KILLED":
                killed += 1

            # Format the mutator for verbose logging
            mutator = r[2].split('.')
            mutator = mutator[len(mutator)-1]

            # Format class for verbose logging
            classname = r[3]
            if (classname == "<init>"):
                classname = "Constructor"
            else:
                classname += "()"

            verbose_log += "[" + r[0] + "] " + r[5] + "\t\t" + mutator + " on " + classname + "\n"

    score = (killed/total) * float(args.max_points)
    output = "Correctly identified " + str(killed) + " mutants of " + str(total) + " total mutants"
    if args.verbose:
        output += ":\n\n" + verbose_log
    the_test = {
        "name": "Run student test suite against multiple mutant codebases", 
        "max_score": float(args.max_points), 
        "score": round(score, 2),
        "output":output
        }
    with open(args.output, 'w') as outfile:
        json.dump({ "tests" : [ the_test ] }, outfile)

    print("[parse_mutations_csv] Verbose mutation results:")
    print(verbose_log)