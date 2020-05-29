import xml.etree.ElementTree as ET
import argparse
import os
import json

# Initialize command line arguments
parser = argparse.ArgumentParser()
parser.add_argument("-p", "--reportpath", help="Directory for pit-reports")
args = parser.parse_args()

if args.reportpath != None:
    specific_folder = [x[0] for x in os.walk(args.reportpath)][1]
    os.chdir(specific_folder)