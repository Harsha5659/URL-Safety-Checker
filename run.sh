#!/usr/bin/env bash
# run.sh - run url-checker in interactive or batch mode
if [ $# -eq 0 ]; then
  java -jar url-checker.jar
else
  java -jar url-checker.jar "$1"
fi
