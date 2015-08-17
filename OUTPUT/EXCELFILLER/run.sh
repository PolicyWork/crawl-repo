echo "Enter the keyword for search"
read keyword

echo "You entered $keyword"
echo "execution will start now...\n\n\n"


cd /home/manish/workspace/crawl-repo/OUTPUT/PREFILTER
cat Output* |grep "CODE-CHANGE-URL"|sort -u|cut -d "]" -f2 > /home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/LinksToRead.txt

perl -pi -e "s/^://g" /home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/LinksToRead.txt





