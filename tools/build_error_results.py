#!/usr/bin/env python3

import json
import argparse

# Initialize command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-t", "--title", help="Set result title")
parser.add_argument("-b", "--body", help="Set result body contents")
parser.add_argument("-i", "--input", help="Read file as body contents")

args = parser.parse_args()

if args.body != None:
    the_test = {"name": args.title, "max_score": 1, "score": 0  , "output": args.body}
    json_output_as_dict = { "tests" : [ the_test ] }
    print(json.dumps(json_output_as_dict))
    exit(0)

if args.input != None:
    with open(args.input,'r') as infile:
        f = infile.read()
        # Gradescope truncates files > 100,000 chars and maven outputs are longer
        # The bottom half of the file tends to be more useful, so let's just keep that
        if len(f) > 99990:
            f = f[-99990:]
        the_test = {"name": args.title, "max_score": 1, "score": 0  , "output": f}
        json_output_as_dict = { "tests" : [ the_test ] }
        print(json.dumps(json_output_as_dict))
        exit(0)
