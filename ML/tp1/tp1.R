set.seed(1234)

n <- 100
x <- rnorm(n, 3, 1)

sigma <- c(0.1, 0.5, 2, 6)
y <- matrix(0, ncol = 4, nrow = n)
for (i in 1:ncol(y))
  y[,i] = 1 + 2*x + rnorm(n, 0, sqrt(sigma[i]))

par(mfrow = c(2,2))
for (i in 1:ncol(y)) {
  plot(x, y[,i], xlab = "x", ylab = "y", main = paste("sigma = ", sigma[i]))
  abline(1, 2, col = "red", lwd = 2)
}

res = apply(y, 2, function(z) {
  b1 <- cov(z, x) / var(x)
  b0 <- mean(z) - b1 * mean(x)
  sigma <- 0
  
  return(c(b0, b1, sigma))
})


## do.call, by

# Question 5

d1 = data.frame(x = x, y = y[,1])
m1 = lm(y~x, data = d1)
d2 = data.frame(x = x, y = y[,2])
m2 = lm(y~x, data = d2)
d3 = data.frame(x = x, y = y[,3])
m3 = lm(y~x, data = d3)
d4 = data.frame(x = x, y = y[,4])
m4 = lm(y~x, data = d4)

# Question 6

# normalité
shapiro.test(residuals(m1))
# indépendance
library(lmtest)
dwtest(m1)
# homoscelasticité
bptest(m1)

# Question 7

cor = apply(y, 2, function(z) {
  empi = cor(x, z)
  theo = (cov(x, z) / var(x)) / sqrt(var(z) / var(x))
  
  return(c(empi, theo))
})

# Question 8

x_new = c(2.5, 3.5, 4.5)
y_new = c()
for (i in 1:length(x_new)) {
  y_new = c(y_new, res[1,i] + res[2,i] * x_new[i])
}

d_new = data.frame(x = x_new)
predictions = predict(m3, d_new, interval = 'confidence')
print(predictions)

par(mfrow = c(1,1))
plot(d3$x, d3$y)
abline(m3, col = 'red')
matplot(x_new, predictions, col = c('red', 'blue', 'green'), pch = 16, add = TRUE)