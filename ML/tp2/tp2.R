data = read.table('http://math.univ-lille1.fr/~preda/GIS3/RL/us_crime.txt', header = TRUE)

head(data, 10)

data$S = as.factor(data$S)

summary(data)

# Variable R~Ed

library(GGally)
ggpairs(data, columns = c(1, 2, 4, 3), aes(color = S, alpha = 0.8))
