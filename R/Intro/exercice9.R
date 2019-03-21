# Simulation de 1000 tirages d'un dé pipé
x = 1:6
p = c(2/15, 2/15, 2/15, 1/3, 2/15, 2/15)
x = sample(x, 1000, replace = TRUE, prob = p)

# Histogramme
h = hist(x, breaks = 6, plot = TRUE, freq = F)
print(h)

# Extrapolation linaire
y = cumsum(x)
y2 = y/(1:1000)

par(new = FALSE)
plot(1:1000, y2, type = 'l', col = 'red')