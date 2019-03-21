## Intorduction à R et aux statistiques descriptives

#Calculs
1+1
5/2
5%%2
5%/%2
3*4
sqrt(8)
5^2
log(1)
log10(10)
exp(5)

# Variables
a<-1
b<-5

a+b
a-b
a<b

#Booléens
a<-TRUE
b<-FALSE

a&b
a|b


#Chaines de caractères
a<-"a"
b<-"b"

paste(a,b)
paste(a,b, sep = "-")

#Charger des packages
library(lattice)

#Obtenir de l'aide
#mean("help")

# VECTEURS

numeric(5)
numeric(5)->a
a[3]<-2
a

rep(1,4)
rep(1:3,4)
rep(1:3, each=2)

a<-c(5,3,6,NA)
is.na(a)
!is.na(a)
a[!is.na(a)]
mean(a)
mean(a, na.rm = TRUE)
mean(a[!is.na(a)])
a<-a[!is.na(a)]
min(a)
max(a)
range(a)
which.max(a)
sort(a)
sort(a, decreasing = TRUE)

#Voir une partie et taille
x<-c(3.1, 1, 4, 7, 9)
y<-c(0,1,1,0,0)
x[2:4]
x[x>3]
x[y==1]
length(x)


#Concaténation
z<-c(x,0,x)
z<-y[-(1:5)]
v<-2*x+y+1
z<-0:9


#Conversion caractères/numériques
digits<-as.character(z)
d<-as.numeric(digits)

#Créer des vecteurs
x<-rep(0,1000)
x<-seq(1,500,5)


#Matrices
M=matrix(1, nrow=2, ncol=3)
v=seq(1, 6, 1)
M1=matrix(v,nrow=2, ncol=3, byrow=TRUE)
M2=matrix(v,nrow=2, ncol=3, byrow=FALSE)
I<-diag(1,nrow=4)

#Arrays
x<- array(1:20,dim=c(4,5))
x<- array(1:40,dim=c(4,5,2))
x<- array(1:10,dim=c(4,5,2))

#Nommer les élements d'un vecteur/d'une matrice
fruit<-c(5,10,1,20)
names(fruit)<-c("orange","banane","pomme","pèche")
lunch<-fruit[c("pomme", "orange")]
x<-c(10.4,5.6,3.1,6.4,21.7)
assign("x",c(5,2,3,1,4))

##2. Simulation de variables aléatoires

rbinom(1000,1,1/2)
qnorm(0.95,mean=0,sd=1)
pnorm(1.96,mean=0,sd=1)


##3. Representation graphique et densité
x<-rnorm(1000,5,9)
plot(x, dnorm(x, mean=5, sd=9),ylim=c(0,0.5))

x<-seq(-3,3,0.1)
lines(x, dnorm(x,mean=0,sd=1))


#4 Manipulation de data frame

library(MASS)
Melanoma
df<-Melanoma
str(Melanoma)

df$sex<-factor(df$sex)

summary(df$sex)

df$status<-factor(df$status)

summary(df$status)

df$ulcer<-factor(df$ulcer)

summary(df$ulcer)

df2<-df[df$sex==1,]
df3<-df[df$sex==0,]
df3<-df3[df3$age<50,]
df4<-df[(df$sex==1 & df$age>65) | (df$sex==0 & df$age<45),]
week<-(df$time%/%7)
df<-data.frame(df,week)

new<-c(49,1,0,46,1975,5.5,1,49%/%7)
df<-rbind(df,new)

summary(df)


#5. Statistiques univariées

library(datasets)
data<-mtcars
dim(data)
sum(is.na(data))
data$cyl <- factor(data$cyl)
data$vs <- factor(data$vs)
data$am <- factor(data$am)
data$gear <- factor(data$gear)
data$carb <- factor(data$carb)

summary(data)

boxplot(data[,c("mpg", "hp", "disp", "drat", "wt", "qsec")])
boxplot(data[,c("mpg")])
boxplot(data[,c("hp")])
boxplot(data[,c("disp")])
boxplot(data[,c("drat")])
boxplot(data[,c("qsec")])

# Repeter x fois pas le time
hist(data$mpg)

hist(data$qsec, breaks = 5, freq = FALSE)

# Repeter x fois pas le time
barplot(table(data$vs))

#6. Statistiques bivariées
#Variables quantitatives

data()
str(iris)
boxplot(iris$Sepal.Length~iris$Species)
cov(iris[,c(1:4)])
    
pairs(iris[,c(1:4)],col=rainbow(4))

### Variables Qualitatives

# Chargement des donn�es
titanic <- read.table(file="titanic.txt",header=TRUE,sep=" ")

# Division des graphiques en 2
par(mfrow=c(1,2))

## Croisement Survie / Classe
# Somme de toutes les cases � 1
table <- prop.table(table(titanic$Survie,titanic$Classe))
# Somme par ligne fasse 1
prop.table(table(titanic$Survie,titanic$Classe), margin = 1)
# Somme par colonne fasse 1
prop.table(table(titanic$Survie,titanic$Classe), margin = 2)
# Affichage du tableau
mosaicplot(table)

## Croisement Survie / Sexe
table2 <- prop.table(table(titanic$Survie,titanic$Sexe))
mosaicplot(table2)

# Plut�t des hommes, des personnes de l'�quipage et de la classe 3

# Nombre d'enfants ayant surv�cu au naufrage
length(titanic[titanic$Age=="Enfant" & titanic$Survie=="Oui",1])
# Classe des enfants n'ayant pas surv�cu au naufrage
table(titanic[titanic$Age=="Enfant" & titanic$Survie=="Non",]$Classe)
