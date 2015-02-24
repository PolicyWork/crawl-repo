#!/bin/bash
echo $0
echo "+++++++"
filename=$1
echo "About to show all deletions now"

sleep 2

grep "^-.*@" $filename

echo "About to show all code change url links "

sleep 10  

grep "\[CODE-CHANGE_URL\]" $filename|sort -u



