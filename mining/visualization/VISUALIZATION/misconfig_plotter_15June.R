dataSet=misconfig_dataset.xlsx.15June

View(dataSet)

nrow(dataSet)

#Remove blank rows

dataSetWithoutBlankRows=dataSet[rowSums(is.na(dataSet)) !=ncol(dataSet),]

nrow(dataSetWithoutBlankRows)

View(dataSetWithoutBlankRows)

filterDataContextCode=subset(dataSetWithoutBlankRows,dataSetWithoutBlankRows$"New.Total.Score.Context.score...Change.Score" != "#N/A")

nrow(filterDataContextCode)

View(filterDataContextCode)

totalScoreVector=as.numeric(as.character(filterDataContextCode$"New.Total.Score.Context.score...Change.Score"))

summary(totalScoreVector)

summary(filterDataContextCode$"New.Total.Score.Context.score...Change.Score")

#The below will draw the total score histogram

hist(totalScoreVector,main="Considering Context and Code Change Both",xlab="Score", ylab="Frequency",breaks=c(-5,-4,-3,-2,-1,0,1,2,3,4,5))












#########################################################################################
# Code to plot histogram for code change only 
#
#########################################################################################


View(filterdata_contextcode)

filterdata_contextcode1=subset(filterdata_contextcode,filterdata_contextcode$"Context.relevance.score.vlookup." != "#N/A" & filterdata_contextcode$"Commit.change.score" != "#N/A")



plot(filterdata_contextcode1$"Context.relevance.score.vlookup.",filterdata_contextcode1$"Commit.change.score")

filterdata_codeonly=subset(filterdata_contextcode,filterdata_contextcode$"Commit.change.score" != "#N/A")

View(filterdata_codeonly)

