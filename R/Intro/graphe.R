x = seq(-pi, pi, 0.1)
y = sin(x^2 - x)
z = cos(x^2 - x)

plot(x, y, type = 'l', col = 'red')

par(new  = TRUE)
plot(x, z, type = 'l', col = 'cyan', main = 'Superposition de deux courbes')