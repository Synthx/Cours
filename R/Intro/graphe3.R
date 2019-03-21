x = sample(x = 1:6, prob = rep(1/6, 6), size = 100, replace = TRUE)
bords = seq(0.5, 6.5, 1)

h = hist(x, breaks = bords, plot = TRUE)
print(h)