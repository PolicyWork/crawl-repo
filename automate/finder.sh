#!/bin/bash

echo "Enter the first line"
read line1

echo "Enter the second line"
read line2


#echo "line1=$line1"
#echo "line2=$line2"



#grep -inr --exclude-dir=automate $line1 /home/manish/workspace/crawl-repo/ > output1
#grep -inr --exclude-dir=automate $line2 /home/manish/workspace/crawl-repo/ > output2



while read line
do
     fileName=`echo $line|awk 'BEGIN { FS=":" };{print $1 }'`
     lineNumber=`echo $line|awk 'BEGIN { FS=":" };{print $2 }'output1/line2`
     codeChange1=`echo $line|awk 'BEGIN { FS=":" };{print $3 }'`
     sign1=`echo $codeChange1|awk 'BEGIN { FS=" " };{print $1 }'`

    if [ "$sign1" == "-" ]
    then 
    		echo "++"
    		echo "newLineNumber=$newLineNumber"
    		echo "lineNumber=$lineNumber"

     		let newLineNumber=lineNumber+1 2>/dev/null
     
     		searchPattern=$fileName":"$newLineNumber

     
     		codeChange2=`grep $searchPattern output2|awk 'BEGIN { FS=":" };{print $3 }'`

     		if [ $? -eq 0 ] && [ "$codeChange1" != "$codeChange2" ]
     		then
     	   			#clear;

  				   #echo "codeChange1= $codeChange1"
  				   #echo "codeChange2= $codeChange2"
  			   	   echo -e "\n\n ======================================================================\n"
           		   echo "Check this file - $fileName and line number - $lineNumber"
     	 		   echo -e "\n======================================================================\n"
           		  # exit 0
     		fi

    fi

done<output1

