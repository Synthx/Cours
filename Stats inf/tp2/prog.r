data<-read.csv(file="~/StatInf/tp2/poids.csv",header=FALSE)

##Question 1

#on calcule les moyennes et variances empiriques de toutes nos données

moyemp<-function (ech){
  return (sum(ech)/length(ech))
}

varemp<-function(ech){
  return (sum((ech-moyemp(ech))^2)/length(ech))
}

moytot<-moyemp(data$V1)
vartot<-varemp(data$V1)
##var2<-moyemp((data$V1-moyemp(data$V1))**2)


##Question 2

#On crée une fonction renvoyant une liste de n entiers tirés aléatoirement dans nos données
#Les deux dernières valeurs de la liste correspondent à la moyenne et la variance empirique des n valeurs tirés aléatoirement

liste<-function (ech, n){
  echN<-sample(ech,n)
  moy<-moyemp(echN)
  var<-varemp(echN)
  res<-c(echN,moy,var)
  return (res)
}

ech30<-liste(data$V1,30)
ech60<-liste(data$V1,60)
ech80<-liste(data$V1,80)


##Question 3

#On crée une fonction qui renvoie un intervalle de confiance de niveau alpha de la moyenne lorsque la variance est connu 

IC_var_connu<- function (ech,sigma,alpha){
  borneinf<-ech[length(ech)-1]-sigma/sqrt(length(ech)-2)*qnorm(1-(alpha/2),mean=0,sd=1)
  bornesup<-ech[length(ech)-1]+sigma/sqrt(length(ech))*qnorm(1-(alpha/2),mean=0,sd=1)
  IC<-c(borneinf,bornesup)
  return (IC)
}

IC_var_connu_30<-IC_var_connu(ech30,438.9,0.95)
IC_var_connu_60<-IC_var_connu(ech60,438.9,0.95)
IC_var_connu_80<-IC_var_connu(ech80,438.9,0.95)


##Question 4

#On crée une fonction qui renvoie un intervalle de confiance de niveau alpha de la moyenne lorsque la variance est inconnu
#Ici, on suppose que n est assez grand et que la stat de test suit une loi normale centrée et réduite
 
IC_var_inconnu<- function (ech,alpha){
  borneinf<-ech[length(ech)-1]-sqrt(var(ech))/sqrt(length(ech)-2)*qnorm(1-(alpha/2),mean=0,sd=1)
  bornesup<-ech[length(ech)-1]+sqrt(var(ech))/sqrt(length(ech))*qnorm(1-(alpha/2),mean=0,sd=1)
  IC<-c(borneinf,bornesup)
  return (IC)
}

IC_var_inconnu_30<-IC_var_inconnu(ech30,0.95)
IC_var_inconnu_60<-IC_var_inconnu(ech60,0.95)
IC_var_inconnu_80<-IC_var_inconnu(ech80,0.95)


#On crée une fonction qui renvoie un intervalle de confiance de niveau alpha de la moyenne lorsque la variance est inconnu
#Ici, on suppose que n la stat de test suit une loi de student à n-1 degré de liberté

IC_student<- function(ech,alpha){
  borneinf<-ech[length(ech)-1]-sqrt(var(ech))/sqrt(length(ech)-2)*qt(1-(alpha/2),length(ech)-3)
  bornesup<-ech[length(ech)-1]+sqrt(var(ech))/sqrt(length(ech))*qt(1-(alpha/2),length(ech)-3)
  IC<-c(borneinf,bornesup)
  return (IC)
}

IC_student_30<-IC_student(ech30,0.95)
IC_student_60<-IC_student(ech60,0.95)
IC_student_80<-IC_student(ech80,0.95)

#On effectue le même test avec la fonction t.test

student_Test_30<-t.test(ech30[1:30])
student_Test_60<-t.test(ech60[1:60])
student_Test_80<-t.test(ech80[1:80])

##Question 5

#On crée une fonction qui retourne un intervalle de confiance de niveau alpha sur la proportion de lait défectueux 

IC_prop<-function (p,n,alpha){
  borneinf<-p-sqrt((p*(1-p))/n)*qnorm(1-(alpha/2),mean=0,sd=1)
  bornesup<-p+sqrt((p*(1-p))/n)*qnorm(1-(alpha/2),mean=0,sd=1)
  IC<-c(borneinf,bornesup)
  return (IC)
}

p=20/197
n=197

IC_prop1<-IC_prop(p,n,0.95)
IC_prop2<-binom.test(c(20,177),"two.sided",0.95)
