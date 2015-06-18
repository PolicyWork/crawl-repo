dataSet=misconfig_dataset.xlsx.16June

#View(dataSet)

nrow(dataSet)

str(dataSet)

complete.cases(dataSet)

dataSet[dataSet$"Misconfiguration.Category" != "",]

dataSetWithoutBlankRows=dataSet[rowSums(is.na(dataSet)) !=ncol(dataSet),]

nrow(dataSetWithoutBlankRows)

#View(dataSetWithoutBlankRows)

nrow(dataSet)

table(dataSet$Misconfiguration.Category)

filterdata_contextcode=subset(dataSet,dataSet$"Misconfiguration.Category" != "#N/A" & dataSet$"Misconfiguration.Category" != "NA")

nrow(filterdata_contextcode)

#finalMisconfigVector=is.na(filterdata_contextcode$Misconfiguration.Category)

summary(filterdata_contextcode$Misconfiguration.Category)

finalMisconfigVector=filterdata_contextcode$Misconfiguration.Category

finalMisconfigVector=finalMisconfigVector[finalMisconfigVector != ""]

summary(finalMisconfigVector)


unique(finalMisconfigVector)

names(table(finalMisconfigVector))
par(mai=c(2,8,2,3))

#distFromBottom,distanceFromLeft,toCheck,toCheck

#Z <- sample(20,15,replace = TRUE)


summary(finalMisconfigVector)
#barplot(table(finalMisconfigVector),main="Misconfiguration Categories And Their Frequencies",xlab="Frequency",horiz=T,las=1)
barplot(table(finalMisconfigVector),main="Misconfiguration Categories And Their Frequencies",xlab="Frequency",horiz=T,las=1,col = "lightblue")

#barplot(table(finalMisconfigVector),main="Misconfiguration Categories And Their Frequencies",xlab="Frequency",horiz=T,las=1,col = rainbow(14))
#The below will draw the total score histogram
barplot(table(finalMisconfigVector),horiz=T,main="Misconfiguration Categories And Their Frequencies", ylab="Frequency",
        names.arg = c("","Checking for authentication instead of role/permission check or VICEVERSA",
                      "Checking for wrong permissions",
                      "Syntax errors",
                      "Checking for wrong roles and/or conditions",
                      "Missing Security Check",
                      "Checking only authentication and no checks for role/privilege",
                      "Incomplete Security Checks",
                      "Includes unnecessary extra conditions in the annotations",
                      "Checking for incorrect roles",
                      "Adds Unnecessary Security Check",
                      "Wrong usage of parameters",
                      "Change from @PreAuthorize to @PostAuthorize or VICEVERSA",
                      "Not checking object existence before invoking methods on object"))


dotchart(table(finalMisconfigVector),main="Misconfiguration Categories And Their Frequencies",
         xlab="Frequency",ylab="Various Categories")


names.arg = c("Missing Security Check","Checking only authentication and no checks for role/privilege",
              "Checking for incorrect roles",
              "Adds access checks and then blocks everything",
              "Not checking object existence before invoking methods on object",
              "Did not prefix the roles with ROLE_,Change from @PreAuthorize to @PostAuthorize or VICEVERSA",
              "Clash with other software parts",
              "Syntax errors",
              "Wrong usage of parameters",
              "Improper use of regular expressions",
              "Checking for “write” instead of “read”",
              "Uses /**/ instead of specifying all permissible paths",
              "Difficult to test/foresee all possible role requirements for all interfaces/resources",
              "Includes unnecessary extra conditions in the annotations",
              "Checking for authentication instead of role/permission check or VICEVERSA",
              "Checking for wrong permissions",
              "Checking for wrong roles and/or conditions",
              "Incomplete Security Checks",
              "Adds Unnecessary Security Check",
              "Others",
              "NA")

#hist(finalMisconfigVector,main="Considering Context and Code Change Both",xlab="Score", ylab="Frequency")


par(mai=c(2,3,1,1))

dataSetWithoutBlankRows=dataSet[rowSums(is.na(dataSet)) !=ncol(dataSet),]

filterDataContextCode=subset(dataSetWithoutBlankRows,dataSetWithoutBlankRows$"New.Total.Score.Context.score...Change.Score" != "#N/A")

totalScoreVector=as.numeric(as.character(filterDataContextCode$"New.Total.Score.Context.score...Change.Score"))


hist(totalScoreVector,main="Considering Context and Code Change Both",xlab="Score", ylab="Frequency",breaks=c(-5,-4,-3,-2,-1,0,1,2,3,4,5),col="lightblue")

#hist(totalScoreVector,main="Considering Context and Code Change Both",xlab="Score", ylab="Frequency",breaks=8,col="lightblue")

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




