head(iris)

names(iris)

hist(iris$Sepal.Length)

barplot(iris$Sepal.Length)


records <- c('rec1','rec2','rec3','rec4','rec5','rec6','rec7','rec8','rec9','rec10','rec11')

scores <- c(-5,-4,-3,-2,-1,0,-1,-2,-3,-4,5)

data <- data.frame(records,scores)

data

hist(data$scores)

hist(data$scores,cex.axis=0.5, font.main=1, cex.main=0.8)

barplot(data$scores)
hist(data$scores)

names(misconfig_dataset)

misconfig_dataset$"total.of.context.score.and.commit.change.score"

filterdata=subset(misconfig_dataset,misconfig_dataset$"total.of.context.score.and.commit.change.score" != "#N/A")

hist(filterdata$"total.of.context.score.and.commit.change.score")

hist(as.numeric(as.character(filterdata$"total.of.context.score.and.commit.change.score")))


totalScoreVector=as.numeric(as.character(filterdata$"total.of.context.score.and.commit.change.score"))

#The below will draw the total score histogram

hist(totalScoreVector)


CommitChangeVector=filterdata$X

# The below gives the barplot for Code commit change column with name "X"

barplot(CommitChangeVector, main="Frequency Distribution of Categories",xlab="category",ylab="count"
        ,names.arg=CommitChangeVector)


boxplot(CommitChangeVector)

class(filterdata$"total.of.context.score.and.commit.change.score")

hist(as.numeric(filterdata$"total.of.context.score.and.commit.change.score"))

class((filterdata$"total.of.context.score.and.commit.change.score"))