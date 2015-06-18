dataset=misconfig_dataset.xlsx...11.June

View(dataset)

nrow(dataset)

filterdata_contextcode=subset(dataset,dataset$"total.of.context.score.and.commit.change.score" != "#N/A")

totalScoreVector=as.numeric(as.character(filterdata_contextcode$"total.of.context.score.and.commit.change.score"))

#The below will draw the total score histogram

hist(totalScoreVector,main="Considering Context and Code Change Both",xlab="Score", ylab="Frequency")

#########################################################################################
# Code to plot histogram for code change only 
#
#########################################################################################

filterdata_codeonly=subset(filterdata_contextcode,filterdata_contextcode$"Commit.change.score" != "#N/A")

View(filterdata_codeonly)

CodeChangeScoreVector=as.numeric(as.character(filterdata_codeonly$"Commit.change.score"))


histinfo <- hist(CodeChangeScoreVector,main="Considering Code Change Only",xlab="Score", ylab="Frequency",breaks=c(-2,-1,0,1,2))

histinfo <- hist(CodeChangeScoreVector,main="Considering Code Change Only",xlab="Score", ylab="Frequency")

histinfo

#check=filterdata_contextcode[1800:2200,]

#View(check)

#nrow(dataset)

#nrow(filterdata_contextcode)


#########################################################################################
# Code to plot barplot for code change only 
#
#########################################################################################

# Draw the barplot:
barplot(table(CodeChangeScoreVector),main="BarPlot Considering Code Change Only",xlab="Score", ylab="Frequency")

# Draw the barplot:
barplot(table(totalScoreVector),main="BarPlot Considering Context and Code Change Both",xlab="Score", ylab="Frequency")


#View(dataForBarPlot)
#plot(dataForBarPlot$weight,cdc$wtdesire)

#########################################################################################
# Code to plot barplot for types of code change only 
#
#########################################################################################

View(dataset)

filterdata_changetype=subset(dataset,dataset$"total.of.context.score.and.commit.change.score" != "#N/A" & dataset$"Types.of.Code.Changes" != "#N/A")

nrow(filterdata_changetype)

changeTypeVector=as.numeric(as.character(filterdata_changetype$"Types.of.Code.Changes"))

barplot(table(changeTypeVector),main="Frequency Distribution of Each Change Type",xlab="Score", ylab="Frequency")


d <- changeTypeVector
misconfig_vector <- c(6.5,7.5,8.4,9.4,11.4,12,14.4)
misconfig_freq <- d[d %in% misconfig_vector]



barplot(table(misconfig_freq),main="Frequency Distribution of Each Misconfiguration Type",xlab="Misconfiguration Type", ylab="Frequency")




