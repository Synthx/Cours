#===================================================================================
## Exercice 1 - Etude d'un element chimique
#===================================================================================


#===================================================================================
#Question 1 Importation des donnees et etude sommaire
#===================================================================================

#Import des données
echantillon<-read.csv(file="~/StatInf/tpfinal/echantillon.csv",header=TRUE, sep=";",dec=',')


#Etude sommaire des données
#permet d'observer la distribution de la variable valeurs
summary(echantillon$valeurs)

head(echantillon)

#Realisation d'un echantillon de taille 50 aléatoire
ech50<-sample(echantillon$valeurs,50)


#===================================================================================
#Question 2 Calcul d'un estimateur de lambda à partir de la variance empirique
#===================================================================================

#On crée une fonction permettant de calculer la variance empirique (la fonction var de R etant la variance corrigee)
varemp<-function(ech){
  return (sum((ech-mean(ech))^2)/length(ech))
}

#On sait que var(X)=1/lambda²=sigma² pour une loi exponentielle donc lambda = 1/sigma

lambda<-1/sqrt(varemp(ech50))


#===================================================================================
#Question 3 calcul d'un estimateur par la méthode des moments
#===================================================================================


#On prend le moment d'ordre 1 ce qui revient à prendre E(x) c'est a dire la moyenne empirique
m1<-mean(ech50)
#On sait que e(x)=1/lambda donc lambda =1/e(X)
lambda2<-1/m1


#===================================================================================
#Question 4 Calcul d'un estimateur par la méthode du maximum de vraissemblance
#===================================================================================

#On crée une fonction qui calcule la logvraissemblance pour une loi exponentielle
LogVraissemblance_exp<-function(echantillon,lambda){
  logv<-sum(dexp(echantillon,rate=lambda,log=TRUE))
  return (-logv)
}

#Estimation du parmaetre lambda à l'aide des fonctions d'optimisation


optim50<-optim(1,LogVraissemblance_exp,echantillon=ech50)

#===================================================================================
#Question 5 Coomparaison des 3 estimateurs
#===================================================================================


#On remarque que les estimateurs obtenues par la méthode des moments et du maximum du vraissemblance sont très proche
#Tandis que celui obtenu par la variance est légèrement différent


#===================================================================================
#Question 6 Suggestion pour améliorer le calcul des estimateurs
#===================================================================================


#===================================================================================
#Question 7
#===================================================================================

#On crée une fonction qui calcule la log vraissemblance pour une loi gamma
LogVraissemblance_gamma<-function(echantillon,alpha,beta){
  logv<-sum(dgamma(echantillon,shape=alpha,scale=beta, log=TRUE))
  return (-logv)
}

#Estimation du parametre beta à l'aide des fonctions d'optimisation


optimbeta<-optim(1,LogVraissemblance_gamma,echantillon=ech50,alpha=1)

#===================================================================================
##Exercice 2 Securite routiere
#===================================================================================

#===================================================================================
#Partie 1 Vitesse moyenne de l'ensemble des automobilistes
#===================================================================================

#===================================================================================
#Question 1 Importation des donnée et analyse descriptive
#===================================================================================

#Import des données
police<-read.csv(file="~/StatInf/tpfinal/police.csv",header=TRUE, sep=";",dec=",")

#Etude sommaire des données
#permet d'observer les distribution de chaque variable
summary(police)

head(police)

str(police)

#Simplification pour les calculs futurs
Vitesse<-police$Vitesse


#===================================================================================
#Question 2 Test bilatéral sur la moyenne
#===================================================================================


#On crée un nouveau vecteur vitesse de police qui facilitera les calculs en passant d'un vecteur de facteur à un vecteur numérique


#Ici l'hypothèse nulle est Ho: m=m0 et l'hypothes alternative est H1: m!=m0

#Pour affirmer que la vitesse moyenne est differente de 50km/h on créer un intervalle de confiance


#On crée une fonction qui construit un IC de la moyenne 
#grâce une stat de test qui suit une loi de student à n-41 degré de liberté
IC_student<- function(ech,alpha){
  borneinf<-50-sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  bornesup<-50+sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  IC<-c(borneinf,bornesup)
  return (IC)
}
IC_student_1<-IC_student(Vitesse,0.05)
student_Test<-t.test(Vitesse)

#Ici on va conserver la vitesse moyenne appartient à l'IC on ne peut donc pas affirmer que la vitesse moyenne est différente de 50km/h

#On crée une fonction qui calcule la p_value
p_value<-function( ech,sigma){
  stat_test<-mean(ech)/(sigma/sqrt(length(ech)))
  alpha<-2*(1-pnorm(stat_test))
  return (alpha)
}

p_val<-p_value(Vitesse,var(Vitesse))


#Signification excate p-value et alpha


#===================================================================================
#Question 3: Tests unilatéraux sur la moyenne
#===================================================================================


#On crée deux fonction qui crée des Zone de rejet unliatérales à partir de la même statistique de test que précedemment
ZR_student_inf<- function(ech,alpha,m0){
  borneinf<--Inf
  bornesup<-m0-sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  ZR<-c(borneinf,bornesup)
  return (ZR)
}

