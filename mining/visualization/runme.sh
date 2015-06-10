while read line
do
	changeType=`echo $line`
        changeCount=`grep $changeType typesOfCodeChanges.txt|wc -l`
	echo "ChangeType:$changeType  and ChangeCount:$changeCount"
	echo "+++++++++++++++++++++++++++++++++++++++++++++"
done <typesOfCodeChanges.txt_sorted

