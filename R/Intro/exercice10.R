# Probabilité que dans une classe de N personnes 2 ont la 
# meme date d'anniversaire
f = function(N) {
  res = 1
  
  for(i in 1:N-1) {
    res = res * (1 - i/365)
  }
  
  return(1 - res)
}

# Plus petite valeur de N pour laquelle la probabilité soit > à 1/2
i = 2
while(f(i) < 1/2 && i < 365) {
  i = i + 1
}
print(i)

# Affichage de la courbe
vf = Vectorize(f, 'N')
x = 2:50
y = vf(x)
plot(x, y, type = 'l', col = 'red')