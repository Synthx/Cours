# Simulation de 1000 tirages dans N(0,1)
x = rnorm(1000);

# Histogramme
h = hist(x, breaks = 50, freq = F, plot = TRUE)
print(h)

# Densité
par(new = TRUE)
curve(dnorm(x), add = TRUE)

# Extrapolation lineaire
y = cumsum(x)
y2 = y/(1:1000)

par(new = FALSE)
plot(1:1000, y2, type = 'l', col = 'red')