
while true
do
	sleep 30m;
	git status;
	git add -A;
	git commit -m "This commit is being made by scheduled job";
	git push origin master;
       echo "commit done at"+`date` >> job.log;
	echo "=================================";
done




