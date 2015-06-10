
#Remove 0
grep -wv "0" typesOfCodeChanges.txt > newfile

#Remove Column name

grep -vi "type" newfile > typesOfCodeChanges.txt

#Create a sorted file containing unique change types only

sort -u typesOfCodeChanges.txt > typesOfCodeChanges.txt_sorted

#Now get the count of each code change

while read line
do
	changeType=`echo $line`
        changeCount=`grep $changeType typesOfCodeChanges.txt|wc -l`
	echo "ChangeType:$changeType  and ChangeCount:$changeCount"
	echo "+++++++++++++++++++++++++++++++++++++++++++++"
done <typesOfCodeChanges.txt_sorted


while read line
do
	changeType=`echo $line`
        changeCount=`grep $changeType typesOfCodeChanges.txt|wc -l`
	echo "$changeType,$changeCount" >> dataForBarPlot.csv
done <typesOfCodeChanges.txt_sorted


