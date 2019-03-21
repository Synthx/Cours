# Traduction sous forme de matrices de l'équation
M = matrix(c(3,2,1,2,3,1,1,2,3), ncol = 3, nrow = 3, byrow = TRUE)
N = matrix(c(5,1,7), ncol = 1, nrow = 3)

# Résolution de l'équation
solve(M,N)