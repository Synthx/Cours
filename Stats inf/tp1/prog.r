getwd()
data<-read.csv(file="~/StatInf/tp1/fiabilites.csv",header=FALSE)

##Génération des echantillons aléatyoire {X1,...Xn} de taille 30, 50 et 80 provenant des données

ech30<-sample(data$V1,30)
hist(ech30, main="Distribution de plusieurs temps de fonctionnement avant défaillance",
     xlab="Temps avant défaillance", ylab="Effectifs")


ech50<-sample(data$V1,50)
hist(ech50, main="Distribution de plusieurs temps de fonctionnement avant défaillance",
     xlab="Temps avant défaillance", ylab="Effectifs")

ech80<-sample(data$V1,80)
hist(ech80, main="Distribution de plusieurs temps de fonctionnement avant défaillance",
     xlab="Temps avant défaillance", ylab="Effectifs")


#Création de la fonction permettant de calculer la log-vraissemblance pour nos echantillons
LogVraissemblance<-function(parameters,echantillon){
  mu<-parameters[1]
  sigma<-parameters[2]
  n<-length(echantillon)
  logv<- -(n/2)*log(2*pi*(sigma^2)) - sum(log(echantillon)) - (1/(2*sigma^2))*sum((log(echantillon)-mu)^2)
  return (-logv)
}

#Estimation des paramètres mu et siigma à l'aide des fonctions d'optimisation

optim30<-optim(c(1,1),LogVraissemblance,echantillon=ech30)

optim50<-optim(c(1,1),LogVraissemblance,echantillon=ech50)

optim80<-optim(c(1,1),LogVraissemblance,echantillon=ech80)

#Estimation des paramètres mu et sigma de manière analytique



max30<-c(mean(log(ech30)),sd(log(ech30)))

max50<-c(mean(log(ech50)),sd(log(ech50)))

max80<-c(mean(log(ech80)),sd(log(ech80)))


#Calcul des moyennes et variances empiriques

#On définit les fonctions moyemp et varemp permettant de calculer les les moyennes et variance empirique 
#moyemp renvoie exactement la meme valeur que lorsque l'on utilise la fonction mean de R
#varemp renvoie la variance biaisé s² contrairement a la fonction var de R qui renvoie la variance corrigé
moyemp<-function (ech){
  return (sum(ech)/length(ech))
}

varemp<-function(ech){
  return (sum((ech-moyemp(ech))^2)/length(ech))
}

moy30<-moyemp(ech30)
var30<-varemp(ech30)

mu30=log(moy30)-(1/2)*log(1+(var30/moy30^2))
sigma30=sqrt(log(1+(var30/moy30^2)))

moy50<-moyemp(ech50)
var50<-varemp(ech50)

mu50=log(moy50)-(1/2)*log(1+(var50/moy50^2))
sigma50=sqrt(log(1+(var50/moy50^2)))

moy80<-moyemp(ech80)
var80<-varemp(ech80)

mu80=log(moy80)-(1/2)*log(1+(var80/moy80^2))
sigma80=sqrt(log(1+(var80/moy80^2)))
                          