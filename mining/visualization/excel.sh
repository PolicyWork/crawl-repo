while read line
do
	echo $line
	echo "============="
	file $line

	echo "============="
done < testfile 
