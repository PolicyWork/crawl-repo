grep NEXT CrawlerOutputWithCommitMessages3.txt|awk -F '&q=' '{print $2}'|awk -F '&' '{print $1}'

grep -ir "NEXT-PAGE-URL" *|awk -F '&q=' '{print $2}'|awk -F '&' '{print $1}'|sort -u > all_inputs.txt

