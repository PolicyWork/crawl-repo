#!/bin/bash

#clear;


rm -f SearchOutput.txt
rm -f commitsToCheck.txt

echo "Enter the first line"
read line1

echo "Enter the second line"
read line2


grep -inr --exclude-dir=automate $line1 /home/manish/workspace/crawl-repo/ > output1
grep -inr --exclude-dir=automate $line2 /home/manish/workspace/crawl-repo/ > output2


echo -e "\n============================ OUTPUT : CHECK BELOW FILES  ==========================================\n"  >> SearchOutput.txt

while read line
do
     fileName=`echo $line|awk 'BEGIN { FS=":" };{print $1 }'`
     lineNumber=`echo $line|awk 'BEGIN { FS=":" };{print $2 }'`
     codeChange1=`echo $line|awk 'BEGIN { FS=":" };{print $3 }'`
     sign1=`echo $codeChange1|awk 'BEGIN { FS=" " };{print $1 }'`

    if [ "$sign1" == "-" ]
    then 

     		let newLineNumber=lineNumber+1 
     
     		searchPattern=$fileName":"$newLineNumber

        #echo "searchPattern=$searchPattern"
     
     		codeChange2=`grep $searchPattern output2|awk 'BEGIN { FS=":" };{print $3 }'`
        lineNumber2=`grep $searchPattern output2|awk 'BEGIN { FS=":" };{print $2 }'`


        #codeChange2Search=`echo $codeChange2|sed 's/\"/\\\"/g'` 

        #echo "+++++ codeChange2Search=$codeChange2Search"


        #lineNumber2=`grep -F "$codeChange2" output2|awk 'BEGIN { FS=":" };{print $2 }'|head -1`
        #lineNumber2=`grep -F "$codeChange2" output2|awk 'BEGIN { FS=":" };{print $2 }'`

        #echo "------ lineNumber2=$lineNumber2"

        if [ "$lineNumber2" == "" ] || [ $newLineNumber -ne $lineNumber2 ]
        then
              continue;
        fi



     		if [ "$codeChange1" != "$codeChange2" ] && [ "$codeChange2" != "" ]
     		then
     			        #echo "codeChange1=$codeChange1";
     			   #echo "codeChange2=$codeChange2";

           		   echo -e "\n FILE_NAME: $fileName \t LINE_NUMBER: $lineNumber "  >> SearchOutput.txt

           		   let counter=0
           		   let commitLineNumber=lineNumber+1
           		   
                #awk -v var="$commitLineNumber" 'NR==var' $fileName|grep "CODE.*CHANGE.*URL" >/dev/null
           		   while true 
           		   do
           		   		 awk -v var="$commitLineNumber" 'NR==var' $fileName|grep "CODE.*CHANGE.*URL" >/dev/null

           		   		if [ $? -eq  0 ]
           		   		then
           		   			break ;
           		   		fi
           		   		
           		   		let counter=counter+1
           		   		let commitLineNumber=lineNumber-counter
           		   done

           		   echo `awk -v var="$commitLineNumber" 'NR==var' $fileName` >> commitsToCheck.txt 
           		    #break; 
     		fi

    fi

done<output1

echo "=================================================================================================="

sort -u commitsToCheck.txt

awk 'NR==5' SearchOutput.txt
