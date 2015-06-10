dataset=misconfig_dataset.xlsx...Round3

View(dataset)

filterdata_contextcode=subset(dataset,dataset$"total.of.context.score.and.commit.change.score" != "#N/A")

totalScoreVector=as.numeric(as.character(filterdata_contextcode$"total.of.context.score.and.commit.change.score"))

#The below will draw the total score histogram

hist(totalScoreVector,main="Considering Context and Code Change Both",xlab="Score", ylab="Frequency")

#########################################################################################
# Code to plot code change only 
#
#########################################################################################

filterdata_codeonly=subset(filterdata_contextcode,filterdata_contextcode$"Commit.change.score" != "#N/A")

View(filterdata_codeonly)

CodeChangeScoreVector=as.numeric(as.character(filterdata_codeonly$"Commit.change.score"))


hist(CodeChangeScoreVector)

#check=filterdata_contextcode[1800:2200,]

#View(check)

#nrow(dataset)

#nrow(filterdata_contextcode)