ZR_student_sup<- function(ech,alpha,m0){
  borneinf<-m0+sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  bornesup<-Inf
  ZR<-c(borneinf,bornesup)
  return (ZR)
}

ZR_inf<-ZR_student_inf(Vitesse,0.05,65)
ZR_sup<-ZR_student_sup(Vitesse,0.05,45)

#On construit les zones de rejet si la moyenne de notre échantillon appartient à ces intervalles alors on peut rejeter le test
mean(Vitesse)
#On rejette donc les deux test, cad la vitesse moyenne des automobilistes est superieur a 45 et inférieur à 65

#Encadrement [48.1 ; 61.9]


#===================================================================================
#Question 4
#===================================================================================


IC_inf_2<-t.test(Vitesse,alternative="less",mu=65)
IC_sup_2<-t.test(Vitesse,alternative="greater",mu=45)

#Les resultats confirment ceux observés précedemment

#===================================================================================
#Question 5
#===================================================================================

IC_student<- function(ech,alpha){
  borneinf<-mean(ech)-sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  bornesup<-mean(ech)+sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  IC<-c(borneinf,bornesup)
  return (IC)
}
IC_student_2<-IC_student(Vitesse,0.05)
student_Test_2<-t.test(Vitesse)


#===================================================================================
##Partie 2 Influence d'automobilistes atypiques sur la vitesse moyenne
#===================================================================================

#===================================================================================
#Question 1
#===================================================================================
police2<-read.csv(file="~/StatInf/tpfinal/police2.csv",header=TRUE, sep=";",dec=",")
Vitesse2<-police2$Vitesse

summary(police2)

#===================================================================================
#Question 2
#===================================================================================
IC_student<- function(ech,alpha){
  borneinf<-mean(ech)-sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  bornesup<-mean(ech)+sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-1)
  IC<-c(borneinf,bornesup)
  return (IC)
}
IC_student_3<-IC_student(Vitesse2,0.05)
student_Test_3<-t.test(Vitesse2)

#===================================================================================
##Partie 3 Quelques resultats
#===================================================================================

#===================================================================================
#Question 1 Intervalles de confaicne a 99% pour la moyenne t l'ecart type de la vitesse selon le sexe du conducteur
#===================================================================================


Vitesse_Homme<-police2$Vitesse[which(police2$Conducteur=="Homme")]
Vitesse_Femme<-police2$Vitesse[which(police2$Conducteur=="Femme")]

IC_moy_h<-IC_student(Vitesse_Homme,0.01)
IC_moy_f<-IC_student(Vitesse_Femme,0.01)


IC_sd<-function(ech,alpha){
  borneinf<-sqrt((length(ech)-1)*var(ech)/qchisq((1-alpha/2),length(ech-1)))
  bornesup<-sqrt((length(ech)-1)*var(ech)/qchisq((alpha/2),length(ech-1)))
  IC<-c(borneinf,bornesup)
  return (IC)
}

IC_sd_Homme<-IC_sd(Vitesse_Homme,0.01)
IC_sd_Femme<-IC_sd(Vitesse_Femme,0.01)


#===================================================================================
#Question 2
#===================================================================================


# la variance relevée diffère légerement les hommes ont une variance un peu plus faible

#===================================================================================
#Question 3
#===================================================================================


# de meme pour la moyenne la différence est minime


#===================================================================================
#Question 4 Estimation de la proportion d'automobilistes qui ne transporte aucun passager entre 9h00 et 9h30
#===================================================================================


p=length(police2$Vitesse[which(police2$Heure_controle=="Entre 9h00 et 9h30" & police2$Nombre_passagers==0)])/length(police2$Vitesse)


#===================================================================================
#Question 5 Test pour savoir si les automobilistes en exces de vitesses ont une distance de freinage moyenne superieur a 12.30
#===================================================================================


Distance_Exces<-police2$Distance_freinage[which(police2$Exces_Vitesse=="Oui")]

ZR_distance_freinage<-ZR_student_sup(Distance_Exces,0.05,12.3)
mean(Distance_Exces)

#Oui car la moyenne empirique de notre echantillon appartient a la zone de rejet


#===================================================================================
#Question 6
#===================================================================================


# a. lien entre le nombre de passagers et la vitesse

plot(police2$Nombre_passagers,police2$Vitesse)

#b. lien entre le nombre de passagers et le moment de la journée

plot(police2$Nombre_passagers,police2$Heure_controle)

#On remarque que la vitesse a tendance à diminuer lorsque le nombre de passagers augmente

#c. lien entre la distance de freinage et la distance de reaction

plot(police2$Distance_Reaction,police2$Distance_freinage)

#Il y a bien un lien entre la distance de reaction et la distance de freinage
#Plus la distance de réaction est grande plus la distance de freinage le sera aussi et inversement


#===================================================================================
#Bonus 
#===================================================================================


#Les policiers ont effectué un contrôle en ville (vitesse limité à 50 km/h)
#Les policiers ont effectué une contrôle proche d'une école,pratiquement pas de passagers entre 9h00 et 9h30
# A l'inverse beacoup de passagers entre 11h30 et 130h30 (heure de sortie et rentrée des ecoles)