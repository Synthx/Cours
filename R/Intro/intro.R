x = c(-7,84,19)
y = c(x,1,3)
z = c(1:10,x,y)

M = diag(1:8)
N = diag(rep(1,10))

M = matrix(1:8, ncol = 4)
N = matrix(1:8, nrow = 4)
O = matrix(1:8, nrow = 4, byrow = TRUE)

P = cbind(N, O)
Q = rbind(N, 0)

P%*%P
det(P)
eigen(P)
solve(P)
t(P)
